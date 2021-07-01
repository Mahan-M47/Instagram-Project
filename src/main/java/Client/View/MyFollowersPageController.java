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
        listView.setStyle(" -fx-font: 26pt \"System\" ");
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    @FXML
    void backButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.myProfileButton();
    }

    @FXML
    public void showProfileButtonClickHandler(ActionEvent event) {
        String username = listView.getSelectionModel().getSelectedItem();
        CommonClickHandlers.showProfileButton(username);
    }

    @FXML
    void removeButtonClickHandler(ActionEvent event)
    {
        String username = listView.getSelectionModel().getSelectedItem();

        if (username != null)
        {
            Utils.currentUserObj.getFollowers().remove(username);
            Request req = new Request(Utils.REQ.UNFOLLOW, new Data(username, Utils.currentUser) );
            NetworkManager.putRequest(req);
        }
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
