package Server.Controller;

import Server.Model.PersonalChat;
import Server.Model.Post;
import Server.Model.User;
import Server.Utils;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainManager
{
    public synchronized static Response process(Request req, AtomicBoolean state) {
        {
            Data dat = req.getData();
            User user = null;
            Post post = null;
            boolean flag;

            switch ( req.getTitle() )
            {
                case Utils.REQ.SIGNUP:
                    boolean userExists = DatabaseManager.checkIfUserExists(Utils.DB_LOGIN, dat.clientUsername);

                    if (!userExists) {
                        user = new User(dat.clientUsername, dat.dataString);
                        DatabaseManager.adduser(user);
                        flag = true;
                    }
                    else flag = false;
                    return new Response(Utils.REQ.SIGNUP, new Data(dat.clientUsername, flag) );


                case Utils.REQ.LOGIN:
                    flag = DatabaseManager.checkLogin(dat.clientUsername, dat.dataString);
                    return new Response(Utils.REQ.LOGIN, new Data(dat.clientUsername, flag) );


                case Utils.REQ.SEARCH:
                    ArrayList<String> results = DatabaseManager.searchUser( dat.dataString );
                    return new Response(Utils.REQ.SEARCH, new Data(results) );


                case Utils.REQ.PROFILE:
                    user = DatabaseManager.assembleUser(dat.clientUsername);
                    return new Response(Utils.REQ.PROFILE, new Data(user) );


                case Utils.REQ.MY_PROFILE:
                    user = DatabaseManager.assembleUser(dat.clientUsername);
                    return new Response(Utils.REQ.MY_PROFILE, new Data(user) );


                case Utils.REQ.FOLLOW:
                    DatabaseManager.follow(dat.clientUsername, dat.dataString);
                    NotificationManager.followNotification(dat.clientUsername, dat.dataString);
                    user = DatabaseManager.assembleUser(dat.dataString);
                    return new Response(Utils.REQ.FOLLOW, new Data(user));


                case Utils.REQ.UNFOLLOW:
                    DatabaseManager.unfollow(dat.clientUsername, dat.dataString);
                    user = DatabaseManager.assembleUser(dat.dataString);
                    return new Response(Utils.REQ.FOLLOW, new Data(user));


                case Utils.REQ.BIO:
                    DatabaseManager.setBio(dat.user);
                    if (dat.user.getProfilePicture() != null) {
                        DatabaseManager.setProfilePicture(dat.user);
                    }
                    break;


                case Utils.REQ.CREATE_POST:
                    DatabaseManager.createPost(dat.post);
                    NotificationManager.postNotification(dat.clientUsername);
                    break;


                case Utils.REQ.LIKE:
                    post = DatabaseManager.like(dat.clientUsername, dat.dataString);
                    NotificationManager.likeNotification(dat.clientUsername, post);
                    break;


                case Utils.REQ.UNLIKE:
                    DatabaseManager.unlike(dat.clientUsername, dat.dataString);
                    break;


                case Utils.REQ.COMMENT:
                    post = DatabaseManager.comment(dat.clientUsername, dat.postID, dat.text);
                    NotificationManager.commentNotification(dat.clientUsername, post);
                    break;


                case Utils.REQ.TIMELINE:
                    ArrayList<Post> posts = DatabaseManager.assembleTimeline(dat.clientUsername);
                    return new Response(Utils.REQ.TIMELINE, new Data(posts));


                case Utils.REQ.PERSONAL_CHAT:
                    String chatID = DatabaseManager.checkIfPersonalChatExists(dat.clientUsername, dat.dataString);
                    PersonalChat chat;

                    if ( chatID == null ) {
                        chat = DatabaseManager.createPersonalChat(dat.clientUsername, dat.dataString);
                    }
                    else chat = DatabaseManager.getPersonalChat(chatID);

                    return new Response(Utils.REQ.PERSONAL_CHAT, new Data(chat));


                case Utils.REQ.GROUP_CHAT:
                    break;


                case Utils.REQ.ALL_CHATS:
                    break;


                case Utils.REQ.LOGOUT:
                    removeClient(dat.clientUsername);
                    break;


                case Utils.REQ.TERMINATE:
                    state.set(false);
                    break;
            }

            return null;

            //each Request.title corresponds to a case in the switch statement which summons a method from mongo
            //to process the Request. These methods should all return a Response object which is sent back to the client.
        }
    }


    //methods for managing Active Clients
    public static List<ActiveClient> activeClients = new ArrayList<>();


    public synchronized static void addClient(ActiveClient client) {
        activeClients.add(client);
        System.out.println("User \"" + client.getUsername() + "\" Logged In.");
    }

    public synchronized static void removeClient(String username)
    {
        for (ActiveClient client : activeClients)
        {
            if ( client.getUsername().equals(username) ) {
                activeClients.remove(client);
                System.out.println("User \"" + client.getUsername() + "\" Logged Out.");
                break;
            }
        }
    }

    public synchronized static void removeClient(BlockingQueue<Response> queue)
    {
        for (ActiveClient client : activeClients)
        {
            if ( client.getQueue().equals(queue) ) {
                activeClients.remove(client);
                System.out.println("User \"" + client.getUsername() + "\" Logged Out.");
                break;
            }
        }
    }

}
