package Client.Controller;

import Client.Model.Chat;
import Client.Model.Message;
import Client.Model.Post;
import Client.Model.User;

public class Data
{
    public String clientUsername;

    //used by all Requests and Responses with just ONE String of data other than the client's username.
    public static class Basic extends Data
    {
        public String dataString;
        public Basic(String clientUsername, String dataString) {
            this.clientUsername = clientUsername;
            this.dataString = dataString;
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

    public static class PostData extends Data
    {
        public Post post;
        public PostData(String clientUsername, Post post) {
            this.clientUsername = clientUsername;
            this.post = post;
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

    //only used by the server so there's no need to add the client's username.
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