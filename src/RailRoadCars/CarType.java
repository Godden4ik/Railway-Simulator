package RailRoadCars;

// Enum representing types of railroad cars
public enum CarType {

    // Enum constants with associated class types
    PASSENGER(PassangerRailRoadCar.class),
    HEAVYFREIGHT(HeavyRailRoadFreightCar.class),
    BASICFREIGHT(BasicRailRoadFreightCar.class),
    EXPLOSIVE(ExplosiveRailRoadFreightCar.class),
    LIQUID(LiquidRailRoadFreightCar.class),
    LIQUIDTOXIC(LiquidToxicRailRoadFreightCar.class),
    TOXIC(ToxicRailRoadFreightCar.class),
    BAGGAGEANDMAIL(RailRoadBaggageAndMailCar.class),
    POSTOFFICE(RailroadPostOffice.class),
    GASEOUS(GaseousRailRoadFreightCar.class),
    REFRIGERATED(RefrigeratedRailroadCar.class),
    RESTARAUNT(RailroadRestaurantCar.class);

    // Class object to represent the car class associated with the enum constant
    private final Class<? extends RailRoadCar> carClass;

    // Constructor to initialize the CarType enum constant with its corresponding class type
    CarType(Class<? extends RailRoadCar> carClass) {
        this.carClass = carClass;
    }

    // Method to create an instance of the associated car class
    public RailRoadCar getCar() throws Exception {
        return carClass.newInstance();
    }

    // Return the ordinal number of the enum constant along with its name
    @Override
    public String toString() {
        return (this.ordinal() + 1) + ". " + this.name();
    }
}
