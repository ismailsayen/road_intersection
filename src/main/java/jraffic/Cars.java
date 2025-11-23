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
    public Turns turn;

    public Cars(Points p, Directions d, Color color) {
        this.p = p;
        this.from = d;
        this.color = color;
        this.turn = App.colorsCars.get(color);
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
            if (direction == Directions.UP && car.from == Directions.UP && point.getY() < car.p.getY() + 70) {
                return false;
            } else if (direction == Directions.DOWN && car.from == Directions.DOWN
                    && point.getY() + 70 > car.p.getY()) {
                return false;
            } else if (direction == Directions.RIGHT && car.from == Directions.RIGHT
                    && point.getX() + 70 > car.p.getX()) {
                return false;
            } else if (direction == Directions.LEFT && car.from == Directions.LEFT
                    && point.getX() < car.p.getX() + 70) {
                return false;
            }
        }

        return true;
    }

    public static void updateCar() {
        for (Cars car : cars) {
            boolean shouldStop = checkTrafficLight(car);
            if (shouldStop) {
                continue;
            }
            switch (car.from) {
                case UP:
                    double e = car.p.getY() - 1;
                    car.p.setY(e);
                    if (car.turn == Turns.RIGHT && car.p.getY() == App.heightScene / 2) {
                        car.from = Directions.RIGHT;
                        car.turn = Turns.STRAIGHT;
                    } else if (car.turn == Turns.LEFT && car.p.getY() == (App.heightScene / 2) - 50) {
                        car.from = Directions.LEFT;
                        car.turn = Turns.STRAIGHT;

                    }
                    break;
                case DOWN:
                    e = car.p.getY() + 1;
                    car.p.setY(e);
                    if (car.turn == Turns.LEFT && car.p.getY() == (App.heightScene / 2)) {
                        car.from = Directions.RIGHT;
                        car.turn = Turns.STRAIGHT;
                    } else if (car.turn == Turns.RIGHT && car.p.getY() == (App.heightScene / 2) - 50) {
                        car.from = Directions.LEFT;
                        car.turn = Turns.STRAIGHT;
                    }
                    break;
                case LEFT:
                    e = car.p.getX() - 1;
                    car.p.setX(e);
                    if (car.turn == Turns.RIGHT && car.p.getX() == (App.widthScene / 2)) {
                        car.from = Directions.UP;
                        car.turn = Turns.STRAIGHT;
                    } else if (car.turn == Turns.LEFT && car.p.getX() == (App.widthScene / 2) - 50) {
                        car.from = Directions.DOWN;
                        car.turn = Turns.STRAIGHT;
                    }
                    break;
                case RIGHT:
                    e = car.p.getX() + 1;
                    car.p.setX(e);
                    if (car.turn == Turns.RIGHT && car.p.getX() == (App.widthScene / 2) - 50) {
                        car.from = Directions.DOWN;
                        car.turn = Turns.STRAIGHT;
                    } else if (car.turn == Turns.LEFT && car.p.getX() == (App.widthScene / 2)) {
                        car.from = Directions.UP;
                        car.turn = Turns.STRAIGHT;
                    }
                    break;
                default:
                    break;
            }
            car.draw(null);
        }
    }

    private static boolean checkTrafficLight(Cars car) {
        int centerX = App.widthScene / 2;
        int centerY = App.heightScene / 2;

        switch (car.from) {
            case UP:
                if (car.p.getY() <= centerY + 50 && car.p.getY() + 50 >= centerY - 150) {
                    System.out.println("UP car at light");
                    return !Lights.lights.get(0).state;
                }
                break;

            case DOWN:
                if (car.p.getY() >= centerY - 100 && car.p.getY() - 100 <= centerY + 50) {
                    return !Lights.lights.get(1).state;
                }
                break;

            case LEFT:
                if (car.p.getX() <= centerX + 50 && car.p.getX() >= centerX - 150) {
                    return !Lights.lights.get(2).state;
                }
                break;

            case RIGHT:
                if (car.p.getX() >= centerX - 100 && car.p.getX() - 100 <= centerX + 50) {
                    return !Lights.lights.get(3).state;
                }
                break;
        }

        return false;
    }

    @Override
    public void draw(Pane pane) {
        if (this.rec != null) {
            this.rec.setTranslateX(this.p.getX());
            this.rec.setTranslateY(this.p.getY());
        }

    }
}
