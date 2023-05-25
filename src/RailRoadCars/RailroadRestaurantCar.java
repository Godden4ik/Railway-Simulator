package RailRoadCars;

import Exceptions.WeightOutOfBoundsException;

public class RailroadRestaurantCar implements Electrical, RailRoadCar{
    private int numOfTables;
    private int grossWeight;
    private int foodWeight;
    private int pestInfestation;

    public RailroadRestaurantCar() {
        numOfTables = (int) (Math.random() * 10 + 2);
        foodWeight =  (int) (Math.random() * 200 + 300);
        grossWeight = (int) (Math.random() * 5000 + 5000) + foodWeight;
        pestInfestation = (int) (Math.random() * 3) + 1;
    }

    private void orderFood() {
        foodWeight += 500;
    }

    @Override
    public int getCargoWeight() {
        return foodWeight;
    }

    @Override
    public int getGrossWeight() {
        return grossWeight;
    }

    @Override
    public void changeWeightCargo(int foodWeight) throws WeightOutOfBoundsException {
        int totalWeight = this.foodWeight + foodWeight;
        if(totalWeight < 0 || totalWeight > 10000) {
            throw new WeightOutOfBoundsException("The weight of the food exceeds the limit.");
        }
        this.foodWeight = totalWeight;
    }

    @Override
    public void changeProperties() {
        foodWeight = foodWeight - (int) (foodWeight * (0.03) * pestInfestation + numOfTables * 2);
        if(foodWeight <= 0)
            orderFood();
    }

    @Override
    public String toString() {
        return "Railroad restaurant car with " + numOfTables + " tables, " +
               "a food weight of " + foodWeight + " and a gross weight of \n"
                + grossWeight + " and a pest infestation level of " + pestInfestation;
    }
}
