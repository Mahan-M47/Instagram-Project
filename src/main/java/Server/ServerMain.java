package Server;

import Server.Controller.DatabaseManager;
import Server.Controller.NetworkManager;
import Server.Model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ServerMain
{
    public static void main(String[] args) throws IOException
    {
        DatabaseManager.startDatabase(Utils.DATABASE_NAME);
        ServerSocket serverSocket = new ServerSocket(Utils.PORT);
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                NetworkManager networkManager = new NetworkManager(socket);
                Thread thread = new Thread(networkManager);
                thread.start();
                System.out.println("New Client Accepted.");
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }
}
