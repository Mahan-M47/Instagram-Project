package Client.Controller;

import java.net.Socket;

public class MainManager
{
    private NetworkManager networkManager;

    public MainManager(Socket socket) {
        networkManager = new NetworkManager(socket);
    }

    public void startMainManager()
    {
        networkManager.startClient();
        GUIManager.start();

        //once the user closes the app window, GUIManager.start() will end and move to the disconnect method below.
        disconnect();
    }

    public void disconnect() {
        Request terminate = new Request.Termination();
        NetworkManager.CJH.sendToServer(terminate);
        networkManager.stopClient();
    }

}
