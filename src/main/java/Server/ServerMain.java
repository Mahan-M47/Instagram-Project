package Server;

import Server.Controller.ActiveClient;
import Server.Controller.DatabaseManager;
import Server.Controller.MainManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerMain
{
    public static void main(String[] args) throws IOException
    {
        DatabaseManager.startDatabase(Utils.DATABASE_NAME);
        ServerSocket serverSocket = new ServerSocket(Utils.PORT);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ActiveClient client = new ActiveClient(socket);
                MainManager.addNewClient(client);
                System.out.println("New Client Accepted.");
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

    }
}
