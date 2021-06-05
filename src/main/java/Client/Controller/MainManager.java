package Client.Controller;

import java.net.Socket;

public class MainManager
{
    private NetworkManager networkManager;
    private ClientJsonHandler CJH;

    public MainManager(Socket socket) {
        CJH  = new ClientJsonHandler(socket);
    }

    public void startMainManager()
    {
        networkManager = new NetworkManager(CJH);
        networkManager.startClient();
        GUIManager.start();

        disconnect();
    }

    public void disconnect() {
        Request terminate = new Request.Termination();
        CJH.sendToServer(terminate);
        networkManager.stopClient();
    }

}
