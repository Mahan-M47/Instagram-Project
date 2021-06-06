package Server.Controller;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainManager
{
    public static List<NetworkManager> networkManagerList = new ArrayList<>();

    public static void addNewClient(NetworkManager networkManager) {
        networkManagerList.add(networkManager);
    }

    public synchronized static void removeClient(NetworkManager networkManager) {
        networkManagerList.remove(networkManager);
    }


    public synchronized static Response process(Request req, AtomicBoolean state) {
        {
            switch ( req.getTitle() ) {
                case "login":
                    return null;
                case "follow":
                    return null;
                case "setBio":
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
