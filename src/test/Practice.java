package test;

import org.joda.time.Years;

import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.ChronoUnit;

public class Practice {
    public static void main( String args[] ) {
        String DOB = "1996-06-22";
        System.out.println(ChronoUnit.YEARS.between(LocalDate.parse(DOB), LocalDate.now()));
        System.out.println(LocalDate.now());
    }
}
