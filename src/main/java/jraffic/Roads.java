package jraffic;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Roads {
    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;
    
    public static void drawRoads(GraphicsContext gc) {
        int[] pos = getRoadPositions();
        int x = pos[0];
        int y = pos[1];
        int width = pos[2];
        int height = pos[3];
        
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        
        // Vertical lines
        gc.strokeLine(x, 0, x, height);
        gc.strokeLine(x + 50, 0, x + 50, height);
        gc.strokeLine(x - 50, 0, x - 50, height);
        
        // Horizontal lines
        gc.strokeLine(0, y, width, y);
        gc.strokeLine(0, y + 50, width, y + 50);
        gc.strokeLine(0, y - 50, width, y - 50);
    }
    
    public static int[] getRoadPositions() {
        int x = WIDTH / 2;
        int y = HEIGHT / 2;
        return new int[]{x, y, WIDTH, HEIGHT};
    }
}