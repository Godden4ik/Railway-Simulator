package RailRoadCars;

public class RailRoadBaggageAndMailCar implements RailRoadCar{
    private int grossWeight;
    private int baggage;
    private int mail;
    public RailRoadBaggageAndMailCar() {
        this.mail = (int) (Math.random() * 500) +300;
        this.baggage = (int) (Math.random() * 500) +300;
        this.grossWeight = 12000 + mail + baggage;
    }
    @Override
    public int getCargoWeight() {
        return mail + baggage;
    }

    @Override
    public int getGrossWeight() {
        return grossWeight;
    }

    @Override
    public void changeWeightCargo(int cargoWeight) {
         this.mail += cargoWeight/2;
         this.baggage += cargoWeight/2;
    }

    @Override
    public void changeProperties() {

    }

    @Override
    public String toString() {
        return "Baggage and mail railroad car transporting " + getCargoWeight() + " of baggage and mail and has a gross \n" +
                "weight of " +grossWeight;
    }
}
