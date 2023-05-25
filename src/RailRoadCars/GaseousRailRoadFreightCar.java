package RailRoadCars;

public class GaseousRailRoadFreightCar extends BasicRailRoadFreightCar implements RailRoadCar{
    String[][] gases = {
            {"Propane", "Moderate pressure", "High flammability"},
            {"CNG", "High pressure", "High flammability"},
            {"Methane", "High pressure", "High flammability"},
            {"Nitrogen", "High pressure", "Non-flammable"},
            {"Chlorine", "Low pressure", "Non-flammable"},
            {"Hydrogen sulfide", "Moderate pressure", "High flammability"}
    };
    private String flammability;
    private String pressure;

    public GaseousRailRoadFreightCar() {
        super();
        randomGas(gases);

    }
    private void randomGas(String[][] gases) {
        int randomIndex = (int) (Math.random() * gases.length);
        String[] gas = gases[randomIndex];
        this.cargoType = gas[0];
        this.pressure = gas[1];
        this.flammability = gas[2];
    }

    @Override
    public void changeProperties() {
        switch (pressure) {
            case "High pressure":
                cargoWeight -= cargoWeight * 0.03;
                break;
            case "Medium pressure":
                cargoWeight -= cargoWeight * 0.02;
                break;
            case "Low pressure":
                cargoWeight -= cargoWeight * 0.01;
                break;
            default: break;
        }
        if(cargoWeight <= 0) {
            randomGas(gases);
            cargoWeight = (int) (Math.random() * 3000) + 1000;
        }
    }

    @Override
    public String toString() {
         return "Gaseous basic freight railroad car hauling " + cargoType  + " with a flammability level of \n" + flammability + " and " +
                 "with pressure of " + pressure;
    }


}
