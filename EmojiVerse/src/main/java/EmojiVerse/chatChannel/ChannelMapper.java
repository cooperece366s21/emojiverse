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
        Jdbi jdbi_prelim = Jdbi.create(jdbcUrl, System.getenv("EVERSEDB_USER"), System.getenv("EVERSEDB_PASS"));
        jdbi_prelim.installPlugin(new SqlObjectPlugin());
        this.jdbi = jdbi_prelim;
    }
    
    
    /*basic db funcs*/

    @Override
    public String getChannelByID(int id) {
        return jdbi.withHandle(
                handle ->
                        handle.createQuery("select chat_name from chat_list where chat_id  = :chat_id")
                                .bind("chat_id",id)
                                .map((rs, ctx) -> rs.getString("chat_name"))
                                .one());
    }


//    TODO: This function should also check the name of the user? otherwise there will be expect one found multiple when different user have the same chat name
//    TODO: This should also compare the participants in the chat
    @Override
    public int getChatIdFromChatName(String chat_name) {
        int chat_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select chat_id from chat_list where chat_name = :chat_name")
                                .bind("chat_name",chat_name)
                                .map((rs, ctx) -> rs.getInt("chat_id"))
                                .one());
        return chat_id;
    }
    
    
    /*db message funcs*/

    @Override
    public void addMessage(String chat_name, String message, String username, String datetime) {
        int chat_id  = getChatIdFromChatName(chat_name);
        System.out.println("chat_id : " + chat_id);
        System.out.println("message : " + message);
        System.out.println("username : " + username);
        System.out.println("datetime : " + datetime);


        int user_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users where username  = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());

        jdbi.withHandle(h -> h.createUpdate("INSERT INTO  user_messages" +
                "(chat_id,user_id, message,datetime) " +
                "VALUES (:chat_id, :user_id, :message,:datetime) ")
                .bind("chat_id",chat_id)
                .bind("user_id",user_id)
                .bind("message",message)
                .bind("datetime",datetime)
                .execute());
    }
    
     @Override
    public String getMessages(String chat_name) {
        int chat_id = getChatIdFromChatName(chat_name);
        System.out.println(chat_id);
        System.out.println(chat_name);
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        List<String> messages = jdbi.withHandle(
                handle ->
                        handle.createQuery("select message from user_messages" +
                                " where user_messages.chat_id = :chat_id")
                                .bind("chat_id",chat_id)
                                .map((rs, ctx) -> rs.getString("message"))
                                .list());
        List<String> usernames = jdbi.withHandle(
                handle ->
                        handle.createQuery("select users.username from user_messages" +
                                " inner join users on users.user_id= user_messages.user_id" +
                                " where user_messages.chat_id = :chat_id")
                                .bind("chat_id",chat_id)
                                .map((rs, ctx) -> rs.getString("users.username"))
                                .list());
        System.out.println(usernames);
        System.out.println(messages);
        List<String> dates = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_messages.datetime from user_messages" +
                                " inner join chat_participants on user_messages.chat_id = chat_participants.chat_id" +
                                " where user_messages.chat_id = :chat_id")
                                .bind("chat_id",chat_id)
                                .map((rs, ctx) -> rs.getString("user_messages.datetime"))
                                .list());
        List<String> message_info = new ArrayList<String>();

        for(int i = 0; i<messages.size();i++)
        {
            message_info.add(usernames.get(i) + " (" + dates.get(i) + " ) : " + messages.get(i));
        }
        map.put("message_info",message_info);


        return gson.toJson(map);
    }
    
    
    /*db participant funcs*/

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

   
    
    /*channel db funcs*/
    
    
    
    @Override
    public void addChannel(Channel channel, String requester_username) {
        System.out.println(channel.getUserList());

        System.out.println(channel.getUserList());
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO chat_list (chat_name) VALUES (:chat_name)  ")
                .bind("chat_name",channel.getChannelName())
                .execute());
        int chat_id = getChatIdFromChatName(channel.getChannelName());

        int requester_id =  jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users where username = :username")
                                .bind("username",requester_username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());

        jdbi.withHandle(h -> h.createUpdate("INSERT INTO chat_participants (chat_id, user_id) VALUES (:chat_id, :user_id)  ")
                .bind("chat_id",chat_id)
                .bind("user_id",requester_id)
                .execute());

        for(String username : channel.getUserList())
        {
            int user_id =  jdbi.withHandle(
                    handle ->
                            handle.createQuery("select user_id from users where username = :username")
                                    .bind("username",username)
                                    .map((rs, ctx) -> rs.getInt("user_id"))
                                    .one());

            jdbi.withHandle(h -> h.createUpdate("INSERT INTO chat_participants (chat_id, user_id) VALUES (:chat_id, :user_id)  ")
                    .bind("chat_id",chat_id)
                    .bind("user_id",user_id)
                    .execute());
        }
    }
    
    @Override
    public void removeChannel(String chat_name) {
        int chat_id = getChatIdFromChatName(chat_name);
        jdbi.withHandle(h -> h.createUpdate("DELETE FROM chat_participants where chat_id = :chat_id")
                .bind("chat_id",chat_id)
                .execute());

        jdbi.withHandle(h -> h.createUpdate("DELETE FROM chat_list where chat_id = :chat_id")
                .bind("chat_id",chat_id)
                .execute());

        jdbi.withHandle(h -> h.createUpdate("DELETE FROM user_messages where chat_id = :chat_id")
                .bind("chat_id",chat_id)
                .execute());

        System.out.println("Chat channel " + chat_name + " remove successfully");

    }

    @Override

    public String getChannelList(String username) {
        Gson gson = new Gson();
        List<List<String>> chat_participant_list = new ArrayList<List<String>>();
        int user_id =  jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users where username = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());

        List<Integer> channels = jdbi.withHandle(
                handle ->
                        handle.createQuery("select distinct(chat_list.chat_id) from chat_list " +
                                "inner join chat_participants on chat_list.chat_id = chat_participants.chat_id where user_id = :user_id")
                                .bind("user_id",user_id)
                                .map((rs, ctx) -> rs.getInt("chat_id"))
                                .list());
        List<String> chat_names = jdbi.withHandle(
                handle ->
                        handle.createQuery("select distinct(chat_list.chat_name) from chat_list " +
                                "inner join chat_participants on chat_list.chat_id = chat_participants.chat_id where user_id = :user_id")
                                .bind("user_id",user_id)
                                .map((rs, ctx) -> rs.getString("chat_name"))
                                .list());
        int index = 0;
        /*would probably be better formatting to do three inner joins between chat_list, chat_participants, and users*/
        for (String chat_name : chat_names)
        {
            List<Integer> chat_participant_ids = jdbi.withHandle(
                    handle ->
                            handle.createQuery("select distinct(chat_participants.user_id) from chat_list " +
                                    "inner join chat_participants on chat_list.chat_id = chat_participants.chat_id where chat_list.chat_name = :chat_name")
                                    .bind("chat_name",chat_name)
                                    .map((rs, ctx) -> rs.getInt("chat_participants.user_id"))
                                    .list());
            System.out.println(chat_participant_ids);
            List<String> chat_participants = new ArrayList<String>();
            int inside_index=0;
            for(int id:chat_participant_ids)
            {
                String participant = jdbi.withHandle(
                        handle ->
                                handle.createQuery("select distinct(username) from users " +
                                        "where user_id = :user_id")
                                        .bind("user_id",id)
                                        .map((rs, ctx) -> rs.getString("username"))
                                        .one());
                chat_participants.add(inside_index,participant);
                inside_index++;
            }
            chat_participant_list.add(index,chat_participants);
            index++;
        }

        List<String> modified_chat_names = new ArrayList<String>();
        for(int i = 0; i <chat_names.size();i++)
        {
            modified_chat_names.add(i,chat_names.get(i).toUpperCase() + " participants: " + chat_participant_list.get(i) + "$");
        }
        System.out.println(modified_chat_names);
        Map<String, Object> map = new HashMap<>();
        map.put("channels",channels);
        System.out.println(channels);
        map.put("chat_names",modified_chat_names);
        return gson.toJson(map);
    }



}
