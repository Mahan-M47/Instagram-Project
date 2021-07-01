package Client.Controller;

import Client.Model.GroupChat;
import Client.Model.Notification;
import Client.Model.PersonalChat;
import Client.Utils;
import Client.View.ChatPageController;
import Client.View.Starter;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class GUIManager
{
    // This class connects Main Manager to UI elements inside the View Package.
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

    public static void showChatList() { Starter.changeScene(Utils.GUI.CHAT_LIST); }

    public static void showPersonalChatPage(PersonalChat chat) {
        ChatPageController.setPersonalChat(chat);
        Starter.changeScene(Utils.GUI.CHAT_PAGE);
    }

    public static void showGroupChatPage(GroupChat chat) {
        ChatPageController.setGroupChat(chat);
        Starter.changeScene(Utils.GUI.CHAT_PAGE);
    }


    public static void showNotification(Notification notificationObj)
    {
        Platform.runLater(() -> {
            Notifications notification = Notifications.create()
                    .title( notificationObj.getTitle() )
                    .text ( notificationObj.getText() )
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.BOTTOM_RIGHT);

            notification.show();
        });
    }

}
