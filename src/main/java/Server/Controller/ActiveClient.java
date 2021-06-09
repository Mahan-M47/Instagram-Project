package Server.Controller;

import java.net.Socket;

public class ActiveClient
{
    private NetworkManager networkManager;
    private String username;

    public ActiveClient(Socket socket) {
        this.networkManager = new NetworkManager(socket);
        Thread thread = new Thread(networkManager);
        thread.start();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public String getUsername() {
        return username;
    }
}
