package EmojiVerse;
import static spark.Spark.*;

import EmojiVerse.Handler.Handler;
import EmojiVerse.chatChannel.ChannelStore;
import EmojiVerse.emoji.Emoji;
import EmojiVerse.emoji.EmojiMessageStore;
import EmojiVerse.emoji.EmojiStore;
import EmojiVerse.login.LoginController;
import EmojiVerse.user.User;
import EmojiVerse.user.UserDummy;
import EmojiVerse.user.UserUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.norberg.automatter.gson.AutoMatterTypeAdapterFactory;
import spark.*;
import EmojiVerse.messaging.ChatWebSocketHandler;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.websocket.api.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import EmojiVerse.friends.friend;

/**
 * Hello world!
 *
 */
public class App 
{
	public static UserDummy userDummy = new UserDummy();
	public static EmojiStore emojiStore = new EmojiStore();
	public static EmojiMessageStore emojiMessageStore = new EmojiMessageStore();
	public static ChannelStore channelStore= new ChannelStore();

	static Map<Session, String> userUsernameMap = new ConcurrentHashMap<>();
	static int nextUserNumber = 1; //Assign to username for next connecting user
	public static void main( String[] args )
	{

		Gson gson =
				new GsonBuilder().registerTypeAdapterFactory(new AutoMatterTypeAdapterFactory()).create();

		staticFiles.location("/public"); //index.html is served at localhost:4567 (default port)
		staticFiles.expireTime(600000);
		webSocket("/chat", ChatWebSocketHandler.class);
		init();

		get("/ping", (req, res) -> "OK");
		get("/hello", (req, res) -> "Hello World");

		get("/login", 		LoginController.serveLoginPage);
		post("/login",      LoginController.handleLoginPost);
		get("/testlogin", (req, res) -> {
			if (req.session().attribute("currentUser") != null) {
				System.out.println("User session active");
				return "User session active";
			}
			return "User session inactive";
		});
		post("/logout",     LoginController.handleLogoutPost);

		get("/signup", (req, res) -> "Hypothetical signup page");
		post("/signup", (req, res) -> {
			User user = new User();
			try {
				MultiMap<String> params = new MultiMap<String>();
				UrlEncoded.decodeTo(req.body(), params, "UTF-8");
				BeanUtils.populate(user, params);
			} catch (Exception e) {
				halt(501);
				return null;
			}
			String error = UserUtil.validateUser(user);
			
			if (error.isEmpty()) {
				// User existingUser = service.getUserbyUsername(user.getUsername());
				// getUserByUsername from dao
				User existingUser = null;
				if (existingUser == null) {
					// register user to dao
					res.redirect("/login?r=1"); //what does that mean?
					halt();
				} else {
					error = "Username is already taken";
				}
			} else {
				Map<String, Object> map = new HashMap<>();
				map.put("error", error);
				map.put("username", user.getUsername());
				map.put("email", user.getEmail());
				return(map);
			}
		});

		get("/users", printAllUsers); //leet haX0r debug


		//username is username of the current user, friend_username is the username of the friend to block or add, and id is the id of the user in the friends list or blocked list.

		get("/addFriends/:id/:username/:friend_username", (req,res) -> friend.addToFriendsList(req,res));
		get("/getFriendsList/:username", (req,res) -> friend.getFriendsList(req,res));

		get("/getFriendsPhotos/:username", (req,res) -> friend.getFriendsImages(req,res));

		get("/blockFriends/:id/:username/:friend_username", (req,res) -> friend.BlockFriend(req,res));
		get("/getBlockedList/:username", (req,res) -> friend.getBlockedList(req,res));

		//        Test for Messaging Channels
		Handler handler = new Handler(channelStore, emojiStore);

		get("/channel/:id", (req, res) ->  handler.getChannel(req));
		get("/createChannel/:id/:user1/:user2", (req, res) -> handler.createChannel(req));
		get("/channelList",(req, res) -> handler.getAllChannels());
		get("/sendMessage/:channelID/:userID/:emoji",(req, res) -> handler.addMessage(req));

		//        Test for emojis
		get("/emojis", (req, res) ->  handler.showEmojis());
		get("/emoji/:id", (req, res) ->  handler.getEmoji(req));

	};
	public static Route printAllUsers = (Request request, Response response) -> {
		String out = "";
		for (User u : userDummy.users) {
			out = out + u.getUsername() + ' ' + u.getPassword() + '\n';
		}
		return out;
	};
	//derby db
}
