package test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuController {
    public Renter client = new Renter();

    public void start() throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome, here is the menu, select your option.");
        this.printMenu();
        System.out.print("> ");
        String option = input.next();
        while(!option.equals("EXIT")){
            if(option.equals("MENU")){
                this.printMenu();
            }
            else if(option.equals("1")){
                this.logIn();
            }
            else if (option.equals("2")){
                this.signUp();
            }
            else if (option.equals("3")){

            }
            System.out.println("Tell me what's next, type MENU to see options.");
            System.out.print("> ");
            option = input.next();
        }
    }

    public void printMenu() {
        System.out.println("  1. Login");
        System.out.println("  2. Register");
        System.out.println("  3. Search by geo-location");
        System.out.println("  4. Search by postal code");
        System.out.println("  5. Search by address");
        System.out.println("  6. Logout");
    }

    public void logIn() throws Exception {
        Scanner input = new Scanner(System.in);
        List<String> info = new ArrayList<String>();
        System.out.println("Username (email):");
        System.out.print("> ");
        info.add(input.next());

        System.out.println("Password:");
        System.out.print("> ");
        info.add(input.next());
        if (client.signIn(info)) System.out.println("Login successfully!");
        else System.out.println("Username/password wrong.");
    }

    public void signUp() throws Exception {
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

        if (client.register(info, addrInfo)) System.out.println("Register successfully!");
        else System.out.println("Register failed.");
    }

    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            MenuController controller = new MenuController();
            controller.start();
        }
        Database.disconnect();
    }
}
