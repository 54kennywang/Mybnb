package test;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.internal.Row;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static test.Listing.CachedRowSet_to_ListRow;

public abstract class User {
    protected Integer id = null;
    protected Integer type = null;
    protected Boolean active = false;



    /**
     * Register a new user
     *
     * @param userInfo [email, cardNum, name, occ, DOB, SIN, password]
     * @param addrInfo [street, city, pcode, country]
     * @return true if register successfully; false otherwise
     */
    public Boolean register(List<String> userInfo, List<String> addrInfo) throws Exception {
        Boolean success = false;
        if (!this.active) {
//            if (ChronoUnit.YEARS.between(LocalDate.parse(userInfo.get(4)), LocalDate.now()) < 18) {
//                System.out.println("From register() in User.java: you are under 18, too young.");
//                return false;
//            }
            // insert user
            String table = "user";
            String cols = "email, type, cardNum, name, occ, date, DOB, SIN, password";
            String vals = "'" + userInfo.get(0) + "', 1, '" + userInfo.get(1) + "', '" + userInfo.get(2) + "', '" +
                    userInfo.get(3) + "', NOW(), '" + userInfo.get(4) + "', '" + userInfo.get(5) + "', '" +
                    Password.getSaltedHash(userInfo.get(6)) + "'";
            if (Database.insert(table, cols, vals)) {
                String query = "SELECT LAST_INSERT_ID()";
                ResultSet result = Database.queryRead(query);
                Integer newID = null;
                if (result.next()) {
                    newID = result.getInt("LAST_INSERT_ID()");

                    // insert address
                    if (Database.insertAddr(addrInfo, newID, 1)) success = true;
                }
            }
        }
        return success;
    }

    /**
     * Sign in a user
     *
     * @param info [email, password]
     * @return true if signIn successfully; false otherwise
     */
    public Boolean signIn(List<String> info) throws Exception {
        Boolean success = false;
        if (!this.active) {
            String query = "select id, type, password from user where email = '" + info.get(0) + "'";
            ResultSet result = Database.queryRead(query);
            if (result.next()) {
                if (Password.check(info.get(1), result.getString("password"))) {
                    this.id = Integer.parseInt(result.getString("id"));
                    this.type = Integer.parseInt(result.getString("type"));
                    this.active = true;
                    success = true;
                }
            }
        }
        return success;
    }

    /**
     * SignOut the current user
     *
     * @return true if sign out successfully; false otherwise
     */
    public Boolean signOut() {
        if (active) {
            this.id = null;
            this.type = null;
            this.active = false;
            return true;
        }
        return false;
    }

    /**
     * Get user's id
     *
     * @return user's id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Get user's type
     *
     * @return user's type
     */
    public Integer getType() {
        return this.type;
    }

    // initial comment on a user
    // given a list of commentInfo: [receiver, rating, content]
    // return true if successfully
    public abstract Boolean commentOnUser(List<String> info) throws SQLException;

    /**
     * Reply to a comment on a user
     *
     * @param info [receiver, parent_comment, content]
     * @return true if comment successfully; false otherwise
     */
    public Boolean replyUserComment(List<String> info) {
        Boolean success = false;
        if (this.active) {
            // add to "user_comment" table
            String table = "user_comment";
            String cols = "sender, receiver, parent_comment, rating, content, date";
            String vals = this.id + ", " + info.get(0) + ", " + info.get(1) + ", null, '"
                    + info.get(2) + "', " + "NOW()";
            if (Database.insert(table, cols, vals)) success = true;
        }
        return success;
    }

    /**
     * Get a user/listing's average rating
     *
     * @param id   a user/listing's id
     * @param type 1 - user; 0 - listing
     * @return a user/listing's average rating
     */
    public Double getRating(int id, int type) throws SQLException {
        String query;
        if (type == 1)
            query = "select avg(rating) as 'rating_avg' from user_comment where receiver = " + id + " and parent_comment is null;";
        else
            query = "select avg(rating) as 'rating_avg' from mydb.listing_comment where l_id = " + id + " and parent_comment is null;";
        ResultSet rs = Database.queryRead(query);
//		CachedRowSet rowset = new CachedRowSetImpl();
//		rowset.populate(rs);
        Double rating = 0.0;
        if (rs.next()) rating = rs.getDouble("rating_avg");
        return rating;
    }


