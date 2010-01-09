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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import android.os.Handler;
import android.util.Log;

/**
 * Sends provided report to provided url on a new thread and sends empty message
 * to handler if handler is not null.
 * 
 * @author gopalkri
 * 
 */
class ReportSender implements Runnable {

	private String mUrl;
	private String mReport;
	private Handler mHandler;

	/**
	 * Constructs this object with provided parameters.
	 * 
	 * @param url
	 *            Url to send report to.
	 * @param reportData
	 *            Report data to embed - must be a valid xml segment.
	 * @param handler
	 *            Handler to message when sending is done. If null, no message
	 *            is sent.
	 */
	protected ReportSender(String url, String reportData, Handler handler) {
		mUrl = url;
		StringBuilder sb = new StringBuilder();
		sb.append("<report>");
		sb.append(reportData);
		sb.append("</report>");
		mReport = sb.toString();
		mHandler = handler;
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			URL url = new URL(mUrl);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn
					.getOutputStream());
			wr.write(mReport);
			wr.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            String response = "";
            while ((line = rd.readLine()) != null) 
            {
                response += line;
            }
            wr.close();
            rd.close();
		} catch (Exception ex) {
			Log.e("REPORTER", ex.getMessage());
		}
		if (mHandler != null) {
			mHandler.sendEmptyMessage(0);
		}
	}
}
