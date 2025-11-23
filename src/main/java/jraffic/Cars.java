package jraffic;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cars implements Drawable {
    public Points p;
    public static List<Cars> cars = new ArrayList<>();
    // @SuppressWarnings("exports")
    public Rectangle rec;
    public Directions from;
    public Color color;

    public Cars(Points p, Directions d, Color color) {
        this.p = p;
        this.from = d;
        this.color = color;

    }

    public void appendCar(Cars car, Pane pane) {
        if (!isSafePosition(car.p, car.from)) {
            return;
        }
        this.rec = new Rectangle(50, 50, this.color);

        pane.getChildren().add(rec);
        cars.add(this);
    }

    public static boolean isSafePosition(Points point, Directions direction) {
        for (Cars car : cars) {
            if (direction == Directions.UP && point.getY() < car.p.getY() + 70) {
                return false;
            } else if (direction == Directions.DOWN && point.getY() + 70 > car.p.getY()) {
                return false;
            } else if (direction == Directions.RIGHT && point.getX() + 70 > car.p.getX()) {
                return false;
            } else if (direction == Directions.LEFT && point.getX() < car.p.getX() + 70) {
                return false;
            }
        }

        return true;
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
        if (this.rec != null) {
            this.rec.setTranslateX(this.p.getX());
            this.rec.setTranslateY(this.p.getY());
        }

    }
}
