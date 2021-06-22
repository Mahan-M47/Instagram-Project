package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.Post;
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

public class ViewPostController implements Initializable
{
    @FXML
    private ImageView profilePicture, postImage;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel, likeLabel, commentsLabel, captionLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button backButton, messageButton, followButton, commentsButton, likeButton;

    private static Post post;
    public static void setPost(Post chosenPost) {
        post = chosenPost;
    }


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

        loadPost();
    }

    public void loadPost() {
        likeLabel.setText(post.getLikes().toString());
        commentsLabel.setText("" + post.getComments().size());
        captionLabel.setText("");
        // add imageview here
    }


    @FXML
    void commentsButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void likeButtonClickHandler(ActionEvent event)
    {
        Request req;
        if (! post.getLikedBy().contains(Utils.currentUser) ) {
            req = new Request(Utils.REQ.LIKE, new Data(Utils.currentUser, post.getID()));
            post.addLike(Utils.currentUser);
            likeButton.setText("Liked!");
        }
        else {
            req = new Request(Utils.REQ.UNLIKE, new Data(Utils.currentUser, post.getID()));
            post.removeLike(Utils.currentUser);
            likeButton.setText("Like");
        }

        likeLabel.setText(post.getLikes().toString());
        NetworkManager.putRequest(req);
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        Request req = new Request(Utils.REQ.PROFILE, new Data(Utils.receivedUserObj.getUsername()) );
        NetworkManager.putRequest(req);
    }

    @FXML
    void followButtonClickHandler(ActionEvent event)
    {
        Data data = new Data( Utils.currentUser, Utils.receivedUserObj.getUsername() );
        Request req;

        if ( followButton.getText().equals("Follow") ) {
            req = new Request(Utils.REQ.FOLLOW, data);
        }
        else {
            req = new Request(Utils.REQ.UNFOLLOW, data);
        }

        NetworkManager.putRequest(req);
    }

    @FXML
    void messageButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void followersLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.FOLLOWERS); }

    @FXML
    void followingLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.FOLLOWING); }

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

