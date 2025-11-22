package jraffic;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    public final static int widthScene = 650;
    public final static int heightScene = 650;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(createContent(widthScene, heightScene), widthScene, heightScene, Color.BLACK);
        scene.setOnKeyPressed(e -> {
            String key = e.getCode().toString();
            switch (key) {
                case "UP":
                    Points p = new Points(widthScene / 2, heightScene - 50);
                    System.out.println(p.getX());
                    System.out.println(p.getY());
                    break;
                case "DOWN":
                    p = new Points((widthScene / 2) - 50, 0);
                    System.out.println(p.getX());
                    System.out.println(p.getY());
                    break;
                case "LEFT":
                    p = new Points((widthScene / 2) - 50, 0);
                    System.out.println(p.getX());
                    System.out.println(p.getY());
                    break;

                case "RIGHT":
                    break;

                default:
                    break;
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public Parent createContent(int widthScene, int heightScene) {
        Pane pane = new Pane();
        Roads roads = new Roads();
        roads.generateRoads(widthScene, heightScene);
        roads.draw(pane);
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}