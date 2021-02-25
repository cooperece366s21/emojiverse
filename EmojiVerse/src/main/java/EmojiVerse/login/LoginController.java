package EmojiVerse.login;

import spark.*;

public class LoginController {
    public static Route serveLoginPage = (Request request, Response response) -> {
    	return "This is a hypothetical login page";
    };
    
    public static Route handleLoginPost = (Request request, Response response) -> {
    	String user = request.queryParams("username");
    	String psw = request.queryParams("password");
    	// do authentication function
    	return "Authentication failed for " + user;
    };
    
    public static Route handleLogoutPost = (Request request, Response response) -> {
    	request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect("/login");
        return null;
    };
}