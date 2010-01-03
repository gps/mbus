/**
 * 
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
