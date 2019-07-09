package test;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
	public Boolean bookListing(List<String> info) throws SQLException {
		Boolean success = false;
		if(this.active) {
			// add to "rented" table
			String query = "select * from listing where id = " + info.get(0);
			ResultSet rs = Database.queryRead(query);
			Double dayPrice = null;
			if(rs.next()) {
				dayPrice = rs.getDouble("dayPrice");
			}
			String table1 = "rented";
			String cols1 = "u_id, l_id, fromDate, toDate, status, date, dayPrice";
			String vals1 = this.id + ", " + info.get(0) + ", '" + info.get(1) + "', '" + 
							info.get(2) + "', " + "0, " + "NOW()" + ", " + dayPrice;
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
	
	// initial comment on a listing
	// given a list of commentInfo: [receiver, rating, content, l_id]
	// return true if successfully
	public Boolean commentOnListing(List<String> info) {
		Boolean success = false;
		if(this.active) {
			// add to "listing_comment" table
			String table = "listing_comment";
			String cols = "l_id, sender, receiver, parent_comment, rating, content, date";
			String vals = info.get(3) + "," + this.id + ", " + info.get(0) + ", null, " + info.get(1) + ", '" + 
					info.get(2) + "', " + "NOW()" ;
			if(Database.insert(table, cols, vals)) success =true;
		}
		return success;
	}

	// get all my bookings (history + future bookings)
	public CachedRowSet getAllBookings() throws SQLException{
		String query = "SELECT * FROM rented where u_id = " + this.id + ";";
		ResultSet rs = Database.queryRead(query);
		CachedRowSet rowset = new CachedRowSetImpl();
		rowset.populate(rs);
		return rowset;
	}

	// get all history bookings
	public CachedRowSet getAllHistoryBookings() throws SQLException{
		String query = "SELECT * FROM rented where u_id = " + this.id + " and status = 1;";
		ResultSet rs = Database.queryRead(query);
		CachedRowSet rowset = new CachedRowSetImpl();
		rowset.populate(rs);
		return rowset;
	}

	// get all future bookings
	public CachedRowSet getAllFutureBookings() throws SQLException{
		String query = "SELECT * FROM rented where u_id = " + this.id + " and status = 0;";
		ResultSet rs = Database.queryRead(query);
		CachedRowSet rowset = new CachedRowSetImpl();
		rowset.populate(rs);
		return rowset;
	}

	// initial comment on a host
	// given a list of commentInfo: [receiver, rating, content]
	// return true if successfully
	@Override
	public Boolean commentOnUser(List<String> info) throws SQLException {
		Boolean legal = false; // legal to comment on that user?
		CachedRowSet rowset = this.getAllHistoryBookings();
		while (rowset.next()) {
			Integer l_id = rowset.getInt("l_id");
			if(Listing.getOwnerId(l_id) == Integer.parseInt(info.get(0))){
				legal = true;
				break;
			}
		}
		if(!legal) return false;

		Boolean success = false;
		if(this.active) {
			// add to "user_comment" table
			String table = "user_comment";
			String cols = "sender, receiver, parent_comment, rating, content, date";
			String vals = this.id + ", " + info.get(0) + ", null, " + info.get(1) + ", '" +
					info.get(2) + "', " + "NOW()" ;
			if(Database.insert(table, cols, vals)) success =true;
		}
		return success;
	}

	// given list of [l_id, fromDate, toDate, cancel_status]
	// return true if cancelBooking successfully
	@Override
	public Boolean cancelBooking(List<String> info) {
		Boolean success = false;
		if(this.active) {
			// update "rented (status)" table
			String table1 = "rented";
			String newInfo = "status = -1";
			String conditions = "u_id=" + this.id + " and l_id=" + info.get(0) + " and fromDate = '" + info.get(1) 
								+ "' and toDate='" + info.get(2) + "'";
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
			Renter me = new Renter();
			
			List<String> cred = Arrays.asList("michael@outlook.com", "password");
			System.out.println(me.signIn(cred));
			
//			List<String> info = Arrays.asList("10", "2020-07-01", "2020-07-02");
//			System.out.println(me.bookListing(info));

//			List<String> info = Arrays.asList("5", "2019-07-01", "2019-07-02");
//			System.out.println(me.cancelBooking(info));
			
			List<String> info = Arrays.asList("3", "5", "this Apt is good.", "10");
			System.out.println(me.commentOnListing(info));
		}
		Database.disconnect();
    }

}








