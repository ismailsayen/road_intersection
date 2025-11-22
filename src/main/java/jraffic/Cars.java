package jraffic;

import java.util.ArrayList;
import java.util.List;

public class Cars {
    public Points p;
    public static List<Cars> cars = new ArrayList<>();

    public Cars(Points p) {
        this.p = p;
        cars.add(this);
    }
}
