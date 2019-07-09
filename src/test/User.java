package test;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public abstract class User {
	protected Integer id = null;
	protected Integer type = null;
	protected Boolean active = false;
	// userInfo: [email, cardNum, name, occ, DOB, SIN, password]
	// given addrInfo: [street, city, pcode, country]
	// return true if register successfully
	public Boolean register(List<String> userInfo, List<String> addrInfo) throws Exception {
		Boolean success = false;
		if(!this.active) {
			if(ChronoUnit.YEARS.between(LocalDate.parse(userInfo.get(4)), LocalDate.now()) < 18){
				System.out.println("From register() in User.java: you are under 18, too young.");
				return false;
			}
			// insert user
			String table = "user";
			String cols = "email, type, cardNum, name, occ, date, DOB, SIN, password";
			String vals = "'" + userInfo.get(0) + "', 1, '" + userInfo.get(1) + "', '" + userInfo.get(2) + "', '" +
					userInfo.get(3) + "', NOW(), '" + userInfo.get(4) + "', '" + userInfo.get(5) + "', '" +
					Password.getSaltedHash(userInfo.get(6)) + "'";
			if(Database.insert(table, cols, vals)) {
				String query = "SELECT LAST_INSERT_ID()";
				ResultSet result = Database.queryRead(query);
				Integer newID = null;
				if(result.next()) {
					newID = result.getInt("LAST_INSERT_ID()");

					// insert address
					if(Database.insertAddr(addrInfo, newID, 1)) success = true;
				}
			}
		}
		return success;
	}
	
	// given list of [email, password]
	// return true if register successfully
	public Boolean signIn(List<String> info) throws Exception {
		Boolean success = false;
		if(!this.active) {
			String query = "select id, type, password from user where email = '" + info.get(0) + "'";
			ResultSet result = Database.queryRead(query);
			if(result.next()) {
				if(Password.check(info.get(1), result.getString("password"))) {
					this.id = Integer.parseInt(result.getString("id"));
					this.type = Integer.parseInt(result.getString("type"));
					this.active = true;
					success = true;
				}
			}
		}
		return success;
	}
	
	// signOut the current user
	// return true if signOut successfully
	public Boolean signOut() {
		if(active) {
			this.id = null;
			this.type = null;
			this.active = false;
			return true;
		}
		return false;
	}
	
	public Integer getId() {return this.id;}
	
	public abstract Boolean cancelBooking(List<String> info);

	// initial comment on a user
	// given a list of commentInfo: [receiver, rating, content]
	// return true if successfully
	public Boolean commentOnUser(List<String> info){
		Boolean success = false;
		if(this.active) {
			// add to "user_comment" table
			String table = "user_comment";
			String cols = "sender, receiver, parent_comment, rating, content, date";
			String vals = this.id + ", " + info.get(0) + ", null, " + info.get(1) + ", '" + 
							info.get(2) + "', " + "NOW()" ;
			if(Database.insert(table, cols, vals)) success =true;
		}
		return success;
	}
	
	// reply to a initial comment on a user
	// given a list of commentInfo: [receiver, parent_comment, content]
	// return true if successfully
	public Boolean replyUserComment(List<String> info){
		Boolean success = false;
		if(this.active) {
			// add to "user_comment" table
			String table = "user_comment";
			String cols = "sender, receiver, parent_comment, rating, content, date";
			String vals = this.id + ", " + info.get(0) + ", " + info.get(1) + ", null, '"
			+ info.get(2) + "', " + "NOW()" ;
			if(Database.insert(table, cols, vals)) success =true;
		}
		return success;
	}
	
	public static void main( String args[] ) throws Exception {
		if(Database.connect()) {
			Renter me = new Renter();
			
//			List<String> userInfo = Arrays.asList("michael@outlook.com", "2222222222222222", "Michael", "graduate", 
//					"1996-03-05", "12csa442", "password");
//			List<String> addrInfo = Arrays.asList("Tobacco Quay", "Wapping Ln", "London", "UK");
//			System.out.println(me.register(userInfo, addrInfo));
			
			List<String> cred = Arrays.asList("qibowang7@outlook.com", "password");
			System.out.println(me.signIn(cred));
			
//			List<String> info = Arrays.asList("3", "5", "this user is good");
//			System.out.println(me.commentOnUser(info));
			
			List<String> info = Arrays.asList("6", "1", "thanks for the comment: this user is good");
			System.out.println(me.replyUserComment(info));
			
		}
		Database.disconnect();
    }

}








