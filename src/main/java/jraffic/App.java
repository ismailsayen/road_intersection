package jraffic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        TrafficIntersection intersection = new TrafficIntersection();
        
        StackPane root = new StackPane(intersection);
        Scene scene = new Scene(root, 900, 700);
        
        primaryStage.setTitle("Road Intersection");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        intersection.requestFocus();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}