package test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class AddrToCoor {
	public static List<Object> getAddressByGpsCoordinates(String addr) throws Exception {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBU9xbtYaDk0buYIs4Vti4-J2NqsZV0hmo");
//		GeocodingResult[] results =  GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
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
	
	public static void main( String args[] ) throws Exception {
		List<Object> info = getAddressByGpsCoordinates("38 Gladys Rd Scarboroug ON M1C 1C6 Canada");
		System.out.println(info.get(0));
		System.out.println(info.get(1));
		System.out.println(info.get(2));
	}
}
