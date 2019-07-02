package test;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class User {
	
	// given list of [email, cardNum, name, occ, DOB, SIN, password]
	// return true if register successfully
	public Boolean register(List<String> info) throws Exception {
		Boolean success = false;
		String table = "user";
		String cols = "email, type, cardNum, name, occ, date, DOB, SIN, password";
		String vals = "'" + info.get(0) + "', 1, '" + info.get(1) + "', '" + info.get(2) + "', '" + 
				info.get(3) + "', NOW(), '" + info.get(4) + "', '" + info.get(5) + "', '" + 
				Password.getSaltedHash(info.get(6)) + "'";
		if(Database.insert(table, cols, vals)) success = true;
		return success;
	}
	
	// given list of [email, password]
	// return true if register successfully
	public Boolean signIn(List<String> info) throws Exception {
		Boolean success = false;
		String query = "select password from user where email = '" + info.get(0) + "'";
		ResultSet result = Database.queryRead(query);
		if(result.next()) {
			if(Password.check(info.get(1), result.getString("password"))) success = true;
		}
//		if(Database.insert(table, cols, vals)) success = true;
		return success;
	}
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			User me = new User();
			List<String> info = new ArrayList<String>();
//			info.add("qibowang7@outlook.com");
//			info.add("1111222233334444");
//			info.add("Kenny Wang");
//			info.add("Student");
//			info.add("1996-06-22");
//			info.add("123456789");
//			info.add("password");
//			System.out.println(me.register(info));
			info.add("qibowang7@outlook.com");
			info.add("password");
			System.out.println(me.signIn(info));
		}
		Database.disconnect();
    }
}
