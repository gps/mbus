/**
 * 
 */
package edu.umich.mbus.android;

import android.app.Activity;
import android.os.Bundle;
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
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.stop_details_view);

		Bundle extras = getIntent().getExtras();
		String routeName = extras.getString(RouteView.ROUTE_NAME);
		String stopName = extras.getString(StopView.STOP_NAME);
		Route route = MainView.timeFeed.getRouteWithName(routeName);
		Stop stop = route.getStopWithName(stopName);

		TextView tvPrimaryName = (TextView) findViewById(R.id.stop_details_view_primary_name);
		tvPrimaryName.setText(stopName);

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
}
