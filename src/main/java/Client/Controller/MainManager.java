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

        disconnect();
    }

    public void disconnect() {
        Request terminate = new Request.Termination();
        NetworkManager.CJH.sendToServer(terminate);
        networkManager.stopClient();
    }

}
