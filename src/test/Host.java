package test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Host extends Renter{
			
	// given houseInfo: [area, fromDate, toDate, dayPrice, owner, type, amenity]
	// given addrInfo: [street, city, pcode, country]
	// return true if register successfully
	public Boolean postListing(List<String> houseInfo, List<String> addrInfo) throws Exception {
		Boolean success = false;
		if(this.active && this.type.equals(2)) {
			// insert listing
			String table1 = "listing";
			String cols1 = "date, area, dayPrice, owner, type, amenity";
			String vals1 = "NOW(), " + houseInfo.get(0) + ", " + houseInfo.get(3) + ", " + 
			houseInfo.get(4) + ", '" + houseInfo.get(5) + "', '" + houseInfo.get(6) + "'";
			
			if(Database.insert(table1, cols1, vals1)) {
				// insert availability
				String query = "SELECT LAST_INSERT_ID()";
				ResultSet result = Database.queryRead(query);
				Integer newID = null;
				if(result.next()) {
					newID = result.getInt("LAST_INSERT_ID()");
					String table2 = "availability";
					String cols2 = "id, avilDate";

					List<LocalDate> dates = Listing.allDates(houseInfo.get(1), houseInfo.get(2));
					for (int i = 0; i < dates.size(); i ++){
						String vals2 = newID + ", '" + dates.get(i) + "'";
						if(!Database.insert(table2, cols2, vals2)) {
							return false;
						}
					}
					
					// insert address
					if(Database.insertAddr(addrInfo, newID, 0)) success = true;
				}
			}
		}
		return success;
	}
	
	
	// given list of [l_id, fromDate, toDate]
	// return true if cancelBooking successfully
	@Override
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
			
//			List<String> info = Arrays.asList("4", "2019-07-02", "2019-07-02");
//			System.out.println(me.cancelBooking(info));
			
			List<String> houseInfo = Arrays.asList("19", "2020-06-29", "2020-07-02", "50", me.getId().toString(), "Apt", "01010111010100");
			List<String> addrInfo = Arrays.asList("30 Saint Mary Axe - Swiss Re", "30 Mary Axe", "London EC3A 8EP", "UK");
			System.out.println(me.postListing(houseInfo, addrInfo));
		}
		Database.disconnect();
    }
}










