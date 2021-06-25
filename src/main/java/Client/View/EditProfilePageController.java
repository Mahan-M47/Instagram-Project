package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Model.User;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class EditProfilePageController implements Initializable
{
    @FXML
    private ImageView profilePicture;
    @FXML
    private TextArea BioTF;
    @FXML
    private Label updateLabel;
    @FXML
    private Button chatsButton, searchButton, homeButton, postButton, profileButton;
    @FXML
    private Button backButton, applyButton;

    private File chosenFile;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        BioTF.setText(Utils.currentUserObj.getBioText());

        if (Utils.currentUserObj.getProfilePicture() != null) {
            InputStream in = new ByteArrayInputStream( Utils.currentUserObj.getProfilePicture() );
            Image img = new Image(in);
            profilePicture.setImage(img);
        }
    }


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
                profilePicture.setImage(img);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void applyButtonClickHandler(ActionEvent event)
    {
        User user = new User();
        user.setUsername(Utils.currentUser);
        user.setBioText(BioTF.getText());

        if (chosenFile != null) {
            user.setProfilePicture( chosenFile.getPath() );
        }

        Request req = new Request (Utils.REQ.BIO, new Data(user));
        NetworkManager.putRequest(req);
        updateLabel.setVisible(true);
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
