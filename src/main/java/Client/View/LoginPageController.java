package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;


public class LoginPageController
{
    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink createNewAccountHL;

    @FXML
    void loginButtonClickHandler(ActionEvent event) {
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        if ( !username.equals("") && !password.equals("") ) {
            Request req = new Request("login", new Data.Basic(username, password));
            NetworkManager.putRequest(req);
        }

    }

    @FXML
    void createNewAccountHLHandler(ActionEvent event) throws IOException {
        Starter.changeScene("SignupPage");
    }

}
