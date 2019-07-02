package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class Map {
	// given address (may not formatted), return [formatted_addr, lng, lat]
	public List<Object> getAddressByGpsCoordinates(String addr) throws Exception {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBU9xbtYaDk0buYIs4Vti4-J2NqsZV0hmo");
		GeocodingResult[] results =  GeocodingApi.geocode(context, addr).await();
		List<Object> info = new ArrayList<Object>();
//		System.out.println(results[0].formattedAddress);
//		System.out.println(results[0].geometry.location.lat);
//		System.out.println(results[0].geometry.location.lng);
		info.add(results[0].formattedAddress);		
		info.add(results[0].geometry.location.lng);
		info.add(results[0].geometry.location.lat);
		return info;
	}
	
	// given (lng, lat), return (String)formatted_addr 
	@SuppressWarnings("finally")
	public String getAddressByGpsCoordinates(Double lng, Double lat) throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
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
	public double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
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
	public double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}
	public double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	// test
	public static void main( String args[] ) throws Exception {
		Map ong = new Map();
		
		List<Object> info = ong.getAddressByGpsCoordinates("38 Gladys Rd Scarboroug ON M1C 1C6 Canada");
		System.out.println(info.get(0));
		System.out.println(info.get(1));
		System.out.println(info.get(2));
		
		System.out.println(ong.getAddressByGpsCoordinates(-79.180840, 43.785702));
		
		System.out.println(ong.distance(43.783270, -79.203200, 43.785702, -79.180840, 'K'));
	}
}
