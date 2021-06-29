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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ViewPostController implements Initializable
{
    @FXML
    private ImageView profilePicture, postImage;
    @FXML
    private MediaView postVideo;
    @FXML
    private VBox commentsVBox;
    @FXML
    private ScrollPane commentsScrollPane;
    @FXML
    private TextField commentsTF;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel, postsLabel, likeLabel, commentsLabel, captionLabel, playLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button backButton, messageButton, followButton, commentsButton, likeButton, sendButton;

    private MediaPlayer mediaPlayer;

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

        if (Utils.receivedUserObj.getProfilePicture() != null) {
            InputStream in = new ByteArrayInputStream( Utils.receivedUserObj.getProfilePicture() );
            Image img = new Image(in);
            profilePicture.setImage(img);
        }

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

        if ( post.getPostType().equals(Utils.POST_IMAGE) ) {
            loadImage();
        }
        else {
            loadVideo();
        }
    }

    public void loadImage()
    {
        InputStream in = new ByteArrayInputStream( post.getFileBytes() );
        Image img = new Image(in);
        postImage.setImage(img);
    }

    public void loadVideo()
    {
        try {
            Files.createDirectories( Paths.get(Utils.DIR_CLIENT_POST_VIDEOS) );
            File file = new File( Utils.DIR_CLIENT_POST_VIDEOS + post.getID() + post.getPostType());

            Media media = new Media(file.toURI().toURL().toString());
            mediaPlayer = new MediaPlayer(media);
            postVideo.setMediaPlayer(mediaPlayer);

            playLabel.setVisible(true);
            mediaPlayer.setCycleCount(mediaPlayer.getCycleCount() + 1);

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.setCycleCount(mediaPlayer.getCycleCount() + 1);
                    CommonClickHandlers.playButton(mediaPlayer, playLabel);
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void playButtonClickHandler(MouseEvent event) { CommonClickHandlers.playButton(mediaPlayer, playLabel); }

    @FXML
    void likeButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.likeButton(likeButton, likeLabel, post);
    }

    @FXML
    void commentsButtonClickHandler(ActionEvent event) { CommonClickHandlers.commentsButton(commentsScrollPane, sendButton, commentsTF); }

    @FXML
    void sendButtonClickHandler() {
        CommonClickHandlers.sendCommentButton(commentsVBox, commentsTF, commentsLabel, post);
    }

    @FXML
    void followButtonClickHandler(ActionEvent event)
    {
        Data data = new Data( Utils.currentUser, Utils.receivedUserObj.getUsername() );
        Request req;

        if ( followButton.getText().equals("Follow") ) {
            req = new Request(Utils.REQ.FOLLOW, data);
            followButton.setText("Unfollow");
        }
        else {
            req = new Request(Utils.REQ.UNFOLLOW, data);
            followButton.setText("Follow");
        }

        NetworkManager.putRequest(req);
        stopMediaPlayer();
    }

    @FXML
    void messageButtonClickHandler(ActionEvent event) {
        //chat Request
        stopMediaPlayer();
    }

    @FXML
    void followersLinkClickHandler(ActionEvent event) {
        stopMediaPlayer();
        Starter.changeScene(Utils.GUI.FOLLOWERS);
    }

    @FXML
    void followingLinkClickHandler(ActionEvent event) {
        stopMediaPlayer();
        Starter.changeScene(Utils.GUI.FOLLOWING);
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.showProfileButton(Utils.receivedUserObj.getUsername());
    }

    public void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.homeButton();
    }
    @FXML
    void profileButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.myProfileButton();
    }
    @FXML
    void searchButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.searchButton();
    }
    @FXML
    void postButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.postButton();
    }
    @FXML
    void chatsButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.chatsButton();
    }

}

