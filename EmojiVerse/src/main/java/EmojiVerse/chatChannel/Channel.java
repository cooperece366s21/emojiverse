package EmojiVerse.chatChannel;

import EmojiVerse.user.User;
import EmojiVerse.emoji.emojiMessage;

import java.sql.Time;
import java.util.List;

public class Channel {

    String id;

    List<User> userList;

//    TODO: this message should be combination of emoji ids?
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

    public Channel(String id, List<User> userList, List<String> messages, Time timestamp, boolean active) {
        this.id = id;
        this.userList = userList;
        this.messages = messages;
        this.timestamp = timestamp;
        this.active = active;
    }
}