    /**
     * Get comments on a user/listing
     *
     * @param id   user/listing's id
     * @param type 1 - user; 0 - listing
     * @return listing of tables of comments, each table is a comment block
     * table schema: [id, parent_comment, sender, sender_name, receiver, receiver_name, rating, content, date]
     * i.e. * initial comment 1			 |	* initial comment 2
     * - reply to comment 1 (1)	 |	  - reply to comment 2 (7)
     * - reply to (1) (2)		 |	  - reply to (7) (8)           => two comment blocks on a user
     * - reply to (2) (3)		 |	  - reply to (8) (9)
     */
    public List<List<Row>> getComments(int id, int type) throws SQLException {
        List<List<Row>> allComments = new ArrayList<List<Row>>();
        String head_query;
        if (type == 1)
            head_query = "select id from user_comment where receiver = " + id + " and parent_comment is null;";
        else head_query = "select id from mydb.listing_comment where l_id = " + id + " and parent_comment is null;";
        ResultSet head_commentIDs = Database.queryRead(head_query);
        CachedRowSet rowset = new CachedRowSetImpl();
        rowset.populate(head_commentIDs);
        String commentBlock1, commentBlock2, commentBlock3;
        if (type == 1) {
            commentBlock1 = "select  id, parent_comment, sender, (select name from user where user.id = sender) as sender_name, " +
                    "receiver, (select name from user where user.id = receiver) as receiver_name, " +
                    " rating, content, date " +
                    "from user_comment where id = ";
            commentBlock2 =
                    " union " +
                            "select  id, parent_comment, sender, (select name from user where user.id = sender) as sender_name, " +
                            " receiver, (select name from user where user.id = receiver) as receiver_name, " +
                            " rating, content, date " +
                            "from    (select * from user_comment order by parent_comment, id) comment_sorted, " +
                            "        (select @pv := '";
        } else {
            commentBlock1 = "select  id, parent_comment, sender, (select name from user where user.id = sender) as sender_name, " +
                    "receiver, (select name from user where user.id = receiver) as receiver_name, " +
                    " rating, content, date " +
                    "from mydb.listing_comment where id = ";
            commentBlock2 = " union " +
                    "select  id, parent_comment, sender, (select name from user where user.id = sender) as sender_name, " +
                    " receiver, (select name from user where user.id = receiver) as receiver_name, " +
                    " rating, content, date " +
                    "from    (select * from mydb.listing_comment order by parent_comment, id) comment_sorted, " +
                    "        (select @pv := '";
        }
        commentBlock3 = "') initialisation " +
                " where   find_in_set(parent_comment, @pv) and length(@pv := concat(@pv, ',', id)) " +
                "order by date asc;";

        while (rowset.next()) {
            Integer head_ID = rowset.getInt("id");
            String blockComment_query = commentBlock1 + head_ID + commentBlock2 + head_ID + commentBlock3;
            ResultSet blockComment = Database.queryRead(blockComment_query);
            CachedRowSet rowset_temp = new CachedRowSetImpl();
            rowset_temp.populate(blockComment);
            List<Row> temp_table = Listing.CachedRowSet_to_ListRow(rowset_temp);
            allComments.add(temp_table);
        }
        return allComments;
    }

