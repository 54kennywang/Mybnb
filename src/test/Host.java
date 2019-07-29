package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;

import static test.Database.queryRead;

public class Host extends Renter {

    /**
     * Post a listing as a host
     *
     * @param houseInfo [area, fromDate, toDate, dayPrice, owner, type, amenity]
     * @param addrInfo  [street, city, pcode, country]
     * @return true if post successfully
     */
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
                ResultSet result = queryRead(query);
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

//    @Override
//    public Boolean becomeHost() {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Boolean bookListing(List<String> info) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public Boolean commentOnListing(List<String> info) {
//        throw new UnsupportedOperationException();
//    }

    /**
     * Cancel a booking as a host
     *
     * @param info [l_id, fromDate, toDate]
     * @return true if cancelBooking successfully; false otherwise
     */
    /*
    @Override
    public Boolean cancelBooking(List<String> info) throws SQLException {
        Boolean legal = false; // legal to cancel this booking?
        if (Listing.getOwnerId(Integer.parseInt(info.get(0))) != this.id) return false;

        String query = "SELECT * FROM mydb.rented where status = 0 and l_id = " + info.get(0) +
                " and fromDate = '" + info.get(1) +
                "' and toDate = '" + info.get(2) + "'; ";
        ResultSet rowset = Database.queryRead(query);
        while (rowset.next()) {
            legal = true;
            break;
        }
        if (!legal) return false;


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
    */

    /**
     * Update an amenity after posting a listing
     * @return true if update successfully, false otherwise
     */
    public boolean updateAmenities(List<String> info) throws SQLException{
        if (Listing.getOwnerId(Integer.parseInt(info.get(0))) != this.id) return false;
        Boolean success = false;
        if (this.active && this.type.equals(2)) {
            String table = "listing";
            String newInfo = "amenity = " + info.get(1);
            String conditions = "id = " + info.get(0);
            if (Database.update(table, newInfo, conditions)) success = true;
        }
        return success;
    }

    /**
     * Update price of a listing
     *
     * @param info [l_id, newPrice]
     * @return true if successfully; false otherwise
     */
    public Boolean updatePrice(List<String> info) throws SQLException {
        if (Listing.getOwnerId(Integer.parseInt(info.get(0))) != this.id) return false;
//        if (!this.getAllMyListing().contains(info.get(0))) return false;
        Boolean success = false;
        if (this.active && this.type.equals(2)) {
            String table = "listing";
            String newInfo = "dayPrice = " + info.get(1);
            String conditions = "id = " + info.get(0);
            if (Database.update(table, newInfo, conditions)) success = true;
        }
        return success;
    }

    /**
     * Update availabilities of a listing
     *
     * @param info [l_id, from1, to1, from2, to2, ...]
     * @return true if successfully; false otherwise
     * sample: ("10", "2020-06-02", "2020-06-05", "2020-07-29", "2020-08-02") add two slots availabilities to listing 10
     */
    public Boolean updateAvailability(List<String> info) throws SQLException {
        if (Listing.getOwnerId(Integer.parseInt(info.get(0))) != this.id) return false;
//        if (!this.getAllMyListing().contains(info.get(0))) return false;

        List<LocalDate> newDates = new ArrayList<LocalDate>();
        for (int i = 1; i < info.size(); i++) {
            List<LocalDate> dates = Listing.allDates(info.get(i), info.get(++i));
            newDates.addAll(dates);
        }
//        System.out.println("want to add: " + newDates);
        List<LocalDate> unAvail = Listing.allUnavailabilities(Integer.parseInt(info.get(0)));
//        System.out.println("unavailable: " + unAvail);

        unAvail.retainAll(newDates);

//        System.out.println("conflicts: " + unAvail);

        if (unAvail.size() > 0) return false;

        Boolean success = false;
        if (this.active && this.type.equals(2)) {
            // delete existing availabilities
            String table = "availability";
            String conditions = "id = " + info.get(0);
            Database.delete(table, conditions);
            // add new availabilities
            for (int i = 0; i < newDates.size(); i++) {
                String table2 = "availability";
                String cols2 = "id, avilDate";
                String vals2 = info.get(0) + ", '" + newDates.get(i) + "'";
                if (!Database.insert(table2, cols2, vals2)) {
                    return false;
                }
            }
            success = true;
        }

        return success;
    }

    /**
     * Get a list of ID's of my listing
     *
     * @return a list of ID's of my listing
     */
    public List<Integer> getAllMyListing() throws SQLException {
        String query = "SELECT * FROM listing where owner = " + this.id + ";";
        ResultSet rs = queryRead(query);
        List<Integer> allIDs = new ArrayList<Integer>();
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        while (rowset.next()) {
            Integer id = rowset.getInt("id");
            allIDs.add(id);
        }
        return allIDs;
    }


    /**
     * print all my listings
     *
     * @return 0 if no listing exists; 1 otherwise
     */
    public int viewAllMyListing() throws SQLException {
        List<Integer> allMyListings = this.getAllMyListing();
        if(allMyListings.size() == 0) {
            System.out.println("***No listings found***");
            return 0;
        }
        for (int i = 0; i < allMyListings.size(); i++) {
//            System.out.println("==== Listing ID: " + allMyListings.get(i) + " ====");
            Listing.viewListing(allMyListings.get(i));
            System.out.println("\n");
        }
        return 1;
    }

