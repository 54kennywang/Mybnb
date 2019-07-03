package test;

import java.sql.ResultSet;
import java.util.ArrayList;
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

	// [id, fromDate, toDate]
	// change availability to (id, days)
	/*
	 	1 2019-05-03
	 	1 2019-05-03
	 	1 2019-05-05
	 	1 2019-05-06
	 */
	public Boolean bookListing(List<String> info) {
		Boolean success = false;
		String query = "select * from rented where l_id" + id;
		ResultSet result = Database.queryRead(query);
		return success;
	}
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			Renter me = new Renter();
			List<String> info = new ArrayList<String>();
//			info.add("qibowang7@outlook.com");
//			info.add("1111222233334444");
//			info.add("Kenny Wang");
//			info.add("Student");
//			info.add("1996-06-22");
//			info.add("123456789");
//			info.add("password");
//			System.out.println(me.register(info));
			info.add("qibowang7@outlook.com");
			info.add("password");
			System.out.println(me.signIn(info));
			System.out.println(me.becomeHost());
		}
		Database.disconnect();
    }
}
