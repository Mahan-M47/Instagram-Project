package Client.Controller;

import com.google.gson.Gson;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientJsonHandler
{
    private Socket socket;
    private DataOutputStream out;
    private Gson gson;

    ClientJsonHandler () throws IOException {
        socket = new Socket("localhost", 6000);
        out = new DataOutputStream( socket.getOutputStream() );
        gson = new Gson();
    }

    void sendToServer(Request req) throws IOException {
        String json = gson.toJson(req);
        byte[] bytes = json.getBytes();
        out.writeInt(bytes.length);
        out.write(bytes);
    }
}