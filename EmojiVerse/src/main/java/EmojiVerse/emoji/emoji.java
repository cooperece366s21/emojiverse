package EmojiVerse.emoji;

import java.net.URL;


public class emoji {

    String name;
    String img;

    public emoji(String name, String img){
        this.name = name;
        this.img = img;
    }

    public emoji(){ }

    public String getName() {
        return name;
    }

    public String getImg () {
        return img;
    }
}
