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

/**
 * Represents an arrival - toa, bus id pair.
 * @author gopalkri
 */
public class Arrival implements Comparable<Arrival> {
	
	/**
	 * Initializes this object with toa and id.
	 * @param toa Time of arrival.
	 * @param id Bus id.
	 */
	public Arrival(double toa, int id) {
		mToa = toa;
		mId = id;
	}
	
	/**
	 * Gets time of arrival in seconds.
	 * @return Time of arrival in seconds.
	 */
	public double getTimeOfArrival() {
		return mToa;
	}
	
	/**
	 * Gets bus id.
	 * @return Bus id.
	 */
	public int getBusId() {
		return mId;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TOA: " + mToa + " | Id: " + mId;
	}
	
	/**
	 * Compares arrivals based on time of arrival, and then id.
	 */
	@Override
	public int compareTo(Arrival other) {
		if (mToa < other.mToa) {
			return -1;
		}
		else if (mToa > other.mToa) {
			return 1;
		}
		if (mId < other.mId) {
			return -1;
		}
		else if (mId > other.mId) {
			return 1;
		}
		return 0;
	}
	
	private double mToa;
	private int mId;
}