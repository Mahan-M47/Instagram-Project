package Server.Controller;

import Server.Utils;
import java.util.List;
import Server.Model.User;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainManager
{
    public synchronized static Response process(Request req, AtomicBoolean state) {
        {
            Data dat = req.getData();
            User user = null;
            boolean flag;

            switch ( req.getTitle() )
            {
                case Utils.REQ.SIGNUP:
                    boolean userExists = DatabaseManager.checkIfUserExists(Utils.DB_LOGIN, dat.clientUsername);

                    if (!userExists) {
                        user = new User(dat.clientUsername, dat.dataString );
                        DatabaseManager.adduser(user);
                        flag = true;
                    }
                    else flag = false;
                    return new Response(Utils.REQ.SIGNUP, new Data(dat.clientUsername, flag) );


                case Utils.REQ.LOGIN:
                    user = new User(dat.clientUsername, dat.dataString );
                    flag = DatabaseManager.checkLogin(user);
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
                    DatabaseManager.follow(dat.dataString, dat.clientUsername);
                    user = DatabaseManager.assembleUser(dat.dataString);
                    return new Response(Utils.REQ.FOLLOW, new Data(user));


                case Utils.REQ.BIO:
                    DatabaseManager.setBio(dat.clientUsername, dat.dataString);
                    user = DatabaseManager.assembleUser(dat.clientUsername);
                    return new Response(Utils.REQ.BIO, new Data(user) );


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


    //methods for adding and removing ActiveClients
    public static List<ActiveClient> activeClientList = new ArrayList<>();

    public static void addClient(ActiveClient client) {
        activeClientList.add(client);
        System.out.println("User \"" + client.getUsername() + "\" Logged In.");
    }

    public static void removeClient(String username)
    {
        for (ActiveClient client : activeClientList)
        {
            if ( client.getUsername().equals(username) ) {
                System.out.println("User \"" + client.getUsername() + "\" Logged Out.");
                activeClientList.remove(client);
                break;
            }
        }
    }

    public static void removeClient(BlockingQueue<Response> queue)
    {
        for (ActiveClient client : activeClientList)
        {
            if ( client.getQueue().equals(queue) ) {
                System.out.println("User \"" + client.getUsername() + "\" Logged Out.");
                activeClientList.remove(client);
                break;
            }
        }
    }

}
