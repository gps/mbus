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

import edu.umich.mbus.data.MBusDataException;
import edu.umich.mbus.data.Route;
import edu.umich.mbus.data.TimeFeed;

/**
 * Test harness for Magic Bus Data package.
 */

/**
 * @author gopalkri
 */
public class Main {

	/**
	 * @param args
	 *            Command line arguments - ignored.
	 */
	public static void main(String[] args) {
		try {
			//TimeFeed.PRINT_DEBUG_OUTPUT = true;
			TimeFeed tf = new TimeFeed();
			
			for (Route route : tf.getRoutes()) {
				System.out.println(route);
			}
		} catch (MBusDataException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
