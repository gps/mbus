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
 * Provides an interface to send various reports to the report server.
 * 
 * @author gopalkri
 * 
 */
public class Reporter {

	/**
	 * Sends usage report, reporting that view was displayed.
	 * 
	 * @param view
	 *            View that was displayed.
	 */
	public static void sendUsageReport(String view, Handler handler) {
		new Thread(new UsageReportSender(view, handler)).start();
	}

	public static void sendProblemReport(String description, String user_email,
			Handler handler) {
		new Thread(new ProblemReportSender(description, user_email, handler))
				.start();
	}
}
