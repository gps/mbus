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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Represents an instance of the live time of arrival feed.
 * 
 * @author gopalkri
 */
public class TimeFeed {

	public static boolean PRINT_DEBUG_OUTPUT = false;

	/**
	 * Downloads the live feed xml file, parses it and initializes this object
	 * with data from the feed.
	 * 
	 * @throws MBusDataException
	 *             If an error occurs while downloading and parsing feed.
	 */
	public TimeFeed() throws MBusDataException {
		try {
			mFeedUrl = new URL(FEED_URL);
			refresh();
		} catch (MalformedURLException e) {
			throw new MBusDataException("MBus URL is invalid.", e);
		} catch (SAXException e) {
			throw new MBusDataException("Error in parsing feed.", e);
		} catch (ParserConfigurationException e) {
			throw new MBusDataException("Error in parsing feed.", e);
		} catch (IOException e) {
			throw new MBusDataException("Error in opening MBus URL.", e);
		}
	}

	/**
	 * Refreshes the data in this feed by re-downloading the live feed.
	 * 
	 * @throws IOException
	 *             If a problem occurred while fetching the feed.
	 * @throws SAXException
	 *             If a problem occurred while parsing XML.
	 * @throws ParserConfigurationException
	 *             If a problem occurred while parsing XML.
	 */
	public void refresh() throws IOException, SAXException,
			ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document doc = docBuilder.parse(mFeedUrl.openStream());
		doc.getDocumentElement().normalize();

		NodeList routes = doc.getElementsByTagName("route");
		if (PRINT_DEBUG_OUTPUT) {
			System.out.println("Number of Routes: " + routes.getLength());
		}

		for (int r = 0; r < routes.getLength(); ++r) {
			Node route = routes.item(r);
			if (route.getNodeType() == Node.ELEMENT_NODE) {
				mRoutes.add(new Route((Element) route));
			}
		}
	}
	
	/**
	 * Gets list of currently operational routes.
	 * @return List of currently operational routes.
	 */
	public List<Route> getRoutes() {
		return mRoutes;
	}
	
	/**
	 * Returns route object with name routeName. Returns null if not found.
	 * @param routeName Name of route.
	 * @return Route object with name routeName. Null if not found.
	 */
	public Route getRouteWithName(String routeName) {
		for (Route route : mRoutes) {
			if (route.getName().compareTo(routeName) == 0) {
				return route;
			}
		}
		return null;
	}

	private URL mFeedUrl;
	private ArrayList<Route> mRoutes = new ArrayList<Route>();

	//private static final String FEED_URL = "http://www-personal.umich.edu/~gopalkri/public_feed.xml";
	private static final String FEED_URL = "http://mbus.pts.umich.edu/shared/public_feed.xml";
}
