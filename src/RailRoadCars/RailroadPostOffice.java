package RailRoadCars;

import Exceptions.WeightOutOfBoundsException;
import java.util.ArrayList;

public class RailroadPostOffice implements Electrical, RailRoadCar{
    private int numOfEmployees;
    private int grossWeight;
    private int cargoWeight;
    private int revenue;
    int revenueLossPerEmployee = -100; // revenue loss per employee
    private ArrayList<String> shipments = new ArrayList<>();
    revenueCalculation revenueCalculator = () -> (revenueLossPerEmployee * numOfEmployees) + (shipments.size() * 10);

    public RailroadPostOffice() {
        grossWeight = (int)(Math.random()*5000 + 3000);
        String[] possibleShipments = {
                "Books", "Electronics", "Clothing",
                "Toys", "Furniture", "Sporting Goods",
                "Perfumes", "Jewelry", "Food Items",
                "Artwork", "Medical Supplies", "Automotive Parts"
        };

        for(int i = 0; i < (int) (Math.random() * 200 + 100); i++) {
            addShipment(possibleShipments[(int)(Math.random() * possibleShipments.length)]);
            cargoWeight += 10;
        }
        numOfEmployees = shipments.size()/20;

        this.revenue = revenueCalculator.calculate();
    }


    public boolean enoughRevenue() {
        return (numOfEmployees + this.numOfEmployees) * revenueLossPerEmployee <= revenue;
    }

    private interface revenueCalculation {
        int calculate();
    }

    public void addShipment(String shipment) {
        shipments.add(shipment);
    }

   
    @Override
    public int getCargoWeight() {
        return shipments.size() * 10;
    }

    @Override
    public int getGrossWeight() {
        return grossWeight;
    }

    @Override
    public void changeWeightCargo(int cargoWeight) throws WeightOutOfBoundsException {
        if(this.getCargoWeight() + cargoWeight < 0) {
            throw new WeightOutOfBoundsException("Weight specified is out of bounds");
        }
        this.cargoWeight += cargoWeight;
    }

    @Override
    public void changeProperties() {
        if(numOfEmployees * 10 < shipments.size()) {
            if(enoughRevenue())
                numOfEmployees += 1;
            else shipments.remove((int)(Math.random() * shipments.size() - 1));
        }
    }

    @Override
    public String toString() {
        return "Railroad post office with " + numOfEmployees + " employees, carrying " +
                shipments.size() + " shipments with a total weight of " + cargoWeight +
                " and a gross weight of " + grossWeight;
    }
}
