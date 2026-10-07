package Banca;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Start.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Icono (asegúrate de que /img/logo.png exista)
            Image icono = new Image(getClass().getResourceAsStream("/img/logo.png"));
            primaryStage.getIcons().add(icono);

            primaryStage.setTitle("Inicio de Sesión");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
