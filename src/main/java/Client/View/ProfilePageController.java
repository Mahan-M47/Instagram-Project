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
    public void initialize(URL location, ResourceBundle resources)
    {
        usernameLabel.setText(Utils.receivedUserObj.getUsername());
        bioLabel.setText(Utils.receivedUserObj.getBioText());
        followersLink.setText("" + Utils.receivedUserObj.getFollowers().size());
        followingLink.setText("" + Utils.receivedUserObj.getFollowing().size());

        if ( Utils.receivedUserObj.getFollowers().contains(Utils.currentUser) ) {
            followButton.setText("Unfollow");
        }

    }


    @FXML
    void followButtonClickHandler(ActionEvent event)
    {
        Data data = new Data( Utils.currentUser, Utils.receivedUserObj.getUsername() );
        Request req;

        if ( followButton.getText().equals("follow") ) {
            req = new Request("follow", data);
        }
        else {
            req = new Request("unfollow", data);
        }

        NetworkManager.putRequest(req);
    }

    @FXML
    void messageButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void followersLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_FOLLOWERS); }

    @FXML
    void followingLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_FOLLOWING); }

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
