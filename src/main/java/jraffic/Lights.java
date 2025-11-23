package jraffic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Lights implements Drawable {
    public Points p;
    public boolean state;
    public Color color;
    private Rectangle box;
    public static List<Lights> lights = new ArrayList<>();

    public Lights(Points p, Pane pane) {
        this.p = p;
        this.state = false;
        this.color = Color.RED;
        this.box = new Rectangle(50, 50);
        pane.getChildren().add(box);
        lights.add(this);
    }

    @Override
    public void draw(Pane pane) {
        this.box.setTranslateX(this.p.getX());
        this.box.setTranslateY(this.p.getY());
        this.box.setStroke(this.color);
    }

    public static void changeLight(int ind) {
        for (int i = 0; i < lights.size(); i++) {
            if (i == ind) {
                lights.get(ind).state = true;
                continue;
            }
            lights.get(i).state = false;
        }
    }

    public static void updateLights() {
        for (Lights light : lights) {
            if (light.state == true) {
                light.color = Color.GREEN;
            } else {
                light.color = Color.RED;
            }
            light.draw(null);
        }
    }

}