
package EmojiVerse.emoji;

import java.util.HashMap;

public class EmojiStore {
    public static HashMap<String, Emoji> emojiMap = new HashMap<String, Emoji>();

    public EmojiStore(){
        emojiMap.put("Hi",new Emoji("Hi", "Hi.com"));
        emojiMap.put("Happy",new Emoji("Happy", "Happy.com"));
        emojiMap.put("Sad",new Emoji("Sad", "Sad.com"));
    }


    public HashMap<String, Emoji> getEmojiMap() {
        return emojiMap;
    }

    public Emoji get(String id){
        return emojiMap.get(id);
    }
}