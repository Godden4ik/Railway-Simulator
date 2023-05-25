package RailRoadCars;

import java.util.function.Function;

public class ExplosiveRailRoadFreightCar extends HeavyRailRoadFreightCar{
    String[][] explosives = {
            {"Mercury fulminate", "Very sensitive", "Primary explosive"},
            {"TNT", "Slightly sensitive", "High explosive"},
            {"RDX", "Insensitive", "Tertiary explosive"},
            {"C4", "Slightly sensitive", "Secondary explosive"},
            {"Smokeless powder", "Insensitive", "Low explosive"},
            {"Lead azide", "Very sensitive", "Primary explosive"}
    };
    private String explosivity;
    private String impactSensitivity;
    private int degradation;

    public ExplosiveRailRoadFreightCar() {
        super();
        randomExplosive(explosives);
    }

    private void randomExplosive(String[][] explosives) {
        int randomIndex = (int) (Math.random() * explosives.length);
        String[] explosive = explosives[randomIndex];
        this.cargoType = explosive[0];
        this.impactSensitivity = explosive[1];
        this.explosivity = explosive[2];
        degradation = 0;
    }

    @Override
    public void changeProperties() {
        switch (impactSensitivity) {
            case "Very sensitive":
                degradation += 10;
                break;
            case "Slightly sensitive":
                degradation += 5;
                break;
            case "Insensitive":
                degradation += 1;
                break;
            default: break;
        }
        if(degradation >= 100) {
            randomExplosive(explosives);
        }
    }

    @Override
    public String toString() {
        Function<Integer, String> pluralizer = (number) -> number == 1 ? "point" : "points";
        return  "Explosive Heavy railroad car hauling " + cargoType + " with a weight of " + cargoWeight + " with an explosivity level of " + explosivity + " and \n" +
                "with impact sensitivity of " + impactSensitivity + " with a degradation level of " + degradation + " " + pluralizer.apply(degradation);
    }
}
