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

public class Listing {
    // 0, 1, ..., 13
    public static List<String> amenities = Arrays.asList(
            "WIFI", "TV", "AC", "Microwave", "Laundry", "Refrigerator", "Hair Dryer", "Iron",
            "Hangers", "Fire extinguisher", "Coffee Maker", "Dishwasher", "Oven", "BBQ Grill");

    // input listing_id, print its corresponding info
    public static void viewListing(int id) throws SQLException {
        String query = "select * from listing where id = " + id;
        ResultSet rs = Database.queryRead(query);
        if (rs.next()) {
            System.out.println("Area: " + rs.getString("area"));
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
        }
    }

    // given listing id
    // return a list of available dates for that listing
    public static List<LocalDate> allAvailabilities(int id) throws SQLException {
        String query = "select * from availability where id = " + id;
        ResultSet rs = Database.queryRead(query);
        List<LocalDate> avilDates = new ArrayList<LocalDate>();
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        while (rowset.next()) {
            LocalDate date = LocalDate.parse(rowset.getString("avilDate"));
            avilDates.add(date);
        }
        return avilDates;
    }

    // given listing id
    // print all available dates for that listing
    public static void printAllAvailabilities(int id) throws SQLException {
        List<LocalDate> allDates = Listing.allAvailabilities(id);
        for (int i = 0; i < allDates.size(); i++) {
            System.out.print(allDates.get(i) + " ");
        }
    }

    // given listing id
    // return a list of rented dates for that listing
    public static List<LocalDate> allUnavailabilities(int id) throws SQLException {
        String query = "select * from rented where l_id = " + id + " and status = 0";
        ResultSet rs = Database.queryRead(query);
        List<LocalDate> UnavilDates = new ArrayList<LocalDate>();
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        while (rowset.next()) {
            LocalDate fromDate = LocalDate.parse(rowset.getString("fromDate"));
            LocalDate toDate = LocalDate.parse(rowset.getString("toDate"));
            List<LocalDate> tempDates = allDates(fromDate.toString(), toDate.toString());
            for (int i = 0; i < tempDates.size(); i++) UnavilDates.add(tempDates.get(i));
        }
        return UnavilDates;
    }

    // given listing id
    // print all available dates for that listing
    public static void printAllUnAvailabilities(int id) throws SQLException {
        List<LocalDate> allDates = Listing.allUnavailabilities(id);
        for (int i = 0; i < allDates.size(); i++) {
            System.out.print(allDates.get(i) + " ");
        }
    }

    // given listing id
    // return listing's owner ID or -1 if this listing not exist
    public static Integer getOwnerId(int l_id) throws SQLException {
        String query = "SELECT * FROM listing where id = " + l_id + ";";
        ResultSet rs = Database.queryRead(query);
        if (rs.next()) {
            return rs.getInt("owner");
        }
        return -1;
    }

