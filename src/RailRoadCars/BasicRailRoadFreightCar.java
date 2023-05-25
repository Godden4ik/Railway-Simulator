package RailRoadCars;

import Exceptions.WeightOutOfBoundsException;

public class BasicRailRoadFreightCar implements RailRoadCar{
    String[] cargoTypes = {"Pharmaceuticals", "Fertilizer", "Plastic pellets", "Raw sugar", "Textiles", "Salt"};
    protected String cargoType;
    private String quality;
    private int freshness;
    protected int cargoWeight;
    protected int grossWeight;

    public BasicRailRoadFreightCar() {
        this.cargoWeight = (int) (Math.random() * 20000) +10000;
        this.grossWeight = 6000 + cargoWeight;
        randomCargo();
        this.quality = "Excellent";
        this.freshness = 100;
    }

    public String getCargoType() {
        return cargoType;
    }
    protected void randomCargo() {
        this.cargoType = cargoTypes[(int)(Math.random() * cargoTypes.length)];
    }

    public int getCargoWeight() {
        return cargoWeight;
    }

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

    private void changeFreshness() {
        freshness -= (int) (Math.random() * 5 + 1);
    }

    private void changeQuality(String quality) {
        this.quality = quality;
    }

    @Override
    public void changeProperties() {
        String[] qualities = {"Poor", "Average", "Good", "Excellent"};
        changeFreshness();
        if(freshness < 75) {
            changeQuality(qualities[2]);
        }
            if(freshness < 50) {
                changeQuality(qualities[1]);
            }
                if(freshness < 25) {
                    changeQuality(qualities[0]);
                }
                    else {
                        randomCargo();
                        changeQuality(qualities[3]);
                    }


    }


    @Override
    public String toString() {
        return "Basic railroad freight car hauling " + cargoType + " of net weight " + cargoWeight + " and gross weight "
                + grossWeight + " with a quality of " + quality + " and freshness of " + freshness + " units.";
    }
}
