package Client.Controller;

import Client.View.Starter;

public class GUIManager
{
    public static void start() {
        javafx.application.Application.launch(Starter.class);
    }

    public static void showLoginPage() {
        Starter.changeScene("LoginPage");
    }

    public static void showSignupPage() {
        Starter.changeScene("SignupPage");
    }

    public static void showTimeline() {
        Starter.changeScene("Timeline");
    }

}
