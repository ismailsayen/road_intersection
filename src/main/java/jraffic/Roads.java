package jraffic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Roads implements Drawable {
    private List<Line> lines;

    public Roads() {
        this.lines = new ArrayList<>();
    }

    public void generateRoads(int widthScene, int heightScene) {
        // line1
        Points p1 = new Points((widthScene / 2) - 50, 0);
        Points p2 = new Points((widthScene / 2) - 50, heightScene);
        Line line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        line.setStroke(Color.WHITE);
        lines.add(line);

        // line2
        p1.setX((widthScene / 2) + 50);
        p2.setX((widthScene / 2) + 50);
        line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        line.setStroke(Color.WHITE);
        lines.add(line);

        // line3
        p1.setX(0);
        p1.setY((heightScene / 2) - 50);
        p2.setX(widthScene);
        p2.setY((heightScene / 2) - 50);
        line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        line.setStroke(Color.WHITE);
        lines.add(line);
        // line4
        p1.setX(0);
        p1.setY((heightScene / 2) + 50);
        p2.setX(widthScene);
        p2.setY((heightScene / 2) + 50);
        line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        line.setStroke(Color.WHITE);
        lines.add(line);

        // midlleHorizontal
        p1.setX(0);
        p1.setY(heightScene / 2);
        p2.setX(widthScene);
        p2.setY(heightScene / 2);
        line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        line.setStroke(Color.WHITE);
        lines.add(line);

        // midlleVertical
        p1.setY(0);
        p1.setX(650 / 2);
        p2.setY(650);
        p2.setX(650 / 2);
        line = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        line.setStroke(Color.WHITE);
        lines.add(line);

    }

    @Override
    public void draw(Pane pane) {
        for (Line l : this.lines) {
            pane.getChildren().add(l);
        }
    }

}