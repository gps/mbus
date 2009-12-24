/**
 * 
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
