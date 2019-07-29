package test;

import javax.sql.RowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.JdbcRowSet;
import com.sun.rowset.JdbcRowSetImpl;

public class Database {
	static Connection c = null;
	static Statement stmt = null;
	/**
	 * Connect to database
	 */
	public static Boolean connect() {
		try {
//			@SuppressWarnings("resource")
//			Scanner sc = new Scanner(System.in);
			String[] cred = new String[3];
//			System.out.print("Username: ");
//			cred[0] = sc.nextLine();
//			System.out.print("Password: ");
//			cred[1] = sc.nextLine();
//			System.out.print("Database: ");
//			cred[2] = sc.nextLine();
			
			// https://stackoverflow.com/questions/7048216/environment-variables-in-eclipse/12810433#12810433
			cred[0] = "root";
			cred[1] = System.getenv("dbPass");
			cred[2] = "newestDB";
			
			String user = cred[0];
			String pass = cred[1];
			String connection = "jdbc:mysql://127.0.0.1/" + cred[2] + "?useLegacyDatetimeCode=false&serverTimezone=UTC";
			Class.forName("com.mysql.cj.jdbc.Driver");
//			c = DriverManager.getConnection("jdbc:sqlite:Localdata.db");
			c = DriverManager.getConnection(connection, user, pass);
			stmt = c.createStatement();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return true;
	}

	/**
	 * Disconnect from database
	 */
	public static Boolean disconnect() {
		try {
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return true;
	}

	/**
	 * Simple "select" statement in MYSQL to get information from database
	 * @param query MYSQL queery
	 * @return a "table" of information
	 */
	public static ResultSet queryRead(String query) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(query);
		} catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return rs;
	}
	
	/**
	 * Write date to database
	 * @param query MYSQL queery
	 * @return return 1 on success, 0 if not written successfully
	 */
	public static Integer queryWrite(String query) {
		Integer result = null;
		try {
			result = stmt.executeUpdate(query);
		} catch(Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return result;
	}
	
	/**
	 * Insert new record to database
	 * @param table MYSQL table name
	 * @param cols column name
	 * @param vals values
	 * @return return 1 on success, 0 if not written successfully
	 * sample input: "sailers", "name, num, age", "'KENNY', 123, 23"
	 */
	public static Boolean insert(String table, String cols, String vals) {
		StringBuilder query = new StringBuilder("insert into ");
		query.append(table).append(" (").append(cols).append(") Value (").append(vals).append(");");
		if(queryWrite(query.toString()).equals(1)) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Update existing record in database
	 * @param table MYSQL table name
	 * @param newInfo updated information
	 * @param conditions conditions
	 * @return return 1 on success, 0 if not written successfully
	 * sample input: "sailers", "name = 'KENNY', num = -111", "id=35"
	 */
	public static Boolean update(String table, String newInfo, String conditions) {
		StringBuilder query = new StringBuilder("update ");
		query.append(table).append(" set ").append(newInfo).append(" where ").append(conditions).append(";");
		if(queryWrite(query.toString()).equals(1)) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Delete existing record in database
	 * @param table MYSQL table name
	 * @param conditions conditions
	 * @return return 1 on success, 0 if not written successfully
	 * sample input: "sailers", "id=34"
	 */
	public static Boolean delete(String table, String conditions) {
		StringBuilder query = new StringBuilder("delete from ");
		query.append(table).append(" where ").append(conditions).append(";");
		if(queryWrite(query.toString()).equals(1)) {
			return true;
		}
		else return false;
	}
	
	/**
	 * Insert address in database
	 * @param addrInfo [street, city, pcode, country]
	 * @param id id of user/listing
	 * @param type 0 - listing; 1 - user
	 * @return return 1 on success, 0 if not written successfully
	 */
	public static Boolean insertAddr(List<String> addrInfo, int id, int type) throws Exception {
		Boolean success = false;
		List<Object> sanitizedAddr = new ArrayList<Object>();
		sanitizedAddr = Map.getAllByAddr(Map.infoToAddr(addrInfo)); // [street, city, pcode, country, lng, lat]
		String table = "address";
		String cols = "id, street, city, pcode, country, lng, lat, type";
		String vals = id + ", '" + sanitizedAddr.get(0) + "', '" + sanitizedAddr.get(1)
		+ "', '" + sanitizedAddr.get(2) + "', '" + sanitizedAddr.get(3) + "', " + sanitizedAddr.get(4)
		+ ", " + sanitizedAddr.get(5) + ", " + type;
		// sample input: "sailers", "name, num, age", "'KENNY', 123, 23"
		if(Database.insert(table, cols, vals)) success = true;
		return success;
	}
	
	
	public static void main( String args[] ) throws  Exception{
		if(Database.connect()) {
//			System.out.println(Database.insert("sailers", "name, num, age", "'KENNY', 123, 23"));
//			System.out.println(Database.update("sailers", "name = 'KENNY', num = -111", "id=36"));
//			System.out.println(Database.delete("sailers", "id=36"));


//			List<Object> x = Map.getAllByAddr("530 Oxford St W, London, ON N6H 1T6, Canada");
//			List<String> xx = new ArrayList<String>();
//			for(int i = 0; i < x.size(); i ++) {
//				xx.add(x.get(i).toString());
//			}
//
//			System.out.println(Database.insertAddr(xx, 5, 0));
		}
		Database.disconnect();
	}
}