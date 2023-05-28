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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;
import java.util.Collection;

/**
 * The entity bean defining a point of interest (POI). A {@link Tour} is a
 * sequence of points of interest.
 *
 * For the sake of simplicity, we suggest that you use named queries.
 *
 * @author Denis Conan
 */
@Entity
@Table(name = "TOUR_TABLE")
public class POI implements Serializable {
	/**
	 * the serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id of POI
	 */
	@Id
	@Column(name = "POID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String poid;
	/**
	 * name of POI
	 */
	private String name;
	/**
	 * description of POI
	 */
	private String description;
	/**
	 * latitude of POI
	 */
	private double latitude;
	/**
	 * longitude of POI
	 */
	private double longitude;

	/**
	 * the corresponding tour
	 */
	// @ManyToMany(mappedBy = "pois")
	private Collection<Tour> tour;
	// private Tour tour;

	// /**
	//  * constructor for poi.
	//  *
	//  * @param poid
	//  * @param name
	//  * @param description
	//  * @param latitude
	//  * @param longitude
	//  */
	// public POI(final String poid, final String name, final String description, final int latitude,
	// 		final int longitude) {
	// 	this.description = description;
	// 	this.latitude = latitude;
	// 	this.longitude = longitude;
	// 	this.poid = poid;
	// 	this.name = name;
	// }

	/**
	 * gets the poid of the tour.
	 *
	 * @return the poid of the tour.
	 */
	@Id
	public String getPoid() {
		return poid;
	}

	/**
	 * sets the poid of the tour.
	 * 
	 * @param poid
	 *             id of poi
	 */
	public void setPoid(String poid) {
		this.poid = poid;
	}

	/**
	 * gets the name of the tour.
	 * 
	 * @return the name of the tour.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * sets the name of the tour. *
	 * 
	 * @param name
	 *             name of poi
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * gets the description of the tour.
	 *
	 * @return the description of the tour.
	 */
	@Column(name = "POI_DESCRIPTION")
	public String getDescription() {
		return description;
	}

	/**
	 * sets the description of the tour. *
	 * 
	 * @param description
	 *                    description of poi
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * gets the latitude of the tour.
	 *
	 * @return the latitude of the tour.
	 */
	@Column(name = "POI_LATITTUDE")
	public double getLatitude() {
		return latitude;
	}

	/**
	 * sets the latitude of the tour.
	 * 
	 * @param latitude
	 *                 latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * gets the longitude of the tour.
	 *
	 * @return the longitude of the tour.
	 */
	@Column(name = "POI_LONGITUDE")
	public double getLongitude() {
		return longitude;
	}

	/**
	 * sets the longitude of the tour.
	 * 
	 * @param longitude
	 *                  longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * gets the tour.
	 *
	 * @return the tour.
	 */
	@ManyToMany(mappedBy = "pois")
	@JoinColumn(name = "TOUR_ID")
	public Collection<Tour> getTour() {
		return tour;
	}

	/**
	 * sets the tours.
	 *
	 * @param newTour
	 *                newtours
	 */
	public void setTour(final Collection<Tour> newTour) {
		this.tour = newTour;
	}

}
