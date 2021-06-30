package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.Post;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;

public class CreatePostPageController
{
    @FXML
    private TextArea captionTF;
    @FXML
    private ImageView postImage;
    @FXML
    private MediaView postVideo;
    @FXML
    private Label errorLabel, playLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button chooseFileButton, createPostButton;

    private MediaPlayer mediaPlayer;
    private File chosenFile;
    private String filePath;

    @FXML
    void chooseFileButtonClickHandler(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        chosenFile = fileChooser.showOpenDialog(new Stage());

        stopMediaPlayer();

        if (chosenFile != null)
        {
            filePath = chosenFile.getPath();

            if (chosenFile.length() > Utils.POST_FILE_MAX_SIZE) {
                errorLabel.setText("Maximum File Size Is 15 MB.");
                errorLabel.setVisible(true);
                createPostButton.setDisable(true);
            }
            else if (filePath.matches(".+\\.jpe?g")) {
                loadImage();
            }
            else if (filePath.matches(".+\\.mp4")) {
                loadVideo();
            }
            else {
                postImage.setImage(null);
                postVideo.setMediaPlayer(null);
                errorLabel.setText("You Can Only Post jpg and mp4 Files.");
                errorLabel.setVisible(true);
                createPostButton.setDisable(true);
                playLabel.setVisible(false);
            }
        }
    }

    public void loadImage()
    {
        try {
            postVideo.setMediaPlayer(null);
            errorLabel.setVisible(false);
            playLabel.setVisible(false);
            createPostButton.setDisable(false);

            InputStream in = new FileInputStream(chosenFile);
            Image img = new Image(in);
            postImage.setImage(img);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadVideo()
    {
        try {
            postImage.setImage(null);
            createPostButton.setDisable(false);
            playLabel.setVisible(true);
            errorLabel.setVisible(false);

            Media media = new Media(chosenFile.toURI().toURL().toString());
            mediaPlayer = new MediaPlayer(media);
            postVideo.setMediaPlayer(mediaPlayer);

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
    void createPostButtonClickHandler(ActionEvent event)
    {
        Post post = new Post(Utils.currentUser, captionTF.getText(), filePath);

        if (filePath.matches(".+\\.jpe?g")) {
            post.setPostType(Utils.POST_IMAGE);
        }
        else {
            post.setPostType(Utils.POST_VIDEO);
            mediaPlayer.stop();
        }

        Request req = new Request(Utils.REQ.CREATE_POST, new Data(Utils.currentUser, post));
        NetworkManager.putRequest(req);
        ViewMyPostController.setPost(post);
        Starter.changeScene(Utils.GUI.MY_POST);
    }

    @FXML
    void playButtonClickHandler(MouseEvent event) { CommonClickHandlers.playButton(mediaPlayer, playLabel); }

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
