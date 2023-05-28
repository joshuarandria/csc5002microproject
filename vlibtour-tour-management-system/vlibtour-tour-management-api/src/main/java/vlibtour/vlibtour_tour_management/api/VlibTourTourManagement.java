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
package vlibtour.vlibtour_tour_management.api;

import java.util.Collection;
import java.util.SortedSet;

import java.util.List;

import jakarta.ejb.Remote;
import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;

/**
 * This interface defines the operation for managing POIs and Tours. This is the
 * interface of the VLibTour Tour Management System.
 *
 * @author Denis Conan
 */

@Remote
public interface VlibTourTourManagement {

	/**
	 * gets a detached instance of a Tour.
	 * 
	 * @param name the name of the Tour to search for.
	 * @return tour.
	 */
	Collection<Tour> getTour(String name);

	/**
	 * gets a detached instance of a poi.
	 * 
	 * @param name the name of the POI to search for.
	 * @return poi
	 */
	Collection<POI> getPOI(String name);

	/**
	 * create a tour.
	 * 
	 * @param id
	 * @param name
	 * @param desc
	 * 
	 * @return tour created
	 */
	Tour createTour(String id, String name, String desc);

	/**
	 * create a poi.
	 * 
	 * @param id
	 * @param name
	 * @param desc
	 * @param longitude
	 * @param latitude
	 * @return poi created
	 */
	POI createPOI(String id, String name, String desc, double longitude, double latitude);

	/**
	 * link poi to tour.
	 * 
	 * @param poi 
	 * @param tour
	 * @return 1 if ok -1 if failed;
	 */
	int addPOIToTour(POI poi, Tour tour);

	/**
	 * remove poi to tour.
	 * 
	 * @param poi 
	 * @param tour
	 * @return 1 if ok -1 if failed;
	 */
	int removePOIToTour(POI poi, Tour tour);

	/**
	 * remove poi .
	 * 
	 * @param poi
	 * @return 1 if ok -1 if failed;
	 */
	int removePOI(POI poi);

	/**
	 * lists tours.
	 * 
	 * @return a list of tours;
	 */
	List<Tour> listTours();

}
