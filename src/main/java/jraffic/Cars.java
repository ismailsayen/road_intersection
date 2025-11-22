package jraffic;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cars implements Drawable {
    public Points p;
    public static List<Cars> cars = new ArrayList<>();
    public Rectangle rec;
    public Directions from;

    public Cars(Points p, Directions d) {
        this.p = p;
        this.from = d;
    }

    public void appendCar(Cars p, Pane pane) {
        this.rec = new Rectangle(50, 50, Color.RED);
        pane.getChildren().add(rec);
        cars.add(this);
    }

    public static void updateCar() {
        for (Cars car : cars) {
            switch (car.from) {
                case UP:
                    double e = car.p.getY() - 1;
                    car.p.setY(e);
                    break;
                case DOWN:
                    e = car.p.getY() + 1;
                    car.p.setY(e);
                    break;
                case LEFT:
                    e = car.p.getX() - 1;
                    car.p.setX(e);
                    break;
                case RIGHT:
                    e = car.p.getX() + 1;
                    car.p.setX(e);
                    break;
                default:
                    break;
            }
           car.draw(null); 
        }
    }

    @Override
    public void draw(Pane pane) {
        this.rec.setTranslateX(this.p.getX());
        this.rec.setTranslateY(this.p.getY());

    }
}
