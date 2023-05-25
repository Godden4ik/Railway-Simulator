package RailRoadCars;
import Exceptions.NumberOfSeatsExceededException;
import Exceptions.WeightOutOfBoundsException;

public class PassangerRailRoadCar implements RailRoadCar {
    private int passengerCount;
    private int cargoWeight;
    private int seatAmount;
    private int grossWeight;

    public PassangerRailRoadCar() {
        this.seatAmount = (int) (Math.random() * 50) + 20;
        this.passengerCount = (int) (Math.random() * seatAmount);
        this.cargoWeight = passengerCount * 70;
        this.grossWeight = (int) (Math.random() * 5000) + 5000 + cargoWeight;
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

    }


    @Override
    public void changeProperties() {

    }

    public void changePassengerCount(int passengerCount) throws NumberOfSeatsExceededException, WeightOutOfBoundsException {
        if(this.passengerCount + passengerCount > this.seatAmount)
            throw new NumberOfSeatsExceededException("The number of seats was exceeded.");
        if(this.passengerCount + passengerCount < 0 )
            throw new WeightOutOfBoundsException("The number of people is invalid.");
        this.passengerCount += passengerCount;
        this.cargoWeight += passengerCount*70;
    }
    
    public int getPassengerNumber() {
        return passengerCount;
    }

    @Override
    public String toString() {
        return "Passenger railroad car carrying " + passengerCount + " people with a gross weight of " + grossWeight;
    }
}
