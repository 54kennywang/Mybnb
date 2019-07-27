package test;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;

import javax.sql.rowset.CachedRowSet;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static test.Database.queryRead;

public class Listing {
    // 0, 1, ..., 13
    public static List<String> amenities = Arrays.asList(
            "WIFI", "TV", "AC", "Microwave", "Laundry", "Refrigerator", "Hair Dryer", "Iron",
            "Hangers", "Fire extinguisher", "Coffee Maker", "Dishwasher", "Oven", "BBQ Grill");

    /**
     * Get the ownerID of a listing
     *
     * @param l_id the id of a listing
     * @return ownerID of that listing
     */
    public static Integer getOwnerID(int l_id) throws SQLException {
        String query = "SELECT owner FROM mydb.listing where id = " + l_id + ";";
        ResultSet rowset = Database.queryRead(query);
        Integer ownerID = null;
        if (rowset.next()) {
            ownerID = rowset.getInt("owner");
        }
        return ownerID;
    }

    /**
     * Get the address row of a listing
     *
     * @param l_id the id of a listing
     * @return address row of a listing
     * [id, country, city, street, pcode, lng, lat, type]
     */
    public static List<Row> getListingAddr(int l_id) throws SQLException {
        List<String> addr = new ArrayList<String>();
        String query = "SELECT * FROM address where id = " + l_id + " and type = 0;";
        ResultSet rs = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        return CachedRowSet_to_ListRow(rowset);
    }


    /**
     * Prints a listing's info in console.
     *
     * @param id the id of a listing
     * @return 1 if listing exists; 0 for not exist
     */
    public static int viewListing(int id) throws SQLException {
        String query = "select * from listing where id = " + id;
        ResultSet rs = queryRead(query);
        if (rs.next()) {
            System.out.println("Listing ID: " + id);
            System.out.println("Price: $" + rs.getString("dayPrice"));
            System.out.println("Type: " + rs.getString("type"));
            System.out.println("Area: " + rs.getString("area") + " m^2");
            System.out.print("Amenities: ");
            Listing.printAmenity(parseAmenity(rs.getString("amenity")));


            System.out.print("Available: ");
            Listing.printAllAvailabilities(id);
            System.out.println();

            System.out.print("Unavailable: ");
            Listing.printAllUnAvailabilities(id);
            System.out.println();

            List<Row> addrRow = getListingAddr(id);
            List<String> addrInfo = new ArrayList<String>();
            addrInfo.add(addrRow.get(0).getColumnObject(4).toString());
            addrInfo.add(addrRow.get(0).getColumnObject(3).toString());
            addrInfo.add(addrRow.get(0).getColumnObject(5).toString());
            addrInfo.add(addrRow.get(0).getColumnObject(2).toString());
            System.out.println("Address: " + Map.infoToAddr(addrInfo));

            User.viewComments(id, 0);
            return 1;
        }
        else {
            System.out.println("***Sorry, no result found***");
            return 0;
        }
    }


    /**
     * Prints the result from a search query defined below
     *
     * @param printDistance 1 - print the distance; 0 not print the distance
     * @param input         the returned result from a search query defined below
     *                      input schema: [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     */
    public static void viewAllListing(List<Row> input, int printDistance) throws SQLException {
        if(input.size() == 0) {
            System.out.println("***Sorry, no result found***");
            return;
        }
        for (int i = 0; i < input.size(); i++) {
            viewListing(Integer.parseInt(input.get(i).getColumnObject(1).toString()));
            if (printDistance == 1) {
                System.out.println("Distance from searching location: " + input.get(i).getColumnObject(13) + " KM");
            }
            System.out.println("==========");
        }
    }


    /**
     * Gets all availabilities of a listing.
     *
     * @param id the id of a listing
     * @return a list of available LocalDate of that listing
     */
    public static List<LocalDate> allAvailabilities(int id) throws SQLException {
        String query = "select * from availability where id = " + id;
        ResultSet rs = queryRead(query);
        List<LocalDate> avilDates = new ArrayList<LocalDate>();
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        while (rowset.next()) {
            // why do i need to add one day???
            // https://stackoveU:\data\Living_Organ_Donation_Database\BErflow.com/questions/11296606/java-jdbc-dates-consistently-two-days-off
            LocalDate date = LocalDate.parse(rowset.getString("avilDate")).plusDays(1);
            avilDates.add(date);
        }
        return avilDates;
    }

