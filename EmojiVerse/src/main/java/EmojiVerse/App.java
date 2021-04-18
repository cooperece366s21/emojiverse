package EmojiVerse;

import static spark.Spark.*;

import EmojiVerse.chatChannel.Channel;
import EmojiVerse.dao.ChannelDummy;
import EmojiVerse.dao.ChatDao;
import EmojiVerse.dao.UserDao;
import EmojiVerse.dao.UserDummy;
import EmojiVerse.user.LoginResult;
import EmojiVerse.user.User;
import EmojiVerse.user.UserMapper;
import EmojiVerse.user.UserUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
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
		UserDao userDao = new UserDummy();
		ChatDao chatDao = new ChannelDummy();
		//UserUtil userUtil = new UserUtil();
		
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

			LoginResult result = userDao.authUser(user);
			if (result.getUser() != null) {
				map.put("authorized","true");
				req.session().attribute(USER_SESSION_ID, result.getUser());
				res.redirect("/");
				halt();
			} else {

				map.put("authorized", "false");
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
			UserMapper usermapper = new UserMapper("jdbc:mysql://localhost:3306/emojiverse");
			System.out.println(usermapper.isDuplicate(user));
			String error = user.validate();
			if (error.isEmpty()) {
				User existingUser = userDao.getUserByUsername(user.getUsername());
				if (existingUser == null) {
					userDao.registerUser(user);
					res.redirect("/login"); //what does that mean?
					halt();
				} else {
					return "Username is already taken";
				}
			} else {
				Map<String, Object> map = new HashMap<>();
				map.put("error", error);
				map.put("username", user.getUsername());
				map.put("email", user.getEmail());
				return(map);
			}
			return null;
			//return req; //this isn't a possible condition, refactor code?
		});
		
		post("/new", (req, res) -> {
			User authUser = req.session().attribute(USER_SESSION_ID);
			if (authUser == null) {
				return "Unauthenticated";
			}
			System.out.println(authUser.getUsername() + " looking to create a new chat");
			
			MultiMap<String> params = new MultiMap<String>();
			UrlEncoded.decodeTo(req.body(), params, "UTF-8"); //decode url to usernames string 
			// should be replaced with json I guess
			// this is all parsing code to be replaced 
			List<String> unameList = Arrays.asList(params.getString("users").split(" "));
			System.out.println(unameList);
			List<User> userList = new ArrayList<User>();
			for (String uname : unameList) {
				User newUser = userDao.getUserByUsername(uname);
				if (newUser != null) {
					userList.add(newUser);
				}
			}
			//add creator user implicitly 
			// this will choke on my user accessing code.
			authUser.setPermissionLevel(User.OWNER);
			userList.add(authUser);

			Channel newChannel = chatDao.createChannel(userList);
			for (User u : userList) {
				u.addChannel(newChannel.getId());
			}
			res.redirect("/");
			halt();
			return null;
		});
		
		get("/chats", (req, res) -> {
			User authUser = req.session().attribute(USER_SESSION_ID);
			if (authUser == null) {
				return "Unauthenticated";
			}
			System.out.println("Getting chat list for " + authUser.getUsername());
			//return authUser.getChannelIDList(); //this really ought to be json
			return userDao.getChannelList(authUser); 
		});
		
		get("/channelinfo", (req, res) -> {
			try {
				MultiMap<String> params = new MultiMap<String>();
				UrlEncoded.decodeTo(req.body(), params, "UTF-8");
				Channel channel = chatDao.getChannelByID(params.get("channelID").get(0));
				// shoddy parsing 
				return channel.getChannelName(); // should serialize object to json and return 
			} catch (Exception e) {
				halt(501);
				return null;
			}
		});
	}
}
