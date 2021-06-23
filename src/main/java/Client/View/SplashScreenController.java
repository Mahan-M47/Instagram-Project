package Client.View;

import Client.Utils;
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

public class SplashScreenController implements Initializable {

    @FXML
    private AnchorPane mainAnch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!Starter.isSplashLoaded) {
            try {
                loadSplashScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadSplashScreen() throws IOException {
        Starter.isSplashLoaded = true;
        URL splash = Paths.get("./src/main/java/Client/Resources/SplashScreen.fxml").toUri().toURL();
        AnchorPane splashScreen = FXMLLoader.load(splash);
        mainAnch.getChildren().setAll(splashScreen);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1),splashScreen);
        fadeIn.setFromValue(0.5);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2),splashScreen);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setCycleCount(1);

        fadeIn.play();
        fadeIn.setOnFinished((e)-> fadeOut.play());
        fadeOut.setOnFinished((e)->{
            Starter.changeScene(Utils.GUI.LOGIN);
        });
    }

}
