package Server.Controller;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerIO
{
    // This Class includes all communication and data transmission methods for sending Responses and receiving Requests.
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson;
    private Encryption encryption;

    public ServerIO(Socket socket)
    {
        try {
            this.socket = socket;
            in  = new DataInputStream( socket.getInputStream() );
            out = new DataOutputStream( socket.getOutputStream() );
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        encryption = new Encryption();
        gson = new Gson();
    }

    public Request receiveFromClient()
    {
        String json = "";

        try {
            byte[] bytes = new byte[in.readInt()];
            in.readFully(bytes);
            byte[] decryptedBytes = encryption.decrypt(bytes);
            json = new String(decryptedBytes);
        }
        catch (Exception e) {
            close();
        }

        return gson.fromJson(json, Request.class);
    }

    public void sendToClient (Response response)
    {
        String json = gson.toJson(response);
        byte[] bytes = json.getBytes();

        try {
            byte[] encryptedBytes = encryption.encrypt(bytes);
            out.writeInt(encryptedBytes.length);
            out.write(encryptedBytes);
        }
        catch (Exception e) {
            close();
        }
    }

    public void close()
    {
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
