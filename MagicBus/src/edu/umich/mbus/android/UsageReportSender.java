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

import android.os.Handler;

/**
 * Sends a usage report to server.
 * 
 * @author gopalkri
 * 
 */
class UsageReportSender extends ReportSender {

	private static final String USAGE_REPORT_URL = "http://mbus-android.appspot.com/usage";

	/**
	 * Constructs this object with parameters.
	 * 
	 * @param view
	 *            View that was displayed.
	 * @param handler
	 *            Handler to send message to after sending is done. If null, no
	 *            message is sent.
	 */
	public UsageReportSender(String view, Handler handler) {
		super(USAGE_REPORT_URL, "<view>" + view + "</view>", handler);
	}
}
