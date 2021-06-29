package Client.View;

import Client.Model.Post;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class MyProfilePageController implements Initializable
{
    @FXML
    private ImageView profilePicture;
    @FXML
    private AnchorPane scrollAnchorPane;
    @FXML
    private VBox scrollVBox;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel, postsLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, editButton;


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

        addPosts();
    }

    public void addPosts()
    {
        List<Post> posts = Utils.currentUserObj.getPosts();
        double count = posts.size();

        for (int i = 0; i < Math.ceil(count/3); i++)
        {
            scrollAnchorPane.setPrefHeight(scrollAnchorPane.getPrefHeight() + 270);
            HBox hBox = new HBox(10);
            hBox.setPrefSize(770,250);

            for (int j = 3*i; j < (3*i) + 3 && j < count ; j++)
            {
                Post post = posts.get(j);

                if ( post.getPostType().equals(Utils.POST_IMAGE) ) {
                    ImageView postImage = loadImage(post);
                    hBox.getChildren().add(postImage);
                }
                else {
                    MediaView postVideo = loadVideo(post);
                    hBox.getChildren().add(postVideo);
                }
            }
            scrollVBox.getChildren().add(hBox);
        }
    }

    public ImageView loadImage(Post post)
    {
        InputStream in = new ByteArrayInputStream( post.getFileBytes() );
        Image img = new Image(in);
        ImageView postImage = new ImageView(img);
        postImage.setFitHeight(250);
        postImage.setFitWidth(250);

        postImage.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ViewMyPostController.setPost(post);
                Starter.changeScene(Utils.GUI.MY_POST);
            }
        });

        return postImage;
    }

    public MediaView loadVideo(Post post)
    {
        MediaView postVideo = null;

        try {
            Files.createDirectories( Paths.get(Utils.DIR_CLIENT_POST_VIDEOS) );
            File file = new File(Utils.DIR_CLIENT_POST_VIDEOS + post.getID() + post.getPostType());
            OutputStream out = new FileOutputStream(file);
            out.write(post.getFileBytes());

            Media media = new Media(file.toURI().toURL().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            postVideo = new MediaView(mediaPlayer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        postVideo.setFitHeight(250);
        postVideo.setFitWidth(250);

        postVideo.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ViewMyPostController.setPost(post);
                Starter.changeScene(Utils.GUI.MY_POST);
            }
        });

        return postVideo;
    }

    @FXML
    void followersLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.MY_FOLLOWERS); }

    @FXML
    void followingLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.MY_FOLLOWING); }

    @FXML
    void editButtonClickHandler(ActionEvent event) { CommonClickHandlers.editProfileButton(); }

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
