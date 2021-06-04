package Client.Controller;

import java.net.Socket;

public class MainManager
{
    private NetworkManager networkManager;
    private Socket socket;

    public MainManager(Socket socket) {
        this.socket = socket;
    }

    public void startMainManager()
    {
        ClientJsonHandler CJH  = new ClientJsonHandler(socket);
        networkManager = new NetworkManager(CJH);
        networkManager.startClient();

        //next step: create an instance of GUIManager and load the UI from View
    }

}
