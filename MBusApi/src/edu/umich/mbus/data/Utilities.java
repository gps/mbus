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

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Generic utility functions.
 * @author gopalkri
 */
public class Utilities {
	
	public static String getStringValueByTagName(Element element, String tagName) {
		NodeList nl1 = element.getElementsByTagName(tagName);
		if (nl1.getLength() <= 0) {
			return null;
		}
		Element el = (Element) nl1.item(0);
		return el.getChildNodes().item(0).getNodeValue();
	}
}
