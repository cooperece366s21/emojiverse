package EmojiVerse;

import static spark.Spark.*;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.ChannelMapper;

import EmojiVerse.emoji.EmojiMapper;
import EmojiVerse.user.User;
import EmojiVerse.user.UserMapper;

import java.util.*;

import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import com.google.gson.Gson;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */


public class App
{


	private static final String USER_SESSION_ID = "user";
	//private static final List<String> PEOPLE_EMOJIS= new ArrayList<String>(Arrays.asList("\uD83D\uDE00", "\uD83D\uDE03"));
	//private static final List<String> PEOPLE_EMOJIS= new ArrayList<String>(Arrays.asList("\uD83D\uDE00", "\uD83D\uDE03", "\uD83D\uDE04", "\uD83D\uDE01", "\uD83D\uDE06", "\uD83D\uDE05", "\uD83D\uDE02", "\uD83D\uDE07", "\uD83E\uDD23", "☺️", "\uD83D\uDE0A", "\uD83D\uDE42", "\uD83D\uDE43", "\uD83D\uDE09", "\uD83D\uDE0C", "\uD83D\uDE0D", "\uD83D\uDE18", "\uD83D\uDE17", "\uD83D\uDE19", "\uD83D\uDE1A", "\uD83D\uDE0B", "\uD83D\uDE1C","\uD83D\uDC42", "\uD83D\uDC43", "\uD83D\uDC63", "\uD83D\uDC41", "\uD83D\uDC40", "\uD83D\uDC57", "\uD83D\uDC60", "\uD83D\uDC5E", "\uD83D\uDC5F", "\uD83D\uDC52", "\uD83C\uDFA9", "\uD83C\uDF93", "\uD83D\uDC51", "\uD83C\uDF92", "\uD83D\uDC5D", "\uD83D\uDC5B", "\uD83D\uDC5C", "\uD83D\uDCBC", "\uD83D\uDC53", "\uD83D\uDD76", "☂️"));
	//private static final List<String> ANIMALS_NATURE_EMOJIS = new ArrayList<String>(Arrays.asList("🐶", "🐱", "🐭", "🐹", "🐰", "🦊", "🐻", "🐼", "🐨", "🐯", "🦁", "🐮", "🐷", "🐽", "🐸", "🐵", "🙊", "🙉", "🙊", "🐒", "🐔", "🐧", "🐦", "🐤", "🐣", "🐥", "🦆", "🦅", "🦉", "🦇", "🐺", "🐗", "🐴", "🦄", "🐝", "🐛", "🦋", "🐌", "🐚", "🐞", "🐜", "🕷", "🕸", "🐢", "🐍", "🦎", "🦂", "🦀", "🦑", "🐙", "🦐", "🐠", "🐟", "🐡", "🐬", "🦈", "🐳", "🐋", "🐊", "🐆", "🐅", "🐃", "🐂", "🐄", "🦌", "🐪", "🐫", "🐘", "🦏", "🦍", "🐎", "🐖", "🐐", "🐏", "🐑", "🐕", "🐩", "🐈", "🐓", "🦃", "🕊", "🐇", "🐁", "🐀", "🐿", "🐾", "🐉", "🐲", "🌵", "🎄", "🌲", "🌳", "🌴", "🌱", "🌿", "☘️", "🍀", "🎍", "🎋", "🍃", "🍂", "🍁", "🍄", "🌾", "💐", "🌷", "🌹", "🥀", "🌻", "🌼", "🌸", "🌺", "🌎", "🌍", "🌏", "🌕", "🌖", "🌔", "🌚", "🌝", "🌞", "🌛", "🌜", "🌙", "💫", "⭐️", "🌟", "✨", "⚡️", "🔥", "💥", "☄️", "☀️", "🌤", "⛅️", "🌥", "🌦", "🌈", "☁️", "🌧", "⛈", "🌩", "🌨", "☃️", "⛄️", "❄️", "🌬", "💨", "🌪", "🌫", "🌊", "💧", "💦", "☔️"));
	//private static final List<String> FOOD_SPORTS_EMOJIS = new ArrayList<String>(Arrays.asList("🍏", "🍎", "🍐", "🍊", "🍋", "🍌", "🍉", "🍇", "🍓", "🍈", "🍒", "🍑", "🍍", "🥝", "🥑", "🍅", "🍆", "🥒", "🥕", "🌽", "🌶", "🥔", "🍠", "🌰", "🥜", "🍯", "🥐", "🍞", "🥖", "🧀", "🥚", "🍳", "🥓", "🥞", "🍤", "🍗", "🍖", "🍕", "🌭", "🍔", "🍟", "🥙", "🌮", "🌯", "🥗", "🥘", "🍝", "🍜", "🍲", "🍥", "🍣", "🍱", "🍛", "🍚", "🍙", "🍘", "🍢", "🍡", "🍧", "🍨", "🍦", "🍰", "🎂", "🍮", "🍭", "🍬", "🍫", "🍿", "🍩", "🍪", "🥛", "🍼", "☕️", "🍵", "🍶", "🍺", "🍻", "🥂", "🍷", "🥃", "🍸", "🍹", "🍾", "🥄", "🍴", "🍽", "⚽️", "🏀", "🏈", "⚾️", "🎾", "🏐", "🏉", "🎱", "🏓", "🏸", "🥅", "🏒", "🏑", "🏏", "⛳️", "🏹", "🎣", "🥊", "🥋", "⛸", "🎿", "⛷", "🏂", "🏋", "🤺", "⛹️", "🏌", "🏄", "🏊", "🤽", "🚣", "🏇", "🚴", "🚵", "🎬", "🎤", "🎧", "🎼", "🎹", "🥁", "🎷", "🎺", "🎸", "🎻", "🎲", "🎯", "🎳", "🎮", "🏳", "🏴", "🏁", "🚩", "🎽", "🥇", "🥈", "🥉", "🏆"));
	//private static final List<String> TRAVEL_PLACES_EMOJIS = new ArrayList<String>(Arrays.asList("🚗", "🚕", "🚙", "🚌", "🚎", "🏎", "🚓", "🚑", "🚒", "🚐", "🚚", "🚛", "🚜", "🛴", "🚲", "🛵", "🏍", "🚨", "🚔", "🚍", "🚘", "🚖", "🚡", "🚠", "🚟", "🚃", "🚋", "🚞", "🚝", "🚄", "🚅", "🚈", "🚂", "🚆", "🚇", "🚊", "🚉", "🚁", "🛩", "✈️", "🛫", "🛬", "🚀", "🛰", "💺", "🛶", "⛵️", "🛥", "🚤", "🛳", "⛴", "🚢", "⚓️", "🚧", "⛽️", "🚏", "🚦", "🚥", "🗺", "🗿", "🗽", "⛲️", "🗼", "🏰", "🏯", "🏟", "🎡", "🎢", "🎠", "⛱", "🏖", "🏝", "⛰", "🏔", "🗻", "🌋", "🏜", "🏕", "⛺️", "🛤", "🛣", "🏗", "🏭", "🏠", "🏡", "🏘", "🏚", "🏢", "🏬", "🏣", "🏤", "🏥", "🏦", "🏨", "🏪", "🏫", "🏩", "💒", "🏛", "⛪️", "🕌", "🕍", "🕋", "⛩", "🗾", "🎑", "🏞", "🌅", "🌄", "🌠", "🎇", "🎆", "🌇", "🌆", "🏙", "🌃", "🌌", "🌉", "🌁", "🎭", "🎨"));
	//private static final List<String> OBJECTS_EMOJIS = new ArrayList<String>(Arrays.asList("🆓", "📗", "📕", "⌚️", "📱", "📲", "💻", "⌨️", "🖥", "🖨", "🖱", "🖲", "🕹", "🗜", "💽", "💾", "💿", "📀", "📼", "📷", "📸", "📹", "🎥", "📽", "🎞", "📞", "☎️", "📟", "📠", "📺", "📻", "🎙", "🎚", "🎛", "⏱", "⏲", "⏰", "🕰", "⌛️", "⏳", "📡", "🔋", "🔌", "💡", "🔦", "🕯", "🗑", "🛢", "💸", "💵", "💴", "💶", "💷", "💰", "💳", "💎", "⚖️", "🔧", "🔨", "⚒", "⛏", "⚙️", "💣", "🔪", "🗡", "⚔️", "🛡", "🚬", "⚰️", "⚱️", "🏺", "🔮", "📿", "💈", "⚗️", "🔭", "🔬", "🕳", "💊", "💉", "🌡", "🚽", "🚰", "🚿", "🛁", "🛀", "🛎", "🔑", "🗝", "🚪", "🛋", "🛏", "🖼", "🛍", "🛒", "🎁", "🎈", "🎏", "🎀", "🎊", "🎉", "🎎", "🏮", "🎐", "✉️", "📪", "📮", "📯", "📜", "📃", "📄", "📑", "📊", "📈", "📉", "🗒", "🗓", "📆", "📅", "📇", "🗃", "🗳", "🗄", "📋", "🗞", "📰", "📘", "📚", "📖", "🔖", "🔗", "📎", "📐", "📏", "📍", "📌", "🖊", "🖌", "🖍", "📝", "✏️", "🔍", "🔓"));
	//private static final List<String> SYMBOLS_FLAGS_EMOJIS = new ArrayList<String>(Arrays.asList("❤️", "💛", "💚", "💙", "💜", "🖤", "💔", "❣️", "💕", "💞", "💓", "💗", "💖", "💘", "💝", "💟", "☮️", "✝️", "☪️", "🕉", "☸️", "✡️", "🔯", "🕎", "☯️", "☦️", "🛐", "⛎", "♈️", "♉️", "♊️", "♋️", "♌️", "♍️", "♎️", "♏️", "♐️", "♑️", "♒️", "♓️", "🆔", "⚛️", "🉑", "☢️", "☣️", "📴", "📳", "🈶", "🈚", "🈸", "🈺", "🈷", "✴️", "🆚", "💮", "🉐", "㊙️", "㊗️", "🈴", "🈵", "🈹", "🈲", "❌", "⭕️", "🛑", "⛔️", "📛", "🚫", "💯", "💢", "♨️", "🚷", "🚯", "🚳", "🚱", "🔞", "📵", "🚭", "❕", "❔", "‼️", "⁉️", "🔅", "🔆", "〽️", "⚠️", "🚸", "🔱", "⚜️", "🔰", "♻️", "✅", "🈯", "💹", "❇️", "✳️", "❎", "🌐", "💠", "Ⓜ️", "🌀", "💤", "🚺", "🚼", "🎵", "🎶", "➕", "➖", "➗", "✖️", "💲", "💱", "™️", "©️", "®️", "〰️", "➰", "➿", "🔚", "🔙", "🔛", "🔝", "✔️", "☑️", "🔈", "🔇", "🔉", "🔊", "🔔", "🔕", "📣", "📢", "🗨", "💬", "💭", "🗯", "♠️", "♣️", "♥️", "♦️", "🃏", "🎴", "🀄"));

