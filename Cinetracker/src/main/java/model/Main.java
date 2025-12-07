// O Main.java final para JavaFX (Recuperando do Passo 1)
package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carrega o FXML da tela inicial
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));

        primaryStage.setTitle("CineTracker");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}