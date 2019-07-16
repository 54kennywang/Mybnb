package test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

public class Report {

    public static int numOfBookingsByCity(LocalDate fromDate, LocalDate toDate, String city) {
        // natural join address, listing, and rented
        String query = "select count(status) from listing, address, rented where city = " + city + " and status = " + 0 + " and ";
        ResultSet resultSet = Database.queryRead(query);
        return 0;
    }

    public static void numOfBookingByZipCode(List<LocalDate> dateRange, String zipCode){

    }
}
