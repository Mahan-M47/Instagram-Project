package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class TimelineController implements Initializable
{
    @FXML
    private Label timelineLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, logoutButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timelineLabel.setText("Logged in as: " + Utils.currentUser);
    }


    @FXML
    void homeButtonClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI_TIMELINE);  //should be removed
        Request req = new Request("getTimeline", new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void profileButtonClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI_MY_PROFILE);  //should be removed
        Request req = new Request("showMyProfile", new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void searchButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_SEARCH); }

    @FXML
    void postButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_CREATE_POST); }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) { }

    @FXML
    void logoutButtonClickHandler(ActionEvent event)
    {
        Request req = new Request("logout",new Data(Utils.currentUser) );
        NetworkManager.putRequest(req);
        Utils.currentUser = "";
        Starter.changeScene(Utils.GUI_LOGIN);
    }

}