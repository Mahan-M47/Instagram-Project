package Client;

import Client.Controller.ClientJsonHandler;
import Client.Controller.Data;
import Client.Controller.Request;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException
    {
        ClientJsonHandler CJH = new ClientJsonHandler();
        Data.Login Dat = new Data.Login("test", "hello");
        Request req = new Request("login", Dat);

        while (true) {
                CJH.sendToServer(req);
                Thread.sleep(1000);
        }

    }
}
