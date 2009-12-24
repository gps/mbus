/**
 * 
 */
package edu.umich.mbus.android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.Stop;

/**
 * ListView that contains all Stops in a given route.
 * @author gopalkri
 *
 */
public class StopView extends ListActivity {
	
	public static final String STOP_NAME = "STOP_NAME";
	
	private StopAdapter mAdapter = null;
	private Route mRoute = null;

	/**
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.stop_view);
		
		Bundle extras = getIntent().getExtras();
		String routeName = extras.getString(RouteView.ROUTE_NAME);
		mRoute = MainView.timeFeed.getRouteWithName(routeName);
		
		mAdapter = new StopAdapter(this, R.layout.stop_row, mRoute.getStops());
		
		setListAdapter(mAdapter);
		getListView().setTextFilterEnabled(true);
	}

	/**
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Stop stop = mRoute.getStops().get(((Long)id).intValue());
		Intent intent = new Intent(this, StopDetailsView.class);
		intent.putExtra(RouteView.ROUTE_NAME, mRoute.getName());
		intent.putExtra(STOP_NAME, stop.getPrimaryName());
		
		startActivity(intent);
	}
}
