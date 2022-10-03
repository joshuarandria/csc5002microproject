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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


import java.io.Serializable;

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
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	private int latitude;
	/**
	 * longitude of POI
	 */
	private int longitude;
	/**
	 * the corresponding tour
	 */
	private Tour tour;

	/**
	 * gets the poid of the tour.
	 *
	 * @return the poid of the tour.
	 */
	public String getPoid() {
		return poid;
	}

	/**
	 * sets the poid of the tour.
	 */
	public void setPoid(String poid) {
		this.poid = poid;
	}

	/**
	 * gets the name of the tour.
	 *
	 * @return the name of the tour.
	 */
	@Column(name = "POI_NAME")
	public String getName() {
		return this.name;
	}

	/**
	 * sets the name of the tour.
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
	 * sets the description of the tour.
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
	public int getLatitude() {
		return latitude;
	}

	/**
	 * sets the latitude of the tour.
	 */
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	/**
	 * gets the longitude of the tour.
	 *
	 * @return the longitude of the tour.
	 */
	@Column(name = "POI_LONGITUDE")
	public int getLongitude() {
		return longitude;
	}

	/**
	 * sets the longitude of the tour.
	 */
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	/**
	 * gets the tour.
	 *
	 * @return the tour.
	 */
	@ManyToOne()
	@JoinColumn(name = "TOUR_ID")
	public Tour getTour() {
		return tour;
	}

	/**
 * sets the tour.
 *
 * @param tour
 *            the tour.
 */
public void setTour(final Tour tour) {
	this.tour = tour;
}

}
