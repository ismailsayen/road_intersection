package jraffic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Vehicle implements Drawable {
    private double x;
    private double y;
    private Direction direction;
    private Route route;
    private Color color;
    private double speed;
    private Rectangle body;
    private boolean stopped;
    
    public static final double VEHICLE_WIDTH = 20;
    public static final double VEHICLE_HEIGHT = 35;
    public static final double SAFETY_DISTANCE = 10;
    public static final double BASE_SPEED = 2.0;
    
    public Vehicle(double x, double y, Direction direction, Route route) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.route = route;
        this.speed = BASE_SPEED;
        this.stopped = false;
        this.color = getColorForRoute(route);
        
        // Create visual representation
        body = new Rectangle(x, y, VEHICLE_WIDTH, VEHICLE_HEIGHT);
        body.setFill(color);
        body.setStroke(Color.BLACK);
        body.setStrokeWidth(1);
    }
    
    private Color getColorForRoute(Route route) {
        switch (route) {
            case STRAIGHT:
                return Color.BLUE;
            case LEFT:
                return Color.RED;
            case RIGHT:
                return Color.YELLOW;
            default:
                return Color.WHITE;
        }
    }
    
    @Override
    public void draw(Pane pane) {
        if (!pane.getChildren().contains(body)) {
            pane.getChildren().add(body);
        }
    }
    
    public void update() {
        if (!stopped) {
            switch (direction) {
                case NORTH:
                    y -= speed;
                    break;
                case SOUTH:
                    y += speed;
                    break;
                case EAST:
                    x += speed;
                    break;
                case WEST:
                    x -= speed;
                    break;
            }
            updatePosition();
        }
    }
    
    private void updatePosition() {
        body.setX(x);
        body.setY(y);
    }
    
    public void stop() {
        stopped = true;
    }
    
    public void go() {
        stopped = false;
    }
    
    public boolean isOffScreen(double width, double height) {
        return x < -VEHICLE_WIDTH || x > width || 
               y < -VEHICLE_HEIGHT || y > height;
    }
    
    public boolean collidesWithVehicle(Vehicle other) {
        double distance = Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
        return distance < (VEHICLE_HEIGHT + SAFETY_DISTANCE);
    }
    
    public boolean shouldStopForVehicle(Vehicle other) {
        if (direction != other.direction) return false;
        
        switch (direction) {
            case NORTH:
                return other.y < this.y && other.y > this.y - 50;
            case SOUTH:
                return other.y > this.y && other.y < this.y + 50;
            case EAST:
                return other.x > this.x && other.x < this.x + 50;
            case WEST:
                return other.x < this.x && other.x > this.x - 50;
            default:
                return false;
        }
    }
    
    public double getDistanceToPoint(double px, double py) {
        return Math.sqrt(Math.pow(x - px, 2) + Math.pow(y - py, 2));
    }
    
    public void remove(Pane pane) {
        pane.getChildren().remove(body);
    }
    
    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public Direction getDirection() { return direction; }
    public Route getRoute() { return route; }
    public boolean isStopped() { return stopped; }
    public Rectangle getBody() { return body; }
}