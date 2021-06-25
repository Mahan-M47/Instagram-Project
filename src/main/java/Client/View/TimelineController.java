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
import javafx.scene.text.Font;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TimelineController implements Initializable
{
    @FXML
    private VBox scrollVBox;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, logoutButton, loadMoreButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addPosts();
    }

    void addPosts()
    {
        List<Post> posts = Utils.timelineData;

        for (Post post : posts)
        {
            InputStream in = new ByteArrayInputStream( post.getFileBytes() );
            Image img = new Image(in);
            ImageView imageView = new ImageView(img);
            imageView.setFitHeight(500);
            imageView.setFitWidth(500);
            imageView.setLayoutX(0);
            imageView.setLayoutX(0);

            Hyperlink usernameLink = new Hyperlink(post.getUsername());
            Button commentsButton = new Button("Comments");
            Label commentsLabel = new Label("" + post.getComments().size());
            Button likeButton = new Button("Like");
            Label likeLabel = new Label("" + post.getLikedBy().size());
            Label captionLabel = new Label(post.getCaption());

            Button sendButton = new Button("Send");
            TextField commentTF = new TextField();
            commentTF.setPromptText("Leave a Comment");

            if (post.getLikedBy().contains(Utils.currentUser)) {
                likeButton.setText("Unlike");
            }

            createButtons(likeButton, commentsButton, sendButton, usernameLink, likeLabel, commentsLabel, captionLabel, post);
            ScrollPane commentsScrollPane =
                    createCommentScrollPane(commentTF, commentsButton, sendButton, commentsLabel, post);

            AnchorPane pane = new AnchorPane();
            pane.setPrefSize(900, 500);

            pane.getChildren().add(imageView);
            pane.getChildren().add(usernameLink);
            pane.getChildren().add(likeButton);
            pane.getChildren().add(likeLabel);
            pane.getChildren().add(commentsButton);
            pane.getChildren().add(commentsLabel);
            pane.getChildren().add(captionLabel);
            pane.getChildren().add(commentTF);
            pane.getChildren().add(sendButton);
            pane.getChildren().add(commentsScrollPane);

            scrollVBox.getChildren().add(pane);
        }
    }

    public void createButtons(Button likeButton, Button commentsButton, Button sendButton, Hyperlink usernameLink,
                              Label likeLabel, Label commentsLabel, Label captionLabel, Post post)
    {
        commentsButton.setPrefSize(130,50);
        commentsLabel.setPrefSize(50,50);
        captionLabel.setPrefSize(300,290);
        usernameLink.setPrefSize(300,65);
        likeButton.setPrefSize(130,50);
        likeLabel.setPrefSize(50,50);


        commentsButton.setFont( new Font("System",20) );
        commentsLabel.setFont( new Font("System",25) );
        captionLabel.setFont( new Font("Calibri Light", 24));
        usernameLink.setFont( new Font("System",30) );
        likeButton.setFont( new Font("System",20) );
        likeLabel.setFont( new Font("System",25) );

        commentsButton.setStyle("-fx-background-color: #d4d4d4");
        likeButton.setStyle("-fx-background-color: #d4d4d4");

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

        commentsLabel.setAlignment(Pos.CENTER);
        usernameLink.setAlignment(Pos.CENTER);
        captionLabel.setAlignment(Pos.TOP_LEFT);
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

    public ScrollPane createCommentScrollPane(TextField commentsTF, Button commentsButton, Button sendButton,
                              Label commentsLabel, Post post)
    {
        ScrollPane commentsScrollPane = new ScrollPane();
        VBox commentsVBox = new VBox();
        commentsVBox.setPrefSize(205,1000);

        commentsTF.setFont( new Font("System",14) );
        commentsTF.setPrefSize(220,30);
        commentsTF.setLayoutX(880);
        commentsTF.setLayoutY(426);

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
        commentsTF.setVisible(false);
        sendButton.setVisible(false);


        for (String commentText : post.getComments()) {
            Label comment = CommonClickHandlers.createCommentLabel(commentText);
            commentsVBox.getChildren().add(comment);
        }

        commentsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                CommonClickHandlers.commentsButton(commentsScrollPane, sendButton, commentsTF);
            }
        });

        sendButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                CommonClickHandlers.sendButton(commentsVBox, commentsTF, commentsLabel, post);
            }
        });

        return commentsScrollPane;
    }


    @FXML
    void loadMoreButtonClickHandler(ActionEvent event) {
        //reload page with another set of pictures
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

    @FXML
    void logoutButtonClickHandler(ActionEvent event)
    {
        Request req = new Request(Utils.REQ.LOGOUT,new Data(Utils.currentUser) );
        NetworkManager.putRequest(req);
        Utils.currentUser = "";
        Starter.changeScene(Utils.GUI.LOGIN);
    }

}