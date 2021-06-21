package Client.Controller;

import Client.Utils;
import Client.View.Starter;

public class GUIManager
{
    public static void start() {
        javafx.application.Application.launch(Starter.class);
    }

    public static void showLoginPage() { Starter.changeScene(Utils.GUI_LOGIN); }

    public static void showSignupPage() { Starter.changeScene(Utils.GUI_SIGN_UP); }

    public static void showTimeline() { Starter.changeScene(Utils.GUI_TIMELINE); }

    public static void showSearchPage() { Starter.changeScene(Utils.GUI_SEARCH); }

    public static void showMyProfilePage() { Starter.changeScene(Utils.GUI_MY_PROFILE); }

    public static void showProfilePage() { Starter.changeScene(Utils.GUI_PROFILE); }

}
