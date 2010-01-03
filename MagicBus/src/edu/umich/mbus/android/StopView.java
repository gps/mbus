/**
 * 
 */
package edu.umich.mbus.android;

import java.io.IOException;
import java.util.List;

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
import edu.umich.mbus.data.Stop;

/**
 * ListView that contains all Stops in a given route.
 * 
 * @author gopalkri
 * 
 */
public class StopView extends ListActivity {

	private static final int REFRESH_MENU_ID = Menu.FIRST + 1;
	private static final int FAVORITE_CONTEXT_MENU_ID = Menu.FIRST + 2;

	private StopAdapter mAdapter = null;
	// private Route mRoute = null;
	// private String mRouteName = null;
	private List<Stop> mStops = null;
	private int mViewType = Constants.STOPS_FAVORITES;

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
			initializeStops();
			initializeUI();
			mProgressDialog.dismiss();
		}

	};

	/**
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			mViewType = extras.getInt(Constants.STOP_VIEW_TYPE);
		} else {
			// View type not specified, default to favorites.
			mViewType = Constants.STOPS_FAVORITES;
		}

		initializeStops();
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

		Stop stop = mStops.get(((Long) id).intValue());
		Intent intent = new Intent(this, StopDetailsView.class);
		intent.putExtra(Constants.ROUTE_NAME, stop.getRouteName());
		intent.putExtra(Constants.STOP_NAME, stop.getPrimaryName());

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
		Stop stop = mStops.get(((Long) info.id).intValue());
		int msg;
		if (FavoritesStore.getInstance(this).doesFavoriteExist(
				new Favorite(stop.getRouteName(), stop.getPrimaryName()))) {
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
		switch (item.getItemId()) {
		case FAVORITE_CONTEXT_MENU_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			Stop stop = mStops.get(((Long) info.id).intValue());
			Favorite favorite = new Favorite(stop.getRouteName(), stop
					.getPrimaryName());
			if (FavoritesStore.getInstance(this).doesFavoriteExist(favorite)) {
				try {
					FavoritesStore.getInstance(this).removeFavorite(favorite);
				} catch (IOException e) {
					// Should never happen
					Log.e("FAVORITE", e.getMessage());
				}
			} else {
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

	/**
	 * Initializes stops data based on whether stops for a route are being
	 * displayed or favorites are being displayed.
	 */
	private void initializeStops() {
		switch (mViewType) {
		case Constants.STOPS_ROUTE:
			String routeName = getIntent().getExtras().getString(
					Constants.ROUTE_NAME);
			mStops = MainView.timeFeed.getRouteWithName(routeName).getStops();
			break;
		case Constants.STOPS_FAVORITES:
			mStops = LiveFavorites.getLiveFavorites(this);
			break;
		case Constants.STOPS_NEAR_ME:
			break;
		}
	}

	/**
	 * Initializes this ListActivity's list adapter with stop information.
	 */
	private void initializeUI() {
		
		switch (mViewType) {
		case Constants.STOPS_ROUTE:
			setContentView(R.layout.stop_view);
			break;
		case Constants.STOPS_FAVORITES:
			setContentView(R.layout.favorites_view);
			break;
		case Constants.STOPS_NEAR_ME:
			break;
		}

		mAdapter = new StopAdapter(this, R.layout.stop_row, mStops);

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
