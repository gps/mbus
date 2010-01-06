/* 
 * Copyright 2009, 2010 Gopalkrishna Sharma.
 *
 * This file is part of MBus.
 *
 * MBus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MBus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MBus.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.umich.mbus.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;

/**
 * Represents a bus stop.
 * 
 * @author gopalkri
 * 
 */
public class Stop {

	/**
	 * Initializes this object with stop data from element. Assumes element
	 * contains valid xml for a stop.
	 * 
	 * @param element
	 *            XML element representing this object.
	 * @param routeName
	 *            Name of route to which this stop belongs.
	 */
	public Stop(Element element, String routeName) {
		// Get primary name.
		mName = Utilities.getStringValueByTagName(element, "name");
		if (TimeFeed.PRINT_DEBUG_OUTPUT) {
			System.out.println("Name: " + mName);
		}

		// Get other names.
		int nameCtr = 2;
		while (true) {
			String name = Utilities.getStringValueByTagName(element, "name"
					+ nameCtr++);
			if (name == null || name.compareTo("None") == 0) {
				// No more names
				break;
			}
			mOtherNames.add(name);
		}
		if (TimeFeed.PRINT_DEBUG_OUTPUT) {
			System.out.print("Other names: ");
			for (String name : mOtherNames) {
				System.out.print(name + " ");
			}
			System.out.println();
		}
		
		// Route name.
		mRouteName = routeName;
		if (TimeFeed.PRINT_DEBUG_OUTPUT) {
			System.out.println("Route name: " + mRouteName);
		}

		// Get latitude.
		mLatitude = Double.parseDouble(Utilities.getStringValueByTagName(
				element, "latitude"));
		if (TimeFeed.PRINT_DEBUG_OUTPUT) {
			System.out.println("Lat: " + mLatitude);
		}

		// Get longitude.
		mLongitude = Double.parseDouble(Utilities.getStringValueByTagName(
				element, "longitude"));
		if (TimeFeed.PRINT_DEBUG_OUTPUT) {
			System.out.println("Lon: " + mLongitude);
		}

		// Get arrivals.
		int arrivalCtr = 1;
		while (true) {
			String toaStr = Utilities.getStringValueByTagName(element, "toa"
					+ arrivalCtr);
			if (toaStr == null) {
				break;
			}
			double toa = Double.parseDouble(toaStr);
			int id = Integer.parseInt(Utilities.getStringValueByTagName(
					element, "id" + arrivalCtr++));
			mArrivals.add(new Arrival(toa, id));
		}
		Collections.sort(mArrivals);
		if (TimeFeed.PRINT_DEBUG_OUTPUT) {
			System.out.print("Arrivals: ");
			for (Arrival arrival : mArrivals) {
				System.out.print(arrival.getTimeOfArrival() + " "
						+ arrival.getBusId() + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * Gets primary name of stop.
	 * 
	 * @return Primary name of stop.
	 */
	public String getPrimaryName() {
		return mName;
	}

	/**
	 * Gets all known names of stop, excluding primary name.
	 * 
	 * @return All known names of stop, excluding primary stop.
	 */
	public List<String> getOtherNames() {
		return mOtherNames;
	}

	/**
	 * Gets name of route on which this stop exists.
	 * 
	 * @return Route name.
	 */
	public String getRouteName() {
		return mRouteName;
	}

	/**
	 * Gets latitude of stop.
	 * 
	 * @return Latitude of stop.
	 */
	public double getLatitude() {
		return mLatitude;
	}

	/**
	 * Gets longitude of stop.
	 * 
	 * @return Longitude of stop.
	 */
	public double getLongitude() {
		return mLongitude;
	}

	/**
	 * Gets list of arrivals at stop.
	 * 
	 * @return List of arrivals at stop.
	 */
	public List<Arrival> getArrivals() {
		return mArrivals;
	}

	/**
	 * Gets earliest arrival at stop.
	 * 
	 * @return Earliest arrival at stop.
	 */
	public Arrival getEarliestArrival() {
		if (mArrivals.size() <= 0) {
			return null;
		}
		return mArrivals.get(0);
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String ret = "Name: " + mName + " | Other Names: ";
		for (String name : mOtherNames) {
			ret += name + ";";
		}
		ret += " | Lat: " + mLatitude + " | Lon: " + mLongitude;
		ret += "\nArrivals:\n";
		for (Arrival arrival : mArrivals) {
			ret += "\t" + arrival + "\n";
		}
		return ret;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stop other = (Stop) obj;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		return true;
	}

	private String mName;
	private ArrayList<String> mOtherNames = new ArrayList<String>();
	private String mRouteName;
	private double mLatitude;
	private double mLongitude;
	private ArrayList<Arrival> mArrivals = new ArrayList<Arrival>();
}