    /**
     * Prints all available dates for a listing on console.
     *
     * @param id the id of a listing
     */
    public static void printAllAvailabilities(int id) throws SQLException {
        List<LocalDate> allDates = Listing.allAvailabilities(id);
        for (int i = 0; i < allDates.size(); i++) {
            System.out.print(allDates.get(i) + " ");
        }
    }

    /**
     * Gets a list of rented/unavailable LocalDate for a listing.
     *
     * @param id the id of a listing
     * @return a list of rented LocalDate of that listing
     */
    public static List<LocalDate> allUnavailabilities(int id) throws SQLException {
        String query = "select * from rented where l_id = " + id + " and status = 0";
        ResultSet rs = queryRead(query);
        List<LocalDate> UnavilDates = new ArrayList<LocalDate>();
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        while (rowset.next()) {
            // ???????????why plus one????????????
            // https://stackoverflow.com/questions/11296606/java-jdbc-dates-consistently-two-days-off
            LocalDate fromDate = LocalDate.parse(rowset.getString("fromDate")).plusDays(1);
            LocalDate toDate = LocalDate.parse(rowset.getString("toDate")).plusDays(1);
            List<LocalDate> tempDates = allDates(fromDate.toString(), toDate.toString());
            for (int i = 0; i < tempDates.size(); i++) UnavilDates.add(tempDates.get(i));
        }
        return UnavilDates;
    }

    /**
     * Prints all unavailable dates for a listing on console.
     *
     * @param id the id of a listing
     */
    public static void printAllUnAvailabilities(int id) throws SQLException {
        List<LocalDate> allDates = Listing.allUnavailabilities(id);
        for (int i = 0; i < allDates.size(); i++) {
            System.out.print(allDates.get(i) + " ");
        }
    }

    /**
     * Gets a listing's owner ID, return -1 if listing does not exist.
     *
     * @param l_id the id of a listing
     * @return the owner id of the listing, or -1 if listing does not exist
     */
    public static Integer getOwnerId(int l_id) throws SQLException {
        String query = "SELECT * FROM listing where id = " + l_id + ";";
        ResultSet rs = queryRead(query);
        if (rs.next()) {
            return rs.getInt("owner");
        }
        return -1;
    }

