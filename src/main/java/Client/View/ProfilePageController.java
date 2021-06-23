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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProfilePageController implements Initializable {

    @FXML
    private ImageView profilePicture;
    @FXML
    private VBox scrollVBox;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button messageButton, followButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        followersLink.setText("" + Utils.receivedUserObj.getFollowers().size());
        followingLink.setText("" + Utils.receivedUserObj.getFollowing().size());
        usernameLabel.setText(Utils.receivedUserObj.getUsername());
        bioLabel.setText(Utils.receivedUserObj.getBioText());

        if ( Utils.receivedUserObj.getFollowers().contains(Utils.currentUser) ) {
            followButton.setText("Unfollow");
        }

        addPosts();
    }

    public void addPosts()
    {
        List<Post> posts = Utils.receivedUserObj.getPosts();
        Post post = new PostImage("temp username", "temp caption"); // temp

        for (int i = 0; i < 7; i++)  // changes to enhanced for loop over the posts List
        {
            HBox hBox = new HBox(10);
            hBox.setPrefSize(770,250);

            for (int j = 0; j < 3; j++)
            {
                File file = new File("src/main/java/Client/Resources/GUI_Images/TEST_IMG.jpg");

                try {
                    InputStream in = new FileInputStream(file);
                    Image img = new Image(in);
                    ImageView imageView = new ImageView(img);
                    imageView.setFitHeight(250);
                    imageView.setFitWidth(250);

                    imageView.setOnMouseClicked(new EventHandler() {
                        @Override
                        public void handle(Event event) {
                            ViewPostController.setPost(post);
                            Starter.changeScene(Utils.GUI.POST); }
                    });

                    hBox.getChildren().add(imageView);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            scrollVBox.getChildren().add(hBox);
        }
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
