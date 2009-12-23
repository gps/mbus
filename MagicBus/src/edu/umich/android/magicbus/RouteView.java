/**
 * 
 */
package edu.umich.android.magicbus;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import edu.umich.mbus.data.MBusDataException;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.TimeFeed;

/**
 * ListView that shows all routes.
 * 
 * @author gopalkri
 * 
 */
public class RouteView extends ListActivity {
	
	

	/**
	 * Menu id of Refresh.
	 */
	private static final int REFRESH_MENU_ID = 0;

	/**
	 * ProgressDialog to display when fetching feed.
	 */
	private ProgressDialog mProgressDialog = null;

	/**
	 * Fetches feed and dismisses progress dialog.
	 */
	private Runnable feedFetcher = new Runnable() {

		@Override
		public void run() {
			MainView.timeFeed = null;
			try {
				MainView.timeFeed = new TimeFeed();
			} catch (MBusDataException e) {
				Log.e("TIME_FEED_FETCHER", e.getMessage());
				displayFetchError();
			} finally {
				mProgressDialog.dismiss();
				initializeUI();
			}
		}

	};

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		fetchFeed();
	}
	
	/**
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, REFRESH_MENU_ID, Menu.NONE,
				R.string.route_view_refresh);
		return result;
	}

	/**
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean result = super.onOptionsItemSelected(item);
		fetchFeed();
		return result;
	}
	
	private void initializeUI() {
		setListAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, getRouteNames()));
		getListView().setTextFilterEnabled(true);
	}

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
	
	private void fetchFeed() {
		new Thread(feedFetcher).start();
		mProgressDialog = ProgressDialog.show(this,
				getString(R.string.route_view_please_wait),
				getString(R.string.route_view_fetching_feed));
	}

	private void displayFetchError() {
		new AlertDialog.Builder(this)
				.setMessage(R.string.route_view_fetch_error).create().show();
	}

}
