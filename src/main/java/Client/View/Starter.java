package Client.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class Starter extends Application {
    public static boolean isSplashLoaded = false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url = Paths.get("./src/main/java/Client/Resources/LoginPage.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Instagram");
        primaryStage.setScene(new Scene(root, 1300, 850));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
