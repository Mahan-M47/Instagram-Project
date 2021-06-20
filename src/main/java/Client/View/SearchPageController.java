package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    @FXML
    private TextField searchTF;
    @FXML
    private Hyperlink link1, link2, link3, link4, link5, link6, link7, link8;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button startSearchButton;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        List<String> searchResults = Utils.searchResults;

        if (searchResults.size() > 0) {
            link1.setText( searchResults.get(0) );
        if (searchResults.size() > 1) {
            link2.setText( searchResults.get(1) );
        if (searchResults.size() > 2) {
            link3.setText( searchResults.get(2) );
        if (searchResults.size() > 3) {
            link4.setText( searchResults.get(3) );
        if (searchResults.size() > 4) {
            link5.setText( searchResults.get(4) );
        if (searchResults.size() > 5) {
            link6.setText( searchResults.get(5) );
        if (searchResults.size() > 6) {
            link7.setText( searchResults.get(6) );
        if (searchResults.size() > 7) {
            link8.setText( searchResults.get(7) );
        }}}}}}}}

        Utils.searchResults = new ArrayList<>();
    }


    @FXML
    void startSearchClickListener(ActionEvent event) {
        String searchText = searchTF.getText();

        if (!searchText.equals("")) {
            Request req = new Request("search", new Data(searchText));
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

    @FXML
    void link1ClickHandler(ActionEvent event) { showProfile(link1.getText()); }
    @FXML
    void link2ClickHandler(ActionEvent event) { showProfile(link2.getText()); }
    @FXML
    void link3ClickHandler(ActionEvent event) { showProfile(link3.getText()); }
    @FXML
    void link4ClickHandler(ActionEvent event) { showProfile(link4.getText()); }
    @FXML
    void link5ClickHandler(ActionEvent event) { showProfile(link5.getText()); }
    @FXML
    void link6ClickHandler(ActionEvent event) { showProfile(link6.getText()); }
    @FXML
    void link7ClickHandler(ActionEvent event) { showProfile(link7.getText()); }
    @FXML
    void link8ClickHandler(ActionEvent event) { showProfile(link8.getText()); }


    public void showProfile(String username)
    {
        if (!username.equals("")) {
            Request req = new Request("showProfile", new Data(username) );
            NetworkManager.putRequest(req);
        }
    }

}
