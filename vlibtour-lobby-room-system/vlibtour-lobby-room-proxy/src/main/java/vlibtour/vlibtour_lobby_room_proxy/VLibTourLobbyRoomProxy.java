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
package vlibtour.vlibtour_lobby_room_proxy;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.tools.jsonrpc.JsonRpcClient;
import com.rabbitmq.tools.jsonrpc.JsonRpcException;

import vlibtour.vlibtour_lobby_room_api.VLibTourLobbyService;

/**
 * The AMQP/RabbitMQ Proxy (for clients) of the Lobby Room Server.
 * 
 * @author Denis Conan
 */

public class VLibTourLobbyRoomProxy {
	/**
	 * the connection to the RabbitMQ broker.
	 */
	private Connection connection;
	/**
	 * the channel for producing and consuming.
	 */
	private Channel channel;
	/**
	 * the RabbitMQ JSON RPC client.
	 */
	private JsonRpcClient jsonRpcClient;
	/**
	 * the APi.
	 */
	private VLibTourLobbyService client;
	/**
	 * the name of the queue for the RPC.
	 */
	public static final String RPC_QUEUE_NAME = "rpc_queue";

	private String groupId;
	private String userId;
	private String tourId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

//	public void routine() throws IOException, TimeoutException, JsonRpcException, InterruptedException {
//		
//	}

	/**
	 * constructor 1
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public VLibTourLobbyRoomProxy(String tourId, String userId)
			throws IOException, TimeoutException, JsonRpcException, InterruptedException {
//		routine();
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		jsonRpcClient = new JsonRpcClient(channel, VLibTourLobbyService.EXCHANGE_NAME,
				VLibTourLobbyService.BINDING_KEY);
		client = jsonRpcClient.createProxy(VLibTourLobbyService.class);
		// creating groupId
		this.groupId = tourId + VLibTourLobbyService.GROUP_TOUR_USER_DELIMITER + userId;
		this.tourId = tourId;
		this.userId = userId;
		
	}

	/**
	 * constructor 2 
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public VLibTourLobbyRoomProxy(String groupId, String tourId, String userId)
			throws IOException, TimeoutException, JsonRpcException, InterruptedException {
//		routine();
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		connection = factory.newConnection();
		channel = connection.createChannel();
		jsonRpcClient = new JsonRpcClient(channel, VLibTourLobbyService.EXCHANGE_NAME,
				VLibTourLobbyService.BINDING_KEY);
		client = jsonRpcClient.createProxy(VLibTourLobbyService.class);
		
		this.tourId = tourId;
		this.userId = userId;
		
	}

	/**
	 * createGroupAndJoinIt()
	 * 
	 * 
	 */
	public String createGroupAndJoinIt(final String groupId, final String userId) {
		System.out.println("Creating group and Requesting url for "+userId);
		String url = client.createGroupAndJoinIt(groupId, userId);
		System.out.println(" ["+userId+"] Got '" + url + "'");
		return url;
	}

	/**
	 * joinAGroup()
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String joinAGroup(final String groupId, final String userId) throws InterruptedException, IOException {
		System.out.println("Joining group and Requesting url for "+userId);
		String url = client.joinAGroup(groupId, userId);
		System.out.println(" ["+userId+"] Got '" + url + "'");
		return url;
	}

	public void close() throws IOException, TimeoutException {
		jsonRpcClient.close();
		if (channel != null) {
			channel.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}
