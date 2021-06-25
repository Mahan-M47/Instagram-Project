package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.Post;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button chooseFileButton, createPostButton;

    private File chosenFile;

    @FXML
    void chooseFileButtonClickHandler(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        chosenFile = fileChooser.showOpenDialog(new Stage());

        if (chosenFile != null)
        {
            try {
                InputStream in = new FileInputStream(chosenFile);
                Image img = new Image(in);
                postImage.setImage(img);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void createPostButtonClickHandler(ActionEvent event) {
        Post post = new Post(Utils.currentUser, captionTF.getText(), chosenFile.getPath());
        Request req = new Request( Utils.REQ.CREATE_POST, new Data(Utils.currentUser, post) );
        NetworkManager.putRequest(req);
        ViewMyPostController.setPost(post);
        Starter.changeScene(Utils.GUI.MY_POST);
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

}
