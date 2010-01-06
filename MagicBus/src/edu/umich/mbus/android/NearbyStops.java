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

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.Stop;

/**
 * Provides an interface to find stops nearby using location services.
 * 
 * @author gopalkri
 * 
 */
public class NearbyStops {

	/**
	 * Proximity radius within which to look for stops.
	 */
	private static final double PROXIMITY_RADIUS = 0.75; // km

	/**
	 * Radius of the earth - needed for Haversine formula.
	 */
	private static final double RADIUS_OF_EARTH = 6371.0; // km

	/**
	 * Returns all stops near current location. Current location could be
	 * determined inaccurately.
	 * 
	 * @param context
	 *            Context to get location services from.
	 * @return All stops near current location.
	 */
	public static ArrayList<Stop> getNearbyStops(Context context) {
		ArrayList<Stop> ret = new ArrayList<Stop>();
		TreeMap<Double, Stop> stops = new TreeMap<Double, Stop>();

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);

		LocationManager locMan = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		String provider = locMan.getBestProvider(criteria, true);

		Location curLoc = locMan.getLastKnownLocation(provider);
		if (curLoc == null) {
			return ret;
		}
		double lat = curLoc.getLatitude();
		double lon = curLoc.getLongitude();

		for (Route route : MainView.timeFeed.getRoutes()) {
			for (Stop stop : route.getStops()) {
				double distance = getDistanceBetween(lat, lon, stop
						.getLatitude(), stop.getLongitude());
				if (distance < PROXIMITY_RADIUS) {
					stops.put(distance, stop);
				}
			}
		}

		for (Map.Entry<Double, Stop> entry : stops.entrySet()) {
			ret.add(entry.getValue());
		}

		return ret;
	}

	/**
	 * Computes distance between to lat,lon pairs in km. Uses Haversine formula.
	 * 
	 * @param lat1
	 *            Latitude of first point.
	 * @param lon1
	 *            Longitude of first point.
	 * @param lat2
	 *            Latitude of second point.
	 * @param lon2
	 *            Longitude of second point.
	 * @return Distance in km between two points.
	 */
	private static double getDistanceBetween(double lat1, double lon1,
			double lat2, double lon2) {

		double dLat = radiansFromDegrees(lat2 - lat1);
		double dLon = radiansFromDegrees(lon2 - lon1);

		double a = Math.pow(Math.sin(dLat / 2.0), 2.0)
				+ Math.cos(radiansFromDegrees(lat1))
				* Math.cos(radiansFromDegrees(lat2))
				* Math.pow(Math.sin(dLon / 2.0), 2.0);
		double c = 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a));

		return RADIUS_OF_EARTH * c;
	}

	/**
	 * Converts degrees to radians.
	 * 
	 * @param degrees
	 *            Degree value to be converted.
	 * @return Radian value of degrees passed in.
	 */
	private static double radiansFromDegrees(double degrees) {
		return degrees * Math.PI / 180.0;
	}
}
