package EmojiVerse.login;

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
    		//return "Authentication successful for " + user;
    		response.redirect(""); 
    	}
    	return "Authentication failed for " + user;
    	/*
    	System.out.println("User " + user + " tried to log in with password " + psw);
    	if (!UserUtil.auth(user, psw)) {
    		System.out.println("Authentication failed");
    		return "Authentication failed for " + user;
    	} else {
    		System.out.println("Authentication success");
    		request.session().attribute("currentUser", user);
    		response.redirect(request.queryParams("loginRedirect")); //wtf is this
    		return "Authentication successful, redirecting.";
    	}*/
    };
    
    public static Route handleLogoutPost = (Request request, Response response) -> {
    	request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect("/login");
        return null;
    };
}