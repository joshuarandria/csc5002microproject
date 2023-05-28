/**
This file is part of the course CSC5002.

Copyright (C) 2017-2019 Télécom SudParis

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

import java.io.IOException;

import vlibtour.vlibtour_common.ExampleOfAVisitWithTwoTourists;
import vlibtour.vlibtour_common.Position;

/**
 * A REST client test example using a proxy.
 * <p>
 * This class is not necessary; it is provided with the
 * "{@code mvn exec:java@client}" configuration in order to test the server in
 * command line.
 */
public final class VisitEmulationTestClientWithProxy {
	/**
	 * utility class with no instance.
	 */
	private VisitEmulationTestClientWithProxy() {
	}

	/**
	 * the main method.
	 * 
	 * @param args there is no command line arguments.
	 * @throws IOException communication problem.
	 */
	public static void main(final String[] args) throws IOException {
		VisitEmulationProxy myProxy = new VisitEmulationProxy();

		Position position; 
		for(int i=1; i<48; i++) {
			position = myProxy.getCurrentPosition(ExampleOfAVisitWithTwoTourists.USER_ID_JOE);
			System.out.println("current position of Joe: " + position + "\n\n");
			//System.out.println("Next POI of Joe: " + myProxy.getNextPOIPosition(ExampleOfAVisitWithTwoTourists.USER_ID_JOE) + "\n\n");
			 myProxy.stepsInVisit(ExampleOfAVisitWithTwoTourists.USER_ID_JOE);
			 myProxy.stepInCurrentPath(ExampleOfAVisitWithTwoTourists.USER_ID_JOE);
			
		}
	
	}

}
