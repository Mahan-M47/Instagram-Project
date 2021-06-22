package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
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
        for (int i = 0; i < 10; i++)
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            Button likeButton = new Button("Like");
            Button commentsButton = new Button("Comments");
            Hyperlink usernameLink = new Hyperlink("username");
            Label likeLabel = new Label("0");
            Label commentsLabel = new Label("0");

            createButtons(likeButton, commentsButton, usernameLink, likeLabel, commentsLabel);

            AnchorPane pane = new AnchorPane();
            pane.setPrefSize(800, 200);

            pane.getChildren().add(imageView);
            pane.getChildren().add(usernameLink);
            pane.getChildren().add(likeButton);
            pane.getChildren().add(likeLabel);
            pane.getChildren().add(commentsButton);
            pane.getChildren().add(commentsLabel);

            scrollVBox.getChildren().add(pane);
        }
    }

    public void createButtons(Button likeButton, Button commentsButton, Hyperlink usernameLink,
                              Label likeLabel, Label commentsLabel)
    {
        likeButton.setPrefSize(130,50);
        commentsButton.setPrefSize(130,50);
        usernameLink.setPrefSize(270,65);
        likeLabel.setPrefSize(50,50);
        commentsLabel.setPrefSize(50,50);

        likeButton.setFont( new Font("System",20) );
        commentsButton.setFont( new Font("System",20) );
        usernameLink.setFont( new Font("System",30) );
        likeLabel.setFont( new Font("System",25) );
        commentsLabel.setFont( new Font("System",25) );

        likeButton.setLayoutX(585);
        likeButton.setLayoutY(150);

        commentsButton.setLayoutX(585);
        commentsButton.setLayoutY(300);

        usernameLink.setLayoutX(515);
        usernameLink.setLayoutY(25);

        likeLabel.setLayoutX(625);
        likeLabel.setLayoutY(200);

        commentsLabel.setLayoutX(625);
        commentsLabel.setLayoutY(350);

        usernameLink.setAlignment(Pos.CENTER);
        likeLabel.setAlignment(Pos.CENTER);
        commentsLabel.setAlignment(Pos.CENTER);

        likeButton.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                //send like request
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
                Request req = new Request(Utils.REQ.PROFILE, new Data(usernameLink.getText()) );
            }
        });
    }


    @FXML
    void loadMoreButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI.TIMELINE);  //should be removed
        Request req = new Request(Utils.REQ.TIMELINE, new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void profileButtonClickHandler(ActionEvent event) {
        Request req = new Request(Utils.REQ.MY_PROFILE, new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void searchButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.SEARCH); }

    @FXML
    void postButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.CREATE_POST); }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) { }

    @FXML
    void logoutButtonClickHandler(ActionEvent event)
    {
        Request req = new Request(Utils.REQ.LOGOUT,new Data(Utils.currentUser) );
        NetworkManager.putRequest(req);
        Utils.currentUser = "";
        Starter.changeScene(Utils.GUI.LOGIN);
    }

}