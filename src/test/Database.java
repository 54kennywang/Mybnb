package test;

import java.sql.*;
import java.util.Scanner;  
   

public class Database {
	Connection c = null;
	Statement stmt = null;
	public Boolean connect() {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			String[] cred = new String[3];
			System.out.print("Username: ");
			cred[0] = sc.nextLine();
			System.out.print("Password: ");
			cred[1] = sc.nextLine();
			System.out.print("Database: ");
			cred[2] = sc.nextLine();
			
			String user = cred[0];
			String pass = cred[1];
			String connection = "jdbc:mysql://127.0.0.1/" + cred[2] + "?useLegacyDatetimeCode=false&serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
//			c = DriverManager.getConnection("jdbc:sqlite:Localdata.db");
			c = DriverManager.getConnection(connection, user, pass);
			this.stmt = c.createStatement();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return true;
	}
	
	public Boolean disconnect() {
		try {
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return true;
	}

	public ResultSet excuteQuery(String query) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return rs;
	}
}