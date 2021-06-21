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
//        Utils.currentUserObj.setBioText(BioTF.getText());
    }

    @FXML
    void backButtonClickHandler(ActionEvent event) {
        Request req = new Request("showMyProfile", new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void homeButtonClickHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI_TIMELINE);  //should be removed
        Request req = new Request("getTimeline", new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void profileButtonClickHandler(ActionEvent event) {
        Request req = new Request("showMyProfile", new Data(Utils.currentUser));
        NetworkManager.putRequest(req);
    }

    @FXML
    void searchButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_SEARCH); }

    @FXML
    void postButtonClickHandler(ActionEvent event) { Starter.changeScene(Utils.GUI_CREATE_POST); }

    @FXML
    void chatsButtonClickHandler(ActionEvent event) { }


}