    /**
     * Prints amenities to the console.
     *
     * @param amen the string list of amenities, like [AC, Shower, ...]
     */
    public static void printAmenity(List<String> amen) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < amen.size(); i++) {
            result.append(amen.get(i)).append(" ");
        }
        System.out.println(result);
    }

    /**
     * Parses a binary string amenities into a string list of amenities names.
     *
     * @param amen the binary string of amenities stored in the database
     * @return a string list of amenities based on amenities array above
     */
    public static List<String> parseAmenity(String amen) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < amen.length(); i++) {
            if (amen.charAt(i) == '1') {
                result.add(amenities.get(i));
            }
        }
        return result;
    }

    /**
     * Gets all LocalDate between two dates (inclusive).
     * sample: ("2019-05-03", "2019-05-05") return ["2019-05-03", "2019-05-04", "2019-05-05"]
     *
     * @param fromDate the beginning date
     * @param toDate   the ending date
     * @return a LocalDate list of dates between two input dates.
     */
    public static List<LocalDate> allDates(String fromDate, String toDate) {
        final LocalDate start = LocalDate.parse(fromDate);
        final LocalDate end = LocalDate.parse(toDate);
        LocalDate date = start;
        List<LocalDate> result = new ArrayList<LocalDate>();
        for (date = start; date.isBefore(end); date = date.plusDays(1)) {
            result.add(date);
        }
        result.add(date);
        return result;
    }

    /**
     * Searches by geo-point and gets an ordered list of table rows ranked by distance with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param lng    longitude
     * @param lat    latitude
     * @param radius search radius from geo-point (Lng, Lat)
     * @param unit   'K'/'M'
     * @param order  1 - from nearest to farthest; 0 - from farthest to nearest
     * @return a list of table rows within the given radius from the geo-point in KM/MILE in specified distance order ranked by distance
     */
    public static List<Row> searchByCoordinates_rankByDistance(Double lng, Double lat, Double radius, char unit, int order) throws SQLException {
        List<Row> tempResult = searchByCoordinates(lng, lat, radius, unit);
        if (order == 1) return increaseSort(tempResult, 13);
        else return orderReverse(increaseSort(tempResult, 13));
    }

    /**
     * Searches by geo-point and gets an ordered list of table rows ranked by price with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param lng    longitude
     * @param lat    latitude
     * @param radius search radius from geo-point (Lng, Lat)
     * @param unit   'K'/'M'
     * @param order  1 - from cheapest to most expensive; 0 - opposite
     * @return a list of table rows within the given radius from the geo-point in KM/MILE in specified price order ranked by price
     */
    public static List<Row> searchByCoordinates_rankByPrice(Double lng, Double lat, Double radius, char unit, int order) throws SQLException {
        List<Row> tempResult = searchByCoordinates(lng, lat, radius, unit);
        if (order == 1) return increaseSort(tempResult, 10);
        else return orderReverse(increaseSort(tempResult, 10));
    }

    /**
     * Searches by geo-point and gets an unordered list of table rows with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param lng    longitude
     * @param lat    latitude
     * @param radius search radius from geo-point (Lng, Lat)
     * @param unit   'K'/'M'
     * @return a list of table rows within the given radius from the geo-point in KM/MILE
     */
    public static List<Row> searchByCoordinates(Double lng, Double lat, Double radius, char unit) throws SQLException {
        String query = "SELECT address.*, listing.area, listing.dayPrice, listing.owner, listing.amenity, 0.0 as distance " +
                "FROM address join listing " +
                "on listing.id = address.id and address.type = 0;";
        ResultSet rs = queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        List<Row> result = CachedRowSet_to_ListRow(rowset);

        return addDistanceToSearchResult(result, lat, lng, radius, 'K');
//        Double tempLng = new Double(0);
//        Double tempLat = new Double(0);
//        for (int i = 0; i < result.size(); i++) {
//            tempLng = Double.parseDouble(result.get(i).getColumnObject(6).toString());
//            tempLat = Double.parseDouble(result.get(i).getColumnObject(7).toString());
//
//            Double dis = Map.distance(tempLat, tempLng, lat, lng, unit);
//            if (dis > radius) {
//                result.remove(i);
//                i--;
//            } else {
//                result.get(i).setColumnObject(13, dis);
//            }
//        }
//        return result;
    }

    /**
     * Add distance from (lat, lng) to a search query, input distance column all 0, remove the ones with distance bigger than radius
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param result result from a search query with distance column all 0
     * @param lng    longitude
     * @param lat    latitude
     * @param radius search radius from geo-point (Lng, Lat)
     * @param unit   'K'/'M'
     * @return a list of table rows with distance from (lat, lng), remove the ones with distance bigger than radius
     */
    public static List<Row> addDistanceToSearchResult(List<Row> result, double lat, double lng, double radius, char unit) throws SQLException {
        Double tempLng = new Double(0);
        Double tempLat = new Double(0);
        for (int i = 0; i < result.size(); i++) {
            tempLng = Double.parseDouble(result.get(i).getColumnObject(6).toString());
            tempLat = Double.parseDouble(result.get(i).getColumnObject(7).toString());

            Double dis = Map.distance(tempLat, tempLng, lat, lng, unit);
            if (dis > radius) {
                result.remove(i);
                i--;
            } else {
                result.get(i).setColumnObject(13, dis);
            }
        }
        return result;
    }

    /**
     * Searches by address and gets an ordered list of table rows within the searching radius of the specified addr, ranked by distance with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param addrInfo [street, city, pcode, country]
     * @param order    1 - from nearest to farthest; 0 - from farthest to nearest
     * @param radius   searching radius
     * @return a list of table rows within the searching radius of the given addr ranked by distance
     */
    public static List<Row> searchByAddress_rankByDistance(List<String> addrInfo, Double radius, int order) throws SQLException, Exception {
        List<Row> tempResult = searchByAddress(addrInfo, radius);
        if (order == 1) return increaseSort(tempResult, 13);
        else return orderReverse(increaseSort(tempResult, 13));
    }

    /**
     * Searches by address and gets an ordered list of table rows within the searching radius of the specified addr, ranked by price with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param addrInfo [street, city, pcode, country]
     * @param order    1 - from cheapest to most expensive; 0 - from most expensive to cheapest
     * @param radius   searching radius
     * @return a list of table rows within the searching radius of the given addr ranked by price
     */
    public static List<Row> searchByAddress_rankByPrice(List<String> addrInfo, Double radius, int order) throws SQLException, Exception {
        List<Row> tempResult = searchByAddress(addrInfo, radius);
        if (order == 1) return increaseSort(tempResult, 10);
        else return orderReverse(increaseSort(tempResult, 10));
    }

    /**
     * Searches by address and gets an unordered list of table rows within the searching radius of the specified addr with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param addrInfo [street, city, pcode, country]
     * @param radius   searching radius
     * @return a list of table rows within the searching radius
     */
    public static List<Row> searchByAddress(List<String> addrInfo, Double radius) throws SQLException, Exception {
        List<Object> allInfo = Map.getAllByAddr(Map.infoToAddr(addrInfo)); // [street, city, pcode, country, lng, lat]
//        CachedRowSet rowset = searchByCoordinates((Double) allInfo.get(4), (Double) allInfo.get(5), 30.0, 'K');
        List<Row> rowset = searchByCoordinates((Double) allInfo.get(4), (Double) allInfo.get(5), radius, 'K');
        return rowset;
    }

    /**
     * Searches by postal code and gets an ordered list of table rows with the exact same input pcode, ranked by price with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param pcode 6-digit postal code with no space
     * @param order 1 - from cheapest to most expensive; 0 - from most expensive to cheapest
     * @return a list of table rows with the exact same input pcode
     */
    public static List<Row> searchByPcode_rankByPrice_exact(String pcode, int order) throws SQLException, Exception {
        List<Row> tempResult = searchByPcode_exact(pcode);
        if (order == 1) return increaseSort(tempResult, 10);
        else return orderReverse(increaseSort(tempResult, 10));
    }


    /**
     * Searches by postal code and gets an unordered list of table rows with the exact same input pcode with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param pcode 6-digit postal code with no space
     * @return a list of table rows with the exact same input pcode
     */
    public static List<Row> searchByPcode_exact(String pcode) throws SQLException {
        String sanitizedPcode = Map.pcodeSanitizer(pcode);
        String exactQuery = "SELECT address.*, listing.area, listing.dayPrice, listing.owner, listing.amenity, 0.0 as distance " +
                "FROM address join listing " +
                "on listing.id = address.id and address.type = 0 where address.pcode = '" + sanitizedPcode + "';";
        ResultSet rs = queryRead(exactQuery);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        return CachedRowSet_to_ListRow(rowset);
//        return rowset;
    }


    /**
     * Searches by postal code and gets an ordered list of table rows with the similar but not same input pcode, ranked by price with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param pcode 6-digit postal code with no space
     * @param order 1 - from cheapest to most expensive; 0 - from most expensive to cheapest
     * @return a list of table rows with the similar but not same input pcode
     */
    public static List<Row> searchByPcode_rankByPrice_wildcard(String pcode, int order) throws SQLException, Exception {
        List<Row> tempResult = searchByPcode_wildcard(pcode);
        if (order == 1) return increaseSort(tempResult, 10);
        else return orderReverse(increaseSort(tempResult, 10));
    }

    /**
     * Searches by postal code and gets an unordered list of table rows with the similar but not same input pcode, ranked by price with the schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param pcode 6-digit postal code with no space
     * @return a list of table rows with the similar but not same input pcode
     */
    public static List<Row> searchByPcode_wildcard(String pcode) throws SQLException {
        String sanitizedPcode = Map.pcodeSanitizer(pcode);
        String wildcard_pcode = Map.pcodeSanitizer(pcode).substring(0, 4) + "%"; // second num is how wild it is [0, 6], 0 is most wild
        String query = "SELECT address.*, listing.area, listing.dayPrice, listing.owner, listing.amenity, 0.0 as distance " +
                "FROM address join listing " +
                "on listing.id = address.id and address.type = 0 and address.pcode != '" + sanitizedPcode + "'" +
                " where address.pcode like '" + wildcard_pcode + "';";
        ResultSet rs = queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);

        return CachedRowSet_to_ListRow(rowset);
