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

public class FollowersPageController implements Initializable {
    @FXML
    private ListView<String> listView;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton, backButton;
    @FXML
    private Button showProfileButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> followers = FXCollections.observableList(Utils.receivedUserObj.getFollowers());
        listView.setItems(followers);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }


    @FXML
    void backButtonClickHandler(ActionEvent event) {
        Request req = new Request(Utils.REQ.PROFILE, new Data(Utils.receivedUserObj.getUsername()) );
        NetworkManager.putRequest(req);
    }

    @FXML
    void showProfileButtonClickHandler(ActionEvent event)
    {
        String username = listView.getSelectionModel().getSelectedItem();

        if (username != null) {
            Request req = new Request(Utils.REQ.PROFILE, new Data(username));
            NetworkManager.putRequest(req);
        }
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

}