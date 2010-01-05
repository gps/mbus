package edu.umich.mbus.android;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import edu.umich.mbus.data.TimeFeed;

public class MainView extends TabActivity {

	/**
	 * Global object - only want 1 time feed.
	 */
	public static TimeFeed timeFeed = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		TabHost tabHost = getTabHost();

		tabHost.addTab(tabHost.newTabSpec("Routes").setIndicator("Routes",
				getResources().getDrawable(R.drawable.route)).setContent(
				new Intent(this, RouteView.class)));
		tabHost.addTab(tabHost.newTabSpec("Favorites").setIndicator(
				"Favorites", getResources().getDrawable(R.drawable.favorites))
				.setContent(
						new Intent(this, StopView.class).putExtra(
								Constants.STOP_VIEW_TYPE,
								Constants.STOPS_FAVORITES)));
		tabHost.addTab(tabHost.newTabSpec("Near Me").setIndicator("Near Me",
				getResources().getDrawable(R.drawable.near_me)).setContent(
				new Intent(this, StopView.class).putExtra(
						Constants.STOP_VIEW_TYPE, Constants.STOPS_NEAR_ME)));

		tabHost.setCurrentTab(0);
	}
}
