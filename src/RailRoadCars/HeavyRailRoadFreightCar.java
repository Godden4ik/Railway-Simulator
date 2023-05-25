package RailRoadCars;

import Exceptions.WeightOutOfBoundsException;

public class HeavyRailRoadFreightCar implements RailRoadCar{
    protected String cargoType;
    protected int grossWeight;
    protected int cargoWeight;

    public HeavyRailRoadFreightCar() {
        String[] cargoTypes = {"Coal", "Building materials", "Automobiles", "Iron ore", "Lumber", "Sand", "Rebar"};
        this.cargoWeight = (int) (Math.random() * 50000) +30000;
        this.grossWeight = 12000 + cargoWeight;
        this.cargoType = cargoTypes[(int)(Math.random() * cargoTypes.length)];

    }



    @Override
    public int getCargoWeight() {
        return cargoWeight;
    }

    @Override
    public int getGrossWeight() {
        return grossWeight;
    }

    @Override
    public void changeWeightCargo(int cargoWeight) throws WeightOutOfBoundsException {
        if(this.cargoWeight + cargoWeight < 0) {
            throw new WeightOutOfBoundsException("Weight specified is out of bounds");
        }
        this.cargoWeight += cargoWeight;
    }

    @Override
    public void changeProperties() {

    }

    @Override
    public String toString() {
        return "Heavy railroad freight car hauling " + cargoType + " with a weight " + cargoWeight + " and gross weight \n"
                + grossWeight;
    }
}
