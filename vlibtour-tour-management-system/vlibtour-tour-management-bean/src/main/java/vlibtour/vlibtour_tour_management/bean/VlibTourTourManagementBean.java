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

import java.util.Collection;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import vlibtour.vlibtour_tour_management.api.VlibTourTourManagement;
import vlibtour.vlibtour_tour_management.entity.POI;
import vlibtour.vlibtour_tour_management.entity.Tour;

/**
 * This class defines the EJB Bean of the VLibTour tour management system.
 *
 * @author Denis Conan
 */
@Stateless
public class VlibTourTourManagementBean implements VlibTourTourManagement {

	/**
	 * the reference to the entity manager, which persistence context is "pu1".
	 */
	@PersistenceContext(unitName = "vlibtour")
	private EntityManager em;

	@Override
	public Tour createTour(String id, String name, String desc) {
		Tour tour=new Tour();
		tour.setTid(id);
		tour.setName(name);
		tour.setDescription(desc);
		em.persist(tour);
		return tour;
	}

	@Override
	public POI createPOI(String id, String name, String desc, double longitude, double latitude) {
		POI poi= new POI();
		poi.setPoid(id);
		poi.setName(name);
		poi.setDescription(desc);
		poi.setLongitude(longitude);
		poi.setLatitude(latitude);
		em.persist(poi);
		return poi;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Tour> getTour(final String name) {
		Query q = em.createQuery("select t from Tour t where t.name = :name");
		q.setParameter("name", name);
		return (Collection<Tour>) q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<POI> getPOI(final String name) {
		Query q = em.createQuery("select poi from POI poi where poi.name = :name");
		q.setParameter("name", name);
		return (Collection<POI>) q.getResultList();
	}

	@Override
	public int addPOIToTour(POI poi, Tour tour) {
		if (tour == null || poi == null) {
			return -1;
		}
		tour.addPOI(poi);
		em.persist(tour);
		return 1;
	}

	@Override
	public int removePOIToTour(POI poi, Tour tour) {
		if (tour == null || poi == null) {
			return -1;
		}
		tour.remove(poi);
		em.persist(tour);
		return 1;
	}

	@Override
	public int removePOI(POI poi) {
		if (poi == null || !em.contains(poi)) {
			return -1;
		}
		em.remove(poi);
		return 1;
	}

	@Override
	public List<Tour> listTours() {
		Query q = em.createQuery("select t from Tour t");
		@SuppressWarnings("unchecked")
		List<Tour> listTour = (List<Tour>) q.getResultList();
		return listTour;
	}

}
