package Client.Controller;

public class MainManager
{
    private static NetworkManager networkManager;

    public static void startMainManager()
    {
        networkManager = new NetworkManager();
        networkManager.startClient();

        //next step: creates an instance of GUIManager and loads the UI from View.
    }

    public static void terminate() {
        networkManager.stopClient();
    }
}
