package EmojiVerse;

import static spark.Spark.*;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.chatChannel.ChannelMapper;

import EmojiVerse.user.User;
import EmojiVerse.user.UserMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		ChannelMapper chatDao = new ChannelMapper("jdbc:mysql://localhost:3306/emojiverse");
		//UserUtil userUtil = new UserUtil();
		UserMapper usermapper = new UserMapper("jdbc:mysql://localhost:3306/emojiverse");
		//User new_user = new User("fgeyfg","gygdygw","wbdwgf",2,"egfiegfyiew","efigeyfewg",1);
		//usermapper.registerUser(new_user);
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

			boolean result = usermapper.authUser(username);
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

			int id = chatDao.getNextChatId();
			System.out.println(id);
			Channel channel = new Channel(id,unameList,chat_name);
			Map<String, Object> map = new HashMap<>();
			usermapper.addChannel(channel,username);
			return usermapper.getChannelList(username);
		});
		
		post("/chats", (req, res) -> {
			JSONObject json = new JSONObject(req.body());
			String username = json.getString("username");
			System.out.println("Getting chat list for " + username);
			//return authUser.getChannelIDList(); //this really ought to be json
			return usermapper.getChannelList(username);
		});
		
		get("/channelinfo", (req, res) -> {
			try {
				JSONObject json = new JSONObject(req.body());
				int channel_id = json.getInt("channelID");
				// shoddy parsing 
				return chatDao.getChannelByID(channel_id); // should serialize object to json and return
			} catch (Exception e) {
				halt(501);
				return null;
			}
		});
	}
}
