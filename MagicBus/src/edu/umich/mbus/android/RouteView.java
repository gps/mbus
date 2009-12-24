/**
 * 
 */
package edu.umich.mbus.android;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.umich.mbus.data.Route;

/**
 * ListView that shows all routes.
 * 
 * @author gopalkri
 * 
 */
public class RouteView extends ListActivity {

	/**
	 * Route name key in extras passed into StopView.
	 */
	public static final String ROUTE_NAME = "ROUTE_NAME";

	/**
	 * Menu id of Refresh.
	 */
	private static final int REFRESH_MENU_ID = 1;

	/**
	 * ProgressDialog to display when fetching feed.
	 */
	private ProgressDialog mProgressDialog = null;

	/**
	 * Contains all active route names.
	 */
	private String[] mRouteNames = null;

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
			mRouteNames = getRouteNames();
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

		mRouteNames = new String[0];
		setListAdapter(new ArrayAdapter<String>(this, R.layout.route_view,
				mRouteNames));
		getListView().setTextFilterEnabled(true);

		fetchFeed();
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
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case REFRESH_MENU_ID:
			fetchFeed();
			break;
		}
		return result;
	}

	/**
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView,
	 *      android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String routeName = mRouteNames[((Long) id).intValue()];
		Intent intent = new Intent(this, StopView.class);
		intent.putExtra(ROUTE_NAME, routeName);
		startActivity(intent);
	}

	/**
	 * Initializes this ListActivity's list adapter with route information.
	 */
	private void initializeUI() {
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, mRouteNames));
		getListView().setTextFilterEnabled(true);
	}

	/**
	 * Gets names of all routes currently running.
	 * 
	 * @return Names of all routes currently running.
	 */
	private String[] getRouteNames() {
		if (MainView.timeFeed == null) {
			new AlertDialog.Builder(this).setMessage(
					R.string.route_view_no_feed).create().show();
		}
		List<Route> routes = MainView.timeFeed.getRoutes();
		String[] ret = new String[routes.size()];
		for (int i = 0; i < routes.size(); ++i) {
			ret[i] = routes.get(i).getName();
		}
		return ret;
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
