package test;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class AddrToCoor {
	public static void getAddressByGpsCoordinates() throws Exception {
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBU9xbtYaDk0buYIs4Vti4-J2NqsZV0hmo");
//		GeocodingResult[] results =  GeocodingApi.geocode(context, "1600 Amphitheatre Parkway Mountain View, CA 94043").await();
		GeocodingResult[] results =  GeocodingApi.geocode(context, "38 Gladys Rd, Scarborough, ON M1C 1C6, Canada").await();
		System.out.println(results[0].formattedAddress);
		System.out.println(results[0].geometry.location.lat);
		System.out.println(results[0].geometry.location.lng);
	}
}
