package RailRoadCars;

import java.util.function.Function;
public class LiquidToxicRailRoadFreightCar extends HeavyRailRoadFreightCar implements LiquidRailRoadCar{
    String[][] toxicLiquids = {
            {"Benzene", "High toxicity", "Low viscosity"},
            {"Toluene", "Low toxicity", "Low viscosity"},
            {"Hydrochloric acid", "High toxicity", "Low viscosity"},
            {"Ammonia", "Moderate toxicity", "Low viscosity"},
            {"Formaldehyde", "High toxicity", "Low viscosity"},
            {"Acetone", "High toxicity", "Medium viscosity"}
    };
    private String toxicity;
    private String viscosity;
    private int contamination;

    public LiquidToxicRailRoadFreightCar() {
        super();

        randomToxicLiquid(toxicLiquids);
    }
    private void randomToxicLiquid(String [][] toxicLiquids) {
        int randomIndex = (int) (Math.random() * toxicLiquids.length);
        String[] toxicLiquid = toxicLiquids[randomIndex];

        this.cargoType = toxicLiquid[0];
        this.toxicity = toxicLiquid[1];
        this.viscosity = toxicLiquid[2];
        contamination = 0;
    }

    @Override
    public void changeWeightCargo(int cargoWeight) {
        this.cargoWeight += cargoWeight;
    }

    @Override
    public void changeProperties() {

        switch(toxicity){
            case "High toxicity":
                contamination += 10;
                break;
            case "Moderate toxicity" :
                contamination += 5;
                break;
            case "Low toxicity" :
                contamination += 1;
                break;
        }
        if(contamination >= 100) {
            randomToxicLiquid(toxicLiquids);
        }
        switch (viscosity) {
            case "Medium viscosity":
                cargoWeight -= cargoWeight * 0.02;
                break;
            case "Low viscosity":
                cargoWeight -= cargoWeight * 0.01;

        }
        if(cargoWeight <= 0) {
            randomToxicLiquid(toxicLiquids);
            cargoWeight = (int) (Math.random() * 3000) + 1000;
        }
    }

    @Override
    public String toString() {
        Function<Integer, String> pluralizer = (number) -> number == 1 ? "point" : "points";
        return "Toxic liquid " + super.toString() + " with a toxicity level of " + toxicity + " and viscosity \n" +
                "level of " + viscosity + " with a contamination severity of " + contamination + " " + pluralizer.apply(contamination);
    }
}
