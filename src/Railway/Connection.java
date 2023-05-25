package Railway;

import java.util.concurrent.Semaphore;

public class Connection {
    double length;
    Semaphore accessSemaphore = new Semaphore(1);

    TrainStation station1;
    TrainStation station2;

    public Connection(TrainStation station1, TrainStation station2) {
        this.station1 = station1;
        this.station2 = station2;
        length = station1.distance(station2);
    }

    public TrainStation getStation1() {
        return station1;
    }

    public TrainStation getStation2() {
        return station2;
    }
    public synchronized Semaphore getSemaphore() {
        return accessSemaphore;
    }

    public boolean contains(TrainStation station) {
        return station.equals(station1) || station.equals(station2);
    }

    public TrainStation getOtherStation(TrainStation station) {
        if(station.equals(station2))
            return station1;
        else return station2;
    }

    public double getLength() {
        return length;
    }


    @Override
    public String toString() {
        return "Connection between " + station1 + " and " + station2 + " with a length of\t " + (int)length + " \tkm";
     }
}
