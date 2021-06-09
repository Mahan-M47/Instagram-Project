package Server.Controller;

import Server.Utils;
import java.util.List;
import Server.Model.User;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainManager
{
    public static List<ActiveClient> activeClientList = new ArrayList<>();

    public synchronized static Response process(Request req, AtomicBoolean state) {
        {
            Data dat = req.getData();
            User user = null;
            boolean flag;

            switch ( req.getTitle() )
            {
                case "signup":
                    boolean userExists = DatabaseManager.checkIfUserExists(Utils.LOGIN , dat.clientUsername);

                    if (!userExists) {
                        user = new User(dat.clientUsername, dat.dataString );
                        DatabaseManager.adduser(user);
                        flag = true;
                    }
                    else flag = false;
                    return new Response("signup", new Data.BooleanData(dat.clientUsername, flag) );


                case "login":
                    user = new User(dat.clientUsername, dat.dataString );
                    flag = DatabaseManager.checkLogin(user);
                    return new Response("login", new Data.BooleanData(dat.clientUsername, flag) );


                case "logout":
                    removeClient(dat.clientUsername);
                    System.out.println("User \"" + dat.clientUsername + "\" Has Logged Out.");
                    break;


                case "terminate":
                    state.set(false);
                    break;
            }

            return null;

            //each Request.title corresponds to a case in the switch statement which summons a method from mongo
            //to process the Request. These methods should all return a Response object which is sent back to the client.
        }
    }


    //methods for adding and removing ActiveClients
    public static void addClient(ActiveClient client) {
        activeClientList.add(client);
    }

    public static void removeClient(String username)
    {
        for (ActiveClient client : activeClientList)
        {
            if ( client.getUsername().equals(username) ) {
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
                activeClientList.remove(client);
                break;
            }
        }
    }

}
