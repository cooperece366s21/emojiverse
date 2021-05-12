package EmojiVerse.emoji;

import com.google.gson.Gson;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import EmojiVerse.user.UserMapper;
import java.util.List;
import java.util.*;

public class EmojiMapper implements EmojiJDBI{

    private Jdbi jdbi;
    private String jdbcUrl;


    public EmojiMapper(String jdbcUrl) {

        Jdbi jdbi_prelim = Jdbi.create(jdbcUrl, System.getenv("EVERSEDB_USER"), System.getenv("EVERSEDB_PASS"));
        jdbi_prelim.installPlugin(new SqlObjectPlugin());
        this.jdbi = jdbi_prelim;
    }
    @Override
    public void addEmojiCoins(String username) {
        jdbi.withHandle(h -> h.createUpdate("update users" +
                " set emoji_coins = emoji_coins+1" +
                " where username = :username")
                .bind("username",username)
                .execute());

    }

    @Override
    public void subtractEmojiCoins(String username, int price) {
        jdbi.withHandle(h -> h.createUpdate("update users" +
                " set emoji_coins = emoji_coins- :price" +
                " where username = :username")
                .bind("price",price)
                .bind("username",username)
                .execute());


    }



    @Override
    public String getEmojiCoins(String username) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        int emoji_coins = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji_coins from users where username  = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("emoji_coins"))
                                .list().get(0));
        map.put("emoji_coins",emoji_coins);
        System.out.println(emoji_coins);
        return gson.toJson(map);
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
    public void populateEmojiStore(List<String> emojis, int price, String category) {


        /*something fishy here. should not have zeros for majority of emojis*/
        if (price == -1) {
            for(int i =0; i < emojis.size(); i++)
            {
                price = (int) Math.ceil(Math.random()*3000);
                if(price < 500)
                {
                    price = 0;
                }
                System.out.println(i);
                System.out.println(price);
                addEmojiToStore(emojis.get(i), price,category);
            }

        } else
        {
            for(int i =0; i < emojis.size(); i++)
            {
                addEmojiToStore(emojis.get(i), price,category);
            }
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

    @Override
    public String getEmojisFromStore(String username) {
        UserMapper userMapper = new UserMapper("jdbc:mysql://localhost:3306/emojiverse");
        int user_id = userMapper.getUserIdFromUserName(username);
        System.out.println("hello");
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> fullMap = new HashMap<>();
        List<Emoji> emoji_list = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji,category,emoji_price from emoji_store" +
                                " where emoji not in (select emoji from emoji_store" +
                                " inner join emojis e on emoji_store.emoji_id = e.emoji_id" +
                                " where user_id = :user_id) order by emoji_price")
                                .bind("user_id",user_id)
                                .map(new EmojiRowMapper())
                                .list());
        List<String> PEOPLE_EMOJIS = new ArrayList<String>();
        List<String> ANIMALS_NATURE_EMOJIS = new ArrayList<String>();
        List<String> FOOD_SPORTS_EMOJIS = new ArrayList<String>();
        List<String> TRAVEL_PLACES_EMOJIS = new ArrayList<String>();
        List<String> OBJECTS_EMOJIS = new ArrayList<String>();
        List<String> SYMBOLS_FLAGS_EMOJIS = new ArrayList<String>();

        List<Integer> PEOPLE_EMOJIS_prices = new ArrayList<Integer>();
        List<Integer> ANIMALS_NATURE_EMOJIS_prices = new ArrayList<Integer>();
        List<Integer> FOOD_SPORTS_EMOJIS_prices = new ArrayList<Integer>();
        List<Integer> TRAVEL_PLACES_EMOJIS_prices = new ArrayList<Integer>();
        List<Integer> OBJECTS_EMOJIS_prices = new ArrayList<Integer>();
        List<Integer> SYMBOLS_FLAGS_EMOJIS_prices = new ArrayList<Integer>();


        List<Integer> prices = new ArrayList<Integer>();
        for(Emoji emoji : emoji_list)
        {

            switch(emoji.getCategory())
            {
                case "PEOPLE_EMOJIS":
                    PEOPLE_EMOJIS.add(emoji.getName() + " = "  + emoji.getPrice());
                    PEOPLE_EMOJIS_prices.add(emoji.getPrice());
                    break;
                case "ANIMALS_NATURE_EMOJIS":
                    ANIMALS_NATURE_EMOJIS.add(emoji.getName() + " = " + emoji.getPrice());
                    ANIMALS_NATURE_EMOJIS_prices.add(emoji.getPrice());
                    break;
                case "FOOD_SPORTS_EMOJIS":
                    FOOD_SPORTS_EMOJIS.add(emoji.getName() + " = " + emoji.getPrice());
                    FOOD_SPORTS_EMOJIS_prices.add(emoji.getPrice());
                    break;
                case "OBJECTS_EMOJIS":
                    OBJECTS_EMOJIS.add(emoji.getName() + " = " + emoji.getPrice());
                    OBJECTS_EMOJIS_prices.add(emoji.getPrice());
                    break;
                case "TRAVEL_PLACES_EMOJIS":
                    TRAVEL_PLACES_EMOJIS.add(emoji.getName() + " = " + emoji.getPrice());
                    TRAVEL_PLACES_EMOJIS_prices.add(emoji.getPrice());
                    break;
                case "SYMBOLS_FLAGS_EMOJIS":
                    SYMBOLS_FLAGS_EMOJIS.add(emoji.getName() + " = " + emoji.getPrice());
                    SYMBOLS_FLAGS_EMOJIS_prices.add(emoji.getPrice());
                    break;
                default:
                    System.out.println("Emoji does not exist");
                    break;
            }

        }
        map.put("PEOPLE_EMOJIS",PEOPLE_EMOJIS);
        map.put("ANIMALS_NATURE_EMOJIS",ANIMALS_NATURE_EMOJIS);
        map.put("FOOD_SPORTS_EMOJIS",FOOD_SPORTS_EMOJIS);
        map.put("OBJECTS_EMOJIS", OBJECTS_EMOJIS);
        map.put("SYMBOLS_FLAGS_EMOJIS",SYMBOLS_FLAGS_EMOJIS);
        map.put("TRAVEL_PLACES_EMOJIS",TRAVEL_PLACES_EMOJIS);

        map.put("PEOPLE_EMOJIS_prices",PEOPLE_EMOJIS_prices);
        map.put("ANIMALS_NATURE_EMOJIS_prices",ANIMALS_NATURE_EMOJIS_prices);
        map.put("FOOD_SPORTS_EMOJIS_prices",FOOD_SPORTS_EMOJIS_prices);
        map.put("OBJECTS_EMOJIS_prices", OBJECTS_EMOJIS_prices);
        map.put("SYMBOLS_FLAGS_EMOJIS_prices",SYMBOLS_FLAGS_EMOJIS_prices);
        map.put("TRAVEL_PLACES_EMOJIS_prices",TRAVEL_PLACES_EMOJIS_prices);




        return gson.toJson(map);



    }

    public int getEmojiPrice(String emoji)
    {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();

        int price = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji_price from emoji_store" +
                                " where emoji = :emoji")
                                .bind("emoji",emoji)
                                .map((rs, ctx) -> rs.getInt("emoji_price"))
                                .list().get(0));

        return price;
    }

    public String getEmojiCategory(String emoji)
    {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();

        String category = jdbi.withHandle(
                handle ->
                        handle.createQuery("select category from emoji_store" +
                                " where emoji = :emoji")
                                .bind("emoji",emoji)
                                .map((rs, ctx) -> rs.getString("category"))
                                .list().get(0));

        return category;
    }

    public String getUserEmojis(String username)
    {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        UserMapper userMapper  = new UserMapper("jdbc:mysql://localhost:3306/emojiverse");
        int user_id = userMapper.getUserIdFromUserName(username);
        List<Emoji> user_emoji_list = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji,category,emoji_price from emoji_store " +
                                "inner join emojis on emoji_store.emoji_id = emojis.emoji_id" +
                                " where user_id = :user_id")
                                .bind("user_id",user_id)
                                .map(new EmojiRowMapper())
                                .list());
        List<String> PEOPLE_EMOJIS = new ArrayList<String>();
        List<String> ANIMALS_NATURE_EMOJIS = new ArrayList<String>();
        List<String> FOOD_SPORTS_EMOJIS = new ArrayList<String>();
        List<String> TRAVEL_PLACES_EMOJIS = new ArrayList<String>();
        List<String> OBJECTS_EMOJIS = new ArrayList<String>();
        List<String> SYMBOLS_FLAGS_EMOJIS = new ArrayList<String>();




        List<Integer> prices = new ArrayList<Integer>();
        for(Emoji emoji : user_emoji_list)
        {

            switch(emoji.getCategory())
            {
                case "PEOPLE_EMOJIS":
                    PEOPLE_EMOJIS.add(emoji.getName());

                    break;
                case "ANIMALS_NATURE_EMOJIS":
                    ANIMALS_NATURE_EMOJIS.add(emoji.getName());

                    break;
                case "FOOD_SPORTS_EMOJIS":
                    FOOD_SPORTS_EMOJIS.add(emoji.getName());

                    break;
                case "OBJECTS_EMOJIS":
                    OBJECTS_EMOJIS.add(emoji.getName());

                    break;
                case "TRAVEL_PLACES_EMOJIS":
                    TRAVEL_PLACES_EMOJIS.add(emoji.getName());

                    break;
                case "SYMBOLS_FLAGS_EMOJIS":
                    SYMBOLS_FLAGS_EMOJIS.add(emoji.getName());

                    break;
                default:
                    System.out.println("Emoji does not exist");
                    break;
            }

        }
        map.put("PEOPLE_EMOJIS",PEOPLE_EMOJIS);
        map.put("ANIMALS_NATURE_EMOJIS",ANIMALS_NATURE_EMOJIS);
        map.put("FOOD_SPORTS_EMOJIS",FOOD_SPORTS_EMOJIS);
        map.put("OBJECTS_EMOJIS", OBJECTS_EMOJIS);
        map.put("SYMBOLS_FLAGS_EMOJIS",SYMBOLS_FLAGS_EMOJIS);
        map.put("TRAVEL_PLACES_EMOJIS",TRAVEL_PLACES_EMOJIS);



        return gson.toJson(map);
    }
    @Override
    public boolean buyEmoji(String username, String emoji) {
        Map<String, Object> map = new HashMap<>();
        int price = getEmojiPrice(emoji);

        int emoji_coins = jdbi.withHandle(
                handle ->
                        handle.createQuery("select emoji_coins from users where username  = :username")
                                .bind("username",username)
                                .map((rs, ctx) -> rs.getInt("emoji_coins"))
                                .list().get(0));
        if(emoji_coins < price)
        {
            System.out.println(emoji_coins < price);
            return false;
        }
        else
        {
            map.put("verified",true);

            subtractEmojiCoins(username, price);

            int emoji_id = jdbi.withHandle(
                    handle ->
                            handle.createQuery("select emoji_id from emoji_store where emoji  = :emoji")
                                    .bind("emoji",emoji)
                                    .map((rs, ctx) -> rs.getInt("emoji_id"))
                                    .list().get(0));
            int user_id = jdbi.withHandle(
                    handle ->
                            handle.createQuery("select user_id from users where username  = :username")
                                    .bind("username",username)
                                    .map((rs, ctx) -> rs.getInt("user_id"))
                                    .list().get(0));

            jdbi.withHandle(h -> h.createUpdate("INSERT INTO  emojis" +
                    " (emoji_id,user_id) " +
                    "VALUES (:emoji_id, :user_id) ")
                    .bind("emoji_id",emoji_id)
                    .bind("user_id",user_id)
                    .execute());

            return true;


        }


    }

}
