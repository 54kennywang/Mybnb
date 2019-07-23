package test;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;
import java.sql.SQLException;

public class Report {

    /**
     * Get the number of bookings in a city in a specified time window
     *
     * @param fromDate time window start
     * @param toDate   time window end
     * @param city     a city name
     * @return the number of bookings in a city in a specified time window
     */
    public static int numOfBookingsByCity(LocalDate fromDate, LocalDate toDate, String city) throws SQLException {
        //remove "-" from date
        String fDate = dateRefactor(fromDate);
        String tDate = dateRefactor(toDate);
        // natural join address, and rented
        String query = "select count(status) from address join rented on address.id = rented.l_id " +
                "where city = '" + city + "' and status = " + 0 + " and fromDate >= " + fDate + " and toDate <= " + tDate + ";";
        ResultSet resultSet = Database.queryRead(query);
        resultSet.next();
        return resultSet.getInt("count(status)");
    }


    /**
     * Get the number of bookings in a zipCode area in a specified time window
     *
     * @param fromDate time window start
     * @param toDate   time window end
     * @param zipCode  a zipCode
     * @return the number of bookings in a zipCode area in a specified time window
     */
    public static int numOfBookingsByZipCode(LocalDate fromDate, LocalDate toDate, String zipCode) throws SQLException {
        //remove "-" from date
        String fDate = dateRefactor(fromDate);
        String tDate = dateRefactor(toDate);
        // natural join address, and rented
        String query = "select count(status) from address join rented on address.id = rented.l_id " +
                "where pcode = '" + zipCode + "' and status = " + 0 + " and fromDate >= " + fDate + " and toDate <= " + tDate + ";";
        ResultSet resultSet = Database.queryRead(query);
        resultSet.next();
        return resultSet.getInt("count(status)");
    }

    /**
     * Get the number of listing for different countries
     *
     * @return a table of the number of listing for different countries
     * [country, count(listing.id)]
     */
    public static List<Row> numOfListingsPerCountry() throws SQLException {
//    public static HashMap<String, Integer> numOfListingsPerCountry() throws SQLException{
        String query = "select country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by country;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);

//        HashMap<String, Integer> hashMap = new HashMap<>();
//        while(resultSet.next()){
//            String country = resultSet.getString("country");
//            int num = resultSet.getInt("count(listing.id)");
//            hashMap.put(country, num);
//        }
//        return hashMap;
    }

    /**
     * Get the number of listing for different (country, city) pair
     *
     * @return a table of the number of listing for different (country, city) pair
     * [city, country, count(listing.id)]
     */
    public static List<Row> numOfListingsPerCountryPerCity() throws SQLException {
//        public static HashMap<String, Integer> numOfListingsPerCountryPerCity() throws SQLException {
        String query = "select city, country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by city, country;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);

//        HashMap<String, Integer> hashMap = new HashMap<>();
//        while (resultSet.next()) {
//            String country = resultSet.getString("country");
//            String city = resultSet.getString("city");
//            String place = "(" + city + "," + country + ")";
//            int num = resultSet.getInt("count(listing.id)");
//            hashMap.put(place, num);
//        }
//        return hashMap;
    }

    /**
     * Get the number of listing for different (country, city, pcode) pair
     *
     * @return a table of the number of listing for different (country, city, pcode) pair
     * [city, pcode, country, count(listing.id)]
     */
    public static List<Row> numOfListingsPerCountryPerCityPerPCode() throws SQLException {
//        public static HashMap<String, Integer> numOfListingsPerCountryPerCityPerPCode() throws SQLException {
        String query = "select city, pcode, country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by city, pcode, country;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
//        HashMap<String, Integer> hashMap = new HashMap<>();
//        while (resultSet.next()) {
//            String country = resultSet.getString("country");
//            String city = resultSet.getString("city");
//            String pcode = resultSet.getString("pcode");
//            String place = "(" + city + "," + "," + pcode + "," + country + ")";
//            int num = resultSet.getInt("count(listing.id)");
//            hashMap.put(place, num);
//        }
//        return hashMap;
    }

    /**
     * Get the table of number of listings for different hosts in different countries, descending order by count (all records)
     *
     * @param country country name
     * @return a table of number of listings for different hosts in different countries, descending order by count (all records)
     * [ownerID, country, count(listing.id)]
     */
    public static List<Row> rankHostsByListingsPerCountry(String country) throws SQLException {
//        public static ResultSet rankHostsByListingsPerCountry() throws SQLException {
        String query = "select owner, country, count(listing.id) " +
                "from listing, address " +
                "where listing.id = address.id and address.type = 0 and address.country = '" + country +
                "' group by owner, country " +
                "order by count(listing.id) desc;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
//        return resultSet;
    }

