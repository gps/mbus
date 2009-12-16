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
