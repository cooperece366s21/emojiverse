package EmojiVerse.emoji;

import java.util.List;

public class EmojiMessage {
    List<Emoji> Emojis;

//    TODO: need to have a Emoji builder to build Emoji message
//    TODO: May be we dont need this just use string list to contain the Emoji name?
//    Currently only take one Emoji to test
    public EmojiMessage(Emoji emoji1) {
        Emojis.add(emoji1);
    }

    public List<Emoji> getEmojis() {
        return Emojis;
    }
}
