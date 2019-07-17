package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

public class Host extends Renter {

    // given houseInfo: [area, fromDate, toDate, dayPrice, owner, type, amenity]
    // given addrInfo: [street, city, pcode, country]
    // return true if register successfully
    public Boolean postListing(List<String> houseInfo, List<String> addrInfo) throws Exception {
        Boolean success = false;
        if (this.active && this.type.equals(2)) {
            // insert listing
            String table1 = "listing";
            String cols1 = "date, area, dayPrice, owner, type, amenity";
            String vals1 = "NOW(), " + houseInfo.get(0) + ", " + houseInfo.get(3) + ", " +
                    houseInfo.get(4) + ", '" + houseInfo.get(5) + "', '" + houseInfo.get(6) + "'";

            if (Database.insert(table1, cols1, vals1)) {
                // insert availability
                String query = "SELECT LAST_INSERT_ID()";
                ResultSet result = Database.queryRead(query);
                Integer newID = null;
                if (result.next()) {
                    newID = result.getInt("LAST_INSERT_ID()");
                    String table2 = "availability";
                    String cols2 = "id, avilDate";

                    List<LocalDate> dates = Listing.allDates(houseInfo.get(1), houseInfo.get(2));
                    for (int i = 0; i < dates.size(); i++) {
                        String vals2 = newID + ", '" + dates.get(i) + "'";
                        if (!Database.insert(table2, cols2, vals2)) {
                            return false;
                        }
                    }

                    // insert address
                    if (Database.insertAddr(addrInfo, newID, 0)) success = true;
                }
            }
        }
        return success;
    }

    @Override
    public Boolean becomeHost() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean bookListing(List<String> info) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Boolean commentOnListing(List<String> info) {
        throw new UnsupportedOperationException();
    }

    // given list of [l_id, fromDate, toDate]
    // return true if cancelBooking successfully
    @Override
    public Boolean cancelBooking(List<String> info) {
        Boolean success = false;
        if (this.active) {
            // update "rented (status)" table
            String table1 = "rented";
            String newInfo = "status = -2";
            String conditions = "l_id=" + info.get(0) + " and fromDate = '" + info.get(1)
                    + "' and toDate='" + info.get(2) + "' and status = 0";
            if (Database.update(table1, newInfo, conditions)) {
                // recover "availability" table
                String table2 = "availability";
                String cols2 = "id, avilDate";
                List<LocalDate> dates = Listing.allDates(info.get(1), info.get(2));
                for (int i = 0; i < dates.size(); i++) {
                    String vals2 = info.get(0) + ", '" + dates.get(i) + "'";
                    if (!Database.insert(table2, cols2, vals2)) {
                        return false;
                    }
                }
                success = true;
            }
        }
        return success;
    }

    // given list of [l_id, newPrice]
    // return true if successfully
    public Boolean updatePrice(List<String> info) throws SQLException {
        if(!this.getAllMyListing().contains(info.get(0))) return  false;
        Boolean success = false;
        if (this.active && this.type.equals(2)) {
            String table = "listing";
            String newInfo = "dayPrice = " + info.get(1);
            String conditions = "id = " + info.get(0);
            if (Database.update(table, newInfo, conditions)) success = true;
        }
        return success;
    }

    // given list of [l_id, from1, to1, from2, to2, ...]
    // return true if successfully
    public Boolean updateAvailability(List<String> info) throws SQLException {
        if(!this.getAllMyListing().contains(info.get(0))) return  false;
        Boolean success = false;
        if (this.active && this.type.equals(2)) {
            // delete existing availabilities
            String table = "availability";
            String conditions = "id = " + info.get(0);
            Database.delete(table, conditions);
            // add new availabilities
            for (int i = 1; i < info.size(); i++) {
                String table2 = "availability";
                String cols2 = "id, avilDate";
                List<LocalDate> dates = Listing.allDates(info.get(i), info.get(++i));
                for (int j = 0; j < dates.size(); j++) {
                    String vals2 = info.get(0) + ", '" + dates.get(j) + "'";
                    if (!Database.insert(table2, cols2, vals2)) {
                        return false;
                    }
                }
            }
            success = true;
        }
        return success;
    }

    // return a list of ID's of my listing
    public List<Integer> getAllMyListing() throws SQLException {
        String query = "SELECT * FROM listing where owner = " + this.id + ";";
        ResultSet rs = Database.queryRead(query);
        List<Integer> allIDs = new ArrayList<Integer>();
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        while (rowset.next()) {
            Integer id = rowset.getInt("id");
            allIDs.add(id);
        }
        return allIDs;
    }


    // print all my listings
    public void viewAllMyListing() throws SQLException {
        List<Integer> allMyListings = this.getAllMyListing();
        for(int i = 0; i < allMyListings.size(); i ++){
            System.out.println("==== Listing ID: " + allMyListings.get(i) + " ====");
            Listing.viewListing(allMyListings.get(i));
        }
    }


    // initial comment on a Renter
    // given a list of commentInfo: [receiver, rating, content]
    // return true if successfully
    @Override
    public Boolean commentOnUser(List<String> info) throws SQLException {
        Boolean legal = false; // legal to comment on that user?
        CachedRowSet rowset = this.getAllHistoryBookings();
        while (rowset.next()) {
            Integer u_id = rowset.getInt("u_id");
            if(u_id == Integer.parseInt(info.get(0))){
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

    // reply to a initial comment on a user
    // given a list of commentInfo: [receiver, parent_comment, content, l_id]
    // return true if successfully
    public Boolean replyListingComment(List<String> info) {
        Boolean success = false;
        if (this.active) {
            // add to "listing_comment" table
            String table = "listing_comment";
            String cols = "l_id, sender, receiver, parent_comment, rating, content, date";
            String vals = info.get(3) + ", " + this.id + ", " + info.get(0) + ", " + info.get(1) + ", null, '"
                    + info.get(2) + "', " + "NOW()";
            if (Database.insert(table, cols, vals)) success = true;
        }
        return success;
    }

    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            Host me = new Host();
            List<String> cred = Arrays.asList("qibowang7@outlook.com", "password");
            System.out.println(me.signIn(cred));

//			List<String> info = Arrays.asList("4", "2019-07-02", "2019-07-02");
//			System.out.println(me.cancelBooking(info));

            // given houseInfo: [area, fromDate, toDate, dayPrice, owner, type, amenity]
            // given addrInfo: [street, city, pcode, country]
			List<String> houseInfo = Arrays.asList("177", "2019-07-29", "2019-08-02", "99", me.getId().toString(), "Room", "01010111010100");
			List<String> addrInfo = Arrays.asList("701 Military", "Toronto", "ON M1E 4P6", "Canada");
			System.out.println(me.postListing(houseInfo, addrInfo));

//			List<String> info = Arrays.asList("10", "19.99");
//			System.out.println(me.updatePrice(info));

//			List<String> info = Arrays.asList("10", "2020-06-02", "2020-06-05", "2020-07-29", "2020-08-02");
//			System.out.println(me.updateAvailability(info));

//			List<String> info = Arrays.asList("6", "1", "thx for the comment: this Apt is good.", "10");
//			System.out.println(me.replyListingComment(info));

//            me.viewAllMyListing();

//			List<String> info = Arrays.asList("6", "0", "This user never come!!!");
//			System.out.println(me.commentOnUser(info));
        }
        Database.disconnect();
    }
}










