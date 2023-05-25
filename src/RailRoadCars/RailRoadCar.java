package RailRoadCars;

import Exceptions.LocomotiveMaxWeightReachedException;
import Exceptions.WeightOutOfBoundsException;

//an interface from which all the railroad cars will implement its methods
public interface RailRoadCar {

    int getCargoWeight();
    int getGrossWeight();
    void changeWeightCargo(int cargoWeight) throws WeightOutOfBoundsException;
    void changeProperties();

    @Override
    String toString();

}
