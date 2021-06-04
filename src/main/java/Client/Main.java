package Client;

import Client.Controller.MainManager;
import java.net.Socket;

public class Main
{
    public static void main(String[] args) throws InterruptedException
    {
        boolean flag = true;
        Socket socket;

        while (flag)
        {
            try {
                socket = new Socket(Utils.IP_ADDRESS, Utils.PORT);
                MainManager mainManager = new MainManager(socket);
                mainManager.startMainManager();
                flag = false;
            }
            catch (Exception e) {
                Thread.sleep(100);
            }
        }

    }
}
