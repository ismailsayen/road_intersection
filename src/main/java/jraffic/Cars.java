package jraffic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cars implements Drawable {
    public int id;
    public Points p;
    public static List<Cars> cars = new ArrayList<>();
    // @SuppressWarnings("exports")
    public Rectangle rec;
    public Directions from;
    public Color color;
    public Turns turn;
    public boolean isPassed;

    public Cars(Points p, Directions d, Color color) {
        this.id = Math.abs((int) new Date().getTime());
        this.p = p;
        this.from = d;
        this.color = color;
        this.turn = App.colorsCars.get(color);
    }

    public void appendCar(Cars car, Pane pane) {
        if (!isSafePosition(car.p, car.from, car.id, this.isPassed)) {
            return;
        }
        this.rec = new Rectangle(50, 50, this.color);

        pane.getChildren().add(rec);
        cars.add(this);
    }

    public static boolean isSafePosition(Points point, Directions direction, int id, boolean passed) {
        for (Cars car : cars) {
            if (direction == Directions.UP && car.from == Directions.UP && point.getY() < car.p.getY() + 70
                    && car.id > id && !passed) {
                return false;
            } else if (direction == Directions.DOWN && car.from == Directions.DOWN
                    && point.getY() + 70 > car.p.getY() && car.id > id && !passed) {
                return false;
            } else if (direction == Directions.RIGHT && car.from == Directions.RIGHT
                    && point.getX() + 70 > car.p.getX() && car.id > id && !passed) {
                return false;
            } else if (direction == Directions.LEFT && car.from == Directions.LEFT
                    && point.getX() < car.p.getX() + 70 && car.id > id && !passed) {

                return false;
            }
        }

        return true;
    }

    public static void updateCar() {
        List<Cars> toRightCars = new ArrayList<>();
        List<Cars> toLeftCars = new ArrayList<>();
        List<Cars> toUpCars = new ArrayList<>();
        List<Cars> toDownCars = new ArrayList<>();
        for (Cars car : cars) {
            
            if (!isSafePosition(car.p, car.from, car.id, car.isPassed)) {
                continue;
            }

            if (!car.isPassed) {
                switch (car.from) {
                    case UP:
                        toUpCars.add(car);
                        break;
                    case DOWN:
                        toDownCars.add(car);
                        break;
                    case LEFT:
                        toLeftCars.add(car);
                        break;
                    case RIGHT:
                        toRightCars.add(car);
                        break;
                    default:
                        break;
                }
            }
            int maxLen = Math.max(toDownCars.size(),
                    Math.max(toLeftCars.size(), Math.max(toRightCars.size(), toUpCars.size())));
            if (maxLen == toDownCars.size()) {
                Lights.changeLight(2);

            } else if (maxLen == toUpCars.size()) {
                Lights.changeLight(0);
            } else if (maxLen == toRightCars.size()) {
                Lights.changeLight(1);
            } else {
                Lights.changeLight(3);
            }

            switch (car.from) {
                case UP:
                    if (car.p.getY() <= App.heightScene / 2 + 55 && !Lights.lights.get(0).state && !car.isPassed) {
                        break;
                    }
                    if (car.p.getY() <= App.widthScene / 2 + 55) {
                        car.isPassed = true;
                    }
                    double e = car.p.getY() - 1;
                    car.p.setY(e);
                    if (car.turn == Turns.RIGHT && car.p.getY() == App.heightScene / 2) {
                        car.from = Directions.RIGHT;
                        car.turn = Turns.STRAIGHT;
                        car.isPassed = true;
                    } else if (car.turn == Turns.LEFT && car.p.getY() == (App.heightScene / 2) - 50) {
                        car.from = Directions.LEFT;
                        car.turn = Turns.STRAIGHT;
                        car.isPassed = true;

                    }
                    break;
                case DOWN:
                    if (car.p.getY() >= App.heightScene / 2 - 105 && !Lights.lights.get(2).state && !car.isPassed) {
                        break;
                    }
                    if (car.p.getY() >= App.widthScene / 2 - 105) {
                        car.isPassed = true;
                    }
                    e = car.p.getY() + 1;
                    car.p.setY(e);
                    if (car.turn == Turns.LEFT && car.p.getY() == (App.heightScene / 2)) {
                        car.from = Directions.RIGHT;
                        car.turn = Turns.STRAIGHT;
                        car.isPassed = true;

                    } else if (car.turn == Turns.RIGHT && car.p.getY() == (App.heightScene / 2) - 50) {
                        car.from = Directions.LEFT;
                        car.turn = Turns.STRAIGHT;
                        car.isPassed = true;

                    }
                    break;
                case LEFT:
                    if (car.p.getX() <= App.widthScene / 2 + 55 && !Lights.lights.get(3).state) {
                        break;
                    }

                    if (car.p.getX() <= App.widthScene / 2 + 55) {
                        car.isPassed = true;
                    }

                    e = car.p.getX() - 1;
                    car.p.setX(e);
                    if (car.turn == Turns.RIGHT && car.p.getX() == (App.widthScene / 2)) {
                        car.from = Directions.UP;
                        car.turn = Turns.STRAIGHT;
                        car.isPassed = true;
                    } else if (car.turn == Turns.LEFT && car.p.getX() == (App.widthScene / 2) - 50) {
                        car.from = Directions.DOWN;
                        car.turn = Turns.STRAIGHT;
                        car.isPassed = true;
                    }
                    break;
                case RIGHT:
                    if (car.p.getX() + 55 >= App.widthScene / 2 - 55 && !Lights.lights.get(1).state) {
                        break;
                    }

                    if (car.p.getX() + 55 >= App.widthScene / 2 - 50) {
                        car.isPassed = true;
                    }

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

    @Override
    public void draw(Pane pane) {
        if (this.rec != null) {
            this.rec.setTranslateX(this.p.getX());
            this.rec.setTranslateY(this.p.getY());
        }

    }
}
