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

import android.view.Menu;

/**
 * Contains all constants used in the edu.umich.mbus.android package.
 * 
 * @author gopalkri
 * 
 */
public class Constants {
	public static final String ROUTE_NAME = "ROUTE_NAME";
	public static final String STOP_NAME = "STOP_NAME";
	public static final String STOP_VIEW_TYPE = "STOP_VIEW_TYPE";

	public static final int STOPS_ROUTE = 1;
	public static final int STOPS_FAVORITES = 2;
	public static final int STOPS_NEAR_ME = 3;

	public static final int REFRESH_MENU_ID = Menu.FIRST + 1;
	public static final int HELP_MENU_ID = Menu.FIRST + 2;
	public static final int REPORT_PROBLEM_MENU_ID = Menu.FIRST + 3;
	public static final int FAVORITE_CONTEXT_MENU_ID = Menu.FIRST + 4;
	
	public static final String USAGE_REPORT_ROUTES = "routes";
	public static final String USAGE_REPORT_STOPS = "stops";
	public static final String USAGE_REPORT_STOP_DETAILS = "stop_details";
	public static final String USAGE_REPORT_FAVORITES = "favorites";
	public static final String USAGE_REPORT_NEAR_ME = "near_me";
}
