package Client.Controller;

import Client.Utils;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientJsonHandler
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson;

    public ClientJsonHandler() {
        try {
            socket = new Socket(Utils.IP_ADDRESS, Utils.PORT);
            in  = new DataInputStream( socket.getInputStream() );
            out = new DataOutputStream( socket.getOutputStream() );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        gson = new Gson();
    }

    public void sendToServer(Request req) {
        String json = gson.toJson(req);
        byte[] bytes = json.getBytes();
        try {
            out.writeInt(bytes.length);
            out.write(bytes);
        }
        catch (IOException e) {
            close();
        }
    }

    public Response receiveFromServer() {
        String json = "";

        try {
            byte[] bytes = new byte[in.readInt()];
            in.readFully(bytes);
            json = new String(bytes);
        }
        catch (IOException e) {
            close();
        }

        return gson.fromJson(json, Response.class);
    }

    public void close() {
        try {
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}