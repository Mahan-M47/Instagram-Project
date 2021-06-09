package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    private Label errorLabel;

    @FXML
    void createNewAccountHLHandler(ActionEvent event) {
        Starter.changeScene("SignupPage");
    }

    @FXML
    void loginButtonClickHandler(ActionEvent event)
    {
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        if (checkTextFields(username, password) ) {
            Request req = new Request("login", new Data.Basic(username, password));
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
