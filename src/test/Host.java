package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Host extends Renter{
			
	// given list of [area, fromDate, toDate, dayPrice, owner, type, amenity]
	// return true if register successfully
	public Boolean postListing(List<String> info) throws SQLException {
		Boolean success = false;
		if(this.active && this.type.equals(2)) {
			String table1 = "listing";
			String cols1 = "date, area, dayPrice, owner, type, amenity";
			String vals1 = "NOW(), " + info.get(0) + ", " + info.get(3) + ", " + 
			info.get(4) + ", '" + info.get(5) + "', '" + info.get(6) + "'";
			
			
			if(Database.insert(table1, cols1, vals1)) {
				String query = "SELECT LAST_INSERT_ID()";
				ResultSet result = Database.queryRead(query);
				if(result.next()) {
					int newID = result.getInt("LAST_INSERT_ID()");
					String table2 = "availability";
					String cols2 = "id, fromDate, toDate";
					String vals2 = newID + ", '" + info.get(1) + "', '" + info.get(2) + "'";
					if(Database.insert(table2, cols2, vals2)) {
						success = true;
					}
				}
			}
		}
		return success;
	}
	
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			Host me = new Host();
			List<String> info = Arrays.asList("qibowang7@outlook.com", "password");
			System.out.println(me.signIn(info));
			List<String> listing = Arrays.asList("20", "2019-05-03",  "2019-06-15", "50", me.id.toString(), "condo", "10110100100111");
			System.out.println(me.postListing(listing));
		}
		
		Database.disconnect();
    }
}