	public static void main ( String[] args )
	{
		staticFiles.location("/public/SimpleFrontend");
		get("/hello", (req, res) -> "Hello World");
		System.out.println("This is a test");
		setup_routes();
	}

	private static void setup_routes()
	{
		options(
				"/*",
				(request, response) -> {
					String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
					if (accessControlRequestHeaders != null) {
						//            response.header("Access-Control-Allow-Headers",
						// accessControlRequestHeaders);
						response.header("Access-Control-Allow-Headers", "*");
					}

					String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
					if (accessControlRequestMethod != null) {
						response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
						response.header("Access-Control-Allow-Methods", "*");
					}

					return "OK";
				});

		before(
				(req, res) -> {
					res.header("Access-Control-Allow-Origin", "*");
					res.header("Access-Control-Allow-Headers", "*");
					res.type("application/json");
				});
		Gson gson = new Gson();

		ChannelMapper chatMapper = new ChannelMapper("jdbc:mysql://localhost:3306/emojiverse?serverTimezone=EST");
		//UserUtil userUtil = new UserUtil();
		UserMapper usermapper = new UserMapper("jdbc:mysql://localhost:3306/emojiverse?serverTimezone=EST");
		EmojiMapper emojimapper = new EmojiMapper("jdbc:mysql://localhost:3306/emojiverse?serverTimezone=EST");
		//User new_user = new User("fgeyfg","gygdygw","wbdwgf",2,"egfiegfyiew","efigeyfewg",1);
		//usermapper.registerUser(new_user);
		//emojimapper.populateEmojiStore(PEOPLE_EMOJIS,-1,"PEOPLE_EMOJIS");
		//emojimapper.populateEmojiStore(ANIMALS_NATURE_EMOJIS,-1,"ANIMALS_NATURE_EMOJIS");
		//emojimapper.populateEmojiStore(FOOD_SPORTS_EMOJIS ,-1,"FOOD_SPORTS_EMOJIS ");
		//emojimapper.populateEmojiStore(OBJECTS_EMOJIS,-1,"OBJECTS_EMOJIS");
		//emojimapper.populateEmojiStore(TRAVEL_PLACES_EMOJIS,-1,"TRAVEL_PLACES_EMOJIS");
		//emojimapper.populateEmojiStore(SYMBOLS_FLAGS_EMOJIS,-1,"SYMBOLS_FLAGS_EMOJIS");



		get("/ping", (req, res) -> "OK");
		get("/hello", (req, res) -> "Hello World");

		post("/login", (req,res) -> {
			System.out.println(req.body());
			JSONObject json = new JSONObject(req.body());
			System.out.println(json.getString("username"));
			Map<String, Object> map = new HashMap<>();
			String username = json.getString("username");
			String password = json.getString("user_password");
			User user = new User(username,password);

			boolean result = usermapper.authUser(username,password);
			System.out.println(result);
			if (result==true) {
				map.put("authorized",true);
				req.session().attribute(USER_SESSION_ID);


			} else {

				map.put("authorized", false);
			}
			map.put("username", username);
			return gson.toJson(map);
		});

		get("/signup", (req, res) -> "Hypothetical signup page");
		post("/signup", (req, res) -> {

			JSONObject json = new JSONObject(req.body());
			String username = json.getString("username");
			String password = json.getString("user_password");
			String email = json.getString("email");
			User user = new User(username,password,email);
			System.out.println(username);
			System.out.println(password);
			System.out.println(email);
			boolean error = usermapper.isDuplicate(user);
			Map<String, Object> map = new HashMap<>();
			System.out.println(error);
			if (error==false) {
				System.out.println("hello");
				usermapper.registerUser(user);
				map.put("authorized",true);
				//what does that mean?

				}
			else
				{
					map.put("authorized",false);
					halt(404);
				}

				return gson.toJson(map);
			//return req; //this isn't a possible condition, refactor code?
		});
		
		post("/new", (req, res) -> {


			JSONObject json = new JSONObject(req.body());

			String username = json.getString("username");
			System.out.println(username + " looking to create a new chat");
			List<String> unameList = Arrays.asList(json.getString("users").split(","));
			String chat_name = json.getString("chatName");
			System.out.println(unameList);

			int id = chatMapper.getNextChatId();
			System.out.println(id);
			Channel channel = new Channel(id,unameList,chat_name);

			//			Map<String, Object> map = new HashMap<>();
			// 			I think this line is not needed? --Bonny

			chatMapper.addChannel(channel,username);
			return chatMapper.getChannelList(username);
		});
		
		post("/chats", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String username = json.getString("username");
			System.out.println("Getting chat list for " + username);
			//return authUser.getChannelIDList(); //this really ought to be json
			return chatMapper.getChannelList(username);
		});
		
