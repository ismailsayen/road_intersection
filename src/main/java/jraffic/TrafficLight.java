package jraffic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class TrafficLight implements Drawable {

    private double x;
    private double y;
    private Direction direction;
    private boolean isGreen;
    private Circle light;
    private Rectangle pole;

    private static final double LIGHT_RADIUS = 10;
    private static final double POLE_WIDTH = 5;
    private static final double POLE_HEIGHT = 30;

    public TrafficLight(double x, double y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.isGreen = false;

        // Create pole
        pole = new Rectangle(x - POLE_WIDTH / 2, y, POLE_WIDTH, POLE_HEIGHT);
        pole.setFill(Color.DARKGRAY);

        // Create light
        light = new Circle(x, y + POLE_HEIGHT + LIGHT_RADIUS, LIGHT_RADIUS);
        updateLightColor();
    }

    @Override
    public void draw(Pane pane) {
        if (!pane.getChildren().contains(pole)) {
            pane.getChildren().addAll(pole, light);
        }
    }

    public void setGreen(boolean green) {
        this.isGreen = green;
        updateLightColor();
    }

    private void updateLightColor() {
        if (isGreen) {
            light.setFill(Color.LIME);
            light.setStroke(Color.DARKGREEN);
        } else {
            light.setFill(Color.RED);
            light.setStroke(Color.DARKRED);
        }
        light.setStrokeWidth(2);
    }

    public boolean isGreen() {
        return isGreen;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
