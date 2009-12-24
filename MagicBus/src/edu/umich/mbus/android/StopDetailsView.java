/**
 * 
 */
package edu.umich.mbus.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import edu.umich.mbus.data.Arrival;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.Stop;

/**
 * Displays details about a stop.
 * 
 * @author gopalkri
 * 
 */
public class StopDetailsView extends Activity {

	/**
	 * Menu id of Refresh.
	 */
	private static final int REFRESH_MENU_ID = 1;

	private String mRouteName = null;
	private String mStopName = null;

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

		setContentView(R.layout.stop_details_view);

		Bundle extras = getIntent().getExtras();
		mRouteName = extras.getString(RouteView.ROUTE_NAME);
		mStopName = extras.getString(StopView.STOP_NAME);

		initializeUI();
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
	 * Initializes UI elements with stop data.
	 */
	private void initializeUI() {
		Route route = MainView.timeFeed.getRouteWithName(mRouteName);
		Stop stop = route.getStopWithName(mStopName);

		TextView tvPrimaryName = (TextView) findViewById(R.id.stop_details_view_primary_name);
		tvPrimaryName.setText(mStopName);

		TextView tvOtherNames = (TextView) findViewById(R.id.stop_details_view_other_names);
		StringBuilder sb = new StringBuilder();
		for (String name : stop.getOtherNames()) {
			sb.append(name);
			sb.append("\n");
		}
		tvOtherNames.setText(sb.toString());

		TextView tvArrivals = (TextView) findViewById(R.id.stop_details_view_arrivals);
		if (stop.getArrivals().size() <= 0) {
			tvArrivals.setText("No arrival data available!");
		} else {
			sb = new StringBuilder();
			for (Arrival arrival : stop.getArrivals()) {
				sb.append(formatArrival(arrival));
				sb.append("\n");
			}
			tvArrivals.setText(sb.toString());
		}
	}

	/**
	 * Formats an arrival into a human readable String.
	 * 
	 * @param arr
	 *            Arrival to format.
	 * @return Formatted arrival String.
	 */
	private String formatArrival(Arrival arr) {
		int arrMins = ((Double) Math.floor(arr.getTimeOfArrival() / 60.0))
				.intValue();
		if (arrMins == 0) {
			return "Now!";
		}
		String ret = arrMins + " min";
		if (arrMins != 1) {
			ret += "s";
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
