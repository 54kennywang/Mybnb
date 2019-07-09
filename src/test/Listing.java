package test;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
			System.out.println("Price: $" + rs.getString("dayPrice"));
			System.out.println("Type: " + rs.getString("type"));
			System.out.println("Area: " + rs.getString("area") + " m^2");
			System.out.print("Amenities: ");
			Listing.printAmenity(parseAmenity(rs.getString("amenity")));

			System.out.print("Available: " );
			Listing.printAllAvailabilities(id);
			System.out.println();
		}
	}

	// given listing id
	// return a list of available dates for that listing
	public static List<LocalDate> allAvailabilities(int id) throws SQLException {
		String query = "select * from availability where id = " + id;
		ResultSet rs = Database.queryRead(query);
		List<LocalDate> avilDates = new ArrayList<LocalDate>();
		CachedRowSet rowset = new CachedRowSetImpl();
		rowset.populate(rs);
		while (rowset.next()) {
			LocalDate date = LocalDate.parse(rowset.getString("avilDate"));
			avilDates.add(date);
		}
		return avilDates;
	}

	// given listing id
	// print all available dates for that listing
	public static void printAllAvailabilities(int id) throws SQLException{
		List<LocalDate> allDates = Listing.allAvailabilities(id);
		for(int i = 0; i < allDates.size(); i ++){
			System.out.print(allDates.get(i) + " ");
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
	
	// given two dates, return all dates in between (inclusive)
	// sample: ("2019-05-03", "2019-05-05") return ["2019-05-03", "2019-05-04", "2019-05-05"]
	public static List<LocalDate> allDates(String fromDate, String toDate){
		final LocalDate start = LocalDate.parse(fromDate);
		final LocalDate end = LocalDate.parse(toDate);
		LocalDate date = start;
		List<LocalDate> result = new ArrayList<LocalDate>();
		for (date = start; date.isBefore(end); date = date.plusDays(1)){
			result.add(date);
		}
		result.add(date);
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
