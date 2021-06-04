package Server;

import Server.Controller.MainManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(Utils.PORT);

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                MainManager mainManager = new MainManager(serverSocket, socket);
                Thread thread = new Thread(mainManager);
                thread.start();
            }
            catch (IOException e) {
                break;
            }
        }

    }
}