//        return rowset;
    }

    /**
     * Converts CachedRowSet to List<Row>
     *
     * @param input CachedRowSet input
     * @return a converted List<Row> from the CachedRowSet input
     */
    public static List<Row> CachedRowSet_to_ListRow(CachedRowSet input) throws SQLException {
        Collection<Row> rows = (Collection<Row>) input.toCollection();
        List<Row> result = new ArrayList<Row>();
        for (Row row : rows) {
            result.add(row);
        }
        return result;
    }

    /**
     * Sort a 'table' by the column colIndex (not index, it's index + 1)
     *
     * @param input    a 'table' to be sorted
     * @param colIndex the column that is sorted on (index 1, 2, 3... (not from 0), first column index is 1 not 0)
     * @return a sorted (from smallest to biggest) List<Row> based on the colIndex column
     */
    public static List<Row> increaseSort(List<Row> input, int colIndex) throws SQLException {
        Multimap<Double, Row> sorted = MultimapBuilder.treeKeys().linkedHashSetValues().build();
        for (Row row : input) {
            sorted.put(Double.parseDouble((row.getColumnObject(colIndex)).toString()), row);
        }
        List<Row> result = new ArrayList<Row>();
        for (Double key : sorted.keySet()) {
            Collection<Row> temp = sorted.get(key);
            for (Row item : temp) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Convert an non-decreasing ordered 'table' to an non-increasing ordered table
     *
     * @param input an non-decreasing ordered 'table'
     * @return a sorted (from biggest to smallest) List<Row>
     */
    public static List<Row> orderReverse(List<Row> input) {
        return Lists.reverse(input);
    }

    /**
     * Filter table rows by time window, input table is of following schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param input    a 'table' to be filtered
     * @param fromDate starting date
     * @param toDate   ending date
     * @return an after-filtered table where all rows satisfy the time window
     */
    public static List<Row> dateFilter(List<Row> input, String fromDate, String toDate) throws SQLException {
        Integer listing_id = new Integer(0);
        List<LocalDate> all_dates = allDates(fromDate, toDate);
        for (int i = 0; i < input.size(); i++) {
            listing_id = (Integer) input.get(i).getColumnObject(1);
            List<LocalDate> allAvil = allAvailabilities(listing_id);
            if (is_sublist(allAvil, all_dates) == false) {
                input.remove(i);
                i--;
            }
        }
        return input;
    }

    /**
     * Filter table rows by price range, input table is of following schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param input   a 'table' to be filtered
     * @param lowest  lowest price
     * @param highest highest price
     * @return an after-filtered table where all rows satisfy [lowest <= dayPrice <= highest]
     */
    public static List<Row> priceFilter(List<Row> input, Double lowest, Double highest) throws SQLException {
        Double price = 0.0;
        for (int i = 0; i < input.size(); i++) {
            price = (Double) input.get(i).getColumnObject(10);
            if ((price < lowest) || (price > highest)) {
                input.remove(i);
                i--;
            }
        }
        return input;
    }

    /**
     * Filter table rows by amenities, input table is of following schema
     * [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
     *
     * @param input       a 'table' to be filtered
     * @param amenRequest the must-have amenities
     * @return an after-filtered table where all rows satisfy the specified amenities
     * sample: (input, "1.............") -> filter all rows with WIFI
     */
    public static List<Row> amenityFilter(List<Row> input, String amenRequest) throws SQLException {
        Pattern p = Pattern.compile(amenRequest);
        String amen = "";
        for (int i = 0; i < input.size(); i++) {
            amen = (String) input.get(i).getColumnObject(12);
            Matcher m = p.matcher(amen);
            if (!m.find()) {
                input.remove(i);
                i--;
            }
        }
        return input;
    }

    /**
     * Check if a set is a subset of another
     *
     * @param list    parent set
     * @param sublist child set
     * @return true if sublist is a subset of list, false otherwise
     */
    public static boolean is_sublist(List<?> list, List<?> sublist) {
        boolean is = true;
        for (int i = 0; i < sublist.size(); i++) {
            if (list.indexOf(sublist.get(i)) == -1) {
                is = false;
                break;
            }
        }
        return is;
    }


    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            Host me = new Host();
            List<String> info = Arrays.asList("qibowang7@outlook.com", "password");
            System.out.println(me.signIn(info));
            System.out.println(getOwnerId(19));

//            printAllUnAvailabilities(12);

            // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
//            List<Row> result = searchByCoordinates_rankByDistance(-79.186043, 43.784554, 3.0, 'K', 1);
//            List<Row> result = searchByCoordinates_rankByPrice(-79.186043, 43.784554, 3.0, 'K', 1);

            // given addrInfo: [street, city, pcode, country]
//            List<String> addrInfo = Arrays.asList("1265 military", "toronto", "m1c1a6", "Canada");
//            List<Row> result = searchByAddress_rankByDistance(addrInfo, 1);
//            List<Row> result = searchByAddress_rankByPrice(addrInfo, 1);


//            List<Row> result = searchByPcode_rankByPrice_wildcard("M1E4B9", 1);
//            List<Row> result = searchByPcode_rankByPrice_exact("M1E4B9", 1);

//            result = dateFilter(result, "2009-06-29", "2009-07-01");
//            result = priceFilter(result, 50.0, 77.0);
//            result = amenityFilter(result, "1.............");

//            for (int i = 0; i < result.size(); i++) {
//                System.out.println(result.get(i).getColumnObject(1) + " Price: " + result.get(i).getColumnObject(10));
//                System.out.println(result.get(i).getColumnObject(1) + " Distance: " + result.get(i).getColumnObject(13));
//            }


        }


        Database.disconnect();
    }


}
