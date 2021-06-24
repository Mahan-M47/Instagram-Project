package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.Post;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewPostController implements Initializable
{
    @FXML
    private ImageView profilePicture, postImage;
    @FXML
    private VBox commentsVBox;
    @FXML
    private ScrollPane commentsScrollPane;
    @FXML
    private TextField commentsTF;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel, postsLabel, likeLabel, commentsLabel, captionLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button backButton, messageButton, followButton, commentsButton, likeButton, sendButton;

    private static Post post;
    public static void setPost(Post chosenPost) {
        post = chosenPost;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        followersLink.setText("" + Utils.receivedUserObj.getFollowers().size());
        followingLink.setText("" + Utils.receivedUserObj.getFollowing().size());
        postsLabel.setText("" + Utils.receivedUserObj.getPosts().size());
        usernameLabel.setText(Utils.receivedUserObj.getUsername());
        bioLabel.setText(Utils.receivedUserObj.getBioText());

        if ( Utils.receivedUserObj.getFollowers().contains(Utils.currentUser) ) {
            followButton.setText("Unfollow");
        }

        loadPost();
    }

    public void loadPost() {
        likeLabel.setText("" + post.getLikedBy().size() );
        commentsLabel.setText( "" + post.getComments().size() );
        captionLabel.setText( post.getCaption() );

        if (post.getLikedBy().contains(Utils.currentUser)) {
            likeButton.setText("Unlike");
        }

        for (String commentText : post.getComments()) {
            Label comment = CommonClickHandlers.createCommentLabel(commentText);
            commentsVBox.getChildren().add(comment);
        }

        commentsScrollPane.setVisible(false);
        commentsTF.setVisible(false);
        sendButton.setVisible(false);

        // add imageview here
    }

    @FXML
    void likeButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.likeButton(likeButton, likeLabel, post);
    }

    @FXML
    void commentsButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.commentsButton(commentsScrollPane, sendButton, commentsTF);
    }

    @FXML
    void sendButtonClickHandler() {
        CommonClickHandlers.sendButton(commentsVBox, commentsTF, commentsLabel, post);
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
    void backButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.showProfileButton(Utils.receivedUserObj.getUsername());
    }

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

