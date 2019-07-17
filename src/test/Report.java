package test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.sql.SQLException;

public class Report {

    public static int numOfBookingsByCity(LocalDate fromDate, LocalDate toDate, String city) throws SQLException {
        //remove "-" from date
        String fDate = fromDate.toString().replaceAll("-","");
        String tDate = toDate.toString().replaceAll("-","");
        // natural join address, and rented
        String query = "select count(status) from address join rented on address.id = rented.l_id " +
                "where city = '" + city + "' and status = " + 0 + " and fromDate >= " + fDate + " and toDate <= " + tDate + ";";
        ResultSet resultSet = Database.queryRead(query);
        resultSet.next();
        return resultSet.getInt("count(status)");
    }

    public static void numOfBookingByZipCode(List<LocalDate> dateRange, String zipCode){

    }

    public static void main(String args[]) throws SQLException {
        if (Database.connect()){
            LocalDate fromDate = LocalDate.parse("2020-01-01");
            LocalDate toDate = LocalDate.parse("2021-01-01");
            System.out.println(numOfBookingsByCity(fromDate, toDate, "London"));
        }
        Database.disconnect();
    }
}
