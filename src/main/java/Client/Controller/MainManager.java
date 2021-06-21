package Client.Controller;

import Client.Model.User;
import Client.Utils;
import java.net.Socket;
import java.util.ArrayList;

public class MainManager
{
    private static NetworkManager networkManager;

    public static void startClient (Socket socket) {
        networkManager = new NetworkManager(socket);
        GUIManager.start();

        //once the user closes the app window, GUIManager.start() will end and move to the disconnect method below.
        disconnect();
    }

    public static void disconnect()
    {
        try {
            Request terminate = new Request.Termination();
            NetworkManager.putRequest(terminate);
            Thread.sleep(Utils.DISCONNECT_SLEEP_TIMER);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        networkManager.stopClient();
    }


    public static void process(Response response)
    {
        Data dat = response.getData();
        User user = null;

        switch ( response.getTitle() )
        {
            case "signup":
                if (dat.flag) {
                    Utils.currentUser = dat.clientUsername;
                    Utils.resetErrorTexts();
                    GUIManager.showTimeline();
                }
                else {
                    Utils.SIGNUP_ERROR_TEXT = "This Username Has Already Been Taken.";
                    GUIManager.showSignupPage();
                }
                break;


            case "login":
                if (dat.flag) {
                    Utils.currentUser = dat.clientUsername;
                    Utils.resetErrorTexts();
                    GUIManager.showTimeline();
                }
                else {
                    Utils.LOGIN_ERROR_TEXT = "The Entered Username or Password Is Incorrect.";
                    GUIManager.showLoginPage();
                }
                break;


            case "search":
                Utils.searchResults = new ArrayList<>(dat.usernameList);
                GUIManager.showSearchPage();
                break;


            case "showProfile":
                Utils.receivedUserObj = dat.user;
                GUIManager.showProfilePage();
                break;


            case "showMyProfile":
                Utils.currentUserObj = dat.user;
                GUIManager.showMyProfilePage();
                break;
        }

        //each case in switch statement calls a method from GUIManager.
    }

}
