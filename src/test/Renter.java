package test;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static test.Listing.allDates;

public class Renter extends User {

    /**
     * Renter becomes to a host
     *
     * @return true if successfully; false otherwise
     */
    public Boolean becomeHost() {
        if (this.active && this.type.equals(1)) {
            // sample input: "sailers", "name = 'KENNY', num = -111", "id=35"
            if (Database.update("user", "type = 2", "id = " + this.id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Renter books a listing
     *
     * @param info [l_id, fromDate, toDate]
     * @return true if successfully; false otherwise
     */
    public Boolean bookListing(List<String> info) throws SQLException {
        Boolean success = false;
        if (this.active) {
            // check if this booking is legal
            List<LocalDate> allAvil = Listing.allAvailabilities(Integer.parseInt(info.get(0)));
            List<LocalDate> allDays = Listing.allDates(info.get(1), info.get(2));
            if (!Listing.is_sublist(allAvil, allDays)) return success;

            // add to "rented" table
            String query = "select * from listing where id = " + info.get(0);
            ResultSet rs = Database.queryRead(query);
            Double dayPrice = null;
            if (rs.next()) {
                dayPrice = rs.getDouble("dayPrice");
            }
            String table1 = "rented";
            String cols1 = "u_id, l_id, fromDate, toDate, status, date, dayPrice";
            String vals1 = this.id + ", " + info.get(0) + ", '" + info.get(1) + "', '" +
                    info.get(2) + "', " + "0, " + "NOW()" + ", " + dayPrice;
            if (Database.insert(table1, cols1, vals1)) {
                // delete from "availability" table
                String table2 = "availability";
                List<LocalDate> dates = Listing.allDates(info.get(1), info.get(2));
                for (int i = 0; i < dates.size(); i++) {
                    String conditions = "id=" + info.get(0) + " and avilDate='" + dates.get(i) + "'";
                    if (!Database.delete(table2, conditions)) {
                        return false;
                    }
                }
                success = true;
            }
        }
        return success;
    }

    /**
     * Renter's initial comment on a listing
     *
     * @param info [receiver, rating, content, l_id]
     * @return true if successfully; false otherwise
     */
    public Boolean commentOnListing(List<String> info) throws SQLException {
        Boolean legal = false; // legal to comment on that listing
        CachedRowSet rowset = this.getBookings(1);
        while (rowset.next()) {
            Integer l_id = rowset.getInt("l_id");
            if (l_id == Integer.parseInt(info.get(3))) {
                legal = true;
                break;
            }
        }
        if (!legal) return false;


        Boolean success = false;
        if (this.active) {
            // add to "listing_comment" table
            String table = "listing_comment";
            String cols = "l_id, sender, receiver, parent_comment, rating, content, date";
            String vals = info.get(3) + "," + this.id + ", " + info.get(0) + ", null, " + info.get(1) + ", '" +
                    info.get(2) + "', " + "NOW()";
            if (Database.insert(table, cols, vals)) success = true;
        }
        return success;
    }

    /**
     * Get renter's bookings based on type
     *
     * @param type 1 for history booking; 0 for future booking; 2 for all (history + future bookings)
     * @return renter's bookings (history + future bookings)
     */
    public CachedRowSet getBookings(int type) throws SQLException {
        String query = "";
        if (type == 1) query = "SELECT * FROM rented where u_id = " + this.id + " and status = 1;";
        else if (type == 0) query = "SELECT * FROM rented where u_id = " + this.id + " and status = 0;";
        else if (type == 2) query = "SELECT * FROM rented where u_id = " + this.id + ";";
        ResultSet rs = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        return rowset;
    }

    /**
     * Renter's initial comment on a host user
     *
     * @param info [receiver, rating, content]
     * @return true if successfully; false otherwise
     */
    @Override
    public Boolean commentOnUser(List<String> info) throws SQLException {
        Boolean legal = false; // legal to comment on that user?
        CachedRowSet rowset = this.getBookings(1);
        while (rowset.next()) {
            Integer l_id = rowset.getInt("l_id");
            if (Listing.getOwnerId(l_id) == Integer.parseInt(info.get(0))) {
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
     * Renter cancels a booking
     *
     * @param info [l_id, fromDate, toDate]
     * @return true if successfully; false otherwise
     */
    @Override
    public Boolean cancelBooking(List<String> info) throws SQLException {
        Boolean legal = false; // legal to cancel this booking?
        CachedRowSet rowset = this.getBookings(0);
        while (rowset.next()) {
            Integer l_id = rowset.getInt("l_id");
            Integer u_id = rowset.getInt("u_id");
            String booked_from = LocalDate.parse(rowset.getString("fromDate")).plusDays(1).toString();
            String booked_to = LocalDate.parse(rowset.getString("toDate")).plusDays(1).toString();
            System.out.println(booked_from + " -- " + booked_to);
            if (u_id == this.id && l_id == Integer.parseInt(info.get(0)) && booked_from.equals(info.get(1)) && booked_to.equals(info.get(2))) {
                legal = true;
                break;
            }
        }
        if (!legal) return false;


        Boolean success = false;
        if (this.active) {
            // update "rented (status)" table
            String table1 = "rented";
            String newInfo = "status = -1";
            String conditions = "u_id=" + this.id + " and l_id=" + info.get(0) + " and fromDate = '" + info.get(1)
                    + "' and toDate='" + info.get(2) + "'";
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

    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            Renter me = new Renter();

            List<String> cred = Arrays.asList("michael@outlook.com", "password");
            System.out.println(me.signIn(cred));

//			List<String> info = Arrays.asList("2", "2019-05-03", "2019-05-05");
//			System.out.println(me.bookListing(info));

            //[l_id, fromDate, toDate]
			List<String> info = Arrays.asList("12", "2019-07-29", "2019-07-30");
			System.out.println(me.cancelBooking(info));

            // [receiver, rating, content, l_id]
//			List<String> info = Arrays.asList("3", "3", "this Condo is good.", "1");
//			System.out.println(me.commentOnListing(info));

//            List<String> info = Arrays.asList("3", "0", "This host is soooo bad!!!");
//            System.out.println(me.commentOnUser(info));
        }
        Database.disconnect();
    }

}








