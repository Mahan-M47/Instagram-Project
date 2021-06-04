package Server.Controller;

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
        NetworkManager networkManager = new NetworkManager(SJH);
        networkManager.startServer();
    }

}
