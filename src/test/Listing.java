package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Listing {
	// 0, 1, ..., 13
	public static List<String> amenities = Arrays.asList(
			"WIFI", "TV", "AC", "Microwave", "Laundry", "Refrigerator", "Hair Dryer", "Iron", 
			"Hangers", "Fire extinguisher", "Coffee Maker", "Dishwasher", "Oven", "BBQ Grill");
	
	// input listing_id, print its corresponding info
	public static void viewListing(int id) throws SQLException {
		String query = "select * from listing where id = " + id;
		ResultSet rs = Database.queryRead(query);
		if(rs.next()) {
			System.out.println("Area: " + rs.getString("area"));
			System.out.println("Available: " + rs.getString("fromDate") + " - " + rs.getString("toDate"));
			System.out.println("Price: $" + rs.getString("dayPrice"));
			System.out.println("Type: " + rs.getString("type"));
			System.out.println("Area: " + rs.getString("area") + "m^2");
			System.out.print("Amenities: ");
			printAmenity(parseAmenity(rs.getString("amenity")));
		}
	}
	
	// print a list of amenities
	public static void printAmenity(List<String> amen) {
		StringBuilder result = new StringBuilder("");
		for(int i = 0; i < amen.size(); i ++) {
			result.append(amen.get(i)).append(" ");
		}
		System.out.println(result);
	}
	
	// given 101101010101..., based on amenities above, return a list of amenities
	public static List<String> parseAmenity(String amen) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < amen.length(); i++){
		    if(amen.charAt(i) == '1') {
		    	result.add(amenities.get(i));
		    }
		}
		return result;
	}
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			Host me = new Host();
			List<String> info = Arrays.asList("qibowang7@outlook.com", "password");
			System.out.println(me.signIn(info));
			viewListing(1);
		}
		
		Database.disconnect();
    }
}
