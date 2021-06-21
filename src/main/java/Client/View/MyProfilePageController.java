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
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class MyProfilePageController implements Initializable
{
    @FXML
    private ImageView profilePicture;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, editButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        usernameLabel.setText(Utils.currentUserObj.getUsername());
        bioLabel.setText(Utils.currentUserObj.getBioText());
        followersLink.setText("" + Utils.currentUserObj.getFollowers().size());
        followingLink.setText("" + Utils.currentUserObj.getFollowing().size());
    }


    @FXML
    void editButtonClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI_EDIT_PROFILE);
    }

    @FXML
    void followersLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_MY_FOLLOWERS); }

    @FXML
    void followingLinkClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI_MY_FOLLOWING);
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI_TIMELINE);  //should be removed
        Request req = new Request("getTimeline", new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void profileButtonClickHandler(ActionEvent event) {
        Request req = new Request("showMyProfile", new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void searchButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_SEARCH); }

    @FXML
    void postButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_CREATE_POST); }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) { }

}
