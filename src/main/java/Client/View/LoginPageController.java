package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import Client.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable
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
    private Label errorLabel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorLabel.setText(Utils.LOGIN_ERROR_TEXT);
    }


    @FXML
    void createNewAccountHLHandler(ActionEvent event) {
        Starter.changeScene(Utils.GUI_SIGN_UP);
        Utils.resetErrorTexts();
    }

    @FXML
    void loginButtonClickHandler(ActionEvent event)
    {
        String username = usernameTF.getText().toLowerCase();
        String password = passwordTF.getText();

        if ( checkTextFields(username, password) ) {
            Request req = new Request("login", new Data(username, password));
            NetworkManager.putRequest(req);
        }
    }

    public boolean checkTextFields (String username, String password)
    {
        boolean flag = false;

        if ( username.equals("") && password.equals("") ) {
            errorLabel.setText("Please Enter Your Username and Password.");
        }
        else if (username.equals("")) {
            errorLabel.setText("Please Enter Your Username.");
        }
        else if (password.equals("")) {
            errorLabel.setText("Please Enter Your Password.");
        }
        else {
            errorLabel.setText("");
            flag = true;
        }

        return flag;
    }

}
