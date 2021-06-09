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
    private Button signupBtn;

    @FXML
    private Label errorLbl;

    @FXML
    void loginInsteadHLHandler(ActionEvent event) {
        Starter.changeScene("LoginPage");
    }

    @FXML
    void signupBtnClickHandler(ActionEvent event) {
        errorLbl.setText("");
        if (samePass()) {
            String username = usernameTF.getText();
            String password = passwordTF.getText();

            if ( !username.equals("") && !password.equals("") ) {
                Request req = new Request("signup", new Data.Basic(username, password));
                NetworkManager.putRequest(req);
            }
        }
        else {
            if (passwordTF.getText().isEmpty()){
                errorLbl.setText("Enter Password");
            }
            else {
                errorLbl.setText("Those Passwords Didn't Match. Try Again.");
            }
        }
    }

    private boolean samePass() {
        if (!passwordTF.getText().isEmpty()) {
            return passwordTF.getText().equals(confirmPasswordTF.getText());
        }
        return false;
    }
}
