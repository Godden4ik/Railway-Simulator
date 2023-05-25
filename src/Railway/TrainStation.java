package Railway;

import java.awt.geom.Point2D;

public class TrainStation extends Point2D.Double {

    private String name;

    public TrainStation(double x, double y, String name) {
        super(x, y);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Station " + name;
    }
}

