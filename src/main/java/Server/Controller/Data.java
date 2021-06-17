package Server.Controller;

import Server.Model.Chat;
import Server.Model.Message;
import Server.Model.Post;
import Server.Model.User;
import java.util.ArrayList;
import java.util.List;

public class Data
{
    public String clientUsername = null;
    public List<String> usernameList = null;
    public String dataString = null;
    public String postID = null;
    public String text = null;
    public User user = null;
    public Post post = null;
    public List<Post> posts = null;
    public Message message = null;
    public Chat chat = null;
    public boolean flag;

    public Data() {}

    public Data(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    //used by all Requests and Responses with just ONE String of data other than the client's username. (e.g. login)
    public Data(String clientUsername, String dataString) {
        this.clientUsername = clientUsername;
        this.dataString = dataString;
    }

    //only used by the server so there's no need to add the client's username. (e.g. returning search results)
    public Data(ArrayList<String> usernameList) {
        for (String username : usernameList) {
            this.usernameList.add(username);
        }
    }

    public Data(String clientUsername, String postID, String text) {
        this.clientUsername = clientUsername;
        this.postID = postID;
        this.text = text;
    }

    //only used by the server so there's no need to add the client's username. (e.g. showing a user's profile)
    public Data(User user) {
        this.user = user;
    }

    public Data(String clientUsername, Post post) {
        this.clientUsername = clientUsername;
        this.post = post;
    }

    //only used by the server so there's no need to add the client's username. (e.g. posts in the Timeline)
    public Data(List<Post> posts) {
        for (Post post: posts) {
            this.posts.add(post);
        }
    }

    public Data(String clientUsername, Chat chat) {
        this.clientUsername = clientUsername;
        this.chat = chat;
    }

    public Data(String clientUsername, Message message) {
        this.clientUsername = clientUsername;
        this.message = message;
    }

    public Data(boolean flag) {
        this.flag = flag;
    }

    public Data(String clientUsername, boolean flag) {
        this.clientUsername = clientUsername;
        this.flag = flag;
    }

}
