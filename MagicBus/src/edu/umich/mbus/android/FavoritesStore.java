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

	private static FavoritesStore sInstance = null;

	public static FavoritesStore getInstance(Context context) {
		if (sInstance == null) {
			sInstance = new FavoritesStore(context);
		}
		return sInstance;
	}

	private ArrayList<Favorite> mFavorites = new ArrayList<Favorite>();
	private Context mContext = null;

	/**
	 * Initializes this object with data from favorites file.
	 * 
	 * @param context
	 *            Context from which to read file.
	 */
	private FavoritesStore(Context context) {
		mContext = context;
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
				try {
					mFavorites.add(new Favorite(line));
				} catch (IllegalArgumentException ex) {
					throw new IOException(ex.getMessage());
				}
			}
		} catch (IOException e) {
			// Unexpected - should not happen.
			Log.e("READ_FAVORITES_FILE", e.getMessage());
			mFavorites.clear();
			context.deleteFile(FAVORITES_FILE_NAME);
		}
	}

	/**
	 * Gets all favorites.
	 * 
	 * @return All favorites.
	 */
	public ArrayList<Favorite> getAllFavorites() {
		return mFavorites;
	}

	/**
	 * Adds favorite to favorites file.
	 * 
	 * @param favorite
	 *            Favorite to add.
	 * @throws IOException
	 *             If an unexpected error occurred when writing to file.
	 */
	public void addFavorite(Favorite favorite) throws IOException {
		FileOutputStream fout;
		try {
			fout = mContext.openFileOutput(FAVORITES_FILE_NAME,
					Context.MODE_APPEND);
		} catch (FileNotFoundException e) {
			// File doesn't exist, so create it.
			fout = mContext.openFileOutput(FAVORITES_FILE_NAME,
					Context.MODE_PRIVATE);
		}
		OutputStreamWriter osw = new OutputStreamWriter(fout);
		osw.write(favorite.toString() + "\n");
		osw.flush();
		osw.close();
		fout.flush();
		fout.close();
		mFavorites.add(favorite);
	}

	/**
	 * Removes favorite from favorites file. Does nothing if favorite does not
	 * exist.
	 * 
	 * @param favorite
	 *            Favorite to remove.
	 * @throws IOException
	 *             If an unexpected error occurs when writing to file.
	 */
	public void removeFavorite(Favorite favorite) throws IOException {
		int index = getIndexOfFavorite(favorite);
		if (index < 0) {
			return;
		}
		mFavorites.remove(index);
		FileOutputStream fout;
		fout = mContext.openFileOutput(FAVORITES_FILE_NAME,
				Context.MODE_PRIVATE);
		OutputStreamWriter osw = new OutputStreamWriter(fout);
		for (Favorite f : mFavorites) {
			osw.write(f.toString() + "\n");
		}
		osw.flush();
		osw.close();
		fout.flush();
		fout.close();
	}

	/**
	 * Returns true if favorite does exist. False otherwise.
	 * 
	 * @param favorite
	 *            Favorite to check for existence.
	 * @return True if favorite exists, false otherwise.
	 */
	public boolean doesFavoriteExist(Favorite favorite) {
		return getIndexOfFavorite(favorite) >= 0;
	}

	private int getIndexOfFavorite(Favorite favorite) {
		for (int i = 0; i < mFavorites.size(); ++i) {
			if (mFavorites.get(i).compareTo(favorite) == 0) {
				return i;
			}
		}
		return -1;
	}
}
