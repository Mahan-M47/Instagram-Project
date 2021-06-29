package Client.Controller;

import Client.Model.Notification;
import Client.Utils;
import Client.View.CommonClickHandlers;
import javafx.application.Platform;
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

        switch ( response.getTitle() )
        {
            case Utils.REQ.SIGNUP:
                if (dat.flag) {
                    Utils.currentUser = dat.clientUsername;
                    Utils.resetErrorTexts();
                    GUIManager.showNotification(
                            new Notification("Signup Was Successful", "Welcome " + dat.clientUsername) );

                    //using CommonClickHandlers to send a ShowMyProfile request
                    CommonClickHandlers.myProfileButton();
                }
                else {
                    Utils.SIGNUP_ERROR_TEXT = "This Username Has Already Been Taken.";
                    GUIManager.showSignupPage();
                }
                break;


            case Utils.REQ.LOGIN:
                if (dat.flag) {
                    Utils.currentUser = dat.clientUsername;
                    Utils.resetErrorTexts();
                    GUIManager.showNotification(
                            new Notification("Login Was Successful", "Welcome Back, " + dat.clientUsername) );

                    //using CommonClickHandlers to send a ShowMyProfile request
                    CommonClickHandlers.myProfileButton();
                }
                else {
                    Utils.LOGIN_ERROR_TEXT = "The Entered Username or Password Is Incorrect.";
                    GUIManager.showLoginPage();
                }
                break;


            case Utils.REQ.SEARCH:
                if (dat.usernameList.size() == 0) {
                    Utils.SEARCH_ERROR_TEXT = "No Results";
                }
                else {
                    Utils.searchResults = new ArrayList<>(dat.usernameList);
                }
                GUIManager.showSearchPage();
                break;


            case Utils.REQ.PROFILE:
                Utils.receivedUserObj = dat.user;
                GUIManager.showProfilePage();
                break;


            case Utils.REQ.MY_PROFILE:
                Utils.currentUserObj = dat.user;
                GUIManager.showMyProfilePage();
                break;


            case Utils.REQ.FOLLOW:
                Utils.receivedUserObj = dat.user;
                GUIManager.reload();
                break;


            case Utils.REQ.UNFOLLOW:
                Utils.receivedUserObj = dat.user;
                GUIManager.reload();
                break;


            case Utils.REQ.PERSONAL_CHAT:
                GUIManager.showPersonalChatPage(dat.personalChat);
                break;

            case Utils.REQ.NOTIFICATION:
                GUIManager.showNotification(dat.notification);
                break;


            case Utils.REQ.TIMELINE:
                Utils.timelineData = dat.posts;
                GUIManager.showTimeline();
                break;
        }

        //each case in switch statement calls a method from GUIManager.
    }

}
