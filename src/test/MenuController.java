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
                else if (subOption.equals("2")) this.searchByPCode();
                else if (subOption.equals("3")) this.searchByAddress();
            } else if (option.equals("5")) {
                System.out.println("  Please specify searching options (1 for viewing; 2 for booking; 3 for cancelling; 4 for confirming a living)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.viewListing();
                else if (subOption.equals("2")) this.bookOrCancel_Listing(1);
                else if (subOption.equals("3")) this.bookOrCancel_Listing(0);
                else if (subOption.equals("4")) this.confirmLiving();
            } else if (option.equals("6")) {
                System.out.println("  Please specify searching options (1 for posting; 2 for updating; 3 for deleting; 4 for canceling)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.postListing();
                else if (subOption.equals("2")) this.updatePosting();
                else if (subOption.equals("3")) this.deleteListing();
                else if (subOption.equals("4")) this.cancelBookingAsHost();
            } else if (option.equals("7")) {
                System.out.println("  Please specify options (1 for comment; 2 for reply)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.comment();
                else if (subOption.equals("2")) this.reply();
            } else if (option.equals("8")) {
                System.out.println("  Please specify options (1 for viewing my user history; 2 for view a specified user)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.viewUserHistory();
                else if (subOption.equals("2")) this.viewSpecifiedUser();
            } else if (option.equals("9")) {
                System.out.println("  Please specify options (1 for becoming host; 2 for deleting this account)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) {
                    this.becomeHost();
                }
                else if (subOption.equals("2")) this.deleteAccount();
            } else if (option.equals("10")) {
                this.report();
            }
            System.out.println("Tell me what's next, type MENU to see options.");
            System.out.print("> ");
            option = input.nextLine();
        }
        System.out.println("Bye!");
    }

    private void printMenu() {
        System.out.println("  1. Login");
        System.out.println("  2. Register");
        System.out.println("  3. Logout");
        System.out.println("  4. Search listings");
        System.out.println("  5. View/book a listing/cancel a booking/confirm living (as Renter)");
        System.out.println("  6. Post/update/delete/cancel a listing (as Host)");
        System.out.println("  7. Comment/reply");
        System.out.println("  8. View user info");
        System.out.println("  9. Become a host/delete account");
        System.out.println("  10. Report");
    }

    private boolean dateFormat(String date) {
        if (date.matches("^\\d{4}-\\d{2}-\\d{2}$")) return true;
        else return false;
    }

    public void deleteAccount() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if(client.type == 1) {
            if(client.deleteMyselfAsRenter()) {
                System.out.println("***Delete account successfully***");
                this.client = null;
                this.type = 0;
            }
            else System.out.println("***Delete account failed***");
        }
        else if(client.type == 2){
            if(client.deleteMyselfAsHost()) {
                System.out.println("***Delete account successfully***");
                this.client = null;
                this.type = 0;
            }
            else System.out.println("***Delete account failed***");
        }
    }

    private void report() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if(client.getId() != 27){
            System.out.println("***You are not admin, no access***");
            return;
        }
        Scanner input = new Scanner(System.in);
        String option = "";
        System.out.println("  Please specify options:");
        System.out.println("  1. Total number of bookings in date range by city.");
        System.out.println("  2. Total number of bookings in date range by postal code in cities.");
        System.out.println("  3. Total number of bookings by countries.");
        System.out.println("  4. Total number of bookings by cities.");
        System.out.println("  5. Total number of bookings by postal code.");
        System.out.println("  6. Total number of listings of hosts by country.");
        System.out.println("  7. Total number of listings of hosts by city.");
        System.out.println("  8. Report on overposting users.");
        System.out.println("  9. Number of bookings for renters during a time period.");
        System.out.println("  10. Number of bookings for renters during a time period by cities.");
        System.out.println("  11. Number of largest cancellations during a time period.");
        System.out.println("  12. The most popular noun phrases with the listing");
        System.out.print("> ");
        option = input.nextLine();

        if (option.equals("1")) {
            List<String> timeWindow = getDateWindow();
            if (timeWindow != null) Report.report_numOfBookingsByCity(timeWindow.get(0), timeWindow.get(1));
        } else if (option.equals("2")) {
            List<String> timeWindow = getDateWindow();
            if (timeWindow != null) Report.report_numOfBookingsByZipCode(timeWindow.get(0), timeWindow.get(1));
        } else if (option.equals("3")) {
            Report.report_numOfListingsPerCountry();
        } else if (option.equals("4")) {
            Report.report_numOfListingsPerCountryPerCity();
        } else if (option.equals("5")) {
            Report.report_numOfListingsPerCountryPerCityPerPCode();
        } else if (option.equals("6")) {
            Report.report_rankHostsByListingsPerCountry();
        } else if (option.equals("7")) {
            Report.report_rankHostsByListingsPerCity();
        } else if (option.equals("8")) {
            System.out.println("Do you want to specify a time window for report (1 for yes, 0 for no):");
            System.out.print("> ");
            String subOp = input.nextLine();
            if (subOp.equals("1")) {
                List<String> timeWin = getDateWindow();
                if (timeWin == null) return;
                else Report.report_spamPosting(timeWin.get(0), timeWin.get(1));
            } else if (subOp.equals("0")) Report.report_spamPosting("", "");
        } else if (option.equals("9")) {
            List<String> timeWin = getDateWindow();
            if (timeWin == null) return;
            else Report.report_rankRentersByNumOfBookings(timeWin.get(0), timeWin.get(1));
        } else if (option.equals("10")) {
            List<String> timeWin = getDateWindow();
            if (timeWin == null) return;
            else Report.report_rankRentersByNumOfBookingsPerCity(timeWin.get(0), timeWin.get(1));
        } else if (option.equals("11")) {
            System.out.println("Do you want to renters or hosts with largest cancellations (1 for renters, 0 for hosts):");
            System.out.print("> ");
            String subOp = input.nextLine();
            List<String> timeWin = getDateWindow();
            if (timeWin == null) return;
            if (subOp.equals("1")) {
                Report.report_largestCancellation(1, timeWin.get(0), timeWin.get(1));
            } else if (subOp.equals("0")) {
                Report.report_largestCancellation(2, timeWin.get(0), timeWin.get(1));
            }
        } else if (option.equals("12")){
            System.out.println("Enter the listing's ID");
            System.out.println("> ");
            String ID = input.nextLine();
            Report.popularPhrasesReport(ID);
        }
        else {
            System.out.println("***Invalid option***");
        }
    }

    private List<String> getDateWindow() {
        Scanner input = new Scanner(System.in);
        System.out.println("Date range from (yyyy-mm-dd):");
        System.out.print("> ");
        String fromDate = input.nextLine();
        if (!dateFormat(fromDate)) {
            System.out.println("***Format error***");
            return null;
        }
        System.out.println("Date range to (yyyy-mm-dd):");
        System.out.print("> ");
        String toDate = input.nextLine();
        if (!dateFormat(toDate)) {
            System.out.println("***Format error***");
            return null;
        }
        List<String> window = new ArrayList<String>();
        window.add(fromDate);
        window.add(toDate);
        return window;
    }


    private void reply() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        int option = 0;
        System.out.println("  Please specify options (1 for replying comment on user, 2 for replying comment on listing) :");
        System.out.print("> ");
        option = Integer.parseInt(input.nextLine());

        // 1- replyUserComment(List<String> info) - User's Reply to a comment on a user
        // [receiver, parent_comment, content]
        String receiver = "";
        String l_id = "";
        if (option == 1) {
            System.out.println("User ID you are replying to:");
            System.out.print("> ");
            receiver = input.nextLine();
            if (!User.viewUserInfo(Integer.parseInt(receiver), 0)) {
                return;
            }
        } else if (option == 2) {
            System.out.println("Listing ID:");
            System.out.print("> ");
            l_id = input.nextLine();
            if (Listing.viewListing(Integer.parseInt(l_id)) == 0 || !User.viewComments(Integer.parseInt(l_id), 0)) {
                return;
            }
            System.out.println("Receiver ID you are replying to:");
            System.out.print("> ");
            receiver = input.nextLine();
        }
        String parent_id = "";
        System.out.println("Comment ID you are replying to:");
        System.out.print("> ");
        parent_id = input.nextLine();

        String content = "";
        System.out.println("Leave you comment:");
        System.out.print("> ");
        content = input.nextLine();

        // renter on host or host on renter
        if (option == 1) {
            info.add(receiver);
            info.add(parent_id);
            info.add(content);
            if (client.replyUserComment(info)) System.out.println("***Reply to comment on user successfully***");
            else System.out.println("***Reply to comment on user failed***");
        } else if (option == 2) {
            // 2- replyListingComment(List<String> info) - Host's reply to a comment on a listing
            // [receiver, parent_comment, content, l_id]
            info.add(receiver);
            info.add(parent_id);
            info.add(content);
            info.add(l_id);
            if (client.replyListingComment(info)) System.out.println("***Reply to comment on user successfully***");
            else System.out.println("***Reply to comment on user failed***");
        }

    }


    public void comment() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        int option = 0;
        System.out.println("  Please specify options (1 for commenting on user, 2 for commenting on listing) :");
        System.out.print("> ");
        option = Integer.parseInt(input.nextLine());

        String receiver = "";
        String l_id = "";
        if (option == 1) {
            System.out.println("User ID:");
            System.out.print("> ");
            receiver = input.nextLine();
            if (!User.viewUserInfo(Integer.parseInt(receiver), 1)) {
                return;
            }
        } else if (option == 2) {
            System.out.println("Listing ID:");
            System.out.print("> ");
            l_id = input.nextLine();
            if (Listing.viewListing(Integer.parseInt(l_id)) == 0) {
                return;
            }
        }
        String rating = "";
        System.out.println("Rating (1 - 5):");
        System.out.print("> ");
        rating = input.nextLine();
        if (!rating.matches("^[1-5]$")) {
            System.out.println("***Rating has to be an integer of scale 1 to 5***");
            return;
        }
        String content = "";
        System.out.println("Leave you comment:");
        System.out.print("> ");
        content = input.nextLine();

        if (option == 1) {
            info.add(receiver);
            info.add(rating);
            info.add(content);
            if (this.type == 2) {
                System.out.println("Is this comment for your renter who lived in your place (1) or for your host whom you rented a listing from (2):");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) {
                    if ((client).commentOnUser(info)) {
                        System.out.println("***Comment on user successfully***");
                        return;
                    }
                } else if (subOption.equals("2")) {
                    if (((Renter) client).commentOnHost(info)) {
                        System.out.println("***Comment on user successfully***");
                        return;
                    }
                }
                System.out.println("***Comment on user failed***");
            } else {
                if (((Renter) client).commentOnHost(info)) {
                    System.out.println("***Comment on user successfully***");
                    return;
                }
                System.out.println("***Comment on user failed***");
            }
        } else if (option == 2) {
            info.add(Listing.getOwnerId(Integer.parseInt(l_id)).toString());
            info.add(rating);
            info.add(content);
            info.add(l_id);
            if (client.commentOnListing(info)) System.out.println("***Comment on listing successfully***");
            else System.out.println("***Comment on listing failed***");
        }

    }


    private void logIn() throws Exception {
        if (loggedIn()) {
            System.out.println("***You already logged in***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        System.out.println("Username (email):");
        System.out.print("> ");
        info.add(input.nextLine());

        System.out.println("Password:");
        System.out.print("> ");
        info.add(input.nextLine());
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

    private void signUp() throws Exception {
        if (loggedIn()) {
            System.out.println("***You already logged in***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        System.out.println("Username (email):");
        System.out.print("> ");
        info.add(input.nextLine());

        System.out.println("Credit Card Number:");
        System.out.print("> ");
        info.add(input.nextLine());

        System.out.println("Real Name:");
        System.out.print("> ");
        info.add(input.nextLine());

        System.out.println("Occupation:");
        System.out.print("> ");
        info.add(input.nextLine());

        System.out.println("DOB:");
        System.out.print("> ");
        String DOB = input.nextLine();
        if (!dateFormat(DOB)) {
            System.out.println("***Format error***");
            return;
        }
        if (ChronoUnit.YEARS.between(LocalDate.parse(DOB), LocalDate.now()) < 18) {
            System.out.println("You are under 18, register failed.");
            return;
        }
        info.add(DOB);

        System.out.println("SIN:");
        System.out.print("> ");
        info.add(input.nextLine());

        System.out.println("Password:");
        System.out.print("> ");
        info.add(input.nextLine());


        List<String> addrInfo = new ArrayList<String>();
        System.out.println("Please tell us your address.");
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

        if ((new Renter()).register(info, addrInfo)) System.out.println("Register successfully! Now you can login.");
        else System.out.println("Register failed.");
    }

    private void logOut() {
        if (!loggedIn()) {
            System.out.println("***You are not logged in***");
            return;
        }
        this.client = null;
        this.type = 0;
        System.out.println("***Logged out successfully***");
    }

    private void searchByCoordinates() throws Exception {
        Scanner input = new Scanner(System.in);
        Double lng = 0.0;
        Double lat = 0.0;
        Double radius = 0.0;
        int order = 0;
        int option = 0;
        System.out.println("Longitude:");
        System.out.print("> ");
        lng = Double.parseDouble(input.nextLine());

        System.out.println("Latitude:");
        System.out.print("> ");
        lat = Double.parseDouble(input.nextLine());

        List<String> filterInfo = filterInfo();
        if (filterInfo == null) return;

        System.out.println("Rank by price (1) or rank by distance (2):");
        System.out.print("> ");
        option = Integer.parseInt(input.nextLine());

        System.out.println("Searching radius (km):");
        System.out.print("> ");
        radius = Double.parseDouble(input.nextLine());

        System.out.println("Order (1 for ascending, 0 for descending)");
        System.out.print("> ");
        order = Integer.parseInt(input.nextLine());

        if (option == 1) {
            Listing.viewAllListing(Listing.searchByCoordinates_rankByPrice(lng, lat, radius, 'K', order, filterInfo), 1);
        } else if (option == 2) {
            Listing.viewAllListing(Listing.searchByCoordinates_rankByDistance(lng, lat, radius, 'K', order, filterInfo), 1);
        }
    }

    private void searchByPCode() throws Exception {
        Scanner input = new Scanner(System.in);
        String pcode = "";
        int order = 0;
        Double radius = 0.0;
        System.out.println("Note: search by postal code doesn't support ranking by distance. By default, result will be shown ranked by price.");
        System.out.println("Postal Code:");
        System.out.print("> ");
        pcode = input.nextLine();

        List<String> filterInfo = filterInfo();
        if (filterInfo == null) return;


        System.out.println("(Ranking by price) order (1 for ascending, 0 for descending)");
        System.out.print("> ");
        order = Integer.parseInt(input.nextLine());

        List<Row> exactResult = Listing.searchByPcode_rankByPrice_exact(pcode, order, filterInfo);
        List<Row> wildcardResult = Listing.searchByPcode_rankByPrice_wildcard(pcode, order, filterInfo);
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

    private void searchByAddress() throws Exception {
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

        List<String> filterInfo = filterInfo();
        if (filterInfo == null) return;


        System.out.println("Searching radius (km):");
        System.out.print("> ");
        radius = Double.parseDouble(input.nextLine());

        System.out.println("Rank by price (1) or rank by distance (2):");
        System.out.print("> ");
        option = Integer.parseInt(input.nextLine());

        System.out.println("Order (1 for ascending, 0 for descending)");
        System.out.print("> ");
        order = Integer.parseInt(input.nextLine());

        if (option == 1) {
            Listing.viewAllListing(Listing.searchByAddress_rankByPrice(addrInfo, radius, order, filterInfo), 1);
        } else if (option == 2) {
            Listing.viewAllListing(Listing.searchByAddress_rankByDistance(addrInfo, radius, order, filterInfo), 1);
        }
    }

    // return [fromDate, toDate, lowest, highest, amenRequest]
    private List<String> filterInfo() {
        Scanner input = new Scanner(System.in);
        System.out.println("Date range from (yyyy-mm-dd):");
        System.out.print("> ");
        String fromDate = input.nextLine();
        if (!dateFormat(fromDate)) {
            System.out.println("***Format error***");
            return null;
        }
        System.out.println("Date range to (yyyy-mm-dd):");
        System.out.print("> ");
        String toDate = input.nextLine();
        if (!dateFormat(toDate)) {
            System.out.println("***Format error***");
            return null;
        }

        System.out.println("Price range lowest ($):");
        System.out.print("> ");
        Double lowest = Double.parseDouble(input.nextLine());
        System.out.println("Price range highest ($):");
        System.out.print("> ");
        Double highest = Double.parseDouble(input.nextLine());

        System.out.println("Amenities filter (1 means yes, 0 means not matter):");
        String amenRequest = "";
        for (int i = 0; i < Listing.amenities.size(); i++) {
            System.out.println(Listing.amenities.get(i));
            System.out.print("> ");
            String need = input.nextLine();
            if (need.equals("0")) need = ".";
            amenRequest = amenRequest + need;
        }
        List<String> filterInfo = new ArrayList<String>();
        filterInfo.add(fromDate);
        filterInfo.add(toDate);
        filterInfo.add(lowest.toString());
        filterInfo.add(highest.toString());
        filterInfo.add(amenRequest);
        return filterInfo;
    }

    private void viewListing() throws SQLException {
        Scanner input = new Scanner(System.in);
        int id = 0;
        System.out.println("Listing ID:");
        System.out.print("> ");
        id = Integer.parseInt(input.nextLine());
        Listing.viewListing(id);
    }

    private void deleteListing() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if (!isHost()) {
            System.out.println("***You are not host***");
            return;
        }
        System.out.println("Listing ID:");
        System.out.print("> ");
        Scanner input = new Scanner(System.in);
        String l_id = input.nextLine();
        if (Listing.getOwnerId(Integer.parseInt(l_id)) != client.getId()) {
            System.out.println("***not your listing, delete failed***");
        } else {
            if (client.deleteListing(Integer.parseInt(l_id))) System.out.println("***Delete listing successfully***");
            else System.out.println("***Delete listing failed***");
        }
    }

    private void cancelBookingAsHost() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if (!isHost()) {
            System.out.println("***You are not host***");
            return;
        }
        System.out.println("Other users' future bookings of your listings:");
        List<Row> table = client.getMyRenterBookingsOfMyListings(0);
        client.viewRentalHistoryOfMyListings(table);

        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<>();

        System.out.println("Listing ID:");
        System.out.print("> ");
        String l_id = input.nextLine();
        if (Listing.viewListing(Integer.parseInt(l_id)) == 1) {
            info.add(l_id);
        } else return;

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

        if (client.cancelBooking(info, 2)) {
            System.out.println("***Cancelled booking successfully***");
        } else {
            System.out.println("***Cancelled booking failed***");
        }
    }

    // 1 for book, 0 for cancel
    private void bookOrCancel_Listing(int i) throws Exception {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<>();
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
            if (client.cancelBooking(info, 1)) {
                System.out.println("***Cancelled booking successfully***");
            } else {
                System.out.println("***Cancelled booking failed***");
            }
        }
    }

    private void postListing() throws Exception {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if (!isHost()) {
            System.out.println("***Only Host type user can post a listing, you can choose to become a host from the main menu***");
            return;
        }


        Scanner input = new Scanner(System.in);
        List<String> houseInfo = new ArrayList<>();
        System.out.println("Area (m^2):");
        System.out.print("> ");
        String sArea = input.nextLine();
        double area = Double.parseDouble(sArea);

        System.out.println("Starting date (yyyy-mm-dd):");
        System.out.print("> ");
        String fromDate = input.nextLine();
        if (!dateFormat(fromDate)) {
            System.out.println("***Format error***");
            return;
        }

        System.out.println("Ending date (yyyy-mm-dd):");
        System.out.print("> ");
        String toDate = input.nextLine();
        if (!dateFormat(toDate)) {
            System.out.println("***Format error***");
            return;
        }

        System.out.println("Type (House, Room, etc.):");
        System.out.print("> ");
        String type = input.nextLine();

        System.out.println("Amenities (1 means yes, 0 means no):");
        String amen = "";
        for (int i = 0; i < Listing.amenities.size(); i++) {
            System.out.println(Listing.amenities.get(i));
            System.out.print("> ");
            amen = amen + input.nextLine().trim();
        }

        double price = Listing.suggestPrice(area, amen);

        System.out.println("Price per day ($):" + " Suggested price is " + price);
        System.out.println();
        //amenities suggestion
        Listing.suggestAmenities();
        System.out.print("> ");

        //add houseInfo
        houseInfo.add(Double.toString(area));
        houseInfo.add(fromDate);
        houseInfo.add(toDate);
        houseInfo.add(Double.toString(price));
        houseInfo.add(client.getId().toString());
        houseInfo.add(type);
        houseInfo.add(amen);

        System.out.println("***Now address info for the new listing***");

        List<String> addrInfo = new ArrayList<>();
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

        if (client.postListing(houseInfo, addrInfo)) {
            System.out.println("***Posted listing successfully***");
        } else System.out.println("***Posted listing failed***");
    }

    private void updatePosting() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if (!isHost()) {
            System.out.println("***Only Host type user can post a listing, you can choose to become a host from the main menu***");
            return;
        }
        System.out.println("***Here are your postings***");
        if (viewMyListing() == 0) return;

        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Update option (1 for price, 2 for availabilities, 3 for amenities):");
        System.out.print("> ");
        int option = Integer.parseInt(input.nextLine());

        if (option != 1 && option != 2 && option != 3) return;

        System.out.println("Listing ID:");
        System.out.print("> ");
        Integer l_id = Integer.parseInt(input.nextLine());

        if (Listing.getOwnerId(l_id) == client.getId()) {
            Listing.viewListing(l_id);
        } else {
            System.out.println("***You don't own that listing***");
            return;
        }

        if (option == 1) {
            System.out.println();
            System.out.println("New price ($):");
            System.out.print("> ");
            String price = input.nextLine();
            List<String> info = new ArrayList<String>();
            info.add(l_id.toString().trim());
            info.add(price);
            if (client.updatePrice(info)) {
                System.out.println("***Updated price successfully***");
            } else System.out.println("***Updated price failed***");
        } else if (option == 2) {
            System.out.println("***You can add multiple time slots***");
            List<String> info = new ArrayList<>();
            info.add(l_id.toString());
            int k = 1;
            String more = "1";
            do {
                System.out.println("***Slot " + k + "***");
                System.out.println("From:");
                System.out.print("> ");
                String fromDate = input.nextLine();
                if (!dateFormat(fromDate)) {
                    System.out.println("***Format error***");
                    return;
                }

                System.out.println("To:");
                System.out.print("> ");
                String toDate = input.nextLine();
                if (!dateFormat(toDate)) {
                    System.out.println("***Format error***");
                    return;
                }
                info.add(fromDate);
                info.add(toDate);

                System.out.println("Do you want to add more time slots (1 for yes, 0 for no):");
                System.out.print("> ");
                more = input.nextLine().trim();
                k++;
            } while (more.equals("1"));
            if (client.updateAvailability(info)) System.out.println("***Updated availabilities successfully***");
            else System.out.println("***Updated availabilities failed***");
        } else if (option == 3) {
            System.out.println("Amenities (1 means yes, 0 means no):");
            String amen = "";
            for (int i = 0; i < Listing.amenities.size(); i++) {
                System.out.println(Listing.amenities.get(i));
                System.out.print("> ");
                amen = amen + input.nextLine().trim();
            }
            List<String> info = new ArrayList<>();
            info.add(l_id.toString());
            info.add(amen);
            client.updateAmenities(info);
        }
    }

    private void becomeHost() {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if (isHost()) {
            System.out.println("***You are already a host***");
            return;
        }

        if (client.becomeHost()) {
            client.type = 2;
            System.out.println("***Now you are a host (you can still rent listing)***");
        }
    }

    private void viewSpecifiedUser() throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Please provide User ID you want to view:");
        System.out.print("> ");
        int id = Integer.parseInt(input.nextLine());
        User.viewUserInfo(id, 1);
    }

    // 1 for having listing; 0 for no result
    private int viewMyListing() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return 0;
        }
        if (!isHost()) {
            System.out.println("***You are not a host, you have not posted anything***");
            return 0;
        }
        if (client.viewAllMyListing() == 1) return 1;
        else return 0;
    }

    private void viewUserHistory() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if (isRenter()) {
            userHistoryHelper();
        } else if (isHost()) {
            userHistoryHelper();
            System.out.println("\n");
            System.out.println("Other users' history bookings of your listings:");
            List<Row> table = client.getMyRenterBookingsOfMyListings(1);
            client.viewRentalHistoryOfMyListings(table);

            System.out.println("\n");
            System.out.println("Other users' future bookings of your listings:");
            table = client.getMyRenterBookingsOfMyListings(0);
            client.viewRentalHistoryOfMyListings(table);
        }
    }

    public void confirmLiving() throws SQLException {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        System.out.println("Your current bookings:");
        client.viewBooking((client.getBookings(0)));
        System.out.println("Listing ID:");
        System.out.print("> ");
        Scanner input = new Scanner(System.in);
        String l_id = input.nextLine();
        List<String> timeWin = getDateWindow();
        List<String> info = new ArrayList<String>();
        info.add(l_id);
        info.add(timeWin.get(0));
        info.add(timeWin.get(1));
        if (client.confirmation_AfterLiving(info)) {
            System.out.println("***Confirmed living successfully***");
        } else System.out.println("***Confirmed living failed***");
    }

    private void userHistoryHelper() throws SQLException {
        System.out.println("Your history of listings that you rented as a renter:");
        client.viewBooking((client.getBookings(1)));
        System.out.println("\n");
        System.out.println("Your future bookings:");
        client.viewBooking((client.getBookings(0)));
    }

    private boolean loggedIn() {
        if (client == null) {
            return false;
        } else return true;
    }

    public boolean isRenter() {
        if (client.type == 1) {
            return true;
        } else return false;
    }

    public boolean isHost() {
        if (client.type == 2) {
            return true;
        } else return false;
    }

    public static void main(String[] args) throws Exception {
        if (Database.connect()) {
            MenuController controller = new MenuController();
            controller.start();
        }
        Database.disconnect();
    }
}
