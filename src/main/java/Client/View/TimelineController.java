package Client.View;

import Client.Controller.Data;
import Client.Controller.MainManager;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {

    @FXML
    private Label TimelineLabel;

    @FXML
    private Button logoutButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TimelineLabel.setText("Hello " + MainManager.currentUser + "!");
    }

    @FXML
    void logoutButtonClickHandler(ActionEvent event) {
        Request req = new Request("logout",new Data(MainManager.currentUser) );
        NetworkManager.putRequest(req);
        MainManager.currentUser = "";
        Starter.changeScene("LoginPage");
    }
}