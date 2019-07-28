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
                System.out.println("  Please specify searching options (1 for posting; 2 for updating; 3 for deleting)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.postLIsting();
                else if (subOption.equals("2")) this.updatePosting();
                else if (subOption.equals("3")) ;
            } else if (option.equals("7")) {
                System.out.println("  Please specify options (1 for comment; 2 for reply)");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.comment();
                else if (subOption.equals("2")) this.reply();
            } else if (option.equals("8")) {
                System.out.println("  Please specify options (1 for viewing my postings; 2 for ; 3 for )");
                System.out.print("> ");
                String subOption = input.nextLine();
                if (subOption.equals("1")) this.viewMyListing();
                else if (subOption.equals("2")) ;
                else if (subOption.equals("3")) ;
            } else if (option.equals("9")) {
                this.becomeHost();
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
        System.out.println("  9. Become a host");
    }

    public boolean dateFormat(String date) {
        if (date.matches("^\\d{4}-\\d{2}-\\d{2}$")) return true;
        else return false;
    }


    public void reply() throws SQLException {
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

        String receiver = "";
        String l_id = "";
        if (option == 1) {
            System.out.println("User ID you are replying to:");
            System.out.print("> ");
            receiver = input.nextLine();
            if (!User.viewUserInfo(Integer.parseInt(receiver)) || !User.viewComments(Integer.parseInt(receiver), 1)) {
                return;
            }
        } else if (option == 2) {
            System.out.println("Listing ID:");
            System.out.print("> ");
            l_id = input.nextLine();
            if (Listing.viewListing(Integer.parseInt(l_id)) == 0 || !User.viewComments(Integer.parseInt(l_id), 0)) {
                return;
            }
        }
        String parent_id = "";
        System.out.println("Comment ID you are replying to:");
        System.out.print("> ");
        parent_id = input.nextLine();

        String content = "";
        System.out.println("Leave you comment:");
        System.out.print("> ");
        content = input.nextLine();

    // 1- replyUserComment(List<String> info) - User's Reply to a comment on a user
    // [receiver, parent_comment, content]
        // renter on host or host on renter
        if(option == 1){
            info.add(receiver);
            info.add(parent_id);
            info.add(content);
            if(client.replyUserComment(info)) System.out.println("***Reply to comment on user successfully***");
            else System.out.println("***Reply to comment on user failed***");
        }
        else if(option == 2){
            // 2- replyListingComment(List<String> info) - Host's reply to a comment on a listing
            // [receiver, parent_comment, content, l_id]
            info.add(Listing.getOwnerId(Integer.parseInt(l_id)).toString());
            info.add(parent_id);
            info.add(content);
            info.add(l_id);
            client.replyListingComment(info);
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
            if (!User.viewUserInfo(Integer.parseInt(receiver))) {
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
                    if (((Host) client).commentOnUser(info)) {
                        System.out.println("***Comment on user successfully***");
                        return;
                    }
                } else if (subOption.equals("1")) {
                    if (((Renter) client).commentOnUser(info)) {
                        System.out.println("***Comment on user successfully***");
                        return;
                    }
                }
                System.out.println("***Comment on user failed***");
            } else {
                if (((Renter) client).commentOnUser(info)) {
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


    public void logIn() throws Exception {
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

    public void signUp() throws Exception {
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
        if (!dateFormat(DOB)) return;
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

    public void logOut() {
        if (!loggedIn()) {
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
        lng = Double.parseDouble(input.nextLine());

        System.out.println("Latitude:");
        System.out.print("> ");
        lat = Double.parseDouble(input.nextLine());

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
        order = Integer.parseInt(input.nextLine());

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
        radius = Double.parseDouble(input.nextLine());

        System.out.println("Rank by price (1) or rank by distance (2):");
        System.out.print("> ");
        option = Integer.parseInt(input.nextLine());

        System.out.println("Order (1 for ascending, 0 for descending)");
        System.out.print("> ");
        order = Integer.parseInt(input.nextLine());

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
        id = Integer.parseInt(input.nextLine());
        Listing.viewListing(id);
    }

    // 1 for book, 0 for cancel
    public void bookOrCancel_Listing(int i) throws Exception {
        if (!loggedIn()) {
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

    public void postLIsting() throws Exception {
        if (!loggedIn()) {
            System.out.println("***Please login first***");
            return;
        }
        if (!isHost()) {
            System.out.println("***Only Host type user can post a listing, you can choose to become a host from the main menu***");
            return;
        }


        Scanner input = new Scanner(System.in);
        List<String> houseInfo = new ArrayList<String>();
        System.out.println("Area (m^2):");
        System.out.print("> ");
        houseInfo.add(input.nextLine());

        System.out.println("Starting date (yyyy-mm-dd):");
        System.out.print("> ");
        String fromDate = input.nextLine();
        if (!dateFormat(fromDate)) {
            System.out.println("***Format error***");
            return;
        }
        houseInfo.add(fromDate);

        System.out.println("Ending date (yyyy-mm-dd):");
        System.out.print("> ");
        String toDate = input.nextLine();
        if (!dateFormat(toDate)) {
            System.out.println("***Format error***");
            return;
        }
        houseInfo.add(toDate);

        System.out.println("Price per day ($):");
        System.out.print("> ");
        houseInfo.add(input.nextLine());

        houseInfo.add(client.getId().toString());

        System.out.println("Type (House, Room, etc.):");
        System.out.print("> ");
        houseInfo.add(input.nextLine());

        System.out.println("Amenities (1 means yes, 0 means no):");
        String amen = "";
        for (int i = 0; i < Listing.amenities.size(); i++) {
            System.out.println(Listing.amenities.get(i));
            System.out.print("> ");
            amen = amen + input.nextLine().trim();
        }
        // here to add recommendation
        houseInfo.add(amen);

        System.out.println("***Now address info for the new listing***");

        List<String> addrInfo = new ArrayList<String>();
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

    public void updatePosting() throws SQLException {
        System.out.println("***Here are your postings***");
        if (viewMyListing() == 0) return;

        Scanner input = new Scanner(System.in);
        System.out.println();
        System.out.println("Update option (1 for price, 2 for availabilities):");
        System.out.print("> ");
        int option = Integer.parseInt(input.nextLine());

        if (option != 1 && option != 2) return;

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
            List<String> info = new ArrayList<String>();
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
        }
    }

    public void becomeHost() {
        if (loggedIn()) {
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

    // 1 for having listing; 0 for no result
    public int viewMyListing() throws SQLException {
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

    public void viewComments() {

    }


    public boolean loggedIn() {
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

    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            MenuController controller = new MenuController();
            controller.start();
        }
        Database.disconnect();
    }
}
