package test;

import com.google.common.collect.*;
import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;
import org.joda.time.Years;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Practice {
    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
//            Renter me = new Renter();
//
//            List<String> cred = Arrays.asList("qibowang7@gmail.com", "password");
//            System.out.println(me.signIn(cred));

//            String query = "select * from mydb.user_comment where id = 1;";
//            ResultSet resultSet = Database.queryRead(query);
//            CachedRowSet rowset = new CachedRowSetImpl();
//            rowset.populate(resultSet);
//            List<Row> x = Listing.CachedRowSet_to_ListRow(rowset);
//            System.out.println(x.get(0).getColumnObject(4) == null);
        }
        Database.disconnect();

        String amer = "11010111010100";
        if (amer.matches("11............")) System.out.println("yes");
        else System.out.println("no");;
    }
}
