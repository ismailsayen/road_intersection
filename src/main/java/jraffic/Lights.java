package jraffic;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Lights implements Drawable {
    public Points p;
    public static List<Lights> lights = new ArrayList<>();

    public Lights(Points p) {
        this.p = p;
        lights.add(this);
    }

    @Override
    public void draw(Pane pane) {
         for (Lights light : lights) {
            Rectangle rec = new Rectangle(50, 50, Color.RED);
            rec.setTranslateX(light.p.getX());
            rec.setTranslateY(light.p.getY());
            pane.getChildren().add(rec);
        }
    }

}
