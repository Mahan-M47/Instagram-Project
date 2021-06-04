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

        //next step:  use GUIManager to load the UI from View
    }

}
