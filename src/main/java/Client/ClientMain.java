package Client;

import Client.Controller.MainManager;
import java.net.Socket;

public class ClientMain
{
    public static void main(String[] args) throws InterruptedException
    {
        boolean flag = true;
        Socket socket;

        while (flag)
        {
            try {
                socket = new Socket(Utils.IP_ADDRESS, Utils.PORT);
                MainManager.startClient(socket);
                flag = false;
            }
            catch (Exception e) {
                Thread.sleep(Utils.MAIN_SLEEP_TIMER);
            }
        }

    }
}
