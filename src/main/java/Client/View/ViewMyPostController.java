package Client.View;

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

public class ViewMyPostController implements Initializable
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
    private Label usernameLabel, bioLabel, likeLabel, commentsLabel, captionLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, editButton;
    @FXML
    private Button backButton, commentsButton, likeButton, sendButton;

    private static Post post;
    public static void setPost(Post chosenPost) {
        post = chosenPost;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        followersLink.setText("" + Utils.currentUserObj.getFollowers().size());
        followingLink.setText("" + Utils.currentUserObj.getFollowing().size());
        usernameLabel.setText(Utils.currentUserObj.getUsername());
        bioLabel.setText(Utils.currentUserObj.getBioText());

        loadPost();
    }

    public void loadPost()
    {
        commentsLabel.setText("" + post.getComments().size());
        likeLabel.setText("" + post.getLikedBy().size());
        captionLabel.setText(post.getCaption());

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

