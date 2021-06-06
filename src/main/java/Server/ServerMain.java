package Server;

import Server.Controller.DatabaseManager;
import Server.Controller.MainManager;
import Server.Controller.NetworkManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain
{
    public static void main(String[] args) throws IOException
    {
        new DatabaseManager(Utils.DATABASE_NAME);
        ServerSocket serverSocket = new ServerSocket(Utils.PORT);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Accepted.");

                NetworkManager networkManager = new NetworkManager(socket);
                MainManager.addNewClient(networkManager);

                Thread thread = new Thread(networkManager);
                thread.start();
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }
}
