package EmojiVerse.chatChannel;

import EmojiVerse.dao.ChatDao;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.user.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import com.google.gson.Gson;
import java.util.*;


public class ChannelMapper implements ChatDao {
    private List<Channel> channels = new ArrayList<Channel>();
    private int newChanID = 1;

    private Jdbi jdbi;
    private String jdbcUrl;


    public ChannelMapper(String jdbcUrl) {

        Jdbi jdbi_prelim = Jdbi.create(jdbcUrl, "root", "new_password");
        jdbi_prelim.installPlugin(new SqlObjectPlugin());
        this.jdbi = jdbi_prelim;
    }

    @Override
    public String getChannelByID(int id) {
        return jdbi.withHandle(
                handle ->
                        handle.createQuery("select chat_name from chat_list where chat_id  = :chat_id")
                                .bind("chat_id",id)
                                .map((rs, ctx) -> rs.getString("chat_name"))
                                .one());
    }
    @Override
    public int getChatIdFromChatName(String chat_name) {
        int chat_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select chat_id from chat_list" +
                                " where chat_name = :chat_name")
                                .bind("chat_name",chat_name)
                                .map((rs, ctx) -> rs.getInt("chat_id"))
                                .one());
        return chat_id;
    }

    @Override
    public void addMessage(Channel channel, String message, String username) {
        int user_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users where username  = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());

        jdbi.withHandle(h -> h.createUpdate("INSERT INTO  user_messages" +
                "(chat_id,user_id, message) " +
                "VALUES (:chat_id, :user_id, :message) ")
                .bind("chat_id",channel.getId())
                .bind("user_id",user_id)
                .bind("message",message)
                .execute());
    }

    @Override
    public void addUser(Channel channel,String target_username) {
        int user_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users where username  = :username")
                                .bind("username",target_username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO  chat_participants" +
                "(chat_id,user_id) " +
                "VALUES (:chat_id, :user_id) ")
                .bind("chat_id",channel.getId())
                .bind("user_id",user_id)
                .execute());
    }

    @Override
    public void removeUser(Channel channel, String target_username) {
        int user_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users where username  = :username")
                                .bind("username",target_username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());
        jdbi.withHandle(h -> h.createUpdate("delete from chat_participants where user_id = :user_id")
                .bind("user_id",user_id)
                .execute());

    }

    @Override
    public int getNextChatId() {
        int next_chat_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select max(chat_id)+1 as next_chat_id from chat_list")
                                .map((rs, ctx) -> rs.getInt("next_chat_id"))
                                .one());
        return next_chat_id;
    }

    @Override
    public String getMessages(String chat_name) {
        int chat_id = getChatIdFromChatName(chat_name);
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        List<String> messages = jdbi.withHandle(
                handle ->
                        handle.createQuery("select message from user_messages" +
                                " inner join chat_participants on user_messages.chat_id = chat_participants.chat_id" +
                                " where user_messages.chat_id = :chat_id")
                                .bind("chat_id",chat_id)
                                .map((rs, ctx) -> rs.getString("message"))
                                .list());
        List<String> usernames = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_messages.user_id from user_messages" +
                                " inner join chat_participants on user_messages.chat_id = chat_participants.chat_id" +
                                " where user_messages.chat_id = :chat_id")
                                .bind("chat_id",chat_id)
                                .map((rs, ctx) -> rs.getString("message"))
                                .list());
        map.put("messages",messages);
        map.put("usernames",usernames);

        return gson.toJson(map);
    }


}
