package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.Post;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TimelineController implements Initializable
{
    @FXML
    private AnchorPane scrollAnchorPane;
    @FXML
    private VBox scrollVBox;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, logoutButton, loadMoreButton;

    private int postCounter = 0;
    private List<MediaPlayer> mediaPlayerList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mediaPlayerList = new ArrayList<>();
        loadPosts();
    }

    void loadPosts()
    {
        List<Post> posts = Utils.timelineData;

        for (int i = postCounter; i < posts.size() && i < postCounter + 10; i++)
        {
            scrollAnchorPane.setPrefHeight(scrollAnchorPane.getPrefHeight() + 600);
            scrollVBox.setPrefHeight(scrollVBox.getPrefHeight() + 600);
            Post post = posts.get(i);

            Hyperlink usernameLink = new Hyperlink(post.getUsername());
            Button commentsButton = new Button("Comments");
            Label commentsLabel = new Label("" + post.getComments().size());
            Button likeButton = new Button("Like");
            Label likeLabel = new Label("" + post.getLikedBy().size());
            Label captionLabel = new Label(post.getCaption());
            Label dateLabel = new Label(post.getDate().toString());

            Button sendButton = new Button("Send");
            TextField commentTF = new TextField();

            Label playLabel = new Label("PLAY");


            if (post.getLikedBy().contains(Utils.currentUser)) {
                likeButton.setText("Unlike");
            }

            createButtons(likeButton, commentsButton, usernameLink, likeLabel, commentsLabel, captionLabel, dateLabel, post);
            ScrollPane commentsScrollPane = createCommentScrollPane(commentTF, commentsButton, sendButton, commentsLabel, post);

            AnchorPane pane = new AnchorPane();
            pane.setPrefSize(900, 600);

            if ( post.getPostType().equals(Utils.POST_IMAGE) ) {
                ImageView postImage = loadImage(post);
                pane.getChildren().add(postImage);
            }
            else {
                MediaView postVideo = loadVideo(post, playLabel);
                pane.getChildren().add(postVideo);
                pane.getChildren().add(playLabel);
            }

            pane.getChildren().add(usernameLink);
            pane.getChildren().add(likeButton);
            pane.getChildren().add(likeLabel);
            pane.getChildren().add(commentsButton);
            pane.getChildren().add(commentsLabel);
            pane.getChildren().add(captionLabel);
            pane.getChildren().add(dateLabel);
            pane.getChildren().add(commentTF);
            pane.getChildren().add(sendButton);
            pane.getChildren().add(commentsScrollPane);

            Separator separator = new Separator();
            separator.setPrefWidth(1000);
            separator.setLayoutY(550);
            pane.getChildren().add(separator);

            scrollVBox.getChildren().add(pane);
        }

        if ( postCounter + 10 < posts.size() )
        {
            Button loadMoreButton = new Button("Load More Posts");
            loadMoreButton.setFont(new Font("System", 20));

            loadMoreButton.setOnAction(new EventHandler() {
                @Override
                public void handle(Event event)
                {
                    scrollAnchorPane.setPrefHeight(scrollAnchorPane.getPrefHeight() + 50);
                    scrollVBox.setPrefHeight(scrollVBox.getPrefHeight() + 50);
                    scrollVBox.getChildren().add(new Label(""));
                    postCounter += 10;
                    loadPosts();
                }
            });

            scrollVBox.getChildren().add(loadMoreButton);
        }
    }

    public ImageView loadImage(Post post)
    {
        InputStream in = new ByteArrayInputStream( post.getFileBytes() );
        Image img = new Image(in);
        ImageView postImage = new ImageView(img);
        postImage.setFitHeight(500);
        postImage.setFitWidth(500);

        return postImage;
    }

    public MediaView loadVideo(Post post, Label playLabel)
    {
        playLabel.setStyle(Utils.PLAY_BUTTON_CSS);
        playLabel.setLayoutX(185);
        playLabel.setLayoutY(210);

        try {
            Files.createDirectories( Paths.get(Utils.DIR_CLIENT_POST_VIDEOS) );
            File file = new File( Utils.DIR_CLIENT_POST_VIDEOS + post.getID() + post.getPostType());
            OutputStream out = new FileOutputStream(file);
            out.write(post.getFileBytes());

            Media media = new Media(file.toURI().toURL().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            MediaView postVideo = new MediaView(mediaPlayer);

            postVideo.setFitHeight(500);
            postVideo.setFitWidth(500);

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.setCycleCount(mediaPlayer.getCycleCount() + 1);
                    CommonClickHandlers.playButton(mediaPlayer, playLabel);
                }
            });

            mediaPlayer.setCycleCount(mediaPlayer.getCycleCount() + 1);
            mediaPlayerList.add(mediaPlayer);

            postVideo.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) { CommonClickHandlers.playButton(mediaPlayer, playLabel); }
            });

            playLabel.setOnMouseClicked(new EventHandler() {
                @Override
                public void handle(Event event) { CommonClickHandlers.playButton(mediaPlayer, playLabel); }
            });

            return postVideo;
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createButtons(Button likeButton, Button commentsButton, Hyperlink usernameLink, Label likeLabel,
                              Label commentsLabel, Label captionLabel, Label dateLabel, Post post)
    {
        commentsButton.setPrefSize(130,50);
        commentsLabel.setPrefSize(50,50);
        captionLabel.setPrefSize(300,290);
        usernameLink.setPrefSize(300,65);
        likeButton.setPrefSize(130,50);
        dateLabel.setPrefSize(300,30);
        likeLabel.setPrefSize(50,50);

        commentsButton.setFont( new Font("System",20) );
        commentsLabel.setFont( new Font("System",25) );
        captionLabel.setFont( new Font("Calibri Light", 24));
        usernameLink.setFont( new Font("System",30) );
        likeButton.setFont( new Font("System",20) );
        dateLabel.setFont( new Font("System",18) );
        likeLabel.setFont( new Font("System",25) );

        commentsButton.setStyle("-fx-background-color: #d4d4d4");
        likeButton.setStyle("-fx-background-color: #d4d4d4");
        dateLabel.setStyle("-fx-text-fill: #5289a1");

        likeButton.setLayoutX(550);
        likeButton.setLayoutY(100);

        commentsButton.setLayoutX(720);
        commentsButton.setLayoutY(100);

        usernameLink.setLayoutX(550);
        usernameLink.setLayoutY(10);

        likeLabel.setLayoutX(590);
        likeLabel.setLayoutY(150);

        commentsLabel.setLayoutX(760);
        commentsLabel.setLayoutY(150);

        captionLabel.setLayoutX(550);
        captionLabel.setLayoutY(210);
        captionLabel.setWrapText(true);

        dateLabel.setLayoutX(550);
        dateLabel.setLayoutY(470);

        usernameLink.setAlignment(Pos.CENTER);
        commentsLabel.setAlignment(Pos.CENTER);
        captionLabel.setAlignment(Pos.CENTER);
        dateLabel.setAlignment(Pos.CENTER);
        likeLabel.setAlignment(Pos.CENTER);

        likeButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                CommonClickHandlers.likeButton(likeButton, likeLabel, post);
            }
        });

        usernameLink.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                CommonClickHandlers.showProfileButton(usernameLink.getText());
            }
        });
    }

    public ScrollPane createCommentScrollPane(TextField commentTF, Button commentsButton, Button sendButton,
                              Label commentsLabel, Post post)
    {
        ScrollPane commentsScrollPane = new ScrollPane();
        VBox commentsVBox = new VBox();
        commentsVBox.setPrefSize(205,1000);


        commentTF.setFont( new Font("System",14) );
        commentTF.setPrefSize(220,30);
        commentTF.setLayoutX(880);
        commentTF.setLayoutY(426);
        commentTF.setPromptText("Leave a Comment");

        sendButton.setFont( new Font("System",14) );
        sendButton.setStyle("-fx-background-color: #d4d4d4");
        sendButton.setPrefSize(51,30);
        sendButton.setLayoutX(965);
        sendButton.setLayoutY(460);

        commentsScrollPane.setPrefSize(220,400);
        commentsScrollPane.setContent(commentsVBox);
        commentsScrollPane.setPannable(true);
        commentsScrollPane.setLayoutX(880);
        commentsScrollPane.setLayoutY(25);

        commentsScrollPane.setVisible(false);
        commentTF.setVisible(false);
        sendButton.setVisible(false);


        for (String commentText : post.getComments()) {
            Label comment = CommonClickHandlers.createCommentLabel(commentText);
            commentsVBox.getChildren().add(comment);
        }

        commentsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                CommonClickHandlers.commentsButton(commentsScrollPane, sendButton, commentTF);
            }
        });

        sendButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                CommonClickHandlers.sendCommentButton(commentsVBox, commentTF, commentsLabel, post);
            }
        });

        return commentsScrollPane;
    }


    public void stopMediaPlayers()
    {
        for (MediaPlayer mp : mediaPlayerList) {
            mp.stop();
        }
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) {
        stopMediaPlayers();
        CommonClickHandlers.homeButton();
    }

    @FXML
    void profileButtonClickHandler(ActionEvent event) {
        stopMediaPlayers();
        CommonClickHandlers.myProfileButton();
    }

    @FXML
    void searchButtonClickHandler(ActionEvent event) {
        stopMediaPlayers();
        CommonClickHandlers.searchButton();
    }

    @FXML
    void postButtonClickHandler(ActionEvent event) {
        stopMediaPlayers();
        CommonClickHandlers.postButton();
    }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) {
        stopMediaPlayers();
        CommonClickHandlers.chatsButton();
    }

    @FXML
    void logoutButtonClickHandler(ActionEvent event)
    {
        stopMediaPlayers();
        Request req = new Request(Utils.REQ.LOGOUT,new Data(Utils.currentUser) );
        NetworkManager.putRequest(req);
        Utils.currentUser = "";
        Starter.changeScene(Utils.GUI.LOGIN);
    }

}