package Client.View;

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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MyProfilePageController implements Initializable
{
    @FXML
    private ImageView profilePicture;
    @FXML
    private VBox scrollVBox;
    @FXML
    private Hyperlink followingLink, followersLink;
    @FXML
    private Label usernameLabel, bioLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, editButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        followersLink.setText("" + Utils.currentUserObj.getFollowers().size());
        followingLink.setText("" + Utils.currentUserObj.getFollowing().size());
        usernameLabel.setText(Utils.currentUserObj.getUsername());
        bioLabel.setText(Utils.currentUserObj.getBioText());

        addPosts();
    }

    public void addPosts()
    {
        List<Post> posts = Utils.currentUserObj.getPosts();
        double count = posts.size();

        for (int i = 0; i < Math.ceil(count/3); i++)  // changes to enhanced for loop over the posts List
        {
            HBox hBox = new HBox(10);
            hBox.setPrefSize(770,250);

            int x = 3*i + 3;
            if (x - count == 1) {
                x = 3*i + 2;
            }
            else if (x - count == 2) {
                x = 3*i + 1;
            }

            for (int j = 3*i; j < x; j++)
            {
                Post post = posts.get(j);
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
                            ViewMyPostController.setPost(post);
                            Starter.changeScene(Utils.GUI.MY_POST);
                        }
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
    void followersLinkClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI.MY_FOLLOWERS); }

    @FXML
    void followingLinkClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI.MY_FOLLOWING);
    }

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
