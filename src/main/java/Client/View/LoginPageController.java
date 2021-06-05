package Client.View;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private AnchorPane mainanch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Starter.isSplashLoaded) {
            try {
                loadSplashScreen();
            } catch (IOException e) {
            }
        }
    }

    public void loadSplashScreen() throws IOException {
        Starter.isSplashLoaded = true;
        URL splash = Paths.get("./src/main/java/Client/Resources/SplashScreen.fxml").toUri().toURL();
        AnchorPane splashScreen = FXMLLoader.load(splash);
        mainanch.getChildren().setAll(splashScreen);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1),splashScreen);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2),splashScreen);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();
        fadeIn.setOnFinished((e)-> fadeOut.play());
        fadeOut.setOnFinished((e)->{
            try {
                URL login = Paths.get("./src/main/java/Client/Resources/LoginPage.fxml").toUri().toURL();
                AnchorPane mainPage = FXMLLoader.load(login);
                mainanch.getChildren().setAll(mainPage);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });
    }

}
