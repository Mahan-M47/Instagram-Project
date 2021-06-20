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

public class MyFollowersPageController implements Initializable
{
    @FXML
    private Hyperlink link1, link2, link3, link4, link5, link6, link7, link8;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, backButton;
    @FXML
    private Button removeButton1, removeButton2, removeButton3, removeButton4, removeButton5,
            removeButton6, removeButton7, removeButton8;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<String> followers = Utils.currentUserObj.getFollowers();

        if (followers.size() > 0) {
            link1.setText( followers.get(0) );
            removeButton1.setVisible(true);

        if (followers.size() > 1) {
            link2.setText( followers.get(1) );
            removeButton2.setVisible(true);

        if (followers.size() > 2) {
            link3.setText( followers.get(2) );
            removeButton3.setVisible(true);

        if (followers.size() > 3) {
            link4.setText( followers.get(3) );
            removeButton4.setVisible(true);

        if (followers.size() > 4) {
            link5.setText( followers.get(4) );
            removeButton5.setVisible(true);

        if (followers.size() > 5) {
            link6.setText( followers.get(5) );
            removeButton6.setVisible(true);

        if (followers.size() > 6) {
            link7.setText( followers.get(6) );
            removeButton7.setVisible(true);

        if (followers.size() > 7) {
            link8.setText( followers.get(7) );
            removeButton8.setVisible(true);

        }}}}}}}}
    }


    @FXML
    void backButtonClickHandler(ActionEvent event) {
        Starter.changeScene("MyProfilePage");
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) { Starter.changeScene("Timeline"); }

    @FXML
    void profileButtonClickHandler(ActionEvent event) { Starter.changeScene("MyProfilePage"); }

    @FXML
    void searchButtonClickHandler(ActionEvent event) {
        Starter.changeScene("SearchPage");
    }

    @FXML
    void postButtonClickHandler(ActionEvent event) { Starter.changeScene("CreatePostPage"); }

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

    @FXML
    void removeButton1ClickHandler(ActionEvent event) { remove(link1.getText()); }
    @FXML
    void removeButton2ClickHandler(ActionEvent event) { remove(link2.getText()); }
    @FXML
    void removeButton3ClickHandler(ActionEvent event) { remove(link3.getText()); }
    @FXML
    void removeButton4ClickHandler(ActionEvent event) { remove(link4.getText()); }
    @FXML
    void removeButton5ClickHandler(ActionEvent event) { remove(link5.getText()); }
    @FXML
    void removeButton6ClickHandler(ActionEvent event) { remove(link6.getText()); }
    @FXML
    void removeButton7ClickHandler(ActionEvent event) { remove(link7.getText()); }
    @FXML
    void removeButton8ClickHandler(ActionEvent event) { remove(link8.getText()); }

    public void remove(String username)
    {
        if (!username.equals("")) {
            Request req = new Request("unfollow", new Data(username, Utils.currentUser) );
            NetworkManager.putRequest(req);

            Utils.currentUserObj.getFollowers().remove(username);
            Starter.changeScene("MyFollowersPage");
        }
    }

}
