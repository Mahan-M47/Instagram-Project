package Server.Controller;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerIO
{
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson;

    public ServerIO(Socket socket) {
        try {
            this.socket = socket;
            in  = new DataInputStream( socket.getInputStream() );
            out = new DataOutputStream( socket.getOutputStream() );
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        gson = new Gson();
    }

    public Request receiveFromClient() {
        String json = "";

        try {
            byte[] bytes = new byte[in.readInt()];
            in.readFully(bytes);
            json = new String(bytes);
        }
        catch (IOException e) {
            close();
        }

        return gson.fromJson(json, Request.class);
    }

    public void sendToClient (Response response)
    {
        String json = gson.toJson(response);
        byte[] bytes = json.getBytes();
        try {
            out.writeInt(bytes.length);
            out.write(bytes);
        }
        catch (IOException e) {
            close();
        }
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
