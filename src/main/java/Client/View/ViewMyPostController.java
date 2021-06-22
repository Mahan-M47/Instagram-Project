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

public class ViewMyPostController implements Initializable
{
    @FXML
    private ImageView profilePicture, postImage;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel, likeLabel, commentsLabel, captionLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, editButton;
    @FXML
    private Button backButton, commentsButton, likeButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        usernameLabel.setText(Utils.currentUserObj.getUsername());
        bioLabel.setText(Utils.currentUserObj.getBioText());
        followersLink.setText("" + Utils.currentUserObj.getFollowers().size());
        followingLink.setText("" + Utils.currentUserObj.getFollowing().size());

        likeLabel.setText("0");
        commentsLabel.setText("0");
        captionLabel.setText("");
    }


    @FXML
    void commentsButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void likeButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void followersLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.MY_FOLLOWERS); }

    @FXML
    void followingLinkClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI.MY_FOLLOWING);
    }

    @FXML
    void editButtonClickHandler(ActionEvent event) { CommonClickHandlers.editProfileButton(); }

    @FXML
    void backButtonClickHandler(ActionEvent event) { CommonClickHandlers.myProfileButton(); }

    @FXML
    void homeButtonClickHandler(ActionEvent event) { CommonClickHandlers.homeButton(); }
    @FXML
    void profileButtonClickHandler(ActionEvent event) { CommonClickHandlers.myProfileButton(); }
    @FXML
    void searchButtonClickHandler(ActionEvent event) { CommonClickHandlers.searchButton(); }
    @FXML
    void postButtonClickHandler(ActionEvent event) { CommonClickHandlers.postButton(); }
    @FXML
    void chatsButtonClickHandler(ActionEvent event) { CommonClickHandlers.chatsButton(); }

}

