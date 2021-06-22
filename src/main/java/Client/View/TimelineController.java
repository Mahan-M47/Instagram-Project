package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.Post;
import Client.Model.PostImage;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TimelineController implements Initializable
{
    @FXML
    private VBox scrollVBox;
    @FXML
    private Label timelineLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, logoutButton, loadMoreButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timelineLabel.setText("Logged in as: " + Utils.currentUser);
        addPosts();
    }

    void addPosts()
    {
        List<Post> posts = Utils.timelinePosts;
        Post post = new PostImage("temp username", "temp caption"); // temp

        for (int i = 0; i < 10; i++)  // changes to enhanced for loop over the posts List.
        {
            File file = new File("src/main/java/Client/Resources/GUI_Images/TEST_IMG.jpg");
            ImageView imageView = null;

            try {
                InputStream in = new FileInputStream(file);
                Image img = new Image(in);
                imageView = new ImageView(img);
                imageView.setFitHeight(500);
                imageView.setFitWidth(500);
                imageView.setLayoutX(0);
                imageView.setLayoutX(0);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            Button likeButton = new Button("Like");
            Button commentsButton = new Button("Comments");
            Hyperlink usernameLink = new Hyperlink(post.getUsername());
            Label likeLabel = new Label(post.getLikes().toString());
            Label commentsLabel = new Label("" + post.getComments().size());
            Label captionLabel = new Label(post.getCaption());

            if (post.getLikedBy().contains(Utils.currentUser)) {
                likeButton.setText("Unlike");
            }

            createButtons(likeButton, commentsButton, usernameLink, likeLabel, commentsLabel, captionLabel, post);

            AnchorPane pane = new AnchorPane();
            pane.setPrefSize(900, 500);

            pane.getChildren().add(imageView);
            pane.getChildren().add(usernameLink);
            pane.getChildren().add(likeButton);
            pane.getChildren().add(likeLabel);
            pane.getChildren().add(commentsButton);
            pane.getChildren().add(commentsLabel);
            pane.getChildren().add(captionLabel);

            scrollVBox.getChildren().add(pane);
        }
    }

    public void createButtons(Button likeButton, Button commentsButton, Hyperlink usernameLink,
                              Label likeLabel, Label commentsLabel, Label captionLabel, Post post)
    {
        likeButton.setPrefSize(130,50);
        commentsButton.setPrefSize(130,50);
        usernameLink.setPrefSize(300,65);
        likeLabel.setPrefSize(50,50);
        commentsLabel.setPrefSize(50,50);
        captionLabel.setPrefSize(300,290);

        likeButton.setFont( new Font("System",20) );
        commentsButton.setFont( new Font("System",20) );
        usernameLink.setFont( new Font("System",30) );
        likeLabel.setFont( new Font("System",25) );
        commentsLabel.setFont( new Font("System",25) );
        captionLabel.setFont( new Font("Calibri Light", 24));

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

        usernameLink.setAlignment(Pos.CENTER);
        likeLabel.setAlignment(Pos.CENTER);
        commentsLabel.setAlignment(Pos.CENTER);
        captionLabel.setAlignment(Pos.TOP_LEFT);

        likeButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event)
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
        });

        commentsButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                //load comment page
            }
        });

        usernameLink.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                CommonClickHandlers.showProfileButton(usernameLink.getText());
            }
        });
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