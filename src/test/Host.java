package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
					String cols2 = "id, avilDate";

					List<LocalDate> dates = Listing.allDates(info.get(1), info.get(2));
					for (int i = 0; i < dates.size(); i ++){
						String vals2 = newID + ", '" + dates.get(i) + "'";
						if(!Database.insert(table2, cols2, vals2)) {
							return false;
						}
					}
					success = true;
				}
			}
		}
		return success;
	}
	
	
	@Override
	// given list of [l_id, fromDate, toDate]
	// return true if cancelBooking successfully
	public Boolean cancelBooking(List<String> info) {
		Boolean success = false;
		if(this.active) {
			// update "rented (status)" table
			String table1 = "rented";
			String newInfo = "status = -2";
			String conditions = "l_id=" + info.get(0) + " and fromDate = '" + info.get(1) 
								+ "' and toDate='" + info.get(2) + "' and status = 0";
			if(Database.update(table1, newInfo, conditions)) {
				// recover "availability" table
				String table2 = "availability";
				String cols2 = "id, avilDate";
				List<LocalDate> dates = Listing.allDates(info.get(1), info.get(2));
				for (int i = 0; i < dates.size(); i ++){
					String vals2 = info.get(0) + ", '" + dates.get(i) + "'";
					if(!Database.insert(table2, cols2, vals2)) {
						return false;
					}
				}
				success = true;
			}
		}
		return success;
	}
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			Host me = new Host();
			
			List<String> cred = Arrays.asList("qibowang7@outlook.com", "password");
			System.out.println(me.signIn(cred));
			
			List<String> info = Arrays.asList("4", "2019-07-02", "2019-07-02");
			System.out.println(me.cancelBooking(info));
		}
		Database.disconnect();
    }
}










