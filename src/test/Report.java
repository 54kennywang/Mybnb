package test;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.util.*;
import java.sql.SQLException;

public class Report {

    /**
     * Get all x
     *
     * @return a table of rows where each row is a x
     * [x]
     */
    public static List<Row> getAll_x(String x) throws SQLException {
        String query = "select distinct " + x + " from address;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Get the number of bookings in a city in a specified time window
     *
     * @param fromDate time window start
     * @param toDate   time window end
     * @param city     a city name
     * @return the number of bookings in a city in a specified time window
     */
    public static int numOfBookingsByCity(String fromDate, String toDate, String city) throws SQLException {
        // natural join address, and rented
        String query = "select count(status) from address join rented on address.id = rented.l_id " +
                "where city = '" + city + "' and (status = 0 or status = 1) and fromDate >= '" + fromDate + "' and toDate <= '" + toDate + "';";
        ResultSet resultSet = Database.queryRead(query);
        resultSet.next();
        return resultSet.getInt("count(status)");
    }

    /**
     * Print the number of bookings in a city in a specified time window
     *
     * @param fromDate time window start
     * @param toDate   time window end
     */
    public static void report_numOfBookingsByCity(String fromDate, String toDate) throws SQLException {
        List<Row> cities = getAll_x("city");
        System.out.println("===Report on cities during " + fromDate + " - " + toDate + "===");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(cities.get(i).getColumnObject(1) + ": " + numOfBookingsByCity(fromDate, toDate, cities.get(i).getColumnObject(1).toString()));
        }
    }


    /**
     * Get the number of bookings in a zipCode area in a specified time window
     *
     * @param fromDate time window start
     * @param toDate   time window end
     * @param zipCode  a zipCode
     * @return the number of bookings in a zipCode area in a specified time window
     */
    public static int numOfBookingsByZipCode(String fromDate, String toDate, String zipCode) throws SQLException {
        //remove "-" from date
//        String fDate = dateRefactor(fromDate);
//        String tDate = dateRefactor(toDate);
        // natural join address, and rented
        String query = "select count(status) from address join rented on address.id = rented.l_id " +
                "where pcode = '" + zipCode + "' and (status = 0 or status = 1) and fromDate >= '" + fromDate + "' and toDate <= '" + toDate + "';";
        ResultSet resultSet = Database.queryRead(query);
        resultSet.next();
        return resultSet.getInt("count(status)");
    }

    /**
     * Print the number of bookings by pcode in all cities in a specified time window
     *
     * @param fromDate time window start
     * @param toDate   time window end
     */
    public static void report_numOfBookingsByZipCode(String fromDate, String toDate) throws SQLException {

        List<Row> cities = getAll_x("city");
        System.out.println("===Report on zip code during " + fromDate + " - " + toDate + " in cities===");
        for (int i = 0; i < cities.size(); i++) {
            System.out.println(cities.get(i).getColumnObject(1));
            String query = "SELECT distinct pcode FROM address where city = '" + cities.get(i).getColumnObject(1).toString() + "';";
            ResultSet resultSet = Database.queryRead(query);
            CachedRowSet rowset = new CachedRowSetImpl();
            rowset.populate(resultSet);
            List<Row> temp = Listing.CachedRowSet_to_ListRow(rowset);
            for (int j = 0; j < temp.size(); j++) {
                System.out.println("  " + temp.get(j).getColumnObject(1).toString() + ": " + numOfBookingsByZipCode(fromDate, toDate, temp.get(j).getColumnObject(1).toString()));
            }
        }
    }

