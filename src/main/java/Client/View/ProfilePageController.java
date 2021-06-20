package Client.View;

import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {

    @FXML
    private ImageView profilePicture;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button messageButton, followButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameLabel.setText(Utils.receivedUser.getUsername());
        bioLabel.setText(Utils.receivedUser.getBioText());
        followersLink.setText("" + Utils.receivedUser.getFollowers().size());
        followingLink.setText("" + Utils.receivedUser.getFollowing().size());
    }


    @FXML
    void followButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void messageButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void followersLinkClickHandler(ActionEvent event) {
        Starter.changeScene("FollowersPage");
    }

    @FXML
    void followingLinkClickHandler(ActionEvent event) {
        Starter.changeScene("FollowingPage");
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

}
