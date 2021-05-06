package EmojiVerse.user;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.dao.UserDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import com.google.gson.Gson;
import EmojiVerse.chatChannel.ChannelMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapper implements UserDao {

    private Jdbi jdbi;
    private String jdbcUrl;
    private List<User> userList;

    public UserMapper(String jdbcUrl) {

        Jdbi jdbi_prelim = Jdbi.create(jdbcUrl, "root", "13362478363");
        jdbi_prelim.installPlugin(new SqlObjectPlugin());
        this.jdbi = jdbi_prelim;
    }
    
    
    /*basic funcs*/

    public void testJDBI(){
        System.out.println(1);
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO users " +
                "(username, public_name, profile_img,email,hashed_psw) " +
                "VALUES (:username, :public_name, :profile_img,:email, :hashed_psw) ")
                .bind("username","xxxedf")
                .bind("public_name","dgyugewf")
                .bind("profile_img","dygdfew")
                .bind("email","fhuiewfewfy")
                .bind("hashed_psw","gfewfgef")
                .execute());
    }

    @Override
    public User getUserByUsername(String username) {
        return userList.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
    }

    @Override
    public int getUserIdFromUserName(String username) {
        int chat_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users" +
                                " where username = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());
        return chat_id;

    }
    
    
    
    
     public String getProfileImg(String username)
    {   Gson gson= new Gson();
        int user_id = getUserIdFromUserName(username);
        String profile_img = jdbi.withHandle(
                handle ->
                        handle.createQuery("select profile_img from users where user_id = :user_id")
                                .bind("user_id",user_id)
                                .map((rs, ctx) -> rs.getString("profile_img"))
                                .one());
        Map<String, Object> map = new HashMap<>();
        map.put("profile_img",profile_img);
        return gson.toJson(map);
    }
    
    
    /*friend funcs*/

    @Override
    public void addFriend(String username, String friend_username) {
        int user_id = getUserIdFromUserName(username);
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO friends " +
                "(friend_username, user_id) " +
                "VALUES (:friend_username, :user_id) ")
                .bind("friend_username",friend_username)
                .bind("user_id",user_id)
                .execute());
    }

    @Override
    public void removeFriend(String username, String friend_username) {
        int user_id = getUserIdFromUserName(username);
        jdbi.withHandle(h -> h.createUpdate("delete from friends where friend_username = :friend_username and user_id = :user_id")
                .bind("friend_username",friend_username)
                .bind("user_id",user_id)
                .execute());
    }

    @Override
    public boolean isFriendsWith(User source, User target) {
        List<String> friend_name =  jdbi.withHandle(
                handle ->
                        handle.createQuery("select friend_username from friends where username  = :friend_username")
                                .bind("friend_username",target.getUsername())
                                .map((rs, ctx) -> rs.getString("friend_username"))
                                .list());
        return friend_name.isEmpty();

    }
    
    @Override
    public String getFriendList(String username) {
        int user_id = getUserIdFromUserName(username);
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        List<String> friends = jdbi.withHandle(
                handle ->
                        handle.createQuery("select friend_username from friends where user_id = :user_id")
                                .bind("user_id",user_id)
                                .map((rs, ctx) -> rs.getString("friend_username"))
                                .list());

        map.put("friends",friends);
        return gson.toJson(map);
    }


    
    /*block funcs*/
    
    

    @Override
    public void addToBlockList(User source, User target) {
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO blocked " +
                "(blocked_username, blocked_public_name, blocked_profile_img, username) " +
                "VALUES (:blocked_username, :blocked_public_name, :blocked_profile_img, :username) ")
                .bind("blocked_username",target.getUsername())
                .bind("blocked_public_name",target.getDisplayname())
                .bind("username",source.getUsername())
                .bind("blocked_profile_img",target.getProfile_img())
                .execute());

    }

    @Override
    public void removeFromBlockList(User source, User target) {
        jdbi.withHandle(h -> h.createUpdate("DELETE FROM blocked where blocked_username = :blocked_username")
                .bind("blocked_username",target.getUsername())
                .execute());

    }
    
    
    /*authentication funcs*/
    
    
    @Override
    public boolean authUser(String username, String password) {
        List<String> users = jdbi.withHandle(
                handle ->
                        handle.createQuery("select username from users where username = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getString("username"))
                                .list());
        if(!users.isEmpty())
        {
            String user_password = jdbi.withHandle(
                    handle ->
                            handle.createQuery("select hashed_psw from users where username = :username")
                                    .bind("username",username)
                                    .map((rs, ctx) -> rs.getString("hashed_psw"))
                                    .one());
            ;
            //System.out.println(password.replaceAll("\\s+", "").compareTo(user_password.replaceAll("\\s+", "")));

            if(user_password.compareTo(password) == 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    @Override
    public void registerUser(User user) {
        String img = "https://emojiverse.s3.us-east-2.amazonaws.com/vector_graphic.png";

        jdbi.withHandle(h -> h.createUpdate("INSERT INTO users " +
                "(username, hashed_psw, email,permission_level, emoji_coins, profile_img) " +
                "VALUES (:username, :hashed_psw, :email,:permission_level, :emoji_coins, :profile_img)  ")
                .bind("username",user.getUsername())
                .bind("hashed_psw",user.getPassword())
                .bind("email",user.getEmail())
                .bind("permission_level",2)
                .bind("emoji_coins",0)
                .bind("profile_img",img)
                .execute());

    }
    
    @Override
    public boolean isDuplicate(User user) {
        System.out.println(jdbi);
        List<String> users = jdbi.withHandle(
                handle ->
                        handle.createQuery("select username from users where username = :username")
                                .bind("username",user.getUsername())
                                .map((rs, ctx) -> rs.getString("username"))
                                .list());
        List<String> emails = jdbi.withHandle(
                handle ->
                        handle.createQuery("select email from users where email = :email")
                                .bind("email",user.getEmail())
                                .map((rs, ctx) -> rs.getString("email"))
                                .list());
        return !(users.isEmpty()) && !(emails.isEmpty());
    }
   

    

    
}
