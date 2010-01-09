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
 * Sends a problem report to server.
 * 
 * @author gopalkri
 * 
 */
public class ProblemReportSender extends ReportSender {

	private static final String PROBLEM_REPORT_URL = "http://mbus-android.appspot.com/problem";

	/**
	 * Constructs this object with parameters.
	 * 
	 * @param description
	 *            Description of problem.
	 * @param user_email
	 *            Email id of user, not sent if null.
	 * @param handler
	 *            Handler to message after sending is done. If null, no message
	 *            is sent.
	 */
	public ProblemReportSender(String description, String user_email,
			Handler handler) {
		super(PROBLEM_REPORT_URL, generateReport(description, user_email),
				handler);
	}

	/**
	 * Generates xml report from raw data.
	 * 
	 * @param description
	 *            Description of problem.
	 * @param user_email
	 *            Email id of user, not sent if null.
	 * @return XML report from raw data passed in.
	 */
	private static String generateReport(String description, String user_email) {
		StringBuilder sb = new StringBuilder();
		sb.append("<description>");
		sb.append(description);
		sb.append("</description>");
		if (user_email != null) {
			sb.append("<user_email>");
			sb.append(user_email);
			sb.append("</user_email>");
		}
		return sb.toString();
	}
}
