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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    @FXML
    private ListView<String> listView;
    @FXML
    private TextField searchTF;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button startSearchButton, showProfileButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        ObservableList<String> searchResults = FXCollections.observableList(Utils.searchResults);

        if (searchResults.size() > 0) {
            listView.setItems(searchResults);
            listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            listView.setVisible(true);
            showProfileButton.setVisible(true);
        }

        Utils.searchResults = new ArrayList<>();
    }


    @FXML
    void startSearchClickListener(ActionEvent event)
    {
        String searchText = searchTF.getText();

        if (!searchText.equals("")) {
            Request req = new Request("search", new Data(searchText));
            NetworkManager.putRequest(req);
        }
    }

    @FXML
    public void showProfileButtonClickHandler(ActionEvent event)
    {
        String username = listView.getSelectionModel().getSelectedItem();

        if (username != null) {
            Request req = new Request("showProfile", new Data(username) );
            NetworkManager.putRequest(req);
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
