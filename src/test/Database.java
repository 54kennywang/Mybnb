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

	// read info from database
	public ResultSet queryRead(String query) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return rs;
	}
	
	// write to database
	// success return 1, no affected return 0
	public Integer queryWrite(String query) {
		Integer result = null;
		try {
			result = stmt.executeUpdate(query);
		} catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return result;
	}
	
	// insert new record
	// sample input: "sailers", "name, num, age", "'KENNY', 123, 23"
	public Boolean insert(String table, String cols, String vals) {
		StringBuilder query = new StringBuilder("insert into ");
		query.append(table).append(" (").append(cols).append(") Value (").append(vals).append(");");
		if(this.queryWrite(query.toString()).equals(1)) {
			return true;
		}
		else return false;
	}
	
	// update existing record
	// sample input: "sailers", "name = 'KENNY', num = -111", "id=35"
	public Boolean update(String table, String newInfo, String conditions) {
		StringBuilder query = new StringBuilder("update ");
		query.append(table).append(" set ").append(newInfo).append(" where ").append(conditions).append(";");
		if(this.queryWrite(query.toString()).equals(1)) {
			return true;
		}
		else return false;
	}
	
	// delete existing record
	// sample input: "sailers", "id=34"
	public Boolean delete(String table, String conditions) {
		StringBuilder query = new StringBuilder("delete from ");
		query.append(table).append(" where ").append(conditions).append(";");
		if(this.queryWrite(query.toString()).equals(1)) {
			return true;
		}
		else return false;
	}
	
	public static void main( String args[] ) {
		Database db = new Database();
		if(db.connect()) {
//			System.out.println(db.insert("sailers", "name, num, age", "'KENNY', 123, 23"));
//			System.out.println(db.update("sailers", "name = 'KENNY', num = -111", "id=35"));
//			System.out.println(db.delete("sailers", "id=34"));
		}
		db.disconnect();
	}
}