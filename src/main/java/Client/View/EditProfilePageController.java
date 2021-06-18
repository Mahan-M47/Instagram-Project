package Client.View;

import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProfilePageController implements Initializable
{
    @FXML
    private ImageView profilePicture;
    @FXML
    private TextArea BioTF;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button backButton, applyButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BioTF.setText(Utils.currentUserObj.getBioText());
    }


    @FXML
    void applyButtonClickHandler(ActionEvent event) {
        Utils.currentUserObj.setBioText(BioTF.getText());
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        Starter.changeScene("MyProfilePage");
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
    void postButtonClickHandler(ActionEvent event) {
    }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) {
    }


}
