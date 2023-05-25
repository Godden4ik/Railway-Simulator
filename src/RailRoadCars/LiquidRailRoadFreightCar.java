package RailRoadCars;

public class LiquidRailRoadFreightCar extends BasicRailRoadFreightCar implements LiquidRailRoadCar{
    String[] liquids = {"Ethanol", "Crude oil", "Jet fuel", "Liquid fertilizer", "Corn syrup"};
    private int purity;
    private String transparency;
    public LiquidRailRoadFreightCar() {
        super();
        randomLiquid(liquids);
    }

    private void changePurity() {
        purity += (int) (Math.random() * 5 + 1);
    }

    private void changeTransparency(String transparency) {
        this.transparency = transparency;
    }

    private void randomLiquid(String[] liquids) {
        int randomIndex = (int) (Math.random() * liquids.length);
        this.cargoType = liquids[randomIndex];
        this.purity = 100;
        this.transparency = "Transparent";
    }

    @Override
    public void changeProperties() {
        String[] transparencies = {"Opaque", "Translucent", "Transparent"};
        changePurity();
        if(purity < 75) {
            changeTransparency(transparencies[1]);
        }
        if(purity < 50) {
            changeTransparency(transparencies[0]);
        }
        else {
            randomCargo();
            changeTransparency(transparencies[2]);
        }
    }


    @Override
    public String toString() {
        return "Liquid railroad freight car hauling " + cargoType + " with purity of " + purity +
                " and transparency of " + transparency;
    }
}
