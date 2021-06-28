package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatPageController implements Initializable {

    @FXML
    private Label username;

    @FXML
    private TextField messageTF;

    @FXML
    private Button sendBtn;

    @FXML
    private AnchorPane background;

    @FXML
    void sendButtonClickHandler(ActionEvent event) {

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
