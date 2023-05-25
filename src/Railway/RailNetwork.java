package Railway;

import Exceptions.ConnectionAlreadyExistsException;
import Exceptions.StationAlreadyExistsException;

import java.util.*;

public class RailNetwork {

    private final ArrayList<TrainStation> trainStations;
    private ArrayList<Connection> connections;

    public RailNetwork() {
        this.trainStations = new ArrayList<>();
        this.connections = new ArrayList<>();
    }

    public RailNetwork generateStations(int stations) {
        // iterate over the specified number of stations
        for (int i = 0; i < stations; i++) {
            // generate random x and y coordinates between 0 and 1000
            double x = Math.random() * 1000;
            double y = Math.random() * 1000;
            // create a unique name for the train station using the current index
            String name = Integer.toString(i);
            // create a new train station object with the generated coordinates and name
            TrainStation station = new TrainStation(x, y, name);
            // add the new train station to the list of train stations in the rail network
            trainStations.add(station);
        }
        // return the rail network to chain the methods
        return this;
    }


    public RailNetwork generateConnections() {
        // iterate over every pair of train stations in the network
        for (int i = 0; i < trainStations.size(); i++) {
            // get the starting station for the connection
            TrainStation fromStation = trainStations.get(i);
            for (int j = i + 1; j < trainStations.size(); j++) {
                // get the ending station for the connection
                TrainStation toStation = trainStations.get(j);
                // create a new Connection object using the starting and ending stations
                Connection connection = new Connection(fromStation, toStation);
                // add the new Connection object to the list of connections in the network
                connections.add(connection);
            }
        }
        // return the RailNetwork object to allow method chaining
        return this;
    }

    public RailNetwork generateNetwork() { // source -> https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
        // Create a set to keep track of all unvisited train stations
        Set<TrainStation> unvisitedStations = new HashSet<>(trainStations);

        // Pick a random starting station and mark it as visited
        TrainStation currentStation = trainStations.get(0);
        unvisitedStations.remove(currentStation);

        // Create a list to hold the minimum spanning tree connections
        ArrayList<Connection> minimumSpanningTree = new ArrayList<>();

        // Keep adding connections to the minimum spanning tree until all stations are visited
        while (!unvisitedStations.isEmpty()) {
            // Initialize variables to keep track of the shortest connection found so far
            Connection shortestConnection = null;
            double shortestLength = Double.MAX_VALUE;

            // Loop through all connections and find the shortest one that starts or ends at the current station
            for (Connection connection : connections) {
                if (connection.contains(currentStation)) {
                    double connectionLength = connection.getLength();
                    if (connectionLength < shortestLength) {
                        TrainStation otherStation = connection.getOtherStation(currentStation);
                        if (unvisitedStations.contains(otherStation)) {
                            shortestConnection = connection;
                            shortestLength = connectionLength;
                        }
                    }
                }
            }

            // If no connection was found, break out of the loop
            if (shortestConnection == null) {
                break;
            }

            // Get the destination station of the shortest connection
            TrainStation destinationStation = shortestConnection.getOtherStation(currentStation);

            // If the destination station is unvisited, add the connection to the minimum spanning tree
            if (unvisitedStations.contains(destinationStation)) {
                minimumSpanningTree.add(shortestConnection);

                // Mark the destination station as visited and remove it from the unvisited set
                unvisitedStations.remove(destinationStation);

                // Set the current station to the destination station
                currentStation = destinationStation;
            }
        }

        // Replace the connections in the RailNetwork instance with the minimum spanning tree connections
        connections = minimumSpanningTree;
        return this;
    }

    public synchronized ArrayList<Connection> findPath(TrainStation start, TrainStation end) {
        // create an empty ArrayList to store the connections in the path
        ArrayList<Connection> path = new ArrayList<>();
        // set the starting station to the current station
        TrainStation currentStation = start;
        // continue iterating until the end station is reached
        while(!currentStation.equals(end)) {
            // iterate over all connections in the network
            for (Connection connection : connections) {
                // check if the current station is part of the current connection
                if (connection.contains(currentStation)) {
                    // add the current connection to the path
                    path.add(connection);
                    // set the current station to the other station in the connection
                    currentStation = connection.getOtherStation(currentStation);
                }
            }
        }
        // return the ArrayList of connections in the path
        return path;
    }

    public void addStation(double x, double y) throws StationAlreadyExistsException {
        boolean stationExists = false;
        for (TrainStation station : trainStations) {
            if (station.getX() == x && station.getY() == y) {
                stationExists = true;
                break;
            }
        }
        if (stationExists) {
            throw new StationAlreadyExistsException("A station with these coordinates already exists.");
        }
        TrainStation station = new TrainStation(x, y, Integer.toString(trainStations.size()));
        trainStations.add(station);
    }

    public void addConnection(int id1, int id2) throws ConnectionAlreadyExistsException {
        TrainStation station1 = trainStations.get(id1);
        TrainStation station2 = trainStations.get(id2);

        boolean connectionExists = false;
        for (Connection connection : connections) {
            if (connection.getStation1() == station1 && connection.getStation2() == station2 ||
                    connection.getStation1() == station2 && connection.getStation2() == station1) {
                connectionExists = true;
                break;
            }
        }
        if (connectionExists) {
            throw new ConnectionAlreadyExistsException("A connection already exists between these two stations.");
        }
        connections.add(new Connection(station1, station2));
    }

    public ArrayList<TrainStation> getTrainStations() {
        return trainStations;
    }

    public ArrayList<Connection> getConnections() {
        return connections;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Train stations:\n");
        for (TrainStation station : trainStations) {
            sb.append(station.toString()).append("\n");
        }
        sb.append("Connections:\n");
        for (Connection connection : connections) {
            sb.append(connection.toString()).append("\n");
        }
        return sb.toString();
    }
}

