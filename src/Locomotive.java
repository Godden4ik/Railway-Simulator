import Railway.TrainStation;
import Exceptions.RailroadHazard;

import java.util.UUID;
import java.util.concurrent.Semaphore;

public class Locomotive implements Runnable{
    private final int maxCars;
    private final int maxWeight;
    private final int gridLimit;
    private TrainSet trainSet;
    private double speed = 60; // km/h

    private TrainStation homeStation;
    private TrainStation currentStation;

    private final UUID locomotiveID = UUID.randomUUID();
    private Semaphore changeSpeedSemaphore;

    //automatically generating properties of the locomotive
    public Locomotive(Semaphore semaphore) {
        this.maxCars = (int) (Math.random() * 5) + 5;
        this.maxWeight = (int) (Math.random() * 500000) +200000;
        this.gridLimit = (int) (Math.random() * 5) +2;
        this.changeSpeedSemaphore = semaphore;
    }

    public synchronized void changeSpeed() throws RailroadHazard {
        if (Math.random() < 0.5)
            this.speed = speed + speed * 0.03;
        else {
            this.speed = speed - speed * 0.03;
            if (this.speed < 0) {
                this.speed = 10;
            }
        }
        if (speed > 200) {
            speed = 60;
            throw new RailroadHazard("Speed exceeded 200 km/h");
        }
    }

    public synchronized double getSpeed() {
        return speed;
    }

    public int getGridLimit() {
        return gridLimit;
    }

    public int getMaxCars() {
        return maxCars;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public TrainStation getHomeStation() {
        return homeStation;
    }

    public void setHomeStation(TrainStation station) {
        this.homeStation = station;
    }

    public void setTrainSet(TrainSet trainSet) {
        this.trainSet = trainSet;
    }

    public synchronized void setCurrentStation(TrainStation currentStation) {
        this.currentStation = currentStation;
    }

    public synchronized TrainStation getCurrentStation() {
        return currentStation;
    }

    @Override
    public String toString() {
        return "Locomotive that can carry " + maxCars + " cars, has max weight " +
                maxWeight + " and has a \ngrid limit of " + gridLimit + " electrified cars, and has an ID " +
                locomotiveID;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                changeSpeedSemaphore.acquire();
                changeSpeed();
                changeSpeedSemaphore.release();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            catch (RailroadHazard e) {
                System.err.println(e.getMessage() + "\n" + trainSet);
            }
        }
    }
}
