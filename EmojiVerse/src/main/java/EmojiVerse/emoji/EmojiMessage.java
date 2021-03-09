package EmojiVerse.emoji;

import java.util.List;

public class EmojiMessage {
    List<Emoji> Emojis;
    String UserID;

    public EmojiMessage(Emoji emoji1, String userId) {
        Emojis = List.of(emoji1);
        this.UserID = userId;
    }

    public List<Emoji> getEmojis() {
        return Emojis;
    }

    public EmojiMessage add(Emoji emoji){
        Emojis.add(emoji);
        return this;
    }
}