    // print a list of amenities
    public static void printAmenity(List<String> amen) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < amen.size(); i++) {
            result.append(amen.get(i)).append(" ");
        }
        System.out.println(result);
    }

    // given 101101010101..., based on amenities above, return a list of amenities
    public static List<String> parseAmenity(String amen) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < amen.length(); i++) {
            if (amen.charAt(i) == '1') {
                result.add(amenities.get(i));
            }
        }
        return result;
    }

    // given two dates, return all dates in between (inclusive)
    // sample: ("2019-05-03", "2019-05-05") return ["2019-05-03", "2019-05-04", "2019-05-05"]
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

    // given (Longitude, Latitude, searchRadius, 'K'/'M', 1/0)
    // order: 1 - from nearest to farthest; 0 - from farthest to nearest
    // return a list of table rows within the given radius from the geo-point in KM/MILE in specified distance order
    // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static List<Row> searchByCoordinates_rankByDistance(Double lng, Double lat, Double radius, char unit, int order) throws SQLException {
        CachedRowSet tempResult = searchByCoordinates(lng, lat, radius, unit);
        if (order == 1) return increaseSort(tempResult, 13, 13);
        else return orderReverse(increaseSort(tempResult, 13, 13));
    }

    // given (Longitude, Latitude, searchRadius, 'K'/'M', 1/0)
    // order: 1 - from cheapest to most expensive; 0 - from most expensive to cheapest
    // return a list of table rows within the given radius from the geo-point in KM/MILE in specified price order
    // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static List<Row> searchByCoordinates_rankByPrice(Double lng, Double lat, Double radius, char unit, int order) throws SQLException {
        CachedRowSet tempResult = searchByCoordinates(lng, lat, radius, unit);
        if (order == 1) return increaseSort(tempResult, 10, 13);
        else return orderReverse(increaseSort(tempResult, 10, 13));
    }

    // given (Longitude, Latitude, searchRadius, 'K'/'M')
    // return CachedRowSet rows in mydb.address that is within the radius of given (Longitude, Latitude) in unit
    // return [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static CachedRowSet searchByCoordinates(Double lng, Double lat, Double radius, char unit) throws SQLException {
        String query = "SELECT address.*, listing.area, listing.dayPrice, listing.owner, listing.amenity, 0.0 as distance " +
                "FROM address join listing " +
                "on listing.id = address.id and address.type = 0;";
        ResultSet rs = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);

        while (rowset.next()) {
            Double tempLng = rowset.getDouble("lng");
            Double tempLat = rowset.getDouble("lat");
            Double dis = Map.distance(tempLat, tempLng, lat, lng, unit);
//            System.out.println("id: " + rowset.getInt("id") + " | distance: " + dis);
            if (dis > radius) rowset.updateDouble("distance", -1.0); // use -1 to mark unqualified rows
            else rowset.updateDouble("distance", dis);
//            rowset.updateDouble("distance", dis);
        }
        rowset.beforeFirst();
