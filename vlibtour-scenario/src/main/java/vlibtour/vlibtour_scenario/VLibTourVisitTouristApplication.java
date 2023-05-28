/**
This file is part of the course CSC5002.

Copyright (C) 2017-2020 Télécom SudParis

This is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This software platform is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with the muDEBS platform. If not, see <http://www.gnu.org/licenses/>.

Initial developer(s): Denis Conan
Contributor(s):
 */
package vlibtour.vlibtour_scenario;

import static vlibtour.vlibtour_common.Log.EMULATION;
import static vlibtour.vlibtour_common.Log.LOG_ON;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

import javax.naming.Context;
import javax.naming.NamingException;

import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.tools.jsonrpc.JsonRpcException;

import jakarta.ejb.embeddable.EJBContainer;
import vlibtour.vlibtour_common.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_common.Position;
import vlibtour.vlibtour_group_communication_proxy.VLibTourGroupCommunicationSystemProxy;
import vlibtour.vlibtour_lobby_room_api.InAMQPPartException;
import vlibtour.vlibtour_lobby_room_proxy.VLibTourLobbyRoomProxy;
import vlibtour.vlibtour_scenario.map_viewer.BasicMap;
import vlibtour.vlibtour_scenario.map_viewer.MapHelper;
import vlibtour.vlibtour_tour_management.api.VlibTourTourManagement;
import vlibtour.vlibtour_tour_management.entity.Tour;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;
import vlibtour.vlibtour_visit_emulation_proxy.VisitEmulationProxy;

/**
 * This class is the client application of the tourists. The access to the lobby
 * room server, to the group communication system, and to the visit emulation
 * system should be implemented using the design pattern Delegation (see
 * https://en.wikipedia.org/wiki/Delegation_pattern).
 * <p>
 * A client creates two queues to receive messages from the broker; the binding
 * keys are of the form "{@code *.*.identity}" and "{@code *.*.location}" while
 * the routing keys are of the form "{@code sender.receiver.identity|location}".
 * <p>
 * This class uses the classes
 * {@link vlibtour.vlibtour_scenario.map_viewer.MapHelper} and
 * {@link vlibtour.vlibtour_scenario.map_viewer.BasicMap} for displaying the
 * tourists on the map of Paris. Use the attributes for the color, the map, the
 * map marker dot, etc.
 * 
 * @author Denis Conan
 */
public class VLibTourVisitTouristApplication {
	/**
	 * the colour onto the map of the user identifier of the first tourist.
	 */
	private static final Color COLOR_JOE = Color.RED;
	/**
	 * the colour onto the map of the user identifier of the second tourist.
	 */
	private static final Color COLOR_AVREL = Color.GREEN;
	/**
	 * the map to manipulate. Not all the clients need to have a map; therefore we
	 * use an optional.
	 * <p>
	 * The annotation {@code @SuppressWarnings} is useful as long as you do not use
	 * this attribute.
	 */
	@SuppressWarnings("unused")
	private Optional<BasicMap> map = Optional.empty();
	/**
	 * the dot on the map for the first tourist.
	 * <p>
	 * The annotation {@code @SuppressWarnings} is useful as long as you do not use
	 * this attribute.
	 */

	private static MapMarkerDot mapDotJoe;

	/**
	 * the dot on the map for the second tourist.
	 * <p>
	 * The annotation {@code @SuppressWarnings} is useful as long as you do not use
	 * this attribute. Please remove the annotation when this is so.
	 */

	private static MapMarkerDot mapDotAvrel;
	/**
	 * delegation to the proxy of type
	 * {@link vlibtour.VisitEmulationProxy.VLibTourVisitEmulationProxy}.
	 * <p>
	 * The annotation {@code @SuppressWarnings} is useful as long as you do not use
	 * this attribute. Please remove the annotation when this is so.
	 */

	private VisitEmulationProxy emulationVisitProxy;
	/**
	 * delegation to the proxy of type
	 * {@link vlibtour.vlibtour_lobby_room_proxy.VLibTourLobbyRoomProxy}.
	 * <p>
	 * The annotation {@code @SuppressWarnings} is useful as long as you do not use
	 * this attribute. Please remove the annotation when this is so.
	 */

	private VLibTourLobbyRoomProxy lobbyRoomProxy;
	/**
	 * delegation to the proxy of type
	 * {@link vlibtour.vlibtour_group_communication_proxy.VLibTourGroupCommunicationSystemProxy}.
	 * The identifier of the user is obtained from this reference.
	 * <p>
	 * The annotation {@code @SuppressWarnings} is useful as long as you do not use
	 * this attribute. Please remove the annotation when this is so.
	 */

