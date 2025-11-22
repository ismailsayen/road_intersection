package jraffic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    final int windowScen = 650;
    final int heightScen = 650;

    @Override
    public void start(Stage stage) throws IOException {
        
        scene = new Scene(createContent(), windowScen, heightScen, Color.BLACK);
        stage.setScene(scene);
        stage.show();
    }

    public Parent createContent() {
        Line l1=new Line(42, 50, 60, 78);
        l1.setStroke(Color.WHITE);
        l1.setTranslateX(heightScen/2);
        Pane pane=new Pane();
        pane.getChildren().add(l1);
        return  pane;
    }

    public static void main(String[] args) {
        launch();
    }

}