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

package edu.umich.mbus.android;

/**
 * Represents a favorite. Contains a route name and a stop name.
 * 
 * @author gopalkri
 * 
 */
public class Favorite implements Comparable<Favorite> {

	private static final String ROUTE_STOP_SEPARATOR = "|||";

	private String mRouteName;
	private String mStopName;

	/**
	 * Initializes this object with routeName and stopName.
	 * 
	 * @param routeName
	 *            Name of route.
	 * @param stopName
	 *            Name of stop.
	 */
	public Favorite(String routeName, String stopName) {
		mRouteName = routeName;
		mStopName = stopName;
	}

	/**
	 * Initializes this object with serialized String serialized.
	 * 
	 * @param serialized
	 *            Serialized representation of object.
	 * @throws IllegalArgumentException
	 *             If serialized String is not in right format.
	 */
	public Favorite(String serialized) throws IllegalArgumentException {
		String[] split = serialized.split(ROUTE_STOP_SEPARATOR);
		if (split.length != 2) {
			throw new IllegalArgumentException(
					"Serialized string not in the right format.");
		}
		mRouteName = split[0];
		mStopName = split[1];
	}

	/**
	 * Gets route name.
	 * 
	 * @return Route name.
	 */
	public String getRouteName() {
		return mRouteName;
	}

	/**
	 * Gets stop name.
	 * 
	 * @return Stop name.
	 */
	public String getStopName() {
		return mStopName;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return mRouteName + ROUTE_STOP_SEPARATOR + mStopName;
	}

	/**
	 * Compares based on route name and then stop name.
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Favorite another) {
		int cmp = mRouteName.compareTo(another.mRouteName);
		if (cmp == 0) {
			return mStopName.compareTo(another.mStopName);
		}
		return cmp;
	}
}