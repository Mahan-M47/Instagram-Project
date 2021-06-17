package Client.View;

import Client.Controller.MainManager;
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
    private Button logoutButton, searchButton, homeButton, postButton, profileButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timelineLabel.setText("Logged in as: " + Utils.currentUser);
    }


    @FXML
    void homeButtonClickHandler(ActionEvent event) {

    }

    @FXML
    void logoutButtonClickHandler(ActionEvent event) {
        MainManager.logout();
        Starter.changeScene("LoginPage");
    }

    @FXML
    void postButtonClickHandler(ActionEvent event) {

    }

    @FXML
    void profileButtonClickHandler(ActionEvent event) {

    }

    @FXML
    void searchButtonClickHandler(ActionEvent event) {
        Starter.changeScene("SearchPage");
    }



}