	private VLibTourGroupCommunicationSystemProxy groupCommProxy;

	private static String url;

	private static Consumer consumer;

	/**
	 * creates a client application, which will join a group that must already
	 * exist. The group identifier is optional when this is the first user of the
	 * group ---i.e. the group identifier is built upon the user identifier.
	 * Concerning the tour identifier, it must be provided by the calling method.
	 * 
	 * @param tourId  the tour identifier of this application.
	 * @param groupId the group identifier of this client application.
	 * @param userId  the user identifier of this client application.
	 * @throws InAMQPPartException             the exception thrown in case of a
	 *                                         problem in the AMQP part.
	 * @throws VlibTourTourManagementException problem in the name or description of
	 *                                         POIs.
	 * @throws IOException                     problem when setting the
	 *                                         communication configuration with the
	 *                                         broker.
	 * @throws TimeoutException                problem in creation of connection,
	 *                                         channel, client before the RPC to the
	 *                                         lobby room.
	 * @throws JsonRpcException                problem in creation of connection,
	 *                                         channel, client before the RPC to the
	 *                                         lobby room.
	 * @throws InterruptedException            thread interrupted in call sleep.
	 * @throws NamingException                 the EJB server has not been found
	 *                                         when getting the tour identifier.
	 * @throws URISyntaxException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public VLibTourVisitTouristApplication(final String tourId, Optional<String> groupId, final String userId)
			throws InAMQPPartException, VlibTourTourManagementException, IOException, JsonRpcException,
			TimeoutException, InterruptedException, NamingException, KeyManagementException, NoSuchAlgorithmException,
			URISyntaxException {
		System.out.println(
				"_______________New instance of VLibTourVisitTouristApplication: " + userId + "_________________");
		emulationVisitProxy = new VisitEmulationProxy();
		if (groupId.isPresent()) {
			lobbyRoomProxy = new VLibTourLobbyRoomProxy(groupId.get(), tourId, userId);
		} else {
			lobbyRoomProxy = new VLibTourLobbyRoomProxy(tourId, userId);
		}

		// CASE CREATE A GROUP
		if (!groupId.isPresent()) {
			// we fetch the groupId that was computed by the constructor
			groupId = Optional.ofNullable(lobbyRoomProxy.getGroupId());
			System.out.println("groupid C1 = " + groupId.get());
			url = lobbyRoomProxy.createGroupAndJoinIt(groupId.get(), userId);
		}
		// CASE JOIN A GROUP
		else {
			System.out.println("groupid C2 = " + groupId.get());
			url = lobbyRoomProxy.joinAGroup(groupId.get(), userId);
		}

		groupCommProxy = new VLibTourGroupCommunicationSystemProxy(url);

		consumer = new DefaultConsumer(groupCommProxy.getChannel()) {
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope,
					final AMQP.BasicProperties properties, final byte[] body) throws IOException {
				String message = new String(body, StandardCharsets.UTF_8);
				System.out.println("[" + userId + "] received '" + envelope.getRoutingKey() + "':'" + message + "'");
				Position position = Position.GSON.fromJson(new String(body, StandardCharsets.UTF_8), Position.class);
				// System.out.println("userId: "+userId);
				String sender = envelope.getRoutingKey().split("\\.")[0];
				// if (sender.equals(userId)) {
					if (sender.equals(ExampleOfAVisitWithTwoTourists.USER_ID_AVREL)) {
						System.out.println("Update position of Avrel on Map");
						MapHelper.moveTouristOnMap(mapDotAvrel, position);
					} else {
						System.out.println("Update position of Joe on Map");
						MapHelper.moveTouristOnMap(mapDotJoe, position);
					}
				// }
			}
		};

		groupCommProxy.setConsumer(consumer);

	}

	/**
	 * executes the tourist application.
	 * <p>
	 * We prefer inserting comments in the method instead of detailing the method
	 * here.
	 * 
	 * @param args the command line arguments.
	 * @throws Exception all the potential problems (since this is a demonstrator,
	 *                   apply the strategy "fail fast").
	 */
	public static void main(final String[] args) throws Exception {
		System.out.println("\nVLibTourVisitTouristeApplication" + args.toString() + "\n\n");
		@SuppressWarnings("unused")
		String usage = "USAGE: " + VLibTourVisitTouristApplication.class.getCanonicalName()
				+ " userId (either Joe or Avrel)" + "tourID (The unusual Paris)" + "optional: groupId";
		// if (args.length != 2 || args.length != 3) {
		// throw new IllegalArgumentException(usage);
		// }

		VLibTourVisitTouristApplication client;

		// ################ GETTING ARGS ###################
		String userId = args[0];
		// System.out.println("userId = " + userId);
		String tourId = args[1];
		// System.out.println("tourId = " + tourId);
		Optional<String> groupId = Optional.empty();
		if (args.length == 3) {
			groupId = Optional.ofNullable(args[2]);
		}

		client = new VLibTourVisitTouristApplication(tourId, groupId, userId);

		// if (LOG_ON && EMULATION.isInfoEnabled()) {
		// EMULATION.info(userId + "'s application is sConsumertarting");
		// }

		// ################ MAP ###################
		// Set the map viewer of the scenario (if this is this client application that
		// has created the group [see #VLibTourVisitTouristApplication(...)])
		// The following code should be completed.
		// FIXMEclient.groupCommProxy.setConsumer(consumer);
		if (LOG_ON && EMULATION.isDebugEnabled()) {
			EMULATION.debug("Current directory = " + System.getProperty("user.dir") + ".\n" + "We assume that class "
					+ client.getClass().getCanonicalName() + " is launched from directory "
					+ "./vlibtour-scenario/src/main/resources/osm-mapnik/");
		}
		client.map = Optional.of(MapHelper.createMapWithCenterAndZoomLevel(48.851412, 2.343166, 14));
		Font font = new Font("name", Font.BOLD, 20);
		client.map.ifPresent(m -> {
			MapHelper.addMarkerDotOnMap(m, 48.871799, 2.342355, Color.BLACK, font, "Musée Grevin");
			MapHelper.addMarkerDotOnMap(m, 48.860959, 2.335757, Color.BLACK, font, "Pyramide du Louvre");
			MapHelper.addMarkerDotOnMap(m, 48.833566, 2.332416, Color.BLACK, font, "Les catacombes");

			// all the tourists start at the same position
			System.out.print("add " + userId + " on Map \n");

			Position positionOfJoe = client.emulationVisitProxy
					.getCurrentPosition(ExampleOfAVisitWithTwoTourists.USER_ID_JOE);
			mapDotJoe = MapHelper.addTouristOnMap(m, COLOR_JOE, font, ExampleOfAVisitWithTwoTourists.USER_ID_JOE,
					positionOfJoe);

			Position positionOfAvrel = client.emulationVisitProxy
					.getCurrentPosition(ExampleOfAVisitWithTwoTourists.USER_ID_AVREL);
			mapDotAvrel = MapHelper.addTouristOnMap(m, COLOR_AVREL, font,
					ExampleOfAVisitWithTwoTourists.USER_ID_AVREL, positionOfAvrel);

			client.map.get().repaint();

			// wait for painting the map
			try {
				final long timeout = 1000;
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		// ################ GROUP COMM ###################

		try {

			client.groupCommProxy.startConsumption();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.print("\n\n - - - - - - - - - - - - - - - - - - startLoop - - - - - - - - - - - - - - - \n");
		Position positionOfClient = client.emulationVisitProxy.getCurrentPosition(userId);
		client.emulationVisitProxy.stepsInVisit(userId);
		Position nextPositionOfClient = client.emulationVisitProxy.stepInCurrentPath(userId);
		client.emulationVisitProxy.stepInCurrentPath(userId);

		while (!(positionOfClient.equals(nextPositionOfClient))) {
			System.out.print("\n");
			// Step in the current path.
			positionOfClient = nextPositionOfClient;
			System.out.print("currentPosition of " + userId + ": " + positionOfClient + "\n");
			client.emulationVisitProxy.stepsInVisit(userId);
			nextPositionOfClient = client.emulationVisitProxy.stepInCurrentPath(userId);
			client.map.get().repaint();

			Thread.sleep(3000);
			if (userId.equals(ExampleOfAVisitWithTwoTourists.USER_ID_JOE)) {
				MapHelper.moveTouristOnMap(mapDotJoe, nextPositionOfClient);
			} else {
				MapHelper.moveTouristOnMap(mapDotAvrel, nextPositionOfClient);
			}
			// share Position
			System.out.println(userId + " publishes his position");
			String jsonPos = Position.GSON.toJson(positionOfClient, Position.class);
			// System.out.println("json pos = " + jsonPos);
			client.groupCommProxy.publish(userId + ".all.#", jsonPos);
			Thread.sleep(2500);
			client.map.get().repaint();
			// wait for painting the map
			try {
				final long timeout = 500;
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.print("- - - - - - - - - - - - - - - - - - - endLoop - - - - - - - - - - - - - - - \n");

		// Close the channel and the connection.
		client.groupCommProxy.close();
		System.out.println("fin tour app " + userId);
		System.exit(0);
	}
}
