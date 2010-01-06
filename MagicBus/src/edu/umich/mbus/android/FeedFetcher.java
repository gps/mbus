/* 
 * Copyright 2009, 2010 Gopalkrishna Sharma.
 *
 * This file is part of MBus.
 *
 * MBus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * MBus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with MBus.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.umich.mbus.android;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import edu.umich.mbus.data.MBusDataException;
import edu.umich.mbus.data.TimeFeed;

/**
 * Fetches magic bus feed.
 * 
 * @author gopalkri
 * 
 */
public class FeedFetcher implements Runnable {

	Handler mHandler = null;
	Context mContext = null;

	public FeedFetcher(Context context, Handler handler) {
		mContext = context;
		mHandler = handler;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		MainView.timeFeed = null;
		try {
			MainView.timeFeed = new TimeFeed();
		} catch (MBusDataException e) {
			Log.e("TIME_FEED_FETCHER", e.getMessage());
			displayFetchError();
		} finally {
			mHandler.sendEmptyMessage(0);
		}
	}

	/**
	 * Displays error dialog with feed fetch error message.
	 */
	private void displayFetchError() {
		new AlertDialog.Builder(mContext).setMessage(R.string.fetch_error)
				.create().show();
	}

}
