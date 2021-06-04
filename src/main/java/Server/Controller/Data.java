package Server.Controller;

import Server.Model.Chat;
import Server.Model.Message;
import Server.Model.Post;
import Server.Model.User;
import java.util.List;

public class Data
{
    public String clientUsername;

    //used by all Requests and Responses with just ONE String of data other than the client's username. (e.g. login)
    public static class Basic extends Data
    {
        public String dataString;
        public Basic(String clientUsername, String dataString) {
            this.clientUsername = clientUsername;
            this.dataString = dataString;
        }
    }

    //only used by the server so there's no need to add the client's username. (e.g. returning the list of followers)
    public static class UsernameList extends Data
    {
        public List<String> usernames;
        public UsernameList(List<String> usernames) {
            this.usernames.addAll(usernames);
        }
    }

    public static class Comment extends Data
    {
        public String postID;
        public String text;
        public Comment(String clientUsername, String postID, String text) {
            this.clientUsername = clientUsername;
            this.postID = postID;
            this.text = text;
        }
    }

    public static class UserData extends Data
    {
        public User user;
        public UserData(String clientUsername, User user) {
            this.clientUsername = clientUsername;
            this.user = user;
        }
    }

    public static class PostSingle extends Data
    {
        public Post post;
        public PostSingle(String clientUsername, Post post) {
            this.clientUsername = clientUsername;
            this.post = post;
        }
    }

    //only used by the server so there's no need to add the client's username. (e.g. returning all posts made by your following)
    public static class PostList extends Data
    {
        public List<Post> posts;
        public PostList(List<Post> posts) {
            this.posts.addAll(posts);
        }
    }

    public static class ChatData extends Data
    {
        public Chat chat;
        public ChatData(String clientUsername, Chat chat) {
            this.clientUsername = clientUsername;
            this.chat = chat;
        }
    }

    public static class MessageData extends Data
    {
        public Message message;
        public MessageData(String clientUsername, Message message) {
            this.clientUsername = clientUsername;
            this.message = message;
        }
    }

    //only used by the server so there's no need to add the client's username
    public static class BooleanData extends Data
    {
        public boolean flag;
        public BooleanData(boolean flag) {
            this.flag = flag;
        }
    }

    //mostly used by the Heartbeat thread
    public static class Empty extends Data
    {
        public Empty() {}
    }

}
