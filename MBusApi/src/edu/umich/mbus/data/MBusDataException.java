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

package edu.umich.mbus.data;

/**
 * Base exception type of MBus data exception.
 * @author gopalkri
 */
public class MBusDataException extends Exception {

	/**
	 * Eclipse says I should create this.
	 */
	private static final long serialVersionUID = -4854189516802075400L;

	public MBusDataException(String message, Throwable cause) {
		super(message, cause);
	}

	public MBusDataException(String message) {
		super(message);
	}
}
