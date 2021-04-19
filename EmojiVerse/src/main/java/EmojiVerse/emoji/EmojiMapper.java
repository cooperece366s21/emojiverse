package EmojiVerse.emoji;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.List;
import java.util.*;

public class EmojiMapper implements EmojiJDBI{

    private Jdbi jdbi;
    private String jdbcUrl;


    public EmojiMapper(String jdbcUrl) {

        Jdbi jdbi_prelim = Jdbi.create(jdbcUrl, "root", "new_password");
        jdbi_prelim.installPlugin(new SqlObjectPlugin());
        this.jdbi = jdbi_prelim;
    }
    @Override
    public void addEmojiCoins(String username) {
        jdbi.withHandle(h -> h.createUpdate("update users" +
                "set emoji_coins = emoji_coins+1" +
                "where username = :username")
                .bind("username",username)
                .execute());

    }

    @Override
    public void subtractEmojiCoins(String username, int price) {
        jdbi.withHandle(h -> h.createUpdate("update users" +
                "set emoji_coins = emoji_coins-price" +
                "where username = :username")
                .bind("username",username)
                .execute());


    }

    @Override
    public int getEmojiCoins(String username) {
        int emoji_coins = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji_coins from users where username  = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());
        return emoji_coins;
    }

    @Override
    public void addEmoji(String emoji, String username) {
        int user_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select user_id from users where username  = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("user_id"))
                                .one());

        int emoji_id = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji_id from emoji_store where emoji  = :emoji")
                                .bind("emoji",emoji)
                                .map((rs, ctx) -> rs.getInt("emoji_id"))
                                .one());

        jdbi.withHandle(h -> h.createUpdate("INSERT INTO  emojis" +
                "(:emoji_id,:user_id) " +
                "VALUES (:emoji_id, :user_id) ")
                .bind("emoji_id",emoji_id)
                .bind("user_id",user_id)
                .execute());
        int price = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji_price from emoji_store inner join emojis where emoji  = :emoji")
                                .bind("emoji",emoji)
                                .map((rs, ctx) -> rs.getInt("emoji_price"))
                                .one());

        subtractEmojiCoins(username, price);

    }

    @Override
    public void addEmojiToStore(String emoji, int price, String category) {
        jdbi.withHandle(h -> h.createUpdate("INSERT INTO emoji_store " +
                "(emoji, emoji_price,category) " +
                "VALUES (:emoji, :emoji_price, :category) ")
                .bind("emoji",emoji)
                .bind("emoji_price",price)
                .bind("category",category)
                .execute());

    }

    @Override
    public void populateEmojiStore(List<String> emojis, List<Integer> prices, List<String> categories)  {
        for(int i =0; i < emojis.size(); i++)
        {
            addEmojiToStore(emojis.get(i), prices.get(i),categories.get(i));
        }

    }

    @Override
    public List<String> getEmojis(String username) {
        return jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji from emoji_store inner join emojis where username = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getString("emoji"))
                                .list());
    }


}
