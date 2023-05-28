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
package vlibtour.vlibtour_tour_management_admin_client;

import javax.naming.InitialContext;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.spi.Context;
import vlibtour.vlibtour_tour_management.api.*;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;
import vlibtour.vlibtour_tour_management.entity.Tour;

/**
 * This class defines the administration client of the Tour Management System.
 * <ul>
 * <li>USAGE:
 * <ul>
 * <li>vlibtour.vlibtour_tour_management_admin_client.VlibTourAdminClient
 * populate toursAndPOIs</li>
 * <li>vlibtour.vlibtour_tour_management_admin_client.VlibTourAdminClient empty
 * toursAndPOIs</li>
 * </ul>
 * </li>
 * </ul>
 *
 * @author Denis Conan
 */
public final class VlibTourTourManagementAdminClient {
	/**
	 * constructs an instance of the administration client.
	 *
	 * @throws Exception the exception thrown by the lookup.
	 */
	private VlibTourTourManagementAdminClient() throws Exception {

	}

	@EJB

	/**
	 * the main of the administration client.
	 *
	 * @param args the command line arguments. See class documentation of this
	 *             class.
	 * @throws Exception the exception that can be thrown (none is treated).
	 */
	public static void main(final String[] args) throws Exception {
	}

}
