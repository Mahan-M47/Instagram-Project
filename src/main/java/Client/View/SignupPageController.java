package Client.View;

import Client.Controller.Data;
import Client.Controller.NetworkManager;
import Client.Controller.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class SignupPageController {
    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private PasswordField confirmPasswordTF;

    @FXML
    private Hyperlink loginInsteadHL;

    @FXML
    private Button signupButton;

    @FXML
    private Label errorLabel;

    @FXML
    void loginInsteadHLHandler(ActionEvent event) {
        Starter.changeScene("LoginPage");
    }

    @FXML
    void signupButtonClickHandler(ActionEvent event)
    {
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        if (checkTextFields(username, password) ) {
            Request req = new Request("signup", new Data.Basic(username, password));
            NetworkManager.putRequest(req);
        }
    }


    public boolean checkTextFields(String username, String password)
    {
        boolean flag = false;
        String confirmPassword = confirmPasswordTF.getText();

        if (username.equals("") && password.equals("")) {
            errorLabel.setText("Please Enter Your Username and Password.");
        }
        else if (username.equals("")) {
            errorLabel.setText("Please Enter Your Username.");
        }
        else if (password.equals("")) {
            errorLabel.setText("Please Enter Your Password.");
        }
        else if (confirmPassword.equals("")) {
            errorLabel.setText("Please Confirm Your Password.");
        }
        else if (!password.equals(confirmPassword)) {
            errorLabel.setText("The Passwords Didn't Match. Please Try Again.");
        }
        else {
            errorLabel.setText("");
            flag = true;
        }

        return flag;
    }

}
