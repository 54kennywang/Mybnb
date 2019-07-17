package test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
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

    private static String dateRefactor(LocalDate date){
        return date.toString().replaceAll("-", "");
    }

    public static void main(String args[]) throws SQLException {
        if (Database.connect()){
            LocalDate fromDate = LocalDate.parse("2020-01-01");
            LocalDate toDate = LocalDate.parse("2021-01-01");
            System.out.println(numOfBookingsByCity(fromDate, toDate, "London"));
            System.out.println(numOfBookingsByZipCode(fromDate, toDate, "C3A8BF"));
        }
        Database.disconnect();
    }
}
