package test;

import java.sql.SQLException;
import java.util.*;

public class Test {
    public static void main(String args[]) throws Exception {
        if (Database.connect()){
            Renter renter = new Renter();
//            renter.signIn(Arrays.asList("D", "pass"));
//            System.out.println(renter.bookListing(Arrays.asList("17", "2019-06-27", "2019-07-02")));
            // comment after staying
//            renter.commentOnListing(Arrays.asList("1", "5", "Excellent", "17"));
//            renter.signIn(Arrays.asList("E", "pass"));
//            System.out.println(renter.bookListing(Arrays.asList("19", "2019-05-01", "2019-05-05")));
//            System.out.println(renter.commentOnListing(Arrays.asList("2", "1", "worst", "19")));
//            renter.signIn(Arrays.asList("A", "pass"));
//            System.out.println(renter.commentOnListing(Arrays.asList("3", "3", "good", "22")));
//            renter.signIn(Arrays.asList("F", "pass"));
//            System.out.println(renter.commentOnListing(Arrays.asList("3", "3", "good", "23")));
            renter.signIn(Arrays.asList("G", "pass"));
            System.out.println(renter.commentOnListing(Arrays.asList("3", "3", "good", "24")));
        }
        Database.disconnect();
    }
}