		get("/channelinfo", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.body());
				int channel_id = json.getInt("channelID");
				// shoddy parsing 
				return chatMapper.getChannelByID(channel_id); // should serialize object to json and return
			} catch (Exception e) {
				halt(501);
				return null;
			}
		});
		// no authentication here lol
		post("/getMessages", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String chat_name = json.getString("chatName");
			return chatMapper.getMessages(chat_name);});

		post("/addMessage", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String chat_name = json.getString("chatName");
			String username = json.getString("username");
			String message = json.getString("message");
			System.out.println(message);
			System.out.println(username);
			System.out.println(chat_name);
			Date date = new Date();
			String date_time = date.toString();
			chatMapper.addMessage(chat_name,message,username,date_time);

			return chatMapper.getMessages(chat_name);
		});

		post("/getFriend", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String username = json.getString("username");
			return usermapper.getFriendList(username);
		});


		post("/addFriend", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String friend_username = json.getString("friend_username");
			String username = json.getString("username");
			usermapper.addFriend(username, friend_username);
			return usermapper.getFriendList(username);
		});

		post("/removeFriend", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String friend_username = json.getString("friend_username");
			String username = json.getString("username");
			usermapper.removeFriend(username, friend_username);
			return usermapper.getFriendList(username);
		});

		post("/removeChat",(req,res) ->{
			JSONObject json = new JSONObject(req.body());

			System.out.println(json.toString());
			String username = json.getString("username");
			String chat_name = json.getString("chatName");

			chatMapper.removeChannel(chat_name);


			return chatMapper.getChannelList(username);
		});

		post("/getProfileImg", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String username = json.getString("username");
			return usermapper.getProfileImg(username);
		});

		post("/populateEmojiStore", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String username = json.getString("username");
			List<String> emojis = Arrays.asList(json.getString("PEOPLE_EMOJIS").split(","));
			int price = json.getInt("Price");
			String category = json.getString("Category");
			emojimapper.populateEmojiStore(emojis,price,category);
			return emojimapper.getEmojisFromStore(username);
		});

		post("/getEmojisFromEmojiStore", (req,res)->{
			JSONObject json = new JSONObject(req.body());
			String username = json.getString("username");
			return emojimapper.getEmojisFromStore(username);
		});

		post("/getEmojiPrice", (req,res)->{
			JSONObject json = new JSONObject(req.body());
			String emoji = json.getString("emoji");
			System.out.println(emoji);
			return emojimapper.getEmojiPrice(emoji);
		});
	}
}

//Testing
//eyrgfuergfieyr
