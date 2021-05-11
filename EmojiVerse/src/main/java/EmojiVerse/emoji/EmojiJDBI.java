package EmojiVerse.emoji;
import java.util.*;

public interface EmojiJDBI {
    void addEmojiCoins(String username);

    void subtractEmojiCoins(String username, int price);

    void addEmoji(String emoji, String username);

    void addEmojiToStore(String emoji,int price, String category);

    void populateEmojiStore(List<String> emojis, int price, String category);

    List<String> getEmojis(String username);

    String getEmojisFromStore(String username);

    String getEmojiCoins(String username);

    String getUserEmojis(String username);

}
