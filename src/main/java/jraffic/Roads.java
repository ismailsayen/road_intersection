package jraffic;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Roads implements Drawable {

    private double centerX;
    private double centerY;
    private double roadWidth;
    private double intersectionSize;
    private double laneWidth;

    public Roads(double centerX, double centerY, double roadWidth, double intersectionSize) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.roadWidth = roadWidth;
        this.intersectionSize = intersectionSize;
        this.laneWidth = roadWidth / 2;
    }

    @Override
    public void draw(Pane pane) {
        double sceneWidth = pane.getWidth() > 0 ? pane.getWidth() : 800;
        double sceneHeight = pane.getHeight() > 0 ? pane.getHeight() : 800;

        // Draw vertical road (North-South)
        Rectangle verticalRoad = new Rectangle(
                centerX - roadWidth / 2,
                0,
                roadWidth,
                sceneHeight
        );
        verticalRoad.setFill(Color.DARKGRAY);

        // Draw horizontal road (East-West)
        Rectangle horizontalRoad = new Rectangle(
                0,
                centerY - roadWidth / 2,
                sceneWidth,
                roadWidth
        );
        horizontalRoad.setFill(Color.DARKGRAY);

        // Draw intersection
        Rectangle intersection = new Rectangle(
                centerX - intersectionSize / 2,
                centerY - intersectionSize / 2,
                intersectionSize,
                intersectionSize
        );
        intersection.setFill(Color.GRAY);

        pane.getChildren().addAll(verticalRoad, horizontalRoad, intersection);

        // Draw lane markings
        drawLaneMarkings(pane, sceneWidth, sceneHeight);
    }

    private void drawLaneMarkings(Pane pane, double sceneWidth, double sceneHeight) {
        // Vertical center line (yellow dashed)
        drawDashedLine(pane, centerX, 0, centerX, centerY - intersectionSize / 2, Color.YELLOW);
        drawDashedLine(pane, centerX, centerY + intersectionSize / 2, centerX, sceneHeight, Color.YELLOW);

        // Horizontal center line (yellow dashed)
        drawDashedLine(pane, 0, centerY, centerX - intersectionSize / 2, centerY, Color.YELLOW);
        drawDashedLine(pane, centerX + intersectionSize / 2, centerY, sceneWidth, centerY, Color.YELLOW);

        // Stop lines (white solid lines at intersection)
        // North approach
        Line northStop = new Line(centerX - roadWidth / 2, centerY - intersectionSize / 2,
                centerX + roadWidth / 2, centerY - intersectionSize / 2);
        northStop.setStroke(Color.WHITE);
        northStop.setStrokeWidth(3);

        // South approach
        Line southStop = new Line(centerX - roadWidth / 2, centerY + intersectionSize / 2,
                centerX + roadWidth / 2, centerY + intersectionSize / 2);
        southStop.setStroke(Color.WHITE);
        southStop.setStrokeWidth(3);

        // East approach
        Line eastStop = new Line(centerX + intersectionSize / 2, centerY - roadWidth / 2,
                centerX + intersectionSize / 2, centerY + roadWidth / 2);
        eastStop.setStroke(Color.WHITE);
        eastStop.setStrokeWidth(3);

        // West approach
        Line westStop = new Line(centerX - intersectionSize / 2, centerY - roadWidth / 2,
                centerX - intersectionSize / 2, centerY + roadWidth / 2);
        westStop.setStroke(Color.WHITE);
        westStop.setStrokeWidth(3);

        pane.getChildren().addAll(northStop, southStop, eastStop, westStop);
    }

    private void drawDashedLine(Pane pane, double x1, double y1, double x2, double y2, Color color) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(color);
        line.setStrokeWidth(2);
        line.getStrokeDashArray().addAll(15.0, 10.0);
        pane.getChildren().add(line);
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRoadWidth() {
        return roadWidth;
    }

    public double getLaneWidth() {
        return laneWidth;
    }

    public double getIntersectionSize() {
        return intersectionSize;
    }
}
