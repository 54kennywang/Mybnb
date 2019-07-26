package test;

import com.sun.rowset.internal.Row;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MenuController {
    public Host client = null;
    public int type = 0; // 1 for renter; 2 for host

    public void start() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, here is the menu, select your option.");
        this.printMenu();
        System.out.print("> ");
        String option = input.nextLine();
        while (!option.equals("EXIT")) {
            if (option.equals("MENU")) {
                this.printMenu();
            } else if (option.equals("1")) {
                this.logIn();
            } else if (option.equals("2")) {
                this.signUp();
            } else if (option.equals("3")) {
                this.logOut();
            } else if (option.equals("4")) {
                System.out.println("  Please specify listing options (1 for searching by geo-location; 2 for searching by postal code; 3 for searching by address)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.searchByCoordinates();
                else if (subOption.equals("2")) this.searchByPcode();
                else if (subOption.equals("3")) this.searchByAddress();
            } else if (option.equals("5")) {
                System.out.println("  Please specify searching options (1 for viewing; 2 for booking; 3 for cancelling)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.viewListing();
                else if (subOption.equals("2")) this.bookOrCancel_Listing(1);
                else if (subOption.equals("3")) this.bookOrCancel_Listing(0);
            } else if (option.equals("6")) {
            } else if (option.equals("7")) {
            } else if (option.equals("8")) {
            } else if (option.equals("9")) {
            }
            System.out.println("Tell me what's next, type MENU to see options.");
            System.out.print("> ");
            option = input.nextLine();
        }
        System.out.println("Bye!");
    }

    public void printMenu() {
        System.out.println("  1. Login");
        System.out.println("  2. Register");
        System.out.println("  3. Logout");
        System.out.println("  4. Search listings");

        System.out.println("  5. View/book/cancel a listing");
        System.out.println("  6. Post/update/delete a listing");
        System.out.println("  7. Comment/reply");
        System.out.println("  8. View user info");
    }

    public boolean dateFormat(String date) {
        if (date.matches("^\\d{4}-\\d{2}-\\d{2}$")) return true;
        else return false;
    }

    public void logIn() throws Exception {
        if (client != null) {
            System.out.println("***You already logged in***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        System.out.println("Username (email):");
        System.out.print("> ");
        info.add(input.next());

        System.out.println("Password:");
        System.out.print("> ");
        info.add(input.next());
        Host user = new Host();
        if (user.signIn(info)) {
            this.client = user;
            if (client.getType() == 1) {
                this.type = 1;
            } else if (client.getType() == 2) {
                this.type = 2;
            }
            System.out.println("Login successfully!");
        } else System.out.println("Username/password wrong.");
    }

    public void signUp() throws Exception {
        if (client != null) {
            System.out.println("***You already logged in***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        System.out.println("Username (email):");
        System.out.print("> ");
        info.add(input.next());

        System.out.println("Credit Card Number:");
        System.out.print("> ");
        info.add(input.next());

        System.out.println("Real Name:");
        System.out.print("> ");
        info.add(input.next());

        System.out.println("Occupation:");
        System.out.print("> ");
        info.add(input.next());

        System.out.println("DOB:");
        System.out.print("> ");
        String DOB = input.next();
        if (!dateFormat(DOB)) return;
        if (ChronoUnit.YEARS.between(LocalDate.parse(DOB), LocalDate.now()) < 18) {
            System.out.println("You are under 18, register failed.");
            return;
        }
        info.add(DOB);

        System.out.println("SIN:");
        System.out.print("> ");
        info.add(input.next());

        System.out.println("Password:");
        System.out.print("> ");
        info.add(input.next());


        List<String> addrInfo = new ArrayList<String>();
        System.out.println("Please tell us your address.");
        System.out.println("Street:");
        System.out.print("> ");
        addrInfo.add(input.next());

        System.out.println("City:");
        System.out.print("> ");
        addrInfo.add(input.next());

        System.out.println("Postal Code:");
        System.out.print("> ");
        addrInfo.add(input.next());

        System.out.println("Country:");
        System.out.print("> ");
        addrInfo.add(input.next());

        if ((new Renter()).register(info, addrInfo)) System.out.println("Register successfully! Now you can login.");
        else System.out.println("Register failed.");
    }

    public void logOut() {
        if (client == null) {
            System.out.println("***You are not logged in***");
            return;
        }
        this.client = null;
        this.type = 0;
    }

    public void searchByCoordinates() throws Exception {
        Scanner input = new Scanner(System.in);
        Double lng = 0.0;
        Double lat = 0.0;
        Double radius = 0.0;
        int order = 0;
        int option = 0;
        System.out.println("Longitude:");
        System.out.print("> ");
        lng = input.nextDouble();

        System.out.println("Latitude:");
        System.out.print("> ");
        lat = input.nextDouble();

        System.out.println("Rank by price (1) or rank by distance (2):");
        System.out.print("> ");
        option = input.nextInt();

        System.out.println("Searching radius (km):");
        System.out.print("> ");
        radius = input.nextDouble();

        System.out.println("Order (1 for ascending, 0 for descending)");
        System.out.print("> ");
        order = input.nextInt();


        if (option == 1) {
            Listing.viewAllListing(Listing.searchByCoordinates_rankByPrice(lng, lat, radius, 'K', order), 1);
        } else if (option == 2) {
            Listing.viewAllListing(Listing.searchByCoordinates_rankByDistance(lng, lat, radius, 'K', order), 1);
        }
    }

    public void searchByPcode() throws Exception {
        Scanner input = new Scanner(System.in);
        String pcode = "";
        int order = 0;
        Double radius = 0.0;
        System.out.println("Note: search by postal code doesn't support ranking by distance. By default, result will be shown ranked by price.");
        System.out.println("Postal Code:");
        System.out.print("> ");
        pcode = input.nextLine();

        System.out.println("(Ranking by price) order (1 for ascending, 0 for descending)");
        System.out.print("> ");
        order = input.nextInt();

        List<Row> exactResult = Listing.searchByPcode_rankByPrice_exact(pcode, order);
        List<Row> wildcardResult = Listing.searchByPcode_rankByPrice_wildcard(pcode, order);
        if (exactResult.size() == 0) {
            System.out.println("***Sorry, not result found with the exactly same postal code, nearby area are shown below***");
            Listing.viewAllListing(wildcardResult, 0);
        } else {
            System.out.println("***We have found the listing with the same postal code you want***");
            Listing.viewAllListing(exactResult, 0);
            System.out.println("**************************************************************");
            System.out.println("***Here are also some nearby areas***");
            Listing.viewAllListing(wildcardResult, 0);
        }
    }

    public void searchByAddress() throws Exception {
        Scanner input = new Scanner(System.in);
        List<String> addrInfo = new ArrayList<String>();
        int order = 0;
        int option = 0;
        Double radius = 0.0;
        System.out.println("Street:");
        System.out.print("> ");
        addrInfo.add(input.nextLine());

        System.out.println("City:");
        System.out.print("> ");
        addrInfo.add(input.nextLine());

        System.out.println("Postal Code:");
        System.out.print("> ");
        addrInfo.add(input.nextLine());

        System.out.println("Country:");
        System.out.print("> ");
        addrInfo.add(input.nextLine());

        System.out.println("Searching radius (km):");
        System.out.print("> ");
        radius = input.nextDouble();

        System.out.println("Rank by price (1) or rank by distance (2):");
        System.out.print("> ");
        option = input.nextInt();

        System.out.println("Order (1 for ascending, 0 for descending)");
        System.out.print("> ");
        order = input.nextInt();


        if (option == 1) {
            Listing.viewAllListing(Listing.searchByAddress_rankByPrice(addrInfo, radius, order), 1);
        } else if (option == 2) {
            Listing.viewAllListing(Listing.searchByAddress_rankByDistance(addrInfo, radius, order), 1);
        }
    }

    public void viewListing() throws SQLException {
        Scanner input = new Scanner(System.in);
        int id = 0;
        System.out.println("Listing ID:");
        System.out.print("> ");
        id = input.nextInt();
        Listing.viewListing(id);
    }

    // 1 for book, 0 for cancel
    public void bookOrCancel_Listing(int i) throws Exception {
        if (client == null) {
            System.out.println("***Please login first***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        System.out.println();
        if (i == 0) {
            System.out.println("***Here are your bookings***");
            if (client.viewBooking(client.getBookings(0)) == 0) return;
        }
        System.out.println("Listing ID:");
        System.out.print("> ");
        String l_id = input.nextLine();
        if (i == 1) {
            if (Listing.viewListing(Integer.parseInt(l_id)) == 1) {
                info.add(l_id);
            } else return;
        } else if (i == 0) {
            if (client.bookingValidation(0, Integer.parseInt(l_id))) {
                info.add(l_id);
            } else {
                System.out.println("***You don't have bookings with listing ID " + l_id + "***");
                return;
            }
        }

        System.out.println("Starting date (yyyy-mm-dd):");
        System.out.print("> ");
        String fromDate = input.nextLine();
        if (!dateFormat(fromDate)) {
            System.out.println("***Format error***");
            return;
        }
        info.add(fromDate);

        System.out.println("Ending date (yyyy-mm-dd):");
        System.out.print("> ");
        String toDate = input.nextLine();
        if (!dateFormat(toDate)) {
            System.out.println("***Format error***");
            return;
        }
        info.add(toDate);

        if (i == 1) {
            if (client.bookListing(info)) {
                System.out.println("***Booked listing successfully***");
            } else {
                System.out.println("***Booking listing failed***");
            }
        } else if (i == 0) {
            if (client.cancelBooking(info, this.type)) {
                System.out.println("***Cancelled booking successfully***");
            } else {
                System.out.println("***Cancelled booking failed***");
            }
        }
    }


    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            MenuController controller = new MenuController();
            controller.start();
        }
        Database.disconnect();
    }
}
