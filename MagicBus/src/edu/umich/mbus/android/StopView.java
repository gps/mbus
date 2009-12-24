/**
 * 
 */
package edu.umich.mbus.android;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
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

	private static final int REFRESH_MENU_ID = 1;

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
