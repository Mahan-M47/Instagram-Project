package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FollowersPageController implements Initializable
{
    @FXML
    private Hyperlink link1, link2, link3, link4, link5, link6, link7, link8;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, backButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        List<String> followers = Utils.receivedUser.getFollowers();

        if (followers.size() > 0) {
            link1.setText( followers.get(0) );
        if (followers.size() > 1) {
            link2.setText( followers.get(1) );
        if (followers.size() > 2) {
            link3.setText( followers.get(2) );
        if (followers.size() > 3) {
            link4.setText( followers.get(3) );
        if (followers.size() > 4) {
            link5.setText( followers.get(4) );
        if (followers.size() > 5) {
            link6.setText( followers.get(5) );
        if (followers.size() > 6) {
            link7.setText( followers.get(6) );
        if (followers.size() > 7) {
            link8.setText( followers.get(7) );
        }}}}}}}}
    }


    @FXML
    void backButtonClickHandler(ActionEvent event) { Starter.changeScene("ProfilePage"); }

    @FXML
    void homeButtonClickHandler(ActionEvent event) { Starter.changeScene("Timeline"); }

    @FXML
    void profileButtonClickHandler(ActionEvent event) { Starter.changeScene("MyProfilePage"); }

    @FXML
    void searchButtonClickHandler(ActionEvent event) { Starter.changeScene("SearchPage"); }

    @FXML
    void postButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void link1ClickHandler(ActionEvent event) { showProfile(link1.getText()); }
    @FXML
    void link2ClickHandler(ActionEvent event) { showProfile(link2.getText()); }
    @FXML
    void link3ClickHandler(ActionEvent event) { showProfile(link3.getText()); }
    @FXML
    void link4ClickHandler(ActionEvent event) { showProfile(link4.getText()); }
    @FXML
    void link5ClickHandler(ActionEvent event) { showProfile(link5.getText()); }
    @FXML
    void link6ClickHandler(ActionEvent event) { showProfile(link6.getText()); }
    @FXML
    void link7ClickHandler(ActionEvent event) { showProfile(link7.getText()); }
    @FXML
    void link8ClickHandler(ActionEvent event) { showProfile(link8.getText()); }


    public void showProfile(String username)
    {
        if (!username.equals("")) {
            Request req = new Request("showProfile", new Data(username) );
            NetworkManager.putRequest(req);
        }
    }

}

