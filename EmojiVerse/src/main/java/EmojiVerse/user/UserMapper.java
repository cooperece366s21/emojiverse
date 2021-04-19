package EmojiVerse.user;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.dao.UserDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;

public class UserMapper implements UserDao {

    private Jdbi jdbi;
    private String jdbcUrl;
    private List<User> userList;

    public UserMapper(String jdbcUrl) {

        Jdbi jdbi_prelim = Jdbi.create(jdbcUrl, "root", "new_password");
        jdbi_prelim.installPlugin(new SqlObjectPlugin());
        this.jdbi = jdbi_prelim;
    }

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
    public void addFriend(User user, User friend) {
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO friends " +
                "(friend_username, friend_public_name, username, friend_profile_img) " +
                "VALUES (:friend_username, :friend_public_name, :username, :friend_profile_img) ")
                .bind("friend_username",friend.getUsername())
                .bind("friend_public_name",friend.getDisplayname())
                .bind("username",user.getUsername())
                .bind("friend_profile_img",friend.getProfile_img())
                .execute());
    }

    @Override
    public void removeFriend(User user, User friend) {
        jdbi.withHandle(h -> h.createUpdate("delete from friends where friend_username = :friend_username")
                .bind("friend_username",friend.getUsername())
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

    @Override
    public void registerUser(User user) {

        jdbi.withHandle(h -> h.createUpdate("INSERT INTO users " +
                "(username, hashed_psw, email,permission_level, emoji_coins) " +
                "VALUES (:username, :hashed_psw, :email,:permission_level, :emoji_coins)  ")
                .bind("username",user.getUsername())
                .bind("hashed_psw",user.getPassword())
                .bind("email",user.getEmail())
                .bind("permission_level",2)
                .bind("emoji_coins",0)
                .execute());

    }

    @Override
    public void addChannel(Channel channel) {
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO chat_list (chat_id,chat_name) VALUES (:chat_id,:chat_name)  ")
                .bind("chat_name",channel.getChannelName())
                .bind("chat_id",channel.getId())
                .execute());

        for(User u : channel.getUserList())
        {
            int user_id =  jdbi.withHandle(
                    handle ->
                            handle.createQuery("select user_id from users where username = :username")
                                    .bind("username",u.getUsername())
                                    .map((rs, ctx) -> rs.getInt("chat_id"))
                                    .one());

            jdbi.withHandle(h -> h.createUpdate("INSERT INTO chat_participants (chat_id, user_id) VALUES (:chat_id, :user_id)  ")
                    .bind("chat_id",channel.getId())
                    .bind("user_id",user_id)
                    .execute());
        }
    }





    @Override
    public void removeChannel(Channel channel) {
        jdbi.withHandle(h -> h.createUpdate("DELETE FROM chat_list where chat_id = :chat_id")
                .bind("chat_id",channel.getId())
                .execute());

        jdbi.withHandle(h -> h.createUpdate("DELETE FROM chat_participants where chat_id = :chat_id")
                .bind("chat_id",channel.getId())
                .execute());

        jdbi.withHandle(h -> h.createUpdate("DELETE FROM user_messages where chat_id = :chat_id")
                .bind("chat_id",channel.getId())
                .execute());

    }

    @Override
    public List<String> getChannelList(User user) {
        return jdbi.withHandle(
                handle ->
                        handle.createQuery("select chat_id, chat_name from chat_list " +
                                "inner join chat_participants" +
                                "where username = :username")
                                .bind("username",user.getUsername())
                                .map((rs, ctx) -> rs.getString("chat_id"))
                                .list());
    }

    @Override
    public boolean authUser(String username) {
        List<String> users = jdbi.withHandle(
                handle ->
                        handle.createQuery("select username from users where username = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getString("username"))
                                .list());
        return !(users.isEmpty());
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
                                .bind("username",user.getEmail())
                                .map((rs, ctx) -> rs.getString("email"))
                                .list());
        return !(users.isEmpty()) && !(emails.isEmpty());
    }
}

