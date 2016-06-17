package org.motechproject.OpenXCDataInterface.util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * 
 * Class Name : GeoCodingUtility
 * Purpose    : This class will fetch the address through Google API by taking latitude and longitude as Inputs
 *
 */

public class GeoCodingUtility {
	
		public static String getAddress(String latlong){

		String address = null;
		String gURL = "http://maps.google.com/maps/api/geocode/xml?latlng=" + latlong + "&sensor=true";

		try {

		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = df.newDocumentBuilder();
		Document dom = db.parse(gURL);
		
		Element docEl = dom.getDocumentElement();
		NodeList nl = docEl.getElementsByTagName("result");

		if (nl != null && nl.getLength() > 0){
		    address=((Element)nl.item(0)).getElementsByTagName("formatted_address").item(0).getTextContent();
		}

		} catch (Exception E) {
		    E.printStackTrace();
		    address = "Exception in GEO Coding API";
		}

		return address;

		}


		public static String getAddress(String lat, String lon) {
		return getAddress(lat+ "," + lon);
		}
		
}







