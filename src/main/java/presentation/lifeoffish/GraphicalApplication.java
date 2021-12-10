package presentation.lifeoffish;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GraphicalApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);

        Image icon = new Image("file:src/main/resources/Images/Tuna.png");
        stage.getIcons().add(icon);

        stage.setResizable(false);
        stage.setTitle("Life Of Fish");
        stage.setScene(scene);

        stage.show();
    }
}
