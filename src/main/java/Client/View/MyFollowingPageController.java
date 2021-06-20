package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import java.net.URL;
import java.util.ResourceBundle;

public class MyFollowingPageController implements Initializable
{
    @FXML
    private ListView<String> listView;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, backButton;
    @FXML
    private Button unfollowButton, showProfileButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<String> following = FXCollections.observableList(Utils.currentUserObj.getFollowing());
        listView.setItems(following);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    @FXML
    void backButtonClickHandler(ActionEvent event) {
        Starter.changeScene("MyProfilePage");
    }

    @FXML
    void showProfileButtonClickHandler(ActionEvent event)
    {
        String username = listView.getSelectionModel().getSelectedItem();

        if (username != null) {
            Request req = new Request("showProfile", new Data(username) );
            NetworkManager.putRequest(req);
        }
    }

    @FXML
    void unfollowButtonClickHandler(ActionEvent event)
    {
        String username = listView.getSelectionModel().getSelectedItem();

        if (username != null)
        {
            Request req = new Request("unfollow", new Data(Utils.currentUser, username) );
            NetworkManager.putRequest(req);

            Utils.currentUserObj.getFollowing().remove(username);
            Starter.changeScene("MyFollowingPage");
        }
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) { Starter.changeScene("Timeline"); }

    @FXML
    void profileButtonClickHandler(ActionEvent event) { Starter.changeScene("MyProfilePage"); }

    @FXML
    void searchButtonClickHandler(ActionEvent event) {
        Starter.changeScene("SearchPage");
    }

    @FXML
    void postButtonClickHandler(ActionEvent event) { Starter.changeScene("CreatePostPage"); }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) {
    }

}
