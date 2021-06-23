package Client.View;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class NotificationController {

    public static void show(String tittle, String text){

        Notifications notification = Notifications.create()
                .title(tittle)
                .text(text)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);

        notification.show();

    }
}
