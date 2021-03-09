package EmojiVerse.chatChannel;

import EmojiVerse.user.User;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class Channel {

    String id;

    List<User> userList;

//    TODO: this message should be combination of Emoji ids?
    List<String> messages;

    //    Use to track last updated message
    Time timestamp;
    boolean active;

    public String getId() {
        return id;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Channel(String id, List<User> userList, List<String> messages) {
        this.id = id;
        this.userList = userList;
        this.messages = messages;
        timestamp = Time.valueOf(LocalTime.MAX);
        active = true;
    }
}
