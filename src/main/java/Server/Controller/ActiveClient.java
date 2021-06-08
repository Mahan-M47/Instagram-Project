package Server.Controller;

public class ActiveClient
{
    private NetworkManager networkManager;
    private String username;

    public ActiveClient(NetworkManager networkManager) {
        this.networkManager = networkManager;
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
