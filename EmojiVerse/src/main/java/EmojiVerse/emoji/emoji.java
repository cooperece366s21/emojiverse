package EmojiVerse.emoji;

import java.net.URL;

public class emoji {
    String name;
//    depends on our database, need a link to the img of the emoji
//    TODO: need to have a emoji hash map to map the id to the img in another class?
    URL img;

    public emoji(String name, URL img){
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public URL getImg() {
        return img;
    }

}
