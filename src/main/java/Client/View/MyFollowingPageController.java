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

public class MyFollowingPageController implements Initializable
{
    @FXML
    private Hyperlink link1, link2, link3, link4, link5, link6, link7, link8;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, backButton;
    @FXML
    private Button removeButton1, removeButton2, removeButton3, removeButton4, removeButton5,
            removeButton6, removeButton7, removeButton8;


    public void initialize(URL location, ResourceBundle resources)
    {
        List<String> following = Utils.currentUserObj.getFollowing();

        if (following.size() > 0) {
            link1.setText( following.get(0) );
            removeButton1.setVisible(true);

        if (following.size() > 1) {
            link2.setText( following.get(1) );
            removeButton2.setVisible(true);

        if (following.size() > 2) {
            link3.setText( following.get(2) );
            removeButton3.setVisible(true);

        if (following.size() > 3) {
            link4.setText( following.get(3) );
            removeButton4.setVisible(true);

        if (following.size() > 4) {
            link5.setText( following.get(4) );
            removeButton5.setVisible(true);

        if (following.size() > 5) {
            link6.setText( following.get(5) );
            removeButton6.setVisible(true);

        if (following.size() > 6) {
            link7.setText( following.get(6) );
            removeButton7.setVisible(true);

        if (following.size() > 7) {
            link8.setText( following.get(7) );
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
            Request req = new Request("unfollow", new Data(Utils.currentUser, username) );
            NetworkManager.putRequest(req);

            Utils.currentUserObj.getFollowing().remove(username);
            Starter.changeScene("MyFollowingPage");
        }
    }

}
