package EmojiVerse.chatChannel;

import EmojiVerse.emoji.Emoji;
import EmojiVerse.emoji.EmojiMessage;
import EmojiVerse.emoji.EmojiMessageStore;
import EmojiVerse.user.User;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

public class Channel {

    String id;

    List<User> userList;

//    TODO: this message should be combination of Emoji ids?
    List<EmojiMessage> messages;

    //    Use to track last updated message
    Time timestamp;
    boolean active;

    public String getId() {
        return id;
    }

    public List<EmojiMessage> getMessages() {
        return messages;
    }

    public Channel(String id, List<User> userList, List<EmojiMessage> messages) {
        this.id = id;
        this.userList = userList;
        this.messages = messages;
        timestamp = Time.valueOf(LocalTime.MAX);
        active = true;
    }

    public void addMessage(String userID, EmojiMessage message){
        messages.add(message);
    }
}
