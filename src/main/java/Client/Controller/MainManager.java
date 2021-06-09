package Client.Controller;

import Client.Model.User;
import Client.Utils;
import java.net.Socket;

public class MainManager
{
    private static NetworkManager networkManager;

    public static void startClient (Socket socket) {
        networkManager = new NetworkManager(socket);
        GUIManager.start();

        //once the user closes the app window, GUIManager.start() will end and move to the disconnect method below.
        disconnect();
    }

    public static void disconnect()
    {
        try {
            Request terminate = new Request.Termination();
            NetworkManager.putRequest(terminate);
            Thread.sleep(Utils.DISCONNECT_SLEEP_TIMER);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        networkManager.stopClient();
    }


    public static void process(Response response)
    {
        Data dat = response.getData();
        User user = null;

        switch ( response.getTitle() )
        {
            case "signup":
                if (dat.flag) {
                    GUIManager.showTimeline();
                }
                else System.out.println("sad");

            case "login":
                if (dat.flag) {
                    GUIManager.showTimeline();
                }
                else System.out.println("sad");

                break;
        }

        //each case in switch statement calls a method from GUIManager.
    }

}
