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
package vlibtour.vlibtour_tour_management.bean;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import jakarta.ejb.embeddable.EJBContainer;
import vlibtour.vlibtour_tour_management.api.VlibTourTourManagement;
import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;
import vlibtour.vlibtour_tour_management.entity.VlibTourTourManagementException;

public class TestVlibTourTourManagementBean {
	private static VlibTourTourManagement myVlib;
	private static Context myContext;
	private static EJBContainer ec;

	// @Ignore
	// @BeforeClass
	// public static void setUpClass() throws Exception {
	// 	System.out.println("##### SETUp ######");
	// 	Map<String, Object> properties = new HashMap<String, Object>();
	// 	properties.put(EJBContainer.MODULES, new File("target/classes"));
	// 	ec = EJBContainer.createEJBContainer(properties);
	// 	myContext = ec.getContext();
	// 	myVlib = (VlibTourTourManagement) myContext
	// 			.lookup("vlibtour.vlibtour_tour_management.api.VlibTourTourManagement");
	// 	System.out.println("##### SETUp OK ######");
	// }

	// @Ignore
	// @Test(expected = VlibTourTourManagementException.class)
	// public void createPOITest1() throws Exception {
	// 	System.out.println("##### CREATING POI TEST 1 ######");
	// 	POI poi1 = new POI("1", "poi_1", "first poi", 44, 56);
	// 	myVlib.createPOI(poi1);
	// 	Assert.assertTrue(myVlib.getPOI("poi_1") == poi1);
	// 	System.out.println("##### CREATING POI TEST 1 OK ######");

	// }

	// // Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	// @Test(expected = VlibTourTourManagementException.class)
	// public void findPOIWithPIDTest1() throws Exception {
	// }

	// // Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	// @Test
	// public void findAllPOIsWithNameTest1() throws Exception {
	// 	System.out.println("##### FINDING POIs TEST 1 ######");
	// 	POI poi1 = new POI("1", "name_ok", "first poi", 44, 56);
	// 	POI poi2 = new POI("2", "name_ok", "2 poi", 45, 52);
	// 	POI poi3 = new POI("3", "name_nok", "3 poi", 40, 36);
	// 	POI poi4 = new POI("4", "name_ok", "4 poi", 40, 36);

	// 	myVlib.createPOI(poi1);
	// 	myVlib.createPOI(poi2);
	// 	myVlib.createPOI(poi3);
	// 	myVlib.createPOI(poi4);

	// 	List<POI> listPois = myVlib.getPOI("name_ok");

	// 	Assert.assertTrue(listPois.size() == 3);
	// 	System.out.println("##### FINDING POIs TEST 1 OK ######");

	// }

	// // Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	// @Test
	// public void findAllPOIsTest1() throws Exception {
	// }

	// // Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	// @Test(expected = VlibTourTourManagementException.class)
	// public void createTourTest1() throws Exception {
	// 	System.out.println("##### CREATING TOUR TEST 1 ######");
	// 	Tour tour1 = new Tour("tid1", "tour1", "tour paris", null);
	// 	myVlib.createTour(tour1);
	// 	Assert.assertTrue(myVlib.getTour("tour1") == tour1);
	// 	System.out.println("##### CREATING TOUR TEST 1 OK ######");
	// }

	// // Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	// @Test(expected = VlibTourTourManagementException.class)
	// public void findTourWithTIDTest1() throws Exception {
	// }

	// // Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	// @Test
	// public void findAllToursWithNameTest1() throws Exception {
	// 	System.out.println("##### FINDING TOURS TEST 1 ######");
	// 	Tour tour1 = new Tour("tid1", "tour", "tour paris", null);
	// 	Tour tour2 = new Tour("tid2", "tour1", "tour paris", null);
	// 	Tour tour3 = new Tour("tid3", "tour1", "tour paris", null);
	// 	Tour tour4 = new Tour("tid4", "tour1", "tour paris", null);

	// 	myVlib.createTour(tour1);
	// 	myVlib.createTour(tour2);
	// 	myVlib.createTour(tour3);
	// 	myVlib.createTour(tour4);

	// 	List<Tour> listTours = myVlib.getTour("tour1");

	// 	Assert.assertTrue(listTours.size() == 3);
	// 	System.out.println("##### FINDING TOURS TEST 1 OK ######");

	// }

	// // Be careful! Remove annotation @Ignore for executing this test.
	// @Ignore
	// @Test
	// public void findAllToursTest1() throws Exception {
	// 	System.out.println("##### FINDING TOURS TEST 2  ######");
	// 	Tour tour1 = new Tour("tid1", "tour", "tour paris", null);
	// 	Tour tour2 = new Tour("tid2", "tour1", "tour paris", null);
	// 	Tour tour3 = new Tour("tid3", "tour1", "tour paris", null);
	// 	Tour tour4 = new Tour("tid4", "tour1", "tour paris", null);

	// 	myVlib.createTour(tour1);
	// 	myVlib.createTour(tour2);
	// 	myVlib.createTour(tour3);
	// 	myVlib.createTour(tour4);

	// 	List<Tour> listTours = myVlib.listTours();

	// 	Assert.assertTrue(listTours.size() == 3);
	// 	System.out.println("##### FINDING TOURS TEST 2 OK ######");

	// }

	// @Ignore
	// @After
	// public void tearDown() throws Exception {
	// }

	// @Ignore
	// @AfterClass
	// public static void tearDownClass() throws Exception {
	// }

}