    public boolean deleteMyselfAsHost() throws SQLException {
        CachedRowSet crs = this.getBookings(0);
        if(crs.next()) return false;
        if(getMyRenterBookingsOfMyListings(0).size() != 0) return false;
        String table = "user";
        String conditions = "id = " + this.id;
        if(Database.delete(table, conditions)) return true;
        else return false;
    }

    /**
     * Get renter's bookings on a host's listings (from host's perspective, who booked my listing) based on type
     *
     * @param type 1 for history booking; 0 for future booking; 2 for all (history + future bookings)
     * @return renter's bookings on a host's listings (history + future bookings)
     * [u_id, l_id, fromDate, toDate, status, date, dayPrice, owner]
     */
    public List<Row> getMyRenterBookingsOfMyListings(int type) throws SQLException{
        String query1 = "select * from (SELECT r.*, l.owner FROM rented r join listing l on r.l_id = l.id) s ";
        String query2 = "";
        if (type == 1) query2 = "where s.owner = " + this.id + " and s.status = 1;";
        else if (type == 0) query2 = "where s.owner = " + this.id + " and s.status = 0;";
        else if (type == 2) query2 = "where s.owner = " + this.id + " and (s.status = 0 or s.status = 1);";
        String query = query1 + query2;
        ResultSet rs = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    public void viewRentalHistoryOfMyListings(List<Row> table) throws SQLException{
        if(table.size() == 0) {
            System.out.println("***Sorry, no result found***");
        }
        for (int i = 0; i < table.size(); i++) {
            System.out.println("Renter ID:" + table.get(i).getColumnObject(1));
            System.out.println("Listing ID: " + table.get(i).getColumnObject(2));
            System.out.println("Renting period: " + LocalDate.parse(table.get(i).getColumnObject(3).toString()).plusDays(1)
                    + " - " + LocalDate.parse(table.get(i).getColumnObject(4).toString()).plusDays(1));
            System.out.println("Price: $" + table.get(i).getColumnObject(7) + " per day");
            System.out.println("==========");
        }
    }



    /**
     * Host's initial comment on a Renter
     *
     * @param info [receiver, rating, content]
     * @return true if successfully; false otherwise
     */
//    @Override
    public Boolean commentOnUser(List<String> info) throws SQLException {
        Boolean legal = false; // legal to comment on that user?
        List<Row> whoBookedMine = getMyRenterBookingsOfMyListings(1);
        for(int i = 0; i < whoBookedMine.size(); i ++){
            Integer u_id = Integer.parseInt(whoBookedMine.get(i).getColumnObject(1).toString());
            if (u_id == Integer.parseInt(info.get(0))) {
                legal = true;
                break;
            }
        }
        if (!legal) return false;

        Boolean success = false;
        if (this.active) {
            // add to "user_comment" table
            String table = "user_comment";
            String cols = "sender, receiver, parent_comment, rating, content, date";
            String vals = this.id + ", " + info.get(0) + ", null, " + info.get(1) + ", '" +
                    info.get(2) + "', " + "NOW()";
            if (Database.insert(table, cols, vals)) success = true;
        }
        return success;
    }

    /**
     * Host delete listing id
     *
     * @param l_id listing id
     * @return true if successfully; false otherwise
     */
    public boolean deleteListing(int l_id) throws SQLException {
        if(Listing.getOwnerId(l_id) != this.getId()){
            return false;
        }
        String query = "select * from listing l, rented r where l.id = r.l_id and r.status = 0 and r.l_id = "+l_id+";";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        List<Row> temp = Listing.CachedRowSet_to_ListRow(rowset);
        if(temp.size() != 0) return false;
        String table = "listing";
        String conditions = "id = " + l_id;
        if(Database.delete(table, conditions)) return true;
        else return false;
    }



    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            Host me = new Host();
//            List<String> cred = Arrays.asList("qibowang7@outlook.com", "password");
//            System.out.println(me.signIn(cred));

            List<String> cred = Arrays.asList("michael@outlook.com", "password");
            System.out.println(me.signIn(cred));
//            System.out.println(me.becomeHost());

            // [l_id, fromDate, toDate]
            List<String> info = Arrays.asList("18", "2019-06-27", "2019-07-02");
            System.out.println(me.cancelBooking(info, 2));

            // given houseInfo: [area, fromDate, toDate, dayPrice, owner, type, amenity]
            // given addrInfo: [street, city, pcode, country]

//            List<String> houseInfo = Arrays.asList("177", "2019-06-27", "2019-07-02", "177.77", me.getId().toString(), "Room", "01010111010100");
//            List<String> addrInfo = Arrays.asList("300 Borough Dr", "Toronto", "ON M1P 4P5", "Canada");
//            System.out.println(me.postListing(houseInfo, addrInfo));

//			List<String> info = Arrays.asList("10", "19.99");
//			System.out.println(me.updatePrice(info));

//			List<String> info = Arrays.asList("10", "2020-07-01", "2020-07-02", "2020-02-27", "2020-03-02");
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










