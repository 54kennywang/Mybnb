package test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;
import java.sql.SQLException;

public class Report {

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

    public static int numOfBookingsByZipCode(LocalDate fromDate, LocalDate toDate, String zipCode) throws SQLException{
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

    public static HashMap<String, Integer> numOfListingsPerCountry() throws SQLException{
        String query = "select country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by country;";
        ResultSet resultSet = Database.queryRead(query);
        HashMap<String, Integer> hashMap = new HashMap<>();
        while(resultSet.next()){
            String country = resultSet.getString("country");
            int num = resultSet.getInt("count(listing.id)");
            hashMap.put(country, num);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> numOfListingsPerCountryPerCity() throws SQLException{
        String query = "select city, country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by city, country;";
        ResultSet resultSet = Database.queryRead(query);
        HashMap<String, Integer> hashMap = new HashMap<>();
        while(resultSet.next()){
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String place = "(" + city + "," + country + ")";
            int num = resultSet.getInt("count(listing.id)");
            hashMap.put(place, num);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> numOfListingsPerCountryPerCityPerPCode() throws SQLException{
        String query = "select city, pcode, country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by city, pcode, country;";
        ResultSet resultSet = Database.queryRead(query);
        HashMap<String, Integer> hashMap = new HashMap<>();
        while(resultSet.next()){
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String pcode = resultSet.getString("pcode");
            String place = "(" + city + "," + "," + pcode + "," + country + ")";
            int num = resultSet.getInt("count(listing.id)");
            hashMap.put(place, num);
        }
        return hashMap;
    }

    private static String dateRefactor(LocalDate date){
        return date.toString().replaceAll("-", "");
    }

    public static void main(String args[]) throws SQLException {
        if (Database.connect()){
            LocalDate fromDate = LocalDate.parse("2020-01-01");
            LocalDate toDate = LocalDate.parse("2021-01-01");
//            System.out.println(numOfBookingsByCity(fromDate, toDate, "London"));
//            System.out.println(numOfBookingsByZipCode(fromDate, toDate, "C3A8BF"));
            HashMap<String, Integer> hashMap = numOfListingsPerCountryPerCityPerPCode();
            System.out.println(hashMap.entrySet());
        }
        Database.disconnect();
    }
}
        /*
package test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;
import java.sql.SQLException;

public class Report {

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

    public static int numOfBookingsByZipCode(LocalDate fromDate, LocalDate toDate, String zipCode) throws SQLException{
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

    public static HashMap<String, Integer> numOfListingsPerCountry() throws SQLException{
        String query = "select country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by country;";
        ResultSet resultSet = Database.queryRead(query);
        HashMap<String, Integer> hashMap = new HashMap<>();
        while(resultSet.next()){
            String country = resultSet.getString("country");
            int num = resultSet.getInt("count(listing.id)");
            hashMap.put(country, num);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> numOfListingsPerCountryPerCity() throws SQLException{
        String query = "select city, country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by city, country;";
        ResultSet resultSet = Database.queryRead(query);
        HashMap<String, Integer> hashMap = new HashMap<>();
        while(resultSet.next()){
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String place = "(" + city + "," + country + ")";
            int num = resultSet.getInt("count(listing.id)");
            hashMap.put(place, num);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> numOfListingsPerCountryPerCityPerPCode() throws SQLException{
        String query = "select city, pcode, country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by city, pcode, country;";
        ResultSet resultSet = Database.queryRead(query);
        HashMap<String, Integer> hashMap = new HashMap<>();
        while(resultSet.next()){
            String country = resultSet.getString("country");
            String city = resultSet.getString("city");
            String pcode = resultSet.getString("pcode");
            String place = "(" + city + "," + "," + pcode + "," + country + ")";
            int num = resultSet.getInt("count(listing.id)");
            hashMap.put(place, num);
        }
        return hashMap;
    }

    public static ResultSet rankHostsByListingsPerCountry() throws SQLException {
        String query = "select owner, country, count(listing.id) " +
                "from listing, address " +
                "where listing.id = address.id " +
                "group by owner, country " +
                "order by country, count(listing.id) desc;";
        ResultSet resultSet = Database.queryRead(query);
        return resultSet;
    }

    private static String dateRefactor(LocalDate date){
        return date.toString().replaceAll("-", "");
    }

    public static void main(String args[]) throws SQLException {
        if (Database.connect()){
            LocalDate fromDate = LocalDate.parse("2020-01-01");
            LocalDate toDate = LocalDate.parse("2021-01-01");
//            System.out.println(numOfBookingsByCity(fromDate, toDate, "London"));
//            System.out.println(numOfBookingsByZipCode(fromDate, toDate, "C3A8BF"));
            HashMap<String, Integer> hashMap = numOfListingsPerCountryPerCityPerPCode();
            System.out.println(hashMap.entrySet());
        }
        Database.disconnect();
    }
}
*/
