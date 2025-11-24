package jraffic;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficIntersection extends Canvas {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    private static final double TARGET_FPS = 60.0;
    private static final long FRAME_TIME = (long)(1_000_000_000.0 / TARGET_FPS);
    
    private List<Car> cars;
    private List<Light> lights;
    private Map<String, Integer> capacity;
    private long startTime;
    private long lastLightChange;
    private GraphicsContext gc;
    private long lastFrameTime;
    
    public TrafficIntersection() {
        super(WIDTH, HEIGHT);
        gc = getGraphicsContext2D();
        
        cars = new ArrayList<>();
        lights = new ArrayList<>();
        capacity = new HashMap<>();
        
        capacity.put("North", 0);
        capacity.put("South", 0);
        capacity.put("East", 0);
        capacity.put("West", 0);
        
        int[] pos = Roads.getRoadPositions();
        int x = pos[0];
        int y = pos[1];
        
        lights.add(new Light(x - 100, y - 100, Color.RED, 1));
        lights.add(new Light(x - 100, y + 50, Color.RED, 2));
        lights.add(new Light(x + 50, y - 100, Color.RED, 3));
        lights.add(new Light(x + 50, y + 50, Color.RED, 4));
        
        startTime = System.currentTimeMillis();
        lastLightChange = 0;
        lastFrameTime = System.nanoTime();
        
        setupKeyHandlers();
        startAnimation();
    }
    
    private void setupKeyHandlers() {
        setFocusTraversable(true);
        setOnKeyPressed(e -> handleKeyPress(e.getCode()));
    }
    
    private void handleKeyPress(KeyCode keyCode) {
        int[] pos = Roads.getRoadPositions();
        int x = pos[0];
        int y = pos[1];
        int width = pos[2];
        int height = pos[3];
        
        Car newCar = null;
        String direction = null;
        
        switch (keyCode) {
            case UP:
                newCar = Car.create(x, height - 50, Direction.SOUTH, cars);
                direction = "South";
                break;
            case DOWN:
                newCar = Car.create(x - 50, 0, Direction.NORTH, cars);
                direction = "North";
                break;
            case LEFT:
                newCar = Car.create(width - 50, y - 50, Direction.WEST, cars);
                direction = "West";
                break;
            case RIGHT:
                newCar = Car.create(0, y, Direction.EAST, cars);
                direction = "East";
                break;
            case R:
                newCar = Car.createWithRandomDirection(cars, capacity);
                break;
            case ESCAPE:
                System.exit(0);
                break;
        }
        
        if (newCar != null) {
            cars.add(newCar);
            if (direction != null) {
                capacity.put(direction, capacity.get(direction) + 1);
            }
        }
    }
    
    private void startAnimation() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Frame rate control
                if (now - lastFrameTime >= FRAME_TIME) {
                    render();
                    lastFrameTime = now;
                }
            }
        };
        timer.start();
    }
    
    private void render() {
        // Clear canvas
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        // Draw roads
        Roads.drawRoads(gc);
        
        // Update traffic lights
        long elapsedSeconds = (System.currentTimeMillis() - startTime) / 1000;
        if (elapsedSeconds > lastLightChange && Light.isEmptyCenter(cars)) {
            for (Light light : lights) {
                light.drawTrafficLight(capacity);
            }
            lastLightChange = elapsedSeconds;
        }
        
        // Draw lights
        for (Light light : lights) {
            gc.setStroke(light.color);
            gc.setLineWidth(3);
            gc.strokeRect(light.x, light.y, 50, 50);
        }
        
        // Update and draw cars
        List<Car> copyCars = new ArrayList<>(cars);
        for (Car car : cars) {
            car.updatePosition(lights, copyCars, capacity);
            gc.setFill(car.color);
            gc.fillRect(car.x, car.y, 50, 50);
        }
    }
}