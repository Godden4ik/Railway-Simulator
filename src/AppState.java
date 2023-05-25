import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AppState implements Runnable {
    private final ArrayList<TrainSet> trainSets;

    public AppState(ArrayList<TrainSet> trainSets) {
        this.trainSets = trainSets;
    }

    @Override
    public void run() {
        try {
            while (true) {
                File file = new File("AppState.txt");

                ArrayList<TrainSet> trainSetsCopy = new ArrayList<>(trainSets);

                // loop through each TrainSet object in the copy
                for (int i = 0; i < trainSetsCopy.size() - 1; i++) {
                    // assume the current TrainSet object has the highest total distance
                    int maxIdx = i;
                    // loop through each remaining TrainSet object in trainSetsCopy
                    for (int j = i + 1; j < trainSetsCopy.size(); j++) {
                        // if the total distance of the TrainSet object at index j is greater than
                        // the total distance of the TrainSet object at index maxIdx, update maxIdx
                        if (trainSetsCopy.get(j).getTotalDistance() > trainSetsCopy.get(maxIdx).getTotalDistance()) {
                            maxIdx = j;
                        }
                    }
                    // swap the TrainSet object at index i with the TrainSet object at index maxIdx
                    TrainSet temp = trainSetsCopy.get(i);
                    trainSetsCopy.set(i, trainSetsCopy.get(maxIdx));
                    trainSetsCopy.set(maxIdx, temp);
                }

                // open a FileWriter for the file "AppState.txt" using try-with-resources
                try (FileWriter writer = new FileWriter(file)) {
                    for (TrainSet trainSet : trainSetsCopy) {
                        // write the string representation of the TrainSet object to AppState.txt
                        writer.write(trainSet + "\n");
                    }
                    // flush the FileWriter to ensure all data is written to the file
                    writer.flush();
                } catch (IOException e) {
                    System.err.println("An error occurred while writing to the file: " + e.getMessage());
                    e.printStackTrace();
                }

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
    }
}
