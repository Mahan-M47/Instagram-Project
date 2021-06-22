package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Utils;

public class CommonClickHandlers
{
    public static void homeButton() {
        Starter.changeScene(Utils.GUI.TIMELINE);  //should be removed
        Request req = new Request(Utils.REQ.TIMELINE, new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    public static void myProfileButton() {
        Request req = new Request(Utils.REQ.MY_PROFILE, new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    public static void searchButton() { Starter.changeScene(Utils.GUI.SEARCH); }

    public static void postButton() { Starter.changeScene(Utils.GUI.CREATE_POST); }

    public static void chatsButton() { }

    public static void showProfileButton(String username)
    {
        if (username != null)
        {
            Request req;

            if ( username.equals(Utils.currentUser) ) {
                req = new Request(Utils.REQ.MY_PROFILE, new Data(username) );
            }
            else {
                req = new Request(Utils.REQ.PROFILE, new Data(username) );
            }
            NetworkManager.putRequest(req);
        }
    }

    public static void editProfileButton() {
        Starter.changeScene(Utils.GUI.EDIT_PROFILE);
    }
}
