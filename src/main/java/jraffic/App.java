package jraffic;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class App extends Application {

    private static Scene scene;
    public final static int widthScene = 650;
    public final static int heightScene = 650;
    public static Map<Color, Turns> colorsCars = new HashMap<>();
    public static final Color[] clr = { Color.BLUE, Color.YELLOW, Color.PINK };

    @Override
    public void start(Stage stage) throws IOException {
        Pane pane = new Pane();
        colorsCars.put(Color.BLUE, Turns.STRAIGHT);
        colorsCars.put(Color.YELLOW, Turns.LEFT);
        colorsCars.put(Color.PINK, Turns.RIGHT);
        scene = new Scene(createContent(pane, widthScene, heightScene), widthScene, heightScene, Color.BLACK);
        scene.setOnKeyPressed(e -> {
            String key = e.getCode().toString();
            int rnd = (int) Math.round(Math.random() * 2);

            switch (key) {
                case "UP":
                    Cars car = new Cars(new Points(widthScene / 2, heightScene - 50), Directions.UP, clr[rnd]);
                    car.appendCar(car, pane);
                    car.draw(pane);
                    break;
                case "DOWN":
                    car = new Cars(new Points((widthScene / 2) - 50, 0), Directions.DOWN, clr[rnd]);
                    car.appendCar(car, pane);
                    car.draw(pane);
                    break;
                case "LEFT":
                    car = new Cars(new Points(widthScene - 50, (heightScene / 2) - 50), Directions.LEFT, clr[rnd]);
                    car.appendCar(car, pane);
                    car.draw(pane);
                    break;
                case "RIGHT":
                    car = new Cars(new Points(0, (heightScene / 2)), Directions.RIGHT, clr[rnd]);
                    car.appendCar(car, pane);
                    car.draw(pane);
                    break;
                default:
                    break;
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Cars.updateCar();
                Lights.updateLights();
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public Parent createContent(Pane pane, int widthScene, int heightScene) {

        Roads roads = new Roads();
        roads.generateRoads(widthScene, heightScene);
        roads.draw(pane);
        //0
        Lights l = new Lights(new Points((widthScene / 2) + 55, (heightScene / 2) + 55), pane);
        //1
        l = new Lights(new Points((widthScene / 2) - 105+1 , (heightScene / 2) + 55), pane);
        //2
        l = new Lights(new Points((widthScene / 2) - 105, (heightScene / 2) - 105), pane);
        // //3
        l = new Lights(new Points((widthScene / 2) + 55, (heightScene / 2) - 105), pane);
        l.draw(pane);
        return pane;
    }

    public static void main(String[] args) {
        launch(args);
    }
}