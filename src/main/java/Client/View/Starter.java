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
    public static boolean isSplashLoaded = false;

    @Override
    public void start(Stage primaryStage) throws IOException
    {
        primaryStage.setTitle("Instagram");
        primaryStage.setResizable(false);
        stage = primaryStage;
        URL url = Paths.get("./src/main/java/Client/Resources/SplashScreen.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root, 1300, 850));
        primaryStage.show();
    }

    public static void changeScene(String fxmlFileName)
    {
        try {
            URL url = Paths.get("./src/main/java/Client/Resources/" + fxmlFileName + ".fxml").toUri().toURL();
            Parent root = FXMLLoader.load(url);
            stage.getScene().setRoot(root);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