//        System.out.println(rowset.size() + "==================");
        return rowset;
    }


    // given addrInfo: [street, city, pcode, country]
    // order: 1 - from nearest to farthest; 0 - from farthest to nearest
    // return a list of table rows within the 30km of the given addr
    // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static List<Row> searchByAddress_rankByDistance(List<String> addrInfo, int order) throws SQLException, Exception {
        CachedRowSet tempResult = searchByAddress(addrInfo);
        if (order == 1) return increaseSort(tempResult, 13, 13);
        else return orderReverse(increaseSort(tempResult, 13, 13));
    }

    // given addrInfo: [street, city, pcode, country]
    // order: 1 - from cheapest to most expensive; 0 - from most expensive to cheapest
    // return a list of table rows within the 30km of the given addr
    // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static List<Row> searchByAddress_rankByPrice(List<String> addrInfo, int order) throws SQLException, Exception {
        CachedRowSet tempResult = searchByAddress(addrInfo);
        if (order == 1) return increaseSort(tempResult, 10, 13);
        else return orderReverse(increaseSort(tempResult, 10, 13));
    }

    // given addrInfo: [street, city, pcode, country]
    // return CachedRowSet rows in mydb.address that is within the 30km of the given addr
    // return [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static CachedRowSet searchByAddress(List<String> addrInfo) throws SQLException, Exception {
        List<Object> allInfo = Map.getAllByAddr(Map.infoToAddr(addrInfo)); // [street, city, pcode, country, lng, lat]
        CachedRowSet rowset = searchByCoordinates((Double) allInfo.get(4), (Double) allInfo.get(5), 30.0, 'K');
        return rowset;
    }


    // given pcode
    // order: 1 - from cheapest to most expensive; 0 - from most expensive to cheapest
    // return a list of table rows whose pcodes are similiar to but not same as the given pcode
    // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static List<Row> searchByPcode_rankByPrice_wildcard(String pcode, int order) throws SQLException, Exception {
        CachedRowSet tempResult = searchByPcode_wildcard(pcode);
        if (order == 1) return increaseSort(tempResult, 10, 13);
        else return orderReverse(increaseSort(tempResult, 10, 13));
    }

    // given pcode
    // order: 1 - from cheapest to most expensive; 0 - from most expensive to cheapest
    // return a list of table rows whose pcodes are same as the given pcode
    // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static List<Row> searchByPcode_rankByPrice_exact(String pcode, int order) throws SQLException, Exception {
        CachedRowSet tempResult = searchByPcode_exact(pcode);
        if (order == 1) return increaseSort(tempResult, 10, 13);
        else return orderReverse(increaseSort(tempResult, 10, 13));
    }


    // given (pcode)
    // return CachedRowSet rows in mydb.address that is same as the given pcode
    // return [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static CachedRowSet searchByPcode_exact(String pcode) throws SQLException {
        String sanitizedPcode = Map.pcodeSanitizer(pcode);
        String exactQuery = "SELECT address.*, listing.area, listing.dayPrice, listing.owner, listing.amenity, 0.0 as distance " +
                "FROM address join listing " +
                "on listing.id = address.id and address.type = 0 where address.pcode = '" + sanitizedPcode + "';";
        ResultSet rs = Database.queryRead(exactQuery);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        return rowset;
    }

    // given (pcode)
    // return CachedRowSet rows in mydb.address that is similar to but not same as the given pcode
    // return [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
    public static CachedRowSet searchByPcode_wildcard(String pcode) throws SQLException {
        String sanitizedPcode = Map.pcodeSanitizer(pcode);
        String wildcard_pcode = Map.pcodeSanitizer(pcode).substring(0, 4) + "%"; // second num is how wild it is [0, 6], 0 is most wild
        String query = "SELECT address.*, listing.area, listing.dayPrice, listing.owner, listing.amenity, 0.0 as distance " +
                "FROM address join listing " +
                "on listing.id = address.id and address.type = 0 and address.pcode != '" + sanitizedPcode + "'" +
                " where address.pcode like '" + wildcard_pcode + "';";
        ResultSet rs = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(rs);
        return rowset;
    }


    // given CachedRowSet (think of a table), colIndex on which column it is sorted
    // QAIndex which exams qualification (disqualify if marked -1 because CachedRowSet.deleteRow doesn't commit changes)
    // index 1, 2, 3... (not from 0)
    // return a sorted (from smallest to biggest) List<Row> based on the colIndex column
    public static List<Row> increaseSort(CachedRowSet input, int colIndex, int QAIndex) throws SQLException {
        Collection<Row> rows = (Collection<Row>) input.toCollection();
        Multimap<Double, Row> sorted = MultimapBuilder.treeKeys().linkedHashSetValues().build();
        for (Row row : rows) {
//            if (((BigDecimal) row.getColumnObject(QAIndex)).doubleValue() != -1) {  // use -1 to mark unqualified rows
            if (Double.parseDouble((row.getColumnObject(QAIndex)).toString()) != -1) {  // use -1 to mark unqualified rows
//                sorted.put(((BigDecimal) row.getColumnObject(colIndex)).doubleValue(), row);
                sorted.put(Double.parseDouble((row.getColumnObject(colIndex)).toString()), row);
            }
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

    // helper function for increaseSort from increase to decrease
    // input List<Row>
    // return the reverse order of the given list
    public static List<Row> orderReverse(List<Row> input) {
        return Lists.reverse(input);
    }

    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            Host me = new Host();
            List<String> info = Arrays.asList("qibowang7@outlook.com", "password");
            System.out.println(me.signIn(info));
//            viewListing(1);

            // [id, country, city, streeet, pcode, lng, lat, type, area, dayPrice, owner, amenity, distance]
//            List<Row> result = searchByCoordinates_rankByDistance(-79.186043, 43.784554, 3.0, 'K', 1);
//            List<Row> result = searchByCoordinates_rankByPrice(-79.186043, 43.784554, 3.0, 'K', 1);

            // given addrInfo: [street, city, pcode, country]
//            List<String> addrInfo = Arrays.asList("1265 military", "toronto", "m1c1a6", "Canada");
//            List<Row> result = searchByAddress_rankByDistance(addrInfo, 1);
//            List<Row> result = searchByAddress_rankByPrice(addrInfo, 1);


            List<Row> result = searchByPcode_rankByPrice_wildcard("M1E4B9", 1);
//            List<Row> result = searchByPcode_rankByPrice_exact("M1E4B9", 1);

            for (int i = 0; i < result.size(); i++) {
                System.out.println(result.get(i).getColumnObject(1) + " Price: " + result.get(i).getColumnObject(10));
//                System.out.println(result.get(i).getColumnObject(1) + " Distance: " + result.get(i).getColumnObject(13));
            }
        }


        Database.disconnect();
    }
}
