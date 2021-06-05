package Server.Controller;

import Server.Utils;
import java.net.ServerSocket;
import java.net.Socket;

public class MainManager implements Runnable
{
    private ServerSocket serverSocket;
    private Socket socket;
    private ServerJsonHandler SJH;

    public MainManager(ServerSocket serverSocket, Socket socket)
    {
        this.serverSocket = serverSocket;
        this.socket = socket;
        SJH = new ServerJsonHandler(this.serverSocket, this.socket);
    }

    @Override
    public void run()
    {
        DatabaseManager mongo = new DatabaseManager(Utils.DATABASE_NAME);
        NetworkManager networkManager = new NetworkManager(SJH, mongo);
        networkManager.startServer();
    }

}
