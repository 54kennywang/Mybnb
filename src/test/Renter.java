package test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Renter extends User{

	// from renter to both
	// return true if becomeHost successfully; false if is already a host
	public Boolean becomeHost() {
		if(this.active && this.type.equals(1)) {
			// sample input: "sailers", "name = 'KENNY', num = -111", "id=35"
			if(Database.update("user", "type = 2", "id = " + this.id)){
				return true;
			}
		}
		return false;
	}

	// given list of [l_id, fromDate, toDate]
	// return true if bookListing successfully
	public Boolean bookListing(List<String> info) {
		Boolean success = false;
		if(this.active) {
			// add to "rented" table
			String table1 = "rented";
			String cols1 = "u_id, l_id, fromDate, toDate, status, date";
			String vals1 = this.id + ", " + info.get(0) + ", '" + info.get(1) + "', '" + 
							info.get(2) + "', " + "0, " + "NOW()";
			if(Database.insert(table1, cols1, vals1)) {
				// delete from "availability" table
				String table2 = "availability";
				List<LocalDate> dates = Listing.allDates(info.get(1), info.get(2));
				for (int i = 0; i < dates.size(); i ++){
					String conditions = "id=" + info.get(0) + " and avilDate='" + dates.get(i) + "'";
					if(!Database.delete(table2, conditions)) {
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
			Renter me = new Renter();
			
			List<String> cred = Arrays.asList("qibowang7@gmail.com", "password");
			System.out.println(me.signIn(cred));
			
			List<String> info = Arrays.asList("5", "2019-06-28", "2019-06-29");
			System.out.println(me.bookListing(info));
		}
		Database.disconnect();
    }
}
