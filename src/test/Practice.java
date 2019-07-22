package test;

import com.google.common.collect.*;
import com.sun.rowset.internal.Row;
import org.joda.time.Years;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Practice {
    public static void main(String args[]) throws Exception {
//        if (Database.connect()) {
//            Renter me = new Renter();
//
//            List<String> cred = Arrays.asList("qibowang7@gmail.com", "password");
//            System.out.println(me.signIn(cred));
//
//            CachedRowSet rowset = me.getAllBookings();
//
//            /*
//            Collection<Row> rows = (Collection<Row>) rowset.toCollection();
//            Multimap<Integer, Row> sorted = MultimapBuilder.treeKeys().linkedHashSetValues().build();
//            for(Row row : rows){
//                System.out.println("u_id: " + row.getColumnObject(1) + " i_id: " + row.getColumnObject(2));
//                sorted.put((Integer) row.getColumnObject(2), row);
//            }
//            System.out.println("=====================");
//            List<Row> result = new ArrayList<Row>();
//            for (Integer key : sorted.keySet()) {
//                Collection<Row> temp = sorted.get(key);
//                for(Row item : temp){
//                    result.add(item);
//                }
//            }
//
//            List<Row> reverseResult = Lists.reverse(result);
//            for(int i = 0; i < result.size(); i ++){
//                System.out.println("u_id: " + result.get(i).getColumnObject(1) + " i_id: " + result.get(i).getColumnObject(2));
//                System.out.println("u_id: " + reverseResult.get(i).getColumnObject(1) + " i_id: " + reverseResult.get(i).getColumnObject(2));
//            }
//             */
//
////            while (rowset.next()) {
////                Integer u_id = rowset.getInt("u_id");
////                Integer l_id = rowset.getInt("l_id");
////                System.out.println("u_id: " + u_id + " l_id: " + l_id);
////                if(l_id == 5) rowset.deleteRow();
////                else rowset.updateInt(2, 99);
////            }
////            System.out.println();
////            rowset.beforeFirst();
////            System.out.println(rowset.size() + "================");
////            while (rowset.next()) {
////                Integer u_id = rowset.getInt("u_id");
////                Integer l_id = rowset.getInt("l_id");
////                System.out.println("u_id: " + u_id + " l_id: " + l_id);
////            }
//
////            System.out.println();
////            rowset.moveToInsertRow();
////            rowset.updateInt(1, 99);
////            rowset.updateInt(2, 99);
////            rowset.updateString(3, "2099-09-09");
////            rowset.updateString(4, "2099-09-09");
////            rowset.updateInt(5, 99);
////            rowset.updateString(6, "2099-09-09 23:55:55");
////            rowset.updateString(7, "99");
////            rowset.insertRow();
////            rowset.moveToCurrentRow();
////            System.out.println("u_id: " + rowset.getInt("u_id") + " l_id: " + rowset.getInt("l_id"));
//        }
//        Database.disconnect();
        List<LocalDate> dates1 = new ArrayList<LocalDate>();
        dates1.add(LocalDate.now());
        dates1.add(LocalDate.now().plusDays(-1));

        List<LocalDate> dates2 = new ArrayList<LocalDate>();
        dates2.add(LocalDate.now().plusDays(-1));
        dates2.add(LocalDate.now().plusDays(-2));

        List<LocalDate> dates3 = new ArrayList<LocalDate>(dates1);
        dates3.retainAll(dates2);
        System.out.println(dates1);
        System.out.println(dates2);
        System.out.println(dates3);
    }
}
