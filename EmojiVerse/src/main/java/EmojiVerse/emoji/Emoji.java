package EmojiVerse.emoji;

import java.net.URL;


public class Emoji {

    String name;
    String img;

    public Emoji(String name, String img){
        this.name = name;
        this.img = img;
    }

    public Emoji(){ }

    public String getName() {
        return name;
    }

    public String getImg () {
        return img;
    }
}
