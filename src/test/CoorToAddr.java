package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class CoorToAddr {
	public static String getAddressByGpsCoordinates(String lng, String lat) throws MalformedURLException, IOException, org.json.simple.parser.ParseException {
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
			}
			return "";
		} finally {
			urlConnection.disconnect();
			return formattedAddress;
		}
	}
	
	public static void main( String args[] ) throws MalformedURLException, IOException, ParseException {
		System.out.println(getAddressByGpsCoordinates("-79.180840", "43.785700"));
    }
}
