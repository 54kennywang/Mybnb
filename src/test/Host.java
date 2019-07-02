package test;

import java.util.ArrayList;
import java.util.List;

public class Host extends Renter{
	public Boolean postListing() {
		
		return true;
	}
	
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			Host me = new Host();
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
			System.out.println(me.becomeHost());
		}
		Database.disconnect();
    }
}
