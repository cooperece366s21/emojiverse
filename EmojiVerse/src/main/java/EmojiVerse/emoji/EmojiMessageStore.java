package EmojiVerse.emoji;

import EmojiVerse.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmojiMessageStore {
    public static List<EmojiMessage> messages;

    public EmojiMessageStore(){
        messages = new ArrayList<>();
        messages.add(new EmojiMessage(App.emojiStore.get("Hi"),"bonny"));
    }

    public List<EmojiMessage> getMessages() {
        return messages;
    }
}
