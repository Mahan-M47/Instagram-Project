package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    void chooseFileButtonClickHandler(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null)
        {
            try {
                InputStream in = new FileInputStream(file);
                Image img = new Image(in);
                profilePicture.setImage(img);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void applyButtonClickHandler(ActionEvent event) {
        Request req = new Request (Utils.REQ.BIO, new Data( Utils.currentUser, BioTF.getText()) );
        NetworkManager.putRequest(req);
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        CommonClickHandlers.myProfileButton();
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
