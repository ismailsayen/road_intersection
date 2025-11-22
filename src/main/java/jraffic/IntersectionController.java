package jraffic;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import java.util.*;

public class IntersectionController {
    private Pane pane;
    private Roads roads;
    private List<Vehicle> vehicles;
    private Map<Direction, TrafficLight> trafficLights;
    private AnimationTimer gameLoop;
    
    private double windowWidth;
    private double windowHeight;
    private double centerX;
    private double centerY;
    
    private long lastSpawnTime = 0;
    private static final long SPAWN_COOLDOWN = 500_000_000; // 0.5 seconds in nanoseconds
    
    // Traffic light timing
    private long lastLightChange = 0;
    private static final long GREEN_DURATION = 5_000_000_000L; // 5 seconds
    private Direction currentGreenDirection = Direction.NORTH;
    
    // Lane capacity calculation
    private static final double LANE_LENGTH = 300; // Distance from spawn to intersection
    private int maxCapacityPerLane;
    
    public IntersectionController(double width, double height) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.centerX = width / 2;
        this.centerY = height / 2;
        this.vehicles = new ArrayList<>();
        this.trafficLights = new HashMap<>();
        
        // Calculate lane capacity
        maxCapacityPerLane = (int) Math.floor(LANE_LENGTH / (Vehicle.VEHICLE_HEIGHT + Vehicle.SAFETY_DISTANCE));
    }
    
    public void initialize(Pane pane) {
        this.pane = pane;
        
        // Create and draw roads
        roads = new Roads(centerX, centerY, 100, 100);
        roads.draw(pane);
        
        // Create traffic lights
        createTrafficLights();
        
        // Start with North-South green
        setTrafficLights(Direction.NORTH, true);
        setTrafficLights(Direction.SOUTH, true);
    }
    
    private void createTrafficLights() {
        double lightOffset = 60;
        double intersectionHalf = roads.getIntersectionSize() / 2;
        
        // North traffic light (at south side of north approach)
        trafficLights.put(Direction.NORTH, 
            new TrafficLight(centerX - 25, centerY - intersectionHalf - lightOffset, Direction.NORTH));
        
        // South traffic light (at north side of south approach)
        trafficLights.put(Direction.SOUTH,
            new TrafficLight(centerX + 25, centerY + intersectionHalf + lightOffset, Direction.SOUTH));
        
        // East traffic light (at west side of east approach)
        trafficLights.put(Direction.EAST,
            new TrafficLight(centerX + intersectionHalf + lightOffset, centerY + 25, Direction.EAST));
        
        // West traffic light (at east side of west approach)
        trafficLights.put(Direction.WEST,
            new TrafficLight(centerX - intersectionHalf - lightOffset, centerY - 25, Direction.WEST));
        
        // Draw all traffic lights
        for (TrafficLight light : trafficLights.values()) {
            light.draw(pane);
        }
    }
    
    public void start() {
        lastLightChange = System.nanoTime();
        
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
            }
        };
        gameLoop.start();
    }
    
    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }
    
    private void update(long now) {
        // Update traffic lights
        updateTrafficLights(now);
        
        // Update all vehicles
        List<Vehicle> toRemove = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            // Check if vehicle should stop at traffic light
            checkTrafficLight(vehicle);
            
            // Check for collision with other vehicles
            checkVehicleCollisions(vehicle);
            
            // Update vehicle position
            vehicle.update();
            
            // Remove vehicles that are off screen
            if (vehicle.isOffScreen(windowWidth, windowHeight)) {
                vehicle.remove(pane);
                toRemove.add(vehicle);
            }
        }
        vehicles.removeAll(toRemove);
    }
    
    private void updateTrafficLights(long now) {
        if (now - lastLightChange > GREEN_DURATION) {
            // Change lights
            switchTrafficLights();
            lastLightChange = now;
        }
    }
    
    private void switchTrafficLights() {
        // Turn current direction red
        setTrafficLights(currentGreenDirection, false);
        setTrafficLights(getOppositeDirection(currentGreenDirection), false);
        
        // Switch to perpendicular direction
        if (currentGreenDirection == Direction.NORTH) {
            currentGreenDirection = Direction.EAST;
        } else {
            currentGreenDirection = Direction.NORTH;
        }
        
        // Turn new direction green
        setTrafficLights(currentGreenDirection, true);
        setTrafficLights(getOppositeDirection(currentGreenDirection), true);
    }
    
    private Direction getOppositeDirection(Direction dir) {
        switch (dir) {
            case NORTH: return Direction.SOUTH;
            case SOUTH: return Direction.NORTH;
            case EAST: return Direction.WEST;
            case WEST: return Direction.EAST;
            default: return Direction.NORTH;
        }
    }
    
    private void setTrafficLights(Direction dir, boolean green) {
        TrafficLight light = trafficLights.get(dir);
        if (light != null) {
            light.setGreen(green);
        }
    }
    
    private void checkTrafficLight(Vehicle vehicle) {
        TrafficLight light = trafficLights.get(vehicle.getDirection());
        if (light == null || light.isGreen()) {
            vehicle.go();
            return;
        }
        
        // Calculate distance to stop line
        double distanceToIntersection = vehicle.getDistanceToPoint(centerX, centerY);
        double stopDistance = roads.getIntersectionSize() / 2 + 50;
        
        if (distanceToIntersection < stopDistance) {
            vehicle.stop();
        }
    }
    
    private void checkVehicleCollisions(Vehicle vehicle) {
        for (Vehicle other : vehicles) {
            if (vehicle == other) continue;
            
            if (vehicle.shouldStopForVehicle(other) && other.isStopped()) {
                vehicle.stop();
                return;
            }
        }
    }
    
    public void spawnVehicle(Direction direction) {
        long now = System.nanoTime();
        if (now - lastSpawnTime < SPAWN_COOLDOWN) {
            return; // Prevent spam
        }
        
        // Check lane capacity
        int vehiclesInLane = countVehiclesInLane(direction);
        if (vehiclesInLane >= maxCapacityPerLane) {
            System.out.println("Lane at capacity! Adjusting traffic light timing...");
            return;
        }
        
        // Random route
        Route route = Route.values()[new Random().nextInt(Route.values().length)];
        
        // Calculate spawn position
        double spawnX = 0, spawnY = 0;
        double laneOffset = roads.getLaneWidth() / 2;
        
        switch (direction) {
            case NORTH:
                spawnX = centerX - laneOffset;
                spawnY = windowHeight + Vehicle.VEHICLE_HEIGHT;
                break;
            case SOUTH:
                spawnX = centerX + laneOffset;
                spawnY = -Vehicle.VEHICLE_HEIGHT;
                break;
            case EAST:
                spawnX = -Vehicle.VEHICLE_WIDTH;
                spawnY = centerY - laneOffset;
                break;
            case WEST:
                spawnX = windowWidth + Vehicle.VEHICLE_WIDTH;
                spawnY = centerY + laneOffset;
                break;
        }
        
        Vehicle vehicle = new Vehicle(spawnX, spawnY, direction, route);
        vehicle.draw(pane);
        vehicles.add(vehicle);
        
        lastSpawnTime = now;
    }
    
    public void spawnVehicleRandom() {
        Direction[] directions = Direction.values();
        Direction randomDir = directions[new Random().nextInt(directions.length)];
        spawnVehicle(randomDir);
    }
    
    private int countVehiclesInLane(Direction direction) {
        int count = 0;
        for (Vehicle v : vehicles) {
            if (v.getDirection() == direction) {
                count++;
            }
        }
        return count;
    }
}