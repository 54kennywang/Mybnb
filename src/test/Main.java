package test;


import java.sql.*;


public class Main {

	public static void main( String args[] ) throws SQLException {
		if(Database.connect()) {
			ResultSet rs = Database.queryRead("select * from boats;");
			while(rs.next()) {
				int id = rs.getInt("Column1");
				System.out.println(id);
			}
		}
		
		Database.disconnect();
		
		
		
	}
	
	
}















