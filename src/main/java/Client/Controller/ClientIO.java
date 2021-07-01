package Client.Controller;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientIO
{
    // This Class includes all communication and data transmission methods for sending Requests and receiving Responses.
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Gson gson;
    private Encryption encryption;

    public ClientIO(Socket socket)
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

    public void sendToServer(Request req)
    {
        String json = gson.toJson(req);
        byte[] bytes = json.getBytes();

        try {
            byte[] encryptedBytes = encryption.encrypt(bytes);
            out.writeInt(encryptedBytes .length);
            out.write(encryptedBytes );
        }
        catch (Exception e) {
            close();
        }
    }

    public Response receiveFromServer()
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

        return gson.fromJson(json, Response.class);
    }

    public void close()
    {
        try {
            socket.close();
            in.close();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}