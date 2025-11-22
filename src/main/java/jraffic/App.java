package jraffic;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;
    private final int windowWidth = 800;
    private final int windowHeight = 800;
    private Pane root;
    private IntersectionController controller;

    @Override
    public void start(Stage stage) {
        root = new Pane();
        root.setStyle("-fx-background-color: #2d5016;"); // Grass green background
        
        // Create the intersection controller
        controller = new IntersectionController(windowWidth, windowHeight);
        controller.initialize(root);
        
        // Create scene
        scene = new Scene(root, windowWidth, windowHeight);
        
        // Setup keyboard controls
        setupKeyboardControls(scene);
        
        stage.setTitle("Traffic Intersection Simulation");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        // Start the simulation
        controller.start();
    }

    private void setupKeyboardControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            
            switch (code) {
                case UP:
                    controller.spawnVehicle(Direction.SOUTH); // Coming from south, going north
                    break;
                case DOWN:
                    controller.spawnVehicle(Direction.NORTH); // Coming from north, going south
                    break;
                case RIGHT:
                    controller.spawnVehicle(Direction.WEST); // Coming from west, going east
                    break;
                case LEFT:
                    controller.spawnVehicle(Direction.EAST); // Coming from east, going west
                    break;
                case R:
                    controller.spawnVehicleRandom();
                    break;
                case ESCAPE:
                    controller.stop();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}