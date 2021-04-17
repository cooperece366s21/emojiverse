package EmojiVerse.user;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.dao.UserDao;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class UserMapper implements UserDao {

    private final Jdbi jdbi;
    private List<User> userList;

    public UserMapper(Jdbi jdbi) {
        this.jdbi = jdbi;
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
                "(username, public_name, hashed_psw, profile_img,permission_level, emoji_coins) " +
                "VALUES (:username, :public_name, :hashed_psw, :profile_img,:permission_level, :emoji_coins)  ")
                .bind("username",user.getUsername())
                .bind("public_name",user.getDisplayname())
                .bind("hashed_psw",user.getPassword())
                .bind("profile_img",user.getProfile_img())
                .bind("permission_level",user.getPermissionLevel())
                .bind("emoji_coins",user.getEmoji_coins())
                .execute());
    }

    @Override
    public void addChannel(User user, Channel channel) {
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO chat_list (chat_name) VALUES (:chat_name)  ")
                .bind("chat_name",channel.getChannelName())
                .execute());

        int chat_id =  jdbi.withHandle(
                handle ->
                        handle.createQuery("select chat_id from chat_list where chat_name = :chat_name")
                                .bind("chat_name",channel.getChannelName())
                                .map((rs, ctx) -> rs.getInt("chat_id"))
                                .one());
        
        for(User u : channel.getUserList())
        {
            int user_id =  jdbi.withHandle(
                    handle ->
                            handle.createQuery("select user_id from users where username = :username")
                                    .bind("username",u.getUsername())
                                    .map((rs, ctx) -> rs.getInt("chat_id"))
                                    .one());
            
            jdbi.withHandle(h -> h.createUpdate("INSERT INTO chat_participants (chat_id, user_id) VALUES (:chat_id, :user_id)  ")
                    .bind("chat_id",chat_id)
                    .bind("user_id",user_id)
                    .execute());
        }
    }





    @Override
    public void removeChannel(Channel channel) {

    }

    @Override
    public List<String> getChannelList(User user) {
        return null;
    }

    @Override
    public LoginResult authUser(User user) {
        return null;
    }
}
