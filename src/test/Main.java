package test;


import java.sql.*;


public class Main {

	public static void main( String args[] ) throws SQLException {
		Database db = new Database();
		if(db.connect()) {
			ResultSet rs = db.excuteQuery("select * from boats;");
			while(rs.next()) {
				int id = rs.getInt("Column1");
				System.out.println(id);
			}
		}
		
		db.disconnect();
		
		
		
	}
	
	
}















