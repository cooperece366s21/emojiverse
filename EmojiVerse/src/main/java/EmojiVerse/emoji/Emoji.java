package EmojiVerse.emoji;

import java.net.URL;


public class Emoji {

    String name;
    String img;
    int price;
    String category;

    public Emoji(){

    }

    public void setName(int id) {
    }


    public String getName() {
        return name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImg () {
        return img;
    }
}
