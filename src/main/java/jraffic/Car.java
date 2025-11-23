package jraffic;

import javafx.scene.paint.Color;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Car {
    static int nextId = 1;
    int id;
    int x;
    int y;
    Direction direction;
    Route route;
    Color color;
    boolean state;
    boolean outCalc;
    
    private Car(int id, int x, int y, Direction direction, Route route, Color color) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.route = route;
        this.color = color;
        this.state = true;
        this.outCalc = false;
    }
    
    public static Car create(int x, int y, Direction direction, List<Car> existingCars) {
        if (!isPositionSafe(x, y, direction, existingCars)) {
            return null;
        }
        
        Random rand = new Random();
        int randNumber = rand.nextInt(3) + 1;
        
        Route route;
        Color color;
        
        switch (randNumber) {
            case 1:
                route = Route.LEFT;
                color = Color.PURPLE;
                break;
            case 2:
                route = Route.RIGHT;
                color = Color.ORANGE;
                break;
            default:
                route = Route.STRAIGHT;
                color = Color.DEEPSKYBLUE;
                break;
        }
        
        return new Car(nextId++, x, y, direction, route, color);
    }
    
    public static Car createWithRandomDirection(List<Car> existingCars, Map<String, Integer> capacity) {
        int[] pos = Roads.getRoadPositions();
        int x = pos[0];
        int y = pos[1];
        int width = pos[2];
        int height = pos[3];
        
        Random rand = new Random();
        int choice = rand.nextInt(4) + 1;
        
        int spawnX, spawnY;
        Direction direction;
        String strDir;
        
        switch (choice) {
            case 1:
                spawnX = 0;
                spawnY = y;
                direction = Direction.EAST;
                strDir = "East";
                break;
            case 2:
                spawnX = x - 50;
                spawnY = 0;
                direction = Direction.NORTH;
                strDir = "North";
                break;
            case 3:
                spawnX = x;
                spawnY = height - 50;
                direction = Direction.SOUTH;
                strDir = "South";
                break;
            default:
                spawnX = width - 50;
                spawnY = y - 50;
                direction = Direction.WEST;
                strDir = "West";
                break;
        }
        
        if (isPositionSafe(spawnX, spawnY, direction, existingCars)) {
            capacity.put(strDir, capacity.get(strDir) + 1);
            return create(spawnX, spawnY, direction, existingCars);
        }
        
        return null;
    }
    
    private static boolean isPositionSafe(int x, int y, Direction direction, List<Car> existingCars) {
        int safetyDistance = 100;
        
        for (Car car : existingCars) {
            switch (direction) {
                case EAST:
                    if (car.direction == Direction.EAST && car.y == y) {
                        if (Math.abs(car.x - x) < safetyDistance) {
                            return false;
                        }
                    }
                    break;
                case WEST:
                    if (car.direction == Direction.WEST && car.y == y) {
                        if (Math.abs(car.x - x) < safetyDistance) {
                            return false;
                        }
                    }
                    break;
                case NORTH:
                    if (car.direction == Direction.NORTH && car.x == x) {
                        if (Math.abs(car.y - y) < safetyDistance) {
                            return false;
                        }
                    }
                    break;
                case SOUTH:
                    if (car.direction == Direction.SOUTH && car.x == x) {
                        if (Math.abs(car.y - y) < safetyDistance) {
                            return false;
                        }
                    }
                    break;
            }
        }
        
        return true;
    }
    
    public void updatePosition(List<Light> lights, List<Car> cars, Map<String, Integer> capacity) {
        int speed = 5;
        int[] pos = Roads.getRoadPositions();
        int x = pos[0];
        int y = pos[1];
        
        switch (direction) {
            case EAST:
                if (this.x > x - 100) {
                    this.state = false;
                    if (!this.outCalc) {
                        Integer val = capacity.get("East");
                        if (val > 0) {
                            capacity.put("East", val - 1);
                        }
                        this.outCalc = true;
                    }
                }
                
                if ((!lights.get(1).status && this.x == x - 100) || !isCanMove(cars)) {
                    if (this.state) return;
                }
                
                this.x += speed;
                if (this.route == Route.LEFT && this.x >= x) {
                    this.direction = Direction.SOUTH;
                    this.route = Route.STRAIGHT;
                } else if (this.route == Route.RIGHT && this.x >= x - 50) {
                    this.direction = Direction.NORTH;
                    this.route = Route.STRAIGHT;
                }
                break;
                
            case WEST:
                if (this.x < x + 50) {
                    this.state = false;
                    if (!this.outCalc) {
                        Integer val = capacity.get("West");
                        if (val > 0) {
                            capacity.put("West", val - 1);
                        }
                        this.outCalc = true;
                    }
                }
                
                if ((!lights.get(2).status && this.x == x + 50) || !isCanMove(cars)) {
                    if (this.state) return;
                }
                
                this.x -= speed;
                if (this.route == Route.LEFT && this.x + 50 <= x) {
                    this.direction = Direction.NORTH;
                    this.route = Route.STRAIGHT;
                } else if (this.route == Route.RIGHT && this.x <= x) {
                    this.direction = Direction.SOUTH;
                    this.route = Route.STRAIGHT;
                }
                break;
                
            case NORTH:
                if (this.y > y - 100) {
                    if (!this.outCalc) {
                        Integer val = capacity.get("North");
                        if (val > 0) {
                            capacity.put("North", val - 1);
                        }
                        this.outCalc = true;
                    }
                }
                
                if ((!lights.get(0).status && this.y == y - 100) || !isCanMove(cars)) {
                    if (this.state) return;
                }
                
                this.y += speed;
                if (this.route == Route.LEFT && this.y >= y) {
                    this.direction = Direction.EAST;
                    this.route = Route.STRAIGHT;
                } else if (this.route == Route.RIGHT && this.y >= y - 50) {
                    this.direction = Direction.WEST;
                    this.route = Route.STRAIGHT;
                }
                break;
                
            case SOUTH:
                if (this.y < y + 50) {
                    this.state = false;
                    if (!this.outCalc) {
                        Integer val = capacity.get("South");
                        if (val > 0) {
                            capacity.put("South", val - 1);
                        }
                        this.outCalc = true;
                    }
                }
                
                if ((!lights.get(3).status && this.y == y + 50) || !isCanMove(cars)) {
                    if (this.state) return;
                }
                
                this.y -= speed;
                if (this.route == Route.LEFT && this.y + 50 <= y) {
                    this.direction = Direction.WEST;
                    this.route = Route.STRAIGHT;
                } else if (this.route == Route.RIGHT && this.y <= y) {
                    this.direction = Direction.EAST;
                    this.route = Route.STRAIGHT;
                }
                break;
        }
    }
    
    private boolean isCanMove(List<Car> cars) {
        int safetyDistance = 100;
        
        for (Car car : cars) {
            switch (this.direction) {
                case EAST:
                    if (this.id > car.id && car.direction == Direction.EAST 
                        && this.x + safetyDistance >= car.x) {
                        return false;
                    }
                    break;
                case WEST:
                    if (this.id > car.id && car.direction == Direction.WEST 
                        && this.x <= car.x + safetyDistance) {
                        return false;
                    }
                    break;
                case NORTH:
                    if (this.id > car.id && car.direction == Direction.NORTH 
                        && this.y + safetyDistance >= car.y) {
                        return false;
                    }
                    break;
                case SOUTH:
                    if (this.id > car.id && car.direction == Direction.SOUTH 
                        && this.y <= car.y + safetyDistance) {
                        return false;
                    }
                    break;
            }
        }
        
        return true;
    }
}