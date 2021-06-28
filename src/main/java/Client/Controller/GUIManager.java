package Client.Controller;

import Client.Model.Notification;
import Client.Utils;
import Client.View.Starter;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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

    public static void reload() { Starter.reloadScene(); }

    public static void showNotification(Notification notificationObj)
    {
        Platform.runLater(() -> {
            Notifications notification = Notifications.create()
                    .title( notificationObj.getTitle() )
                    .text( notificationObj.getText() )
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);

            notification.show();
        });
    }

}
