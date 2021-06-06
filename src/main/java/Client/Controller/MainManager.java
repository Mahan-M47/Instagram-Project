package Client.Controller;

import Client.Utils;

import java.net.Socket;

public class MainManager
{
    private NetworkManager networkManager;

    public MainManager(Socket socket) {
        networkManager = new NetworkManager(socket);
        GUIManager.start();

        //once the user closes the app window, GUIManager.start() will end and move to the disconnect method below.
        disconnect();
    }

    public void disconnect()
    {
        try {
            Request terminate = new Request.Termination();
            NetworkManager.queueRequest.put(terminate);
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
