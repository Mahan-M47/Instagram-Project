package Server.Controller;

public class MainManager
{
    public static void startMainManager()
    {
        NetworkManager networkManager = new NetworkManager();
        networkManager.startServer();
    }


}
