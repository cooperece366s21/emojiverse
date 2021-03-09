package EmojiVerse.friends;

import java.util.*;

import EmojiVerse.user.User;
import EmojiVerse.user.UserDummy;
import spark.*;

import static EmojiVerse.App.userDummy;
import EmojiVerse.user.*;

public class friend implements friendUtils{


    public static String addToFriendsList(Request request, Response response) {
        System.out.println(request.params(":id"));
        int id = Integer.parseInt(request.params(":id"));
        String username = request.params(":username");
        String friend_username = request.params(":friend_username");
        User friend = userDummy.getUserByUsername(friend_username);
        User user = userDummy.getUserByUsername(username);
        if(userList.contains(user) && userList.contains(friend))
        {
            user.addFriendtoFriendsList(id, friend);
            return "Authorized";
        }
        return "Username or friend does not exist.";
    }


    public static HashMap<Integer, String> getFriendsList(Request request, Response response) {
        HashMap<Integer,String> userList = new HashMap<Integer,String>();
        String username = request.params(":username");
        User user = userDummy.getUserByUsername(username);
        HashMap<Integer,User> friendsList = user.getFriendsList();
        for(int i = 0; i < friendsList.size();i++)
        {
            Object key = friendsList.keySet().toArray()[i];
            System.out.println((Integer) key);
            userList.put((Integer) key, friendsList.get(key).getUsername());
        }
        return userList;
    }


    public static HashMap<String, String> getFriendsImages(Request request, Response response) {
        HashMap<String,String> userList = new HashMap<String,String>();
        String username = request.params(":username");
        User user = userDummy.getUserByUsername(username);
        HashMap<Integer,User> friendsList = user.getFriendsList();
        for(int i = 0; i < friendsList.size();i++)
        {
            Object key = friendsList.keySet().toArray()[i];
            userList.put(friendsList.get(key).getUsername(),friendsList.get(key).getPicture_link());
        }
        return userList;
    }

    public static String BlockFriend(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        String username = request.params(":username");
        String friend_username = request.params(":friend_username");
        User friend = userDummy.getUserByUsername(friend_username);
        User user = userDummy.getUserByUsername(username);
        if(userList.contains(user) && userList.contains(friend))
        {
            user.blockFriendtoBlockedList(id, friend);
            return "Authorized";
        }
        return "Username or friend does not exist.";

    }

    public static HashMap<Integer, String> getBlockedList(Request request, Response response) {
        HashMap<Integer,String> userList = new HashMap<Integer,String>();
        String username = request.params(":username");
        User user = userDummy.getUserByUsername(username);
        HashMap<Integer,User> blockedList = user.getBlockedList();
        for(int i = 0; i < blockedList.size();i++)
        {
            Object key = blockedList.keySet().toArray()[i];
            userList.put((Integer) key, blockedList.get(key).getUsername());
        }
        return userList;
    }
}
