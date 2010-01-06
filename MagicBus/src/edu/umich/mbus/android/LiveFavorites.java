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

import android.content.Context;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.Stop;

/**
 * Provides an interface to query all favorites that are being serviced
 * currently.
 * 
 * @author gopalkri
 * 
 */
public class LiveFavorites {

	/**
	 * Finds all favorites which are currently running.
	 * 
	 * @param context
	 *            Context with which to initialize FavoritesStore.
	 * @return All favorites currently running.
	 */
	public static ArrayList<Stop> getLiveFavorites(Context context) {
		ArrayList<Stop> ret = new ArrayList<Stop>();
		ArrayList<Favorite> allFavs = FavoritesStore.getInstance(context)
				.getAllFavorites();

		for (Favorite fav : allFavs) {
			Route route = MainView.timeFeed
					.getRouteWithName(fav.getRouteName());
			if (route != null) {
				Stop stop = route.getStopWithName(fav.getStopName());
				if (stop != null) {
					ret.add(stop);
				}
			}
		}

		return ret;
	}
}