    /**
     * Get the number of listing for different countries
     *
     * @return a table of the number of listing for different countries
     * [country, count(listing.id)]
     */
    public static List<Row> numOfListingsPerCountry() throws SQLException {
        String query = "select country, count(listing.id) from address, listing" +
                " where address.id = listing.id group by country;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Print the number of bookings by countries
     */
    public static void report_numOfListingsPerCountry() throws SQLException {
        List<Row> temp = numOfListingsPerCountry();
        System.out.println("===Report on number of listings by countries===");
        for (int i = 0; i < temp.size(); i++) {
            System.out.println("  " + temp.get(i).getColumnObject(1) + ": " + temp.get(i).getColumnObject(2));
        }
    }

    /**
     * Get the number of listing for different cities in specified country
     *
     * @return a table of the number of listing for different cities in specified country
     * [city, country, count(listing.id)]
     */
    public static List<Row> numOfListingsPerCountryPerCity(String country) throws SQLException {
        String query = "select city, country, count(listing.id) from address, listing" +
                " where address.id = listing.id and country = '" + country + "' group by city, country;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Print the number of bookings by cities
     */
    public static void report_numOfListingsPerCountryPerCity() throws SQLException {
        List<Row> countries = getAll_x("country");
        System.out.println("===Report on number of listings by cities===");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).getColumnObject(1));
            List<Row> temp = numOfListingsPerCountryPerCity(countries.get(i).getColumnObject(1).toString());
            for (int j = 0; j < temp.size(); j++) {
                System.out.println("  " + temp.get(j).getColumnObject(1) + ": " + temp.get(j).getColumnObject(3));
            }
        }
    }

    /**
     * Get the number of listing for different (country, city, pcode) pair
     *
     * @return a table of the number of listing for different (country, city, pcode) pair
     * [city, pcode, country, count(listing.id)]
     */
    public static List<Row> numOfListingsPerCountryPerCityPerPCode(String country, String city) throws SQLException {
        String query = "select city, pcode, country, count(listing.id) from address, listing " +
                "where address.id = listing.id and country = '" + country + "' and city = '" + city + "' group by city, pcode, country;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }


    /**
     * Print the number of bookings by postal code
     */
    public static void report_numOfListingsPerCountryPerCityPerPCode() throws SQLException {
        List<Row> countries = getAll_x("country");
        System.out.println("===Report on number of listings by postal code===");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).getColumnObject(1));

            String query = "select distinct city from address where country = '" + countries.get(i).getColumnObject(1).toString() + "';";
            ResultSet resultSet = Database.queryRead(query);
            CachedRowSet rowset = new CachedRowSetImpl();
            rowset.populate(resultSet);
            List<Row> cities = Listing.CachedRowSet_to_ListRow(rowset);

            for (int k = 0; k < cities.size(); k++) {
                System.out.println("  " + cities.get(k).getColumnObject(1));
                List<Row> temp = numOfListingsPerCountryPerCityPerPCode(countries.get(i).getColumnObject(1).toString(), cities.get(k).getColumnObject(1).toString());
                for (int j = 0; j < temp.size(); j++) {
                    System.out.println("    " + temp.get(j).getColumnObject(2) + ": " + temp.get(j).getColumnObject(4));
                }
            }
        }
    }

    /**
     * Get the table of number of listings for different hosts in different countries, descending order by count (all records)
     *
     * @param country country name
     * @return a table of number of listings for different hosts in different countries, descending order by count (all records)
     * [ownerID, country, count(listing.id)]
     */
    public static List<Row> rankHostsByListingsPerCountry(String country) throws SQLException {
        String query = "select owner, country, count(listing.id) " +
                "from listing, address " +
                "where listing.id = address.id and address.type = 0 and address.country = '" + country +
                "' group by owner, country " +
                "order by count(listing.id) desc;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Print the number of listings for hosts by country
     */
    public static void report_rankHostsByListingsPerCountry() throws SQLException {
        List<Row> countries = getAll_x("country");
        System.out.println("===Report on the ranking of the number of listings for host by country===");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).getColumnObject(1));
            List<Row> temp = rankHostsByListingsPerCountry(countries.get(i).getColumnObject(1).toString());
            for (int j = 0; j < temp.size(); j++) {
                System.out.println("  Host ID " + temp.get(j).getColumnObject(1) + " has " + temp.get(j).getColumnObject(3) + " listings.");
            }
        }
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
    }

    /**
     * Print the number of listings for hosts by city
     */
    public static void report_rankHostsByListingsPerCity() throws SQLException {
        List<Row> countries = getAll_x("country");
        System.out.println("===Report on the ranking of the number of listings for host by city===");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).getColumnObject(1));

            String query = "select distinct city from address where country = '" + countries.get(i).getColumnObject(1).toString() + "';";
            ResultSet resultSet = Database.queryRead(query);
            CachedRowSet rowset = new CachedRowSetImpl();
            rowset.populate(resultSet);
            List<Row> cities = Listing.CachedRowSet_to_ListRow(rowset);

            for (int k = 0; k < cities.size(); k++) {
                System.out.println("  " + cities.get(k).getColumnObject(1));
                List<Row> temp = rankHostsByListingsPerCity(countries.get(i).getColumnObject(1).toString(), cities.get(k).getColumnObject(1).toString());
                if (temp.size() == 0) {
                    System.out.println("    ***no record");
                }
                for (int j = 0; j < temp.size(); j++) {
                    System.out.println("    Host ID " + temp.get(j).getColumnObject(1) + " has " + temp.get(j).getColumnObject(3) + " listings.");
                }
            }
        }
    }

    /**
     * Get the table of overposting users, if fromDate and toDate are not empty, a time window is specified
     *
     * @param fromDate time window start
     * @param toDate   time window end
     * @param country  a country name
     * @param city     a city name
     * @return a table of overposting users
     * [owner, count, count_percentage]
     */
    public static List<Row> spamPosting(String fromDate, String toDate, String country, String city) throws SQLException {
        String query = "";
        if (fromDate.equals("")) {
            query =
                    "select * from " +
                            " (select owner, count(*) as 'count', count(*) / " +
                            "(" +
                            " SELECT count(*) as 'count' " +
                            " FROM listing l , address a where l.id = a.id and a.type = 0 " +
                            " and country = '" + country + "' and city = '" + city + "' " +
                            " ) as 'count_percentage' " +
                            " FROM listing l , address a where l.id = a.id and a.type = 0 " +
                            " and country = '" + country + "' and city = '" + city + "' " +
                            " group by owner order by count Desc) x " +
                            " where x.count_percentage > 0.1;";
        } else {
            query = "select * from " +
                    " (select owner, count(*) as 'count', count(*) / " +
                    "(" +
                    " SELECT count(*) as 'count' " +
                    " FROM listing l , address a where l.id = a.id and a.type = 0 " +
                    " and country = 'Canada' and city = 'Toronto' and date => '" + fromDate + "' and date <= '" + toDate + "' " +
                    " ) as 'count_percentage' " +
                    " FROM listing l , address a where l.id = a.id and a.type = 0 " +
                    " and country = '" + country + "' and city = '" + city + "' and date => '" + fromDate + "' and date <= '" + toDate + "' " +
                    " group by owner order by count Desc) x " +
                    " where x.count_percentage > 0.1;";
        }
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Print the overposting users
     */
    public static void report_spamPosting(String fromDate, String toDate) throws SQLException {
        System.out.println("===Report on the hosts with listings more than 10% of the total listing===");
        List<Row> countries = getAll_x("country");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).getColumnObject(1));

            String query = "select distinct city from address where country = '" + countries.get(i).getColumnObject(1).toString() + "';";
            ResultSet resultSet = Database.queryRead(query);
            CachedRowSet rowset = new CachedRowSetImpl();
            rowset.populate(resultSet);
            List<Row> cities = Listing.CachedRowSet_to_ListRow(rowset);

            for (int k = 0; k < cities.size(); k++) {
                System.out.println("  " + cities.get(k).getColumnObject(1));
                List<Row> flagged = spamPosting(fromDate, toDate, countries.get(i).getColumnObject(1).toString(), cities.get(k).getColumnObject(1).toString());
                for (int j = 0; j < flagged.size(); j++) {
                    System.out.println("    Host ID " + flagged.get(j).getColumnObject(1) + " has " + flagged.get(j).getColumnObject(2) + " listings (percentage: " +
                            Double.parseDouble(flagged.get(j).getColumnObject(3).toString()) * 100 + "%)");
                }
            }
        }


    }

    /**
     * Get the table of renter booking (lived) times in a time window
     *
     * @param startDate time window begin time
     * @param toDate    time window ending time
     * @return a table of renter booking (lived) times in a time window
     * [u_id, count]
     */
    public static List<Row> rankRentersByNumOfBookings(String startDate, String toDate) throws SQLException {
        String query = "select r.u_id, count(*) as 'count' from rented r, address a " +
                "where a.id = r.l_id and a.type = 0 and r.fromDate >= '" + startDate + "' and r.toDate <= '" + toDate + "' " +
                "and r.status = 1 " +
                "group by r.u_id having count(*) > 1 " +
                "order by count(*) desc;";
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Print the number of bookings (lived) in a time window
     *
     * @param startDate time window begin time
     * @param toDate    time window ending time
     */
    public static void report_rankRentersByNumOfBookings(String startDate, String toDate) throws SQLException {
        System.out.println("===Report on number of bookings of renters during " + startDate + " to " + toDate + "===");
        List<Row> temp = rankRentersByNumOfBookings(startDate, toDate);
        for (int i = 0; i < temp.size(); i++) {
            System.out.println("Renter ID " + temp.get(i).getColumnObject(1) + " lived " +
                    temp.get(i).getColumnObject(2) + " times on Mybnb." );
        }
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
    public static List<Row> rankRentersByNumOfBookingsPerCity(String startDate, String toDate, String country, String city) throws SQLException {
        List<Row> result = Report.rankRentersByNumOfBookings(startDate, toDate);
        for (int i = 0; i < result.size(); i++) {
            if (!result.get(i).getColumnObject(2).toString().equals(country) || !result.get(i).getColumnObject(3).toString().equals(city)) {
                result.remove(i);
                i--;
            }
        }
        return result;
    }

    /**
     * Print the number of bookings (lived) in a time window by cities
     *
     * @param startDate time window begin time
     * @param toDate    time window ending time
     */
    public static void report_rankRentersByNumOfBookingsPerCity(String startDate, String toDate) throws SQLException {
        List<Row> countries = getAll_x("country");
        System.out.println("===Report on number of bookings of renters during " + startDate + " to " + toDate + " by cities===");
        for (int i = 0; i < countries.size(); i++) {
            System.out.println(countries.get(i).getColumnObject(1));

            String query = "select distinct city from address where country = '" + countries.get(i).getColumnObject(1).toString() + "';";
            ResultSet resultSet = Database.queryRead(query);
            CachedRowSet rowset = new CachedRowSetImpl();
            rowset.populate(resultSet);
            List<Row> cities = Listing.CachedRowSet_to_ListRow(rowset);

            for (int k = 0; k < cities.size(); k++) {
                System.out.println("  " + cities.get(k).getColumnObject(1));
                List<Row> temp = rankRentersByNumOfBookingsPerCity(startDate, toDate, countries.get(i).getColumnObject(1).toString(), cities.get(k).getColumnObject(1).toString());
                if (temp.size() == 0) System.out.println("    ***no record");
                for (int j = 0; j < temp.size(); j++) {
                    System.out.println("    Renter ID " + temp.get(j).getColumnObject(1) + " lived " +
                            temp.get(j).getColumnObject(4) + " times.");
                }
            }
        }
    }

    /**
     * Get a table of largest cancellation times in a specified time window for host/renter (at least one row, maybe severa users have same largest num)
     *
     * @param type     1 - renter; 2 - host
     * @param fromDate time window begin time
     * @param toDate   time window ending time
     * @return a table of largest cancellation times in a specified time window for host/renter
     * [u_id, count]
     */
    public static List<Row> largestCancellation(int type, String fromDate, String toDate) throws SQLException {
        String query = "";
        if (type == 1) {
            query =
                    "select * from " +
                            "(select r.u_id, count(*) as 'count' from rented r " +
                            "where r.status = -1 " +
                            "and r.date >= '" + fromDate + "' and r.date <= '" + toDate + "' " +
                            "group by r.u_id) o " +
                            "where o.count >= all " +
                            "(select count(*) as 'count' from rented r " +
                            "where r.status = -1 " +
                            "and r.date >= '" + fromDate + "' and r.date <= '" + toDate + "' " +
                            "group by r.u_id); ";
        } else if (type == 2) {
            query =
                    "select * from " +
                            "(select l.owner as 'u_id', count(*) as 'count' from rented r, listing l " +
                            "where r.l_id = l.id and r.status = -2 " +
                            "and r.date >= '" + fromDate + "' and r.date <= '" + toDate + "' " +
                            "group by l.owner) o " +
                            "where o.count >= all " +
                            "(select count(*) as 'count' from rented r, listing l " +
                            "where r.l_id = l.id and r.status = -2 " +
                            "and r.date >= '" + fromDate + "' and r.date <= '" + toDate + "' " +
                            "group by l.owner); ";
        }
        ResultSet resultSet = Database.queryRead(query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(resultSet);
        return Listing.CachedRowSet_to_ListRow(rowset);
    }

    /**
     * Print a table of largest cancellation times in a specified time window for host/renter (at least one row, maybe severa users have same largest num)
     *
     * @param type     1 - renter; 2 - host
     * @param fromDate time window begin time
     * @param toDate   time window ending time
     *                 [u_id, count]
     */
    public static void report_largestCancellation(int type, String fromDate, String toDate) throws SQLException {
        if (type == 1) {
            System.out.println("===Report on renters with largest cancellations during " + fromDate + " to " + toDate + "===");
        } else if (type == 2) {
            System.out.println("===Report on hosts with largest cancellations during " + fromDate + " to " + toDate + "===");
        }
        List<Row> temp = largestCancellation(type, fromDate, toDate);
        if (temp.size() == 0) {
            System.out.println("  *** no record");
            return;
        }
        for (int i = 0; i < temp.size(); i++) {
            if (type == 1)
                System.out.println("Renter ID " + temp.get(i).getColumnObject(1) + " has " + temp.get(i).getColumnObject(2) + " cancellations.");
            else if (type == 2)
                System.out.println("Host ID " + temp.get(i).getColumnObject(1) + " has " + temp.get(i).getColumnObject(2) + " cancellations.");
        }
    }

    public static void popularPhrasesReport(String ID) throws SQLException {
        HashMap<String, Integer> wordCloud = Listing.mostPopularPhrases(ID);
        System.out.println("The number of occurrence of the comment on this listing: ");
        Iterator it = wordCloud.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }
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

//            List<Row> x = spamPosting("2019-07-10", "2019-07-31");
//            for (int i = 0; i < x.size(); i++) {
//                System.out.println("owner: " + x.get(i).getColumnObject(1) + " | count: " + x.get(i).getColumnObject(2) + " | count_percentage: " + x.get(i).getColumnObject(3));
//            }

            List<Row> x = largestCancellation(2, "2019-01-01", "2019-12-31");
            for (int i = 0; i < x.size(); i++) {
                System.out.println("u_id: " + x.get(i).getColumnObject(1) + " | count: " + x.get(i).getColumnObject(2));
            }
            Database.disconnect();
        }
    }
}
