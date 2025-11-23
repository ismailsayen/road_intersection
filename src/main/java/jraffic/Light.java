package jraffic;

import javafx.scene.paint.Color;
import java.util.List;
import java.util.Map;

public class Light {
    int id;
    int x;
    int y;
    Color color;
    boolean status;
    
    public Light(int x, int y, Color color, int id) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.color = color;
        this.status = false;
    }
    
    public void drawTrafficLight(Map<String, Integer> capacity) {
        int greenLight = 0;
        int maxCars = 0;
        
        for (Map.Entry<String, Integer> entry : capacity.entrySet()) {
            if (entry.getValue() > maxCars) {
                maxCars = entry.getValue();
                switch (entry.getKey()) {
                    case "North": greenLight = 1; break;
                    case "South": greenLight = 4; break;
                    case "East": greenLight = 2; break;
                    default: greenLight = 3; break;
                }
            }
        }
        
        if (this.id == greenLight) {
            this.color = Color.GREEN;
            this.status = true;
        } else {
            this.color = Color.RED;
            this.status = false;
        }
    }
    
    public static boolean isEmptyCenter(List<Car> cars) {
        int[] pos = Roads.getRoadPositions();
        int x = pos[0];
        int y = pos[1];
        
        int top = y - 50;
        int bottom = y + 50;
        int left = x - 50;
        int right = x + 50;
        
        for (Car car : cars) {
            if (car.x + 50 > left && car.x < right && car.y + 50 > top && car.y < bottom) {
                return false;
            }
        }
        
        return true;
    }
}