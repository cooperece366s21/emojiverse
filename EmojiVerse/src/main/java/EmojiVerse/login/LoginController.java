	package EmojiVerse.login;

import static spark.Spark.halt;

import EmojiVerse.user.UserUtil;
import spark.*;

public class LoginController {
    public static Route serveLoginPage = (Request request, Response response) -> {
    	return "This is a hypothetical login page";
    };
    
    public static Route handleLoginPost = (Request request, Response response) -> {
    	String user = request.queryParams("username");
    	String psw = request.queryParams("password");
    	if (UserUtil.auth(user, psw)) {
    		request.session().attribute("currentUser", user);
    		System.out.println("Authentication successful for " + user);
    		// redirect to user homepage?
    		response.redirect("/"); 
    		return "Authentication Success";
    	}
    	System.out.println("Authentication failed for " + user);
    	halt(401, "Authentication failed");
    	return null;
    };
    
    public static Route handleLogoutPost = (Request request, Response response) -> {
    	request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true); //why is this?
        response.redirect("/");
        return null;
    };
}