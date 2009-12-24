/**
 * 
 */
package edu.umich.mbus.android;

import java.io.IOException;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.Stop;

/**
 * ListView that contains all Stops in a given route.
 * 
 * @author gopalkri
 * 
 */
public class StopView extends ListActivity {

	public static final String STOP_NAME = "STOP_NAME";

	private static final int REFRESH_MENU_ID = Menu.FIRST + 1;
	private static final int FAVORITE_CONTEXT_MENU_ID = 2;

	private StopAdapter mAdapter = null;
	private Route mRoute = null;
	private String mRouteName = null;

	/**
	 * ProgressDialog to display when fetching feed.
	 */
	private ProgressDialog mProgressDialog = null;

	/**
	 * Receives message from feed fetcher thread and updates view.
	 */
	private Handler mHandler = new Handler() {

		/**
		 * @see android.os.Handler#handleMessage(android.os.Message)
		 */
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			mRoute = MainView.timeFeed.getRouteWithName(mRouteName);
			initializeUI();
			mProgressDialog.dismiss();
		}

	};

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.stop_view);

		Bundle extras = getIntent().getExtras();
		mRouteName = extras.getString(RouteView.ROUTE_NAME);
		mRoute = MainView.timeFeed.getRouteWithName(mRouteName);

		initializeUI();

		registerForContextMenu(getListView());
	}

	/**
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView,
	 *      android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Stop stop = mRoute.getStops().get(((Long) id).intValue());
		Intent intent = new Intent(this, StopDetailsView.class);
		intent.putExtra(RouteView.ROUTE_NAME, mRoute.getName());
		intent.putExtra(STOP_NAME, stop.getPrimaryName());

		startActivity(intent);
	}

	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, REFRESH_MENU_ID, Menu.NONE, R.string.refresh);
		return result;
	}

	/**
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		boolean result = super.onMenuItemSelected(featureId, item);
		switch (item.getItemId()) {
		case REFRESH_MENU_ID:
			fetchFeed();
			break;
		}
		return result;
	}

	/**
	 * @see android.app.Activity#onCreateContextMenu(android.view.ContextMenu,
	 *      android.view.View, android.view.ContextMenu.ContextMenuInfo)
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
		String routeName = mRoute.getName();
		String stopName = mRoute.getStops().get(((Long)info.id).intValue()).getPrimaryName();
		int msg;
		if (FavoritesStore.getInstance(this).doesFavoriteExist(
				new Favorite(routeName, stopName))) {
			msg = R.string.stop_view_remove_from_favorites;
		} else {
			msg = R.string.stop_view_add_to_favorites;
		}
		menu.add(Menu.NONE, FAVORITE_CONTEXT_MENU_ID, Menu.NONE, msg);
	}

	/**
	 * @see android.app.Activity#onContextItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
	    case FAVORITE_CONTEXT_MENU_ID:
	        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        String routeName = mRoute.getName();
			String stopName = mRoute.getStops().get(((Long)info.id).intValue()).getPrimaryName();
			Favorite favorite = new Favorite(routeName, stopName);
			if (FavoritesStore.getInstance(this).doesFavoriteExist(favorite)) {
			}
			else {
				try {
					FavoritesStore.getInstance(this).addFavorite(favorite);
				} catch (IOException e) {
					// Should never happen.
					Log.e("FAVORITE", e.getMessage());
				}
			}
	        return true;
	    }
		return super.onContextItemSelected(item);
	}

	private void initializeUI() {
		mAdapter = new StopAdapter(this, R.layout.stop_row, mRoute.getStops());

		setListAdapter(mAdapter);
		getListView().setTextFilterEnabled(true);
	}

	/**
	 * Fetches feed on a new thread.
	 */
	private void fetchFeed() {
		new Thread(new FeedFetcher(this, mHandler)).start();
		mProgressDialog = ProgressDialog.show(this,
				getString(R.string.please_wait),
				getString(R.string.fetching_feed));
	}
}
