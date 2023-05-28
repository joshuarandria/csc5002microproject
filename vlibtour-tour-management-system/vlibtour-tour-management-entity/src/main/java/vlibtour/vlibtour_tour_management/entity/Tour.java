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
package vlibtour.vlibtour_tour_management.entity;

import java.io.Serializable;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import static jakarta.persistence.CascadeType.ALL;

/**
 * The entity bean defining a tour in the VLibTour case study. A tour is a
 * sequence of points of interest ({@link POI}).
 *
 * For the sake of simplicity, we suggest that you use named queries.
 *
 * @author Denis Conan
 */
@Entity
public class Tour implements Serializable {
	/**
	 * the serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * tid of the tour
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String tid;

	/**
	 * name of the tour
	 */
	private String name;

	/**
	 * description of the tour
	 */
	private String description;

	/**
	 * POIs
	 */
	private Collection<POI> pois= new ArrayList<POI>();;

	// /**
	//  * constructor of tour
	//  * 
	//  * @param tid
	//  *                    id of tour
	//  * @param name
	//  *                    name of tour
	//  * @param description
	//  *                    description of tour
	//  * @param pois
	//  *                    pois of tour
	//  */
	// public Tour(final String tid, final String name, final String description, final Collection<POI> pois) {
	// 	this.tid = tid;
	// 	this.name = name;
	// 	this.description = description;
	// 	this.pois = pois;
	// }

	/**
	 * gets the tid of the tour
	 *
	 * @return the tid of the tour
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public String getTid() {
		return tid;
	}

	/**
	 * sets the tid of the tour
	 * 
	 * @param tid
	 *            tid of tour
	 */
	public void setTid(String tid) {
		this.tid = tid;
	}

	/**
	 * gets the name of the tour
	 *
	 * @return the name of the tour
	 */
	public String getName() {
		return name;
	}

	/**
	 * sets the name of the tour
	 * 
	 * @param name
	 *             name of tour
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the description of the tour
	 *
	 * @return the description of the tour
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * sets the description of the tour
	 * 
	 * @param description
	 *                    description of tour
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * gets the collection of POIs.
	 *
	 * @return the collection.
	 */
	@ManyToMany(cascade = ALL, mappedBy = "tour")
	public Collection<POI> getPOIs() {
		return pois;
	}

	/**
	 * sets the collection of POIs.
	 *
	 * @param newValue
	 *                 value of new POIS
	 */
	public void setPOIs(final Collection<POI> newValue) {
		this.pois = newValue;
	}

	/**
	 * adds POI to tour.
	 *
	 * @param poi
	 *            poi to be added
	 */
	public void addPOI(POI poi) {
		pois.add(poi);
	}

	/**
	 * checks if tour contains POI.
	 *
	 * @param poi
	 *            poi
	 */
	public boolean contains(POI poi) {
		return (pois.contains(poi));
	}

	/**
	 * removes POI.
	 *
	 * @param poi
	 *            poi to be removed
	 */
	public void remove(POI poi) {
		pois.remove(poi);
	}

}
