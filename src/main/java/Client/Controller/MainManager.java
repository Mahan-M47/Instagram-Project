package Client.Controller;

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
        switch ( response.getTitle() ) {
            case "basicResponse":
                break;
            case "booleanResponse":
                break;
        }

        //each case in switch statement calls a method from GUIManager.
    }

}
