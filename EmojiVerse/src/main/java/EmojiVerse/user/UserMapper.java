package EmojiVerse.user;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.dao.UserDao;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class UserMapper implements UserDao {

    private final Jdbi jdbi;

    public UserMapper(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @Override
    public List<User> getUserByUsername(String username) {
        return jdbi.withHandle(
                handle ->
                        handle.createQuery("select * from users where username  = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> new User(rs.getString("username"), rs.getString("hashed_psw"),rs.getString("profile_img")))
                                .list());
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
                .bind("friend_username",target.getUsername())
                .bind("friend_public_name",target.getDisplayname())
                .bind("username",source.getUsername())
                .bind("friend_profile_img",target.getProfile_img())
                .execute());

    }

    @Override
    public void removeFromBlockList(User source, User target) {

    }

    @Override
    public void registerUser(User user) {

    }

    @Override
    public void addChannel(User user, Channel channel) {

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


