package EmojiVerse.emoji;
import java.util.*;

public interface EmojiJDBI {
    void addEmojiCoins(String username);

    void subtractEmojiCoins(String username, int price);

    void addEmoji(String emoji, String username);

    void addEmojiToStore(String emoji,int price, String category);

    void populateEmojiStore(List<String> emojis, List<Integer> prices, List<String> categories);

    List<String> getEmojis(String username);


    int getEmojiCoins(String username);

}
