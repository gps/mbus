/**
 * 
 */
package edu.umich.mbus.android;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import edu.umich.mbus.data.Arrival;
import edu.umich.mbus.data.Stop;

/**
 * ArrayAdapter for Stops.
 * 
 * @author gopalkri
 * 
 */
public class StopAdapter extends ArrayAdapter<Stop> {

	private ArrayList<Stop> mStops = null;
	private Context mContext = null;

	public StopAdapter(Context context, int textViewResourceId, List<Stop> stops) {
		super(context, textViewResourceId, stops);

		mContext = context;
		mStops = new ArrayList<Stop>(stops);
	}

	/**
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View,
	 *      android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.stop_row, null);
		}
		Stop stop = mStops.get(position);
		if (stop != null) {
			TextView name = (TextView) v.findViewById(R.id.stop_row_stop_name);
			TextView toa = (TextView) v.findViewById(R.id.stop_row_toa);
			if (name != null) {
				name.setText(stop.getPrimaryName());
			}
			if (toa != null) {
				String arrival = null;
				Arrival earliest = stop.getEarliestArrival();
				if (earliest == null) {
					arrival = "No data available!";
				} else {
					int arrivalMins = ((Double) Math.floor(earliest
							.getTimeOfArrival() / 60.0)).intValue();
					if (arrivalMins == 0) {
						arrival = "Arriving now!";
					} else {
						arrival = "Arriving in: " + arrivalMins + " min";
						if (arrivalMins != 1) {
							arrival += "s";
						}
					}
				}
				toa.setText(arrival);
			}
		}
		return v;
	}
}
