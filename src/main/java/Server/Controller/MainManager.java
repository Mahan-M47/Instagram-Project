package Server.Controller;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainManager
{
    public static List<ActiveClient> activeClientList = new ArrayList<>();

    public static void addNewClient(NetworkManager networkManager) {
        ActiveClient client = new ActiveClient(networkManager);
        activeClientList.add(client);
    }

    public synchronized static void removeClient(NetworkManager networkManager) {
        activeClientList.removeIf(client -> client.getNetworkManager().equals(networkManager));
    }


    public synchronized static Response process(Request req, AtomicBoolean state) {
        {
            switch ( req.getTitle() ) {
                case "signup":
                    return null;
                case "login":
                    return null;
                case "terminate":
                    state.set(false);
            }

            return null;

            //each Request.title corresponds to a case in the switch statement which summons a method from mongo
            //to process the Request. These methods should all return a Response object which is sent back to the client.
        }
    }

}
