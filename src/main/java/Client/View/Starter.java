package Client.View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class Starter extends Application
{
    public static Stage stage;
    public static String currentScene;
    public static boolean isSplashLoaded = false;

    @Override
    public void start(Stage primaryStage)
    {
        primaryStage.setTitle("Instagram");
        primaryStage.setResizable(false);
        stage = primaryStage
        ;
        try {
            URL url = Paths.get("./src/main/java/Client/Resources/SplashScreen.fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url);
            primaryStage.setScene(new Scene(root, 1300, 850));
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeScene(String fxmlFileName)
    {
        currentScene = fxmlFileName;

        try {
            URL url = Paths.get("./src/main/java/Client/Resources/" + currentScene + ".fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url);
            stage.getScene().setRoot(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reloadScene() {
        changeScene(currentScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
