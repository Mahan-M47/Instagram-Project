package Server.Controller;

import Server.Model.User;
import Server.Utils;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainManager
{
    public static List<ActiveClient> activeClientList = new ArrayList<>();

    public static void addNewClient(ActiveClient client) {
        activeClientList.add(client);
    }

    public synchronized static void removeClient(NetworkManager networkManager) {
        activeClientList.removeIf(client -> client.getNetworkManager().equals(networkManager));
    }


    public synchronized static Response process(Request req, AtomicBoolean state) {
        {
            switch ( req.getTitle() ) {
                case "signup":
                    Data.Basic dat = (Data.Basic)req.getData();
                    User user = new User(req.getData().clientUsername, dat.dataString );
                    DatabaseManager.adduser(user);
                    // set flag with  DatabaseManager.checkuser( Utils.LOGIN ,req.getData().clientUsername)
                    return null ;
                case "login":
                    Data.Basic dat2 = (Data.Basic)req.getData();
                    User login2 = new User(req.getData().clientUsername, dat2.dataString );
                    // set flag with DatabaseManager.checklogin(login2)
                    return null ;
                case "terminate":
                    state.set(false);
            }

            return null;

            //each Request.title corresponds to a case in the switch statement which summons a method from mongo
            //to process the Request. These methods should all return a Response object which is sent back to the client.
        }
    }

}
