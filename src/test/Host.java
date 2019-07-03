package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Host extends Renter{
			
	// given list of [area, fromDate, toDate, dayPrice, owner, type, amenity]
	// return true if register successfully
	public Boolean postListing(List<String> info) {
		Boolean success = false;
		if(this.active && this.type.equals(2)) {
			String table = "listing";
			String cols = "date, area, fromDate, toDate, dayPrice, owner, type, amenity";
			String vals = "NOW(), '" + info.get(0) + "', '" + info.get(1) + "', '" + info.get(2) + "', '" + 
					info.get(3) + "', '" + info.get(4) + "', '" + info.get(5) + "', '" + info.get(6) + "'";
			if(Database.insert(table, cols, vals)) success = true;
		}
		return success;
	}
	
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			Host me = new Host();
			List<String> info = Arrays.asList("qibowang7@outlook.com", "password");
			System.out.println(me.signIn(info));
			List<String> listing = Arrays.asList("10", "2019-05-03",  "2019-06-15", "50", me.id.toString(), "condo", "11111111111111");
			System.out.println(me.postListing(listing));
		}
		
		Database.disconnect();
    }
}










