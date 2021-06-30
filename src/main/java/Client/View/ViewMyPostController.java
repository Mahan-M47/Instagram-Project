package Client.View;

import Client.Model.Post;
import Client.Utils;
import com.jfoenix.controls.JFXTextArea;
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
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ViewMyPostController implements Initializable
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
    private JFXTextArea captionTextArea;
    @FXML
    private TextField commentsTF;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel, postsLabel, likeLabel, commentsLabel, dateLabel, playLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, editButton;
    @FXML
    private Button backButton, commentsButton, likeButton, sendButton;

    private MediaPlayer mediaPlayer;

    private static Post post;
    public static void setPost(Post chosenPost) {
        post = chosenPost;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        followersLink.setText("" + Utils.currentUserObj.getFollowers().size());
        followingLink.setText("" + Utils.currentUserObj.getFollowing().size());
        postsLabel.setText("" + Utils.currentUserObj.getPosts().size());
        usernameLabel.setText(Utils.currentUserObj.getUsername());
        bioLabel.setText(Utils.currentUserObj.getBioText());

        if (Utils.currentUserObj.getProfilePicture() != null) {
            InputStream in = new ByteArrayInputStream( Utils.currentUserObj.getProfilePicture() );
            Image img = new Image(in);
            profilePicture.setImage(img);
        }

        loadPost();
    }

    public void loadPost()
    {
        commentsLabel.setText("" + post.getComments().size());
        likeLabel.setText("" + post.getLikedBy().size());
        captionTextArea.setText( post.getCaption() );
        dateLabel.setText( post.getDate().toString() );

        if (post.getLikedBy().contains(Utils.currentUser)) {
            likeButton.setText("Unlike");
        }

        for (String commentText : post.getComments()) {
            Label comment = CommonClickHandlers.createCommentLabel("  " + commentText);
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
            File file = new File(Utils.DIR_CLIENT_POST_VIDEOS + post.getID() + post.getPostType());

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
    void likeButtonClickHandler(ActionEvent event) { CommonClickHandlers.likeButton(likeButton, likeLabel, post); }

    @FXML
    void commentsButtonClickHandler(ActionEvent event) { CommonClickHandlers.commentsButton(commentsScrollPane, sendButton, commentsTF); }

    @FXML
    void sendButtonClickHandler() { CommonClickHandlers.sendCommentButton(commentsVBox, commentsTF, commentsLabel, post); }

    @FXML
    void followersLinkClickHandler(ActionEvent event) {
        stopMediaPlayer();
        Starter.changeScene(Utils.GUI.MY_FOLLOWERS);
    }

    @FXML
    void followingLinkClickHandler(ActionEvent event) {
        stopMediaPlayer();
        Starter.changeScene(Utils.GUI.MY_FOLLOWING);
    }

    @FXML
    void editButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.editProfileButton(); }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        stopMediaPlayer();
        CommonClickHandlers.myProfileButton();
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

