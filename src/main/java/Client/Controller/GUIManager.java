package Client.Controller;

import Client.Utils;
import Client.View.Starter;

public class GUIManager
{
    public static void start() {
        javafx.application.Application.launch(Starter.class);
    }

    public static void showLoginPage() { Starter.changeScene(Utils.GUI.LOGIN); }

    public static void showSignupPage() { Starter.changeScene(Utils.GUI.SIGNUP); }

    public static void showTimeline() { Starter.changeScene(Utils.GUI.TIMELINE); }

    public static void showSearchPage() { Starter.changeScene(Utils.GUI.SEARCH); }

    public static void showMyProfilePage() { Starter.changeScene(Utils.GUI.MY_PROFILE); }

    public static void showProfilePage() { Starter.changeScene(Utils.GUI.PROFILE); }

    public static void showEditProfilePage() { Starter.changeScene(Utils.GUI.EDIT_PROFILE); }

}
