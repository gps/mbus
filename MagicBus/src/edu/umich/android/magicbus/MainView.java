package edu.umich.android.magicbus;

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

		tabHost.addTab(tabHost.newTabSpec("Routes").setIndicator("Routes")
				.setContent(new Intent(this, RouteView.class)));
		tabHost.addTab(tabHost.newTabSpec("Favorites")
				.setIndicator("Favorites").setContent(
						new Intent(this, FavoritesView.class)));

		tabHost.setCurrentTab(0);
	}
}
