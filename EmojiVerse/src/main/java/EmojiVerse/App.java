package EmojiVerse;

import static spark.Spark.*;

import EmojiVerse.dao.UserDao;
import EmojiVerse.dao.UserDummy;
import EmojiVerse.user.LoginResult;
import EmojiVerse.user.User;
import EmojiVerse.user.UserUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.UrlEncoded;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final String USER_SESSION_ID = "user";
	
	public static void main ( String[] args ) 
	{
		get("/hello", (req, res) -> "Hello World");
		System.out.println("This is a test");
		setup_routes();
	}
	
	private static void setup_routes()
	{
		UserDao userDao = new UserDummy();
		UserUtil userUtil = new UserUtil();
		
		get("/ping", (req, res) -> "OK");
		get("/hello", (req, res) -> "Hello World");
		
		get("/login", (req,res) -> "Hypothetical login page");
		post("/login", (req,res) -> {
			Map<String, Object> map = new HashMap<>();
			User user = new User();
			try {
				MultiMap<String> params = new MultiMap<String>();
				UrlEncoded.decodeTo(req.body(), params, "UTF-8");
				BeanUtils.populate(user, params);
				System.out.println(user.getUsername());
			} catch (Exception e) {
				halt(501);
				return null;
			}
			LoginResult result = userDao.authUser(user);
			if (result.getUser() != null) {
				req.session().attribute(USER_SESSION_ID, result.getUser());
				res.redirect("/");
				halt();
			} else {
				map.put("error:", result.getError());
			}
			map.put("username", user.getUsername());
			return map;
		});

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
	}
}
