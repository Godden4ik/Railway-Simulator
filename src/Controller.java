import Exceptions.*;
import RailRoadCars.CarType;
import RailRoadCars.PassangerRailRoadCar;
import RailRoadCars.RailRoadCar;
import Railway.RailNetwork;
import Railway.TrainStation;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller implements Runnable{
    public Controller() {
    }

    @Override
    public void run() {
        int numSets = 25;
        Scanner scanner = new Scanner(System.in);

        ArrayList<Thread> trainSetThreads = new ArrayList<>();
        ArrayList<TrainSet> trainSets = new ArrayList<>();

        RailNetwork railNetwork = new RailNetwork().generateStations(100).generateConnections().generateNetwork();

        for(int i = 0; i < numSets; i++) {
            trainSets.add(new TrainSet(railNetwork, i));
            trainSetThreads.add(new Thread(trainSets.get(i)));
            trainSetThreads.get(i).start();
        }

        Thread appStateThread = new Thread(new AppState(trainSets));
        appStateThread.start();

        try {
            while(true) {
                System.out.println(
                        "Choose the action below: \n" +
                        "1: Add a new station to the graph \n" +
                        "2: Display all stations \n" +
                        "3: Add a new connection between stations \n" +
                        "4: Start operations on train sets \n" +
                        "5: Create a new locomotive \n");

                try {
                    String action = scanner.nextLine();
                    switch (action) {
                        case "1":
                            System.out.println("Set the coordinates of the new station:");
                            while (true) {
                                try {
                                    double x = scanner.nextDouble();
                                    double y = scanner.nextDouble();
                                    if (x < 0 || y < 0) {
                                        System.out.println("Invalid coordinates. Please try again.");
                                        continue;
                                    }
                                    railNetwork.addStation(x, y);
                                    System.out.println("Station added: " +
                                            railNetwork.getTrainStations().get(railNetwork.getTrainStations().size() - 1));
                                    scanner.nextLine();

                                    break;
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter a valid number for the coordinates of the new station");
                                    scanner.next();
                                } catch (StationAlreadyExistsException e) {
                                    System.out.println("Please enter a different set of coordinates, station with these" +
                                            " kind of coordinates already exists.");
                                    scanner.next();
                                }
                            }
                            continue;

                        case "2":
                            for (TrainStation trainStation : railNetwork.getTrainStations()) {
                                System.out.println(trainStation);
                            }
                            continue;

                        case "3":
                            System.out.println(railNetwork);
                            while (true) {
                                try {

                                    System.out.println("Enter the IDs of two stations to connect:");
                                    int id1 = scanner.nextInt();
                                    int id2 = scanner.nextInt();

                                    railNetwork.addConnection(id1, id2);
                                    System.out.println("Connection added successfully: " + railNetwork.getConnections().get(railNetwork.getConnections().size() - 1));
                                    scanner.nextLine();
                                    break;

                                } catch (ConnectionAlreadyExistsException e){
                                    System.out.println(e.getMessage());
                                    System.out.println("Try adding a connection between two other unconnected stations.");

                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter two integers separated by a space.");
                                    scanner.nextLine(); // consume the invalid input
                                } catch (IndexOutOfBoundsException e) {
                                    System.out.println("Invalid station ID. Please enter a valid station ID.");
                                    scanner.nextLine(); // consume the invalid input
                                } catch (Exception e) {
                                    System.out.println("An error occurred while connecting the stations. Please try again.");
                                    scanner.nextLine(); // consume the invalid input
                                }
                            }
                            continue;

                        case "4":
                            break;

                        case "5":
                            trainSets.add(new TrainSet(railNetwork, trainSets.size()));
                            trainSetThreads.add(new Thread(trainSets.get(trainSets.size() - 1)));
                            trainSetThreads.get(trainSetThreads.size() - 1).start();
                            System.out.println("New locomotive created: " +
                                    trainSets.get(trainSets.size() - 1).getLocomotive());
                            continue;


                        default:{
                            System.out.println("Invalid action. Please enter a number between 1 and 5.");
                            continue;}
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input. Enter an integer that corresponds to an action.");
                    continue;
                }

                while (true) {
                    System.out.println("Enter train set number to access (0-" + (trainSets.size() - 1) + ") or type 'q' to return:");
                    String input = scanner.nextLine();

                    if (input.equalsIgnoreCase("q")) {
                        break;
                    }

                    try {
                        int trainSetNumber = Integer.parseInt(input);

                        if (trainSetNumber < 0 || trainSetNumber > trainSets.size() - 1) {
                            System.out.println("Invalid train set number. Please enter a number between 0 and " +
                                    (trainSets.size() - 1));
                            continue;
                        }
                        TrainSet trainSet = trainSets.get(trainSetNumber);

                            while (true) {
                                System.out.println();
                                if (trainSetThreads.get(trainSetNumber).isAlive()) {
                                    System.out.println("Specify the action on thread " + trainSetNumber + ": \n" +
                                            "1: Display total info about the train set \n" +
                                            "2: Display current locomotive station \n" +
                                            "3: Remove a car \n" +
                                            "4: Display distance completed to the target % \n" +
                                            "5: Display distance completed to the nearest train station % \n" +
                                            "6: Add a new car to the train set \n" +
                                            "7: Change the weight of a car \n" +
                                            "8: Board or unboard people from a car \n" +
                                            "Or type 'q' to return.\n");
                                String action = scanner.nextLine();

                                if (action.equalsIgnoreCase("q")) {
                                    break;
                                }

                                switch (action) {
                                    case "1":
                                        trainSet.displaySet();
                                        continue;

                                    case "2":
                                        System.out.println(trainSet.getLocomotive().getCurrentStation());
                                        continue;

                                    case "3":
                                        while (true) {
                                            System.out.println("Remove one of these cars: ");
                                            trainSet.displayCars();
                                            System.out.println("To remove one of the cars enter a number to which that " +
                                                    "car corresponds, or enter 'q' to exit:");

                                            input = scanner.nextLine();
                                            if (input.equalsIgnoreCase("q")) {
                                                break;
                                            }
                                            try {
                                                int carNumber = Integer.parseInt(input) - 1;
                                                if (carNumber < 0 ||
                                                        carNumber > trainSet.getCars().size() - 1) {
                                                    System.out.println("Invalid car number. Please try again.");
                                                    continue;
                                                }

                                                trainSet.removeCar(carNumber);
                                                System.out.println("Car successfully removed.");

                                                break;
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid input. Please enter a number that corresponds " +
                                                        "to a car, or enter 'q' to exit:");
                                            } catch (RailRoadCarNotFoundException e) {
                                                System.err.println(e.getMessage());
                                            }
                                            break;
                                        }
                                        continue;

                                    case "4":
                                        System.out.println("Distance completed: " + trainSet.getDistanceCoveredPercent() + "%");
                                        continue;

                                    case "5":
                                        System.out.println(
                                                "Distance completed to the nearest train station: " +
                                                        trainSet.getDistanceCoveredToStationPercent() + "%");
                                        continue;

                                    case "6":
                                        System.out.println("Available railroad cars:");
                                        for (CarType carType : CarType.values()) {
                                            System.out.println(carType);
                                        }

                                        while (true) {
                                            try {
                                                System.out.println("Enter the number of the type of railroad car to add:");
                                                int carTypeIndex = Integer.parseInt(scanner.nextLine()) - 1;
                                                if (carTypeIndex < 0 || carTypeIndex >= CarType.values().length) {
                                                    System.out.println("Invalid car type number entered. Please try again.");
                                                    continue;
                                                }

                                                trainSet.attachCar(carTypeIndex);

                                                System.out.println("Added car: " + trainSet.getCars().get(trainSet.getCars().size() - 1));
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid input. Enter a number that corresponds to a car.");
                                                continue;
                                            } catch (LocomotiveMaxCarCapacityReachedException e) {
                                                System.out.println(e.getMessage());
                                                System.out.println("The locomotive car limit was reached, try removing" +
                                                        " one of the cars before adding one.");
                                                break;
                                            } catch (LocomotiveGridLimitReached e) {
                                                System.out.println(e.getMessage());
                                                System.out.println("Locomotives grid limit reached, try removing one" +
                                                        "of the grid-connected cars before adding one.");
                                            }

                                            System.out.println("Do you want to add another car? (Y/N)");
                                            String answer = scanner.nextLine();
                                            if (!answer.equalsIgnoreCase("Y")) {
                                                break;
                                            }
                                        }
                                        continue;
                                    case "7":
                                        while (true) {
                                            ArrayList<RailRoadCar> cars = new ArrayList<>();
                                            System.out.println("Choose one of these cars: ");
                                            for (RailRoadCar car : trainSet.getCars()) {
                                                if (!(car instanceof PassangerRailRoadCar)) {
                                                    cars.add(car);
                                                }
                                            }
                                            for (int i = 0; i < cars.size(); i++) {
                                                RailRoadCar car = cars.get(i);
                                                System.out.printf("%d. %s\n", i + 1, car);
                                            }
                                            System.out.println("To choose one of the cars enter a number to which that " +
                                                    "car corresponds, or enter 'q' to exit.");
                                            input = scanner.nextLine();


                                            if (input.equalsIgnoreCase("q")) {
                                                break;
                                            }
                                            try {
                                                int carNumber = Integer.parseInt(input) - 1;
                                                if (carNumber < 0 ||
                                                        carNumber > cars.size()) {
                                                    System.out.println("Invalid car number. Please try again.");
                                                    continue;
                                                }

                                                System.out.println("Cargo weight before change: " +
                                                        trainSet.getCars().get(carNumber).getCargoWeight());
                                                System.out.println("Enter the cargo weight change (- for removing).");
                                                int weight = scanner.nextInt();
                                                trainSet.changeCarCargoWeight(carNumber, weight);
                                                System.out.println("Cars' weight successfully changed: " +
                                                        trainSet.getCars().get(carNumber).getCargoWeight());
                                                break;
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid input. Please enter a number that corresponds " +
                                                        "to a car, or enter 'q' to exit:");
                                            } catch (RailRoadCarNotFoundException e) {
                                                System.err.println(e.getMessage());
                                                System.out.println("Input a valid car number.");
                                            } catch (WeightOutOfBoundsException e) {
                                                System.err.println(e.getMessage());
                                                System.out.println("Input a valid weight change.");
                                            } catch (LocomotiveMaxWeightReachedException e) {
                                                System.err.println(e.getMessage());
                                                System.out.println("Input a lower weight change.");
                                            } finally {
                                                input = scanner.nextLine();
                                            }
                                            break;
                                        }
                                        continue;
                                    case "8":
                                        ArrayList<PassangerRailRoadCar> passengerCars = trainSet.getPassengerCars();
                                        if (passengerCars.size() == 0) {
                                            System.out.println("No passenger cars available.");
                                            break;
                                        }
                                        System.out.println("Passenger cars available: ");
                                        for (int i = 0; i < passengerCars.size(); i++) {
                                            RailRoadCar car = passengerCars.get(i);
                                            System.out.printf("%d. %s\n", i + 1, car);
                                        }
                                        while (true) {
                                            System.out.println("To choose one of the cars enter a number to which that " +
                                                    "car corresponds, or enter 'q' to exit.");
                                            input = scanner.nextLine();


                                            if (input.equalsIgnoreCase("q")) {
                                                break;
                                            }
                                            try {
                                                int carNumber = Integer.parseInt(input) - 1;
                                                if (carNumber < 0 ||
                                                        carNumber > trainSet.numberOfPassengerCars()) {
                                                    System.out.println("Invalid car number. Please try again.");
                                                    continue;
                                                }
                                                PassangerRailRoadCar car = passengerCars.get(carNumber);
                                                System.out.println("Passenger number before change: " + car.getPassengerNumber());
                                                System.out.println("Enter the number of people to change (- for removing).");
                                                int people = scanner.nextInt();
                                                car.changePassengerCount(people);
                                                System.out.println("Cars' people count successfully changed: " +
                                                        car.getPassengerNumber());
                                                break;
                                            } catch (NumberFormatException e) {
                                                System.out.println("Invalid input. Please enter a number that corresponds " +
                                                        "to a car, or enter 'q' to exit:");
                                            } catch (WeightOutOfBoundsException e) {
                                                System.err.println(e.getMessage());
                                                System.out.println("Input a valid people count change.");
                                            } catch (NumberOfSeatsExceededException e) {
                                                System.err.println(e.getMessage());
                                                System.out.println("The value entered exceeded the number of seats available");
                                            } finally {
                                                input = scanner.nextLine();
                                            }
                                            break;
                                        }
                                        continue;


                                    default:
                                        System.out.println("Invalid action. Please enter a number between 1 and 8.");
                                }
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a number between 0 and " + (trainSets.size() - 1) + " or type 'exit' to quit.");
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("An unexpected error occurred. Please try again.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}

