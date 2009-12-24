/**
 * 
 */
package edu.umich.mbus.android;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

/**
 * Provides an interface to read from and write Favorites to file. This class
 * manages the Favorites file.
 * 
 * @author gopalkri
 * 
 */
public class FavoritesStore {

	private static final String FAVORITES_FILE_NAME = "favorites.txt";
	private static final String ROUTE_STOP_SEPARATOR = "|";
	
	/**
	 * Represents a favorite. Contains a route name and a stop name.
	 * @author gopalkri
	 *
	 */
	public class Favorite {
		private String mRouteName;
		private String mStopName;
		
		/**
		 * Initializes this object with routeName and stopName.
		 * @param routeName Name of route.
		 * @param stopName Name of stop.
		 */
		public Favorite(String routeName, String stopName) {
			mRouteName = routeName;
			mStopName = stopName;
		}
		
		/**
		 * Gets route name.
		 * @return Route name.
		 */
		public String getRouteName() {
			return mRouteName;
		}
		
		/**
		 * Gets stop name.
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
	}
	
	private ArrayList<Favorite> mFavoritePairs = new ArrayList<Favorite>();
	private Context mContext = null;

	public FavoritesStore(Context context) {
		FileInputStream fin = null;
		try {
			fin = context.openFileInput(FAVORITES_FILE_NAME);
		} catch (FileNotFoundException e) {
			// File does not exist, so no favorites exist.
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(fin));
		String line = null;
		try {
			while ((line = br.readLine().trim()) != null) {
				if (line.compareTo("") == 0) {
					continue;
				}
				String[] split = line.split(ROUTE_STOP_SEPARATOR);
				if (split.length != 2) {
					throw new IOException("Unable to split non empty line.");
				}
				mFavoritePairs.add(new Favorite(split[0], split[1]));
			}
		} catch (IOException e) {
			// Unexpected - should not happen.
			Log.e("READ_FAVORITES_FILE", e.getMessage());
			mFavoritePairs.clear();
			context.deleteFile(FAVORITES_FILE_NAME);
		}
		mContext = context;
	}
	
	public ArrayList<Favorite> getAllFavoritePairs() {
		return mFavoritePairs;
	}
	
	public void addFavorite(Favorite favorite) throws IOException {
		FileOutputStream fout;
		try {
			fout = mContext.openFileOutput(FAVORITES_FILE_NAME, Context.MODE_APPEND);
		} catch (FileNotFoundException e) {
			// File doesn't exist, so create it.
			fout = mContext.openFileOutput(FAVORITES_FILE_NAME, Context.MODE_PRIVATE);
		}
		OutputStreamWriter osw = new OutputStreamWriter(fout);
		osw.write(favorite.toString() + "\n");
		osw.flush();
		osw.close();
		fout.flush();
		fout.close();
	}
}
