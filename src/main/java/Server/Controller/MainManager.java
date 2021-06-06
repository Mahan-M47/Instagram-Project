package Server.Controller;

import Server.Utils;
import java.net.ServerSocket;
import java.net.Socket;

public class MainManager implements Runnable
{
    private ServerJsonHandler SJH;

    public MainManager(ServerSocket serverSocket, Socket socket) {
        SJH = new ServerJsonHandler(serverSocket, socket);
    }

    @Override
    public void run()
    {
        NetworkManager networkManager = new NetworkManager(SJH);
        networkManager.startServer();
    }

}
