package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;

public class Map {
	// given address (may not formatted), return [street, city, pcode, country, lng, lat]
	public static List<Object> getAllByAddr(String addr) throws Exception {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBU9xbtYaDk0buYIs4Vti4-J2NqsZV0hmo");
		GeocodingResult[] results =  GeocodingApi.geocode(context, addr).await();
		
		List<Object> info = addrStringtoAddrList(results[0].formattedAddress);
		if(info == null) {
			if (results.length > 0) {
				info = new ArrayList<Object>();
				GeocodingResult result = results[0];
				String street = null;
				String city = null;
				String country = null;
				String pcode = null;
				for (AddressComponent component : result.addressComponents) {
					List<AddressComponentType> types = Arrays.asList(component.types);
					if (types.contains(AddressComponentType.COUNTRY)) {
//						System.out.println("country: " + component.longName);
						country = component.longName;
					}
					if (types.contains(AddressComponentType.LOCALITY)) {
//						System.out.println("city: " + component.longName);
						city = component.longName;
					}
					if (types.contains(AddressComponentType.POSTAL_CODE)) {
//						System.out.println("pcode: " + component.longName);
						pcode = component.longName;
					}
					if (types.contains(AddressComponentType.ROUTE)) {
//						System.out.println("route: " + component.longName);
						street += component.longName;
					}
					if (types.contains(AddressComponentType.STREET_NUMBER)) {
//						System.out.println("st num: " + component.longName);
						street = component.longName + " ";
					}
				}
				info.add(street);
				info.add(city);
				info.add(pcode);
				info.add(country);
				info.add(results[0].geometry.location.lng);
				info.add(results[0].geometry.location.lat);
//				System.out.println("=== test: " + results[0].addressComponents[0].types[0]);
			}
//			System.out.println(results[0].formattedAddress);
		}
		else {
			info.add(results[0].geometry.location.lng);
			info.add(results[0].geometry.location.lat);
		}
		if(info.size() == 6) return info;
		else return null;
	}
	
	
	public static List<Object> addrStringtoAddrList(String addr){
		String[] resultArray = addr.trim().split("\\s*,\\s*");
		List<Object> result = new ArrayList<Object>(Arrays.asList(resultArray));
		if(result.size() == 5) result.remove(0);
		else if (result.size() != 4) return null;
		return result;
	}
	
	// given (lng, lat), return (String) formatted_addr 
	@SuppressWarnings("finally")
	public static String getAllByAddr(Double lng, Double lat) throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
		URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyBU9xbtYaDk0buYIs4Vti4-J2NqsZV0hmo&latlng="
				+ lat + "," + lng + "&sensor=true&language=en");
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		String formattedAddress = "";

		try {
			InputStream in = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String result, line = reader.readLine();
			result = line;
			while ((line = reader.readLine()) != null) {
				result += line;
			}
			
			JSONParser parser = new JSONParser();
			JSONObject rsp = (JSONObject) parser.parse(result);
			
			if (rsp.containsKey("results")) {
				JSONArray matches = (JSONArray) rsp.get("results");
				JSONObject data = (JSONObject) matches.get(0);
				formattedAddress = (String) data.get("formatted_address");
//				System.out.println(data.get("address_components"));
			}
			return "";
		} finally {
			urlConnection.disconnect();
			return formattedAddress;
		}
	}

	// given (lng1, lat1) & (lng2, lat2) & 'K'/'M', return distance between these two points
	public static double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == 'K') {
			dist = dist * 1.609344;
		} else if (unit == 'N') {
			dist = dist * 0.8684;
		}
		return (dist);
	}
	// helper functions for distance
	public static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	public static double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
	
	//	given addrInfo: [street, city, pcode, country]
	// return the string of formatted_addr
	public static String infoToAddr(List<String> addrInfo) {
		return addrInfo.get(0) + ", " + addrInfo.get(1) + ", " + addrInfo.get(2) + ", " + addrInfo.get(3);
	}
	
	// test
	public static void main( String args[] ) throws Exception {
		
		List<Object> info0 = Map.getAllByAddr("1265 millitary trail Scarboroug ON M1C 1C6 Canada");
		System.out.println(info0 + "\n");

//		List<Object> info1 = Map.getAllByAddr("Hamburger Allee 22-24, 60486 Frankfurt am Main, Germany");
		List<Object> info1 = Map.getAllByAddr("Tobacco Quay, Wapping Ln, London, UK");
		System.out.println(info1 + "\n");
		
		List<Object> info2 = Map.getAllByAddr("777 Bay Street Market Level, Toronto, ON M7A 2J3");
		System.out.println(info2 + "\n");

//		System.out.println();
//		System.out.println(ong.getAllByAddr(-79.180840, 43.785702));
//
//		System.out.println();
//		System.out.println(ong.distance(43.783270, -79.203200, 43.785702, -79.180840, 'K'));
	}
}
