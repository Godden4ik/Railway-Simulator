import Exceptions.*;
import RailRoadCars.*;
import Railway.Connection;
import Railway.RailNetwork;
import Railway.TrainStation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class TrainSet implements Runnable{
    private Locomotive locomotive = null;
    private int identifier;
    private double totalDistance;
    private double totalDistanceCovered;
    private double distanceCoveredToStation;
    private double distanceToStation;
    private final RailNetwork railNetwork;
    private final Semaphore speedSemaphore = new Semaphore(1);
    private int totalWeight;
    private int gridConnectedCars;
    private final LinkedList<RailRoadCar> cars = new LinkedList<>();
    private ArrayList<Connection> path;

    public TrainSet(RailNetwork railNetwork, int identifier) {
        this.identifier = identifier;
        this.railNetwork = railNetwork;
        this.locomotive = new Locomotive(speedSemaphore);
        locomotive.setHomeStation(railNetwork.getTrainStations().get(
                (int) (Math.random() * railNetwork.getTrainStations().size())));
        locomotive.setCurrentStation(locomotive.getHomeStation());
    }

    public  void attachCar(int carTypeIndex) throws Exception{
        CarType carType = CarType.values()[carTypeIndex];
        RailRoadCar car = carType.getCar();
        if (car instanceof Electrical) {
            if (getLocomotive().getGridLimit() > getGridConnectedCars()) {
                changeGridConnectedCars(1);
            }
            else {
                throw new LocomotiveGridLimitReached("Locomotive grid limit reached.");
            }
        }

        if (cars.isEmpty()) {
            totalWeight += car.getGrossWeight();
            cars.add(car);
        } else if(locomotive.getMaxCars() < cars.size() + 1){
            throw new LocomotiveMaxCarCapacityReachedException("Max car capacity reached.");

        } else if (totalWeight + car.getGrossWeight() < locomotive.getMaxWeight()) {
            cars.add(car);
            totalWeight += car.getGrossWeight();
        } else if (totalWeight + car.getGrossWeight() < locomotive.getMaxWeight()) {
            throw new LocomotiveMaxWeightReachedException("Locomotive max weight reached, railcar wasn't added");
        }

    }

    public void removeCar(int carNumber) throws RailRoadCarNotFoundException {
        RailRoadCar car = cars.get(carNumber);
        if (car instanceof Electrical) {
            changeGridConnectedCars(-1);
        }
        if (cars.remove(car)) {
            totalWeight -= car.getGrossWeight();
        }
        else {
            throw new RailRoadCarNotFoundException("Railroad car " + car + " not found in train set.");
        }
    }

    public void changeCarCargoWeight(int carNumber, int cargoWeight) throws WeightOutOfBoundsException, LocomotiveMaxWeightReachedException, RailRoadCarNotFoundException {
        if(cars.get(carNumber) == null)
            throw new RailRoadCarNotFoundException("No car was found with this number.");
        if(cargoWeight + cars.get(carNumber).getGrossWeight() + totalWeight > locomotive.getMaxWeight())
            throw new LocomotiveMaxWeightReachedException("Car weight exceeded locomotives' max weight, weight wasn't changed.");
        cars.get(carNumber).changeWeightCargo(cargoWeight);
        totalWeight += cargoWeight;
    }

    public void displaySet() {
        System.out.println("Locomotive of the train set: \n" + locomotive +"\n");

        if (cars.isEmpty()) {
            System.out.println("No cars attached to this train set.");
            return;
        }

        System.out.println("Cars of the train set:");
        for (int i = 0; i < cars.size(); i++) {
            RailRoadCar car = cars.get(i);
            System.out.printf("%d. %s\n", i+1, car);
        }
    }

    public int numberOfPassengerCars() {
        int number = 0;
        for(RailRoadCar car : cars) {
            if (car instanceof PassangerRailRoadCar) {
                number++;
            }
        }
        return number;
    }

    public ArrayList<PassangerRailRoadCar> getPassengerCars() {
        ArrayList<PassangerRailRoadCar> passengerCars = new ArrayList<>();
        for(RailRoadCar car : cars) {
            if (car instanceof PassangerRailRoadCar) {
                passengerCars.add((PassangerRailRoadCar) car);
            }
        }
        return passengerCars;
    }

    public void displayCars() {
        System.out.println("Cars of the train set:");
        for (int i = 0; i < cars.size(); i++) {
            RailRoadCar car = cars.get(i);
            System.out.printf("%d. %s\n", i+1, car);
        }
    }

    public  Locomotive getLocomotive() {
        return locomotive;
    }
    public  LinkedList<RailRoadCar> getCars() {
        return cars;
    }

    public  void setDistanceToStation(double distance) {
        this.distanceToStation = distance;
    }

    public void changeGridConnectedCars(int count) {
        gridConnectedCars += count;
    }

    public int getGridConnectedCars() {
        return gridConnectedCars;
    }

    public String getDistanceCoveredPercent() {
        return String.format("%.2f", (totalDistanceCovered /totalDistance) * 100);
    }
    public String getDistanceCoveredToStationPercent() {
        return String.format("%.2f", (distanceCoveredToStation / distanceToStation) * 100 );
    }

    public  void setTotalDistance(int totalDistance) {
        this.totalDistance = totalDistance;
    }

    public  void setTotalDistanceCovered(double totalDistanceCovered) {
        this.totalDistanceCovered = totalDistanceCovered;
    }

    public  double getTotalDistance() {
        return totalDistance;
    }

    public  void calculateTotalDistance(ArrayList<Connection> path) {
        for(Connection connection : path) {
            totalDistance += connection.getLength();
        }
    }

    public  void calculateDistanceCovered(long deltaTime) {
        totalDistanceCovered += distanceCovered(deltaTime);
        distanceCoveredToStation += distanceCovered(deltaTime);
    }
    private  double distanceCovered(long deltaTime) {
        return (locomotive.getSpeed() / 36000) * deltaTime;
    }

    public  void setDistanceCoveredToStation(double distance) {
        distanceCoveredToStation = distance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Train set number: ").append(identifier).append("\n");

        sb.append("Locomotive of the train set:\n").append(locomotive).append("\n");

        LinkedList<RailRoadCar> carsCopy = new LinkedList<>(cars);

        for (int i = 0; i < carsCopy.size() - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < carsCopy.size(); j++) {
                if (carsCopy.get(j).getGrossWeight() < carsCopy.get(maxIdx).getGrossWeight()) {
                    maxIdx = j;
                }
            }
            // swap list[i] with list[maxIdx]
            RailRoadCar temp = carsCopy.get(i);
            carsCopy.set(i, carsCopy.get(maxIdx));
            carsCopy.set(maxIdx, temp);
        }

        if (carsCopy.isEmpty()) {
            sb.append("No cars attached to this train set.\n");
        } else {
            sb.append("Cars of the train set:\n");
            for (int i = 0; i < carsCopy.size(); i++) {
                RailRoadCar car = carsCopy.get(i);
                sb.append(i+1).append(". ").append(car).append("\n");
            }
        }

        sb.append("Total distance: ").append(totalDistance).append(" km").append("\n");
        sb.append("Distance covered: ").append(totalDistanceCovered).append(" km").append("\n");
        sb.append("Distance covered percent: ").append(getDistanceCoveredPercent()).append("%\n");
        sb.append("Total weight: ").append(totalWeight).append(" kg").append("\n");

        return sb.toString();
    }

    @Override
    public void run() {
        //attach a locomotive to the train set
        Thread locomotiveThread = new Thread(locomotive);
        locomotive.setTrainSet(this);
        //generate random railroad cars and attach them
        for (int i = 0; i < locomotive.getMaxCars(); i++) {
            CarType[] types = CarType.values();
            int index = (int) (Math.random() * (types.length));
            try {
                attachCar(index);
            } catch (LocomotiveGridLimitReached ignored) {
            } catch (Exception e) {
                break;
            }
        }

        try{
            speedSemaphore.acquire();
            locomotiveThread.start();

            while(!Thread.currentThread().isInterrupted()) {

                setTotalDistance(0);
                setTotalDistanceCovered(0);

                path = railNetwork.findPath(locomotive.getCurrentStation(), railNetwork.getTrainStations().get(
                        (int)(Math.random() * railNetwork.getTrainStations().size())));

                calculateTotalDistance(path);

                ArrayList<TrainStation> traversedStations = new ArrayList<>();
                traversedStations.add(locomotive.getCurrentStation());

                for(Connection connection : path) {
                    TrainStation station1 = connection.getStation1();
                    TrainStation station2 = connection.getStation2();
                    if (!traversedStations.contains(station1)) {
                        traversedStations.add(station1);
                    }
                    if (!traversedStations.contains(station2)) {
                        traversedStations.add(station2);
                    }
                }

                double distanceChecker = 0;

                for(Connection connection : path) {
                    connection.getSemaphore().acquire();
                    distanceChecker += connection.getLength();
                    setDistanceToStation(connection.getLength());
                    setDistanceCoveredToStation(0);

                    long startTime = System.currentTimeMillis();
                    speedSemaphore.release();

                    while (distanceCoveredToStation < connection.getLength()) {
                        Thread.sleep(200);

                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = currentTime - startTime;
                        calculateDistanceCovered(elapsedTime);

                    }
                    setDistanceCoveredToStation(connection.getLength());
                    setTotalDistanceCovered(distanceChecker);

                    connection.getSemaphore().release();
                    speedSemaphore.acquire();

                    for(RailRoadCar car : cars) {
                        car.changeProperties();
                    }

                    Thread.sleep(2000);
                }
                Thread.sleep(5000);
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage() + "Train set identifier: " + identifier);
        }

    }
}