    /**
     * View comments on a user/listing and it's average rating
     *
     * @param id   user/listing's id
     * @param type 1 - user; 0 - listing
     */
    public void viewComments(int id, int type) throws SQLException {
        List<List<Row>> comments = getComments(id, type);
        int k = 0;
        for (int i = 0; i < comments.size(); i++) {
            for (int j = 0; j < comments.get(i).size(); j++) {
                if (k == 0) {
                    if (type == 1)
                        System.out.println("Comments on " + comments.get(i).get(j).getColumnObject(6) + "(rating: " + getRating(id, type) + ")");
                    else System.out.println("Comments on listing ID = " + id + "(rating: " + getRating(id, type) + ")");
                    k++;
                }
                if (j == 0) System.out.println("*** " + comments.get(i).get(j).getColumnObject(8) + " ***");
                else System.out.println("    @" + comments.get(i).get(j).getColumnObject(6) + " "
                        + comments.get(i).get(j).getColumnObject(8)
                        + " -- " + comments.get(i).get(j).getColumnObject(4)
                        + "(" + comments.get(i).get(j).getColumnObject(9) + ")");
            }
        }
    }

    /**
     * User cancels a booking
     *
     * @param info [l_id, fromDate, toDate]
     * @param type 1 - renter; 2 - host
     * @return true if successfully; false otherwise
     */
    public Boolean cancelBooking(List<String> info, int type) throws SQLException {
        String newInfo = "";
        String conditions = "";
        if (type == 1) { // renter
            Boolean legal = false; // legal to cancel this booking?

            String query = "SELECT * FROM mydb.rented where status = 0 and u_id = " + this.id + " and l_id = " + info.get(0) +
                    " and fromDate = '" + info.get(1) +
                    "' and toDate = '" + info.get(2) + "'; ";
            ResultSet rowset = Database.queryRead(query);
            while (rowset.next()) {
                legal = true;
                newInfo = "status = -1";
                conditions = "u_id=" + this.id + " and l_id=" + info.get(0) + " and fromDate = '" + info.get(1)
                        + "' and toDate='" + info.get(2) + "'";
                break;
            }
            if (!legal) return false;
        }
        else if (type == 2){ // host
            Boolean legal = false; // legal to cancel this booking?
            if (Listing.getOwnerId(Integer.parseInt(info.get(0))) != this.id) return false;

            String query = "SELECT * FROM mydb.rented where status = 0 and l_id = " + info.get(0) +
                    " and fromDate = '" + info.get(1) +
                    "' and toDate = '" + info.get(2) + "'; ";
            ResultSet rowset = Database.queryRead(query);
            while (rowset.next()) {
                legal = true;
                newInfo = "status = -2";
                conditions = "l_id=" + info.get(0) + " and fromDate = '" + info.get(1)
                        + "' and toDate='" + info.get(2) + "' and status = 0";
                break;
            }
            if (!legal) return false;
        }

        Boolean success = false;
        if (this.active) {
            // update "rented (status)" table
            String table1 = "rented";

            if (Database.update(table1, newInfo, conditions)) {
                // recover "availability" table
                String table2 = "availability";
                String cols2 = "id, avilDate";
                List<LocalDate> dates = Listing.allDates(info.get(1), info.get(2));
                for (int i = 0; i < dates.size(); i++) {
                    String vals2 = info.get(0) + ", '" + dates.get(i) + "'";
                    if (!Database.insert(table2, cols2, vals2)) {
                        return false;
                    }
                }
                success = true;
            }
        }
        return success;
    }


    public static void main(String args[]) throws Exception {
        if (Database.connect()) {
            Renter me = new Renter();

//			List<String> userInfo = Arrays.asList("michael@outlook.com", "2222222222222222", "Michael", "graduate",
//					"1996-03-05", "12csa442", "password");
//			List<String> addrInfo = Arrays.asList("Tobacco Quay", "Wapping Ln", "London", "UK");
//			System.out.println(me.register(userInfo, addrInfo));

            List<String> cred = Arrays.asList("qibowang7@outlook.com", "password");
            System.out.println(me.signIn(cred));

//			List<String> info = Arrays.asList("3", "5", "this user is good");
//			System.out.println(me.commentOnUser(info));

//			List<String> info = Arrays.asList("6", "1", "thanks for the comment: this user is good");
//			System.out.println(me.replyUserComment(info));

            me.viewComments(3, 1);
            me.viewComments(10, 0);
        }
        Database.disconnect();
    }

}








