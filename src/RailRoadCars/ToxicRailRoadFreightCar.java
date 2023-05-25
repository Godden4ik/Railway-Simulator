package RailRoadCars;

import java.util.function.Function;

public class ToxicRailRoadFreightCar extends HeavyRailRoadFreightCar {
    String[][] chemicalProperties = {
            {"Asbestos", "Non-toxic", "Non-reactive"},
            {"Mercury chloride", "High toxicity", "Low reactivity"},
            {"Uranium oxide", "High toxicity", "Low reactivity"},
            {"Arsenic", "High toxicity", "Medium reactivity"},
            {"Sodium hydroxide", "Moderate toxicity", "High reactivity"}
    };
    private String toxicity;
    private String reactivity;
    private int corrosion;


    public ToxicRailRoadFreightCar() {
        super();
        randomChemical(chemicalProperties);
    }

    private void randomChemical(String[][] chemicalProperties) {
        int randomIndex = (int) (Math.random() * chemicalProperties.length);
        String[] chemical = chemicalProperties[randomIndex];
        this.cargoType = chemical[0];
        this.toxicity = chemical[1];
        this.reactivity = chemical[2];
        corrosion = 0;
    }

    @Override
    public void changeProperties() {
        switch (reactivity) {
            case "High reactivity":
                corrosion += 10;
                break;
            case "Medium reactivity":
                corrosion += 5;
                break;
            case "Low reactivity":
                corrosion += 1;
                break;
            default: break;
        }
        if(corrosion >= 100) {
            randomChemical(chemicalProperties);
        }
    }

    @Override
    public String toString() {
        Function<Integer, String> pluralizer = (number) -> number == 1 ? "point" : "points";
        return "Toxic " + super.toString() + " with a toxicity level of " + toxicity + " and with reactivity of \n" + reactivity +
                " and corrosion level of " + corrosion + " " + pluralizer.apply(corrosion);
    }
}
