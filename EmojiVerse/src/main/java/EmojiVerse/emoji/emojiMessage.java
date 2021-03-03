package EmojiVerse.emoji;

import java.util.List;

public class emojiMessage {
    List<emoji> emojis;

//    TODO: need to have a emoji builder to build emoji message
//    TODO: May be we dont need this just use string list to contain the emoji name?
//    Currently only take one emoji to test
    public emojiMessage(emoji emoji1) {
        emojis.add(emoji1);
    }

    public List<emoji> getEmojis() {
        return emojis;
    }
}
