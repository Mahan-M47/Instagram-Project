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
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MyFollowersPageController implements Initializable
{
    @FXML
    private ListView<String> listView;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, backButton;
    @FXML
    private Button removeButton, showProfileButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<String> followers = FXCollections.observableList(Utils.currentUserObj.getFollowers());
        listView.setItems(followers);
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
    void removeButtonClickHandler(ActionEvent event)
    {
        String username = listView.getSelectionModel().getSelectedItem();

        if (username != null)
        {
            Request req = new Request("unfollow", new Data(username, Utils.currentUser) );
            NetworkManager.putRequest(req);

            Utils.currentUserObj.getFollowers().remove(username);
            Starter.changeScene("MyFollowersPage");
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
