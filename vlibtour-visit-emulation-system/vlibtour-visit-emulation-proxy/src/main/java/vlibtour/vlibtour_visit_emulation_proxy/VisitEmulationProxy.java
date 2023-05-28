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

Initial developer(s): Chantal Taconet and Denis Conan
Contributor(s):
 */
package vlibtour.vlibtour_visit_emulation_proxy;

import java.io.FileInputStream;
import java.net.URI;
import java.util.Properties;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriBuilder;
import vlibtour.vlibtour_common.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_common.Position;

/**
 * The REST Proxy of the VLibTour Visit Emulation Server.
 */
public final class VisitEmulationProxy implements VisitEmulationInterface {

	private class VisitEmulationProxyClass implements VisitEmulationInterface {

		Client client = ClientBuilder.newClient();
		URI uri = UriBuilder.fromUri(ExampleOfAVisitWithTwoTourists.BASE_URI_WEB_SERVER).build();
		WebTarget service = client.target(uri);

		@Override
		public Position getCurrentPosition(String user) {

			Position position = service.path("visitemulation/getCurrentPosition/" + user).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}

		@Override
		public Position getNextPOIPosition(String user) {

			Position position = service.path("visitemulation/getNextPOIPosition/" + user).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}

		@Override
		public Position stepInCurrentPath(String user) {

			Position position = service.path("visitemulation/stepInCurrentPath/" + user).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}

		@Override
		public Position stepsInVisit(String user) {
			Position position = service.path("visitemulation/stepsInVisit/" + user).request()
					.accept(MediaType.APPLICATION_JSON).get().readEntity(Position.class);
			return position;
		}
	}

	/**
	 * Field myProxy.
	 */
	private VisitEmulationInterface myProxy;

	/**
	 * Constructor.
	 */
	public VisitEmulationProxy() {
		this.myProxy = new VisitEmulationProxyClass();
	}

	@Override
	public Position getCurrentPosition(String user) {
		return myProxy.getCurrentPosition(user);
	}

	@Override
	public Position getNextPOIPosition(String user) {
		return myProxy.getNextPOIPosition(user);
	}

	@Override
	public Position stepInCurrentPath(String user) {
		return myProxy.stepInCurrentPath(user);
	}

	@Override
	public Position stepsInVisit(String user) {
		return myProxy.stepsInVisit(user);
	}

}
