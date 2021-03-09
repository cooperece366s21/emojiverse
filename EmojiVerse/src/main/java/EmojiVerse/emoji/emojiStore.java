
package EmojiVerse.emoji;

import java.net.URL;
import java.util.HashMap;

public class emojiStore {
    public HashMap<String, emoji> emojiMap = new HashMap<String, emoji>();

    public emojiStore(){
        emojiMap.put("Hi",new emoji("Hi", "Hi.com"));
        emojiMap.put("Happy",new emoji("Happy", "Happy.com"));
        emojiMap.put("Sad",new emoji("Sad", "Sad.com"));
    }


    public HashMap<String, emoji> getEmojiMap() {
        return emojiMap;
    }
}