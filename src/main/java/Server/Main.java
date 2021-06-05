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
                System.out.println("New Client Accepted.");
                MainManager mainManager = new MainManager(serverSocket, socket);
                Thread thread = new Thread(mainManager);
                thread.start();
            }
            catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }








    // server database test
//    public static void main(String[] args)  {
//        String tableName = "login";
//        User player2 = new User("ali", "qwertyhn");
//        String databaseName = "instagram";
//        DatabaseManager mongo = null;
//        try {
//            mongo = new DatabaseManager(databaseName);
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        mongo.insertToCollection(tableName, player2.getDBObject());
//
//    }


}
