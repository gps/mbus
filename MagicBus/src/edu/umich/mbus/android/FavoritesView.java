/**
 * 
 */
package edu.umich.mbus.android;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

/**
 * View that contains all favorite stops.
 * 
 * @author gopalkri
 * 
 */
public class FavoritesView extends ListActivity {

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.favorites_view);
		
		String[] favorites = null;

		ArrayList<Favorite> favs = FavoritesStore.getInstance(this)
				.getAllFavorites();
		if (favs.size() <= 0) {
			favorites = new String[0];
		} else {
			favorites = new String[favs.size()];
			for (int i = 0; i < favs.size(); ++i) {
				favorites[i] = favs.get(i).toString();
			}
		}

		setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
				favorites));
		getListView().setTextFilterEnabled(true);
	}

}