    /**
     * Get the table of number of listings for different hosts in different cities, descending order by count (all records)
     *
     * @param country country name
     * @param city    city name
     * @return a table of number of listings for different hosts in different cities, descending order by count (all records)
     * [ownerID, city, count(listing.id)]
     */
    public static List<Row> rankHostsByListingsPerCity(String country, String city) throws SQLException {
//        public static ResultSet rankHostsByListingsPerCity() throws SQLException {
        String query = "select owner, city, count(listing.id) " +
                "from listing, address " +
                "where listing.id = address.id and address.type = 0 and address.country = '" + country + "' and address.city = '" + city +
                "' group by owner, city " +
                "order by count(listing.id) desc;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
//        return resultSet;
    }


    /**
     * Get the table of overposting users, if fromDate and toDate are not empty, a time window is specified
     *
     * @param fromDate time window start
     * @param toDate   time window end
     * @return a table of overposting users
     * [owner, count, count_percentage]
     */
    public static List<Row> spamPosting(String fromDate, String toDate) throws SQLException{
        String query = "";
        if (fromDate.equals("")) {
            query =
                    "select * from " +
                            "(select owner, count(*) as 'count', " +
                            "count(*) / (SELECT count(*) as 'count' FROM listing) as 'count_percentage' " +
                            "FROM listing group by owner order by count Desc) x " +
                            "where x.count_percentage > 0.1;";
        } else {
            query =
                    "select * from " +
                            "(select owner, count(*) as 'count', " +
                            "count(*) / (SELECT count(*) as 'count' FROM listing where date >= '" + fromDate + "' and date <= '" + toDate + "') " +
                            "as 'count_percentage' " +
                            "FROM listing where date >= '" + fromDate + "' and date <= '" + toDate + "' " +
                            "group by owner order by count Desc) x " +
                            "where x.count_percentage > 0.1;";
        }
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Get the table of renter booking (lived) times in a time window
     *
     * @param startDate time window begin time
     * @param toDate    time window ending time
     * @return a table of renter booking (lived) times in a time window
     * [u_id, country, city, count]
     */
    public static List<Row> rankRentersByNumOfBookings(LocalDate startDate, LocalDate toDate) throws SQLException {
        String query =
                "select r.u_id, a.country, a.city, count(*) as 'count' from rented r, address a " +
                        "where a.id = r.l_id and a.type = 0 and r.fromDate >= '" + startDate + "' and r.toDate <= '" + toDate + "' " +
                        "and r.status = 1 " +
                        "group by r.u_id, a.country, a.city having count(*) > 1 " +
                        "order by count(*) desc;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Get the table of renter booking (lived) times in a time window in a particular city
     *
     * @param startDate time window begin time
     * @param toDate    time window ending time
     * @param country   country name
     * @param city      city name
     * @return a table of renter booking (lived) times in a time window in a particular city
     * [u_id, country, city, count]
     */
    public static List<Row> rankRentersByNumOfBookingsPerCity(LocalDate startDate, LocalDate toDate, String country, String city) throws SQLException {
        List<Row> result = Report.rankRentersByNumOfBookings(startDate, toDate);
        for (int i = 0; i < result.size(); i++) {
            if (!result.get(i).getColumnObject(2).toString().equals(country) || !result.get(i).getColumnObject(3).toString().equals(city)) {
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    private static String dateRefactor(LocalDate date) {
        return date.toString().replaceAll("-", "");
    }

    public static void main(String args[]) throws SQLException {
        if (Database.connect()) {
//            LocalDate fromDate = LocalDate.parse("2020-01-01");
//            LocalDate toDate = LocalDate.parse("2021-01-01");
//            System.out.println(numOfBookingsByCity(fromDate, toDate, "London"));
//            System.out.println(numOfBookingsByZipCode(fromDate, toDate, "C3A8BF"));
//            HashMap<String, Integer> hashMap = numOfListingsPerCountryPerCityPerPCode();
//            System.out.println(hashMap.entrySet());

//            List<Row> x = rankHostsByListingsPerCountry("Canada");
//            List<Row> x = rankHostsByListingsPerCity("Canada", "London");
//            for(int i = 0; i < x.size(); i ++){
//                System.out.println("owner: " + x.get(i).getColumnObject(1) + " | count: " + x.get(i).getColumnObject(3));
//            }

//            List<Row> x = rankRentersByNumOfBookings(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-12"));
//            for (int i = 0; i < x.size(); i++) {
//                System.out.println("owner: " + x.get(i).getColumnObject(1) + " | city: " + x.get(i).getColumnObject(3) +" | count: " + x.get(i).getColumnObject(4));
//            }

//            List<Row> y = rankRentersByNumOfBookingsPerCity(LocalDate.parse("2019-01-01"), LocalDate.parse("2019-12-12"), "Canada", "x");
//            if (y.size() == 0) System.out.println("correct");

            List<Row> x = spamPosting("2019-07-10", "2019-07-31");
            for(int i = 0; i < x.size(); i ++){
                System.out.println("owner: " + x.get(i).getColumnObject(1) + " | count: " + x.get(i).getColumnObject(2) +" | count_percentage: " + x.get(i).getColumnObject(3));
            }

        }
        Database.disconnect();
    }
}
