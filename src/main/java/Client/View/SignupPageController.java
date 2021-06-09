package Client.View;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

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
    void loginInsteadHLHandler(ActionEvent event) throws IOException {
        URL signup = Paths.get("./src/main/java/Client/Resources/LoginPage.fxml").toUri().toURL();
        Parent signupPage = FXMLLoader.load(signup);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(signupPage);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signupBtnClickHandler(ActionEvent event) {
        errorLbl.setText("");
        if (samePass()){
            String username = usernameTF.getText();
            String password = passwordTF.getText();
        }
        else {
            if (passwordTF.getText().isEmpty()){
                errorLbl.setText("Enter password");
            }
            else {
                errorLbl.setText("Those passwords didn’t match. Try again.");
            }
        }
    }

    private boolean samePass(){
        if (!passwordTF.getText().isEmpty()) {
            return passwordTF.getText().equals(confirmPasswordTF.getText());
        }
        return false;
    }
}
