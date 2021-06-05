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
        DatabaseManager mongo = new DatabaseManager(Utils.DATABASE_NAME);
        NetworkManager networkManager = new NetworkManager(SJH, mongo);
        networkManager.startServer();
    }

}
