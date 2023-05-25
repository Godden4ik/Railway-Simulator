package RailRoadCars;


public class RefrigeratedRailroadCar extends BasicRailRoadFreightCar implements Electrical{
    String[][] foods = {
            {"Chicken", "Animal"},
            {"Beef", "Animal"},
            {"Tofu", "Plant"},
            {"Ice cream", "Mixed"},
            {"Milk", "Animal"}
    };
    String origin;
    int temperature;
    int rot;

    public RefrigeratedRailroadCar() {
        super();
        randomFood(foods);
    }

    private void randomFood(String [][] foods) {
        int randomIndex = (int) (Math.random() * foods.length);
        String[] food = foods[randomIndex];
        this.cargoType = food[0];
        this.origin = food[1];
        rot = 0;
        temperature = -20;
    }

    private void changeTemp() {
        this.temperature += (int) (Math.random() * 3) + 1;
    }

    @Override
    public void changeProperties() {
        switch(origin){
            case "Animal":
                rot += (temperature > -10)? 10 : 0;
                break;
            case "Mixed" :
                rot += (temperature > -5)? 10 : 0;
                break;
            case "Plant" :
                rot += (temperature > 0)? 10 : 0;
                break;
        }

        if(rot >= 100) {
            randomFood(foods);
        }
        changeTemp();
    }

    @Override
    public String toString() {
        return "Refrigerated railroad car hauling " + cargoType + " with a weight of " + cargoWeight + " of \n" +
        origin + " origin with a rot level of " + rot + " points.";
    }
}
