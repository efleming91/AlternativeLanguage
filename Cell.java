import java.io.*;
import java.util.*;

public class Cell {
    private String oem;
    private String model;
    private String launchAnnounced;
    private String launchStatus;
    private String bodyDimensions;
    private String bodyWeight;
    private String bodySim;
    private String displayType;
    private String displaySize;
    private String displayResolution;
    private String featuresSensors;
    private String platformOs;

    public Cell(String oem, String model, String launchAnnounced, String launchStatus,
            String bodyDimensions, String bodyWeight, String bodySim, String displayType,
            String displaySize, String displayResolution, String featuresSensors, String platformOs) {
        this.oem = oem.isEmpty() ? null : oem;
        this.model = model.isEmpty() ? null : model;
        this.launchAnnounced = launchAnnounced.isEmpty() ? null : launchAnnounced;
        this.launchStatus = launchStatus.isEmpty() ? null : launchStatus;
        this.bodyDimensions = bodyDimensions.isEmpty() ? null : bodyDimensions;
        this.bodyWeight = bodyWeight.isEmpty() ? null : bodyWeight;
        this.bodySim = bodySim.isEmpty() ? null : bodySim;
        this.displayType = displayType.isEmpty() ? null : displayType;
        this.displaySize = displaySize.isEmpty() ? null : displaySize;
        this.displayResolution = displayResolution.isEmpty() ? null : displayResolution;
        this.featuresSensors = featuresSensors.isEmpty() ? null : featuresSensors;
        this.platformOs = platformOs.isEmpty() ? null : platformOs;
    }

    @Override
    public String toString() {
        return "oem = " + oem + "\n" + 
               "model = " + model + "\n" + 
               "launchAnnounced = " + launchAnnounced + "\n" + 
               "launchStatus = " + launchStatus + "\n" + 
               "bodyDimensions = " + bodyDimensions + "\n" + 
               "bodyWeight = " + bodyWeight +"\n" + 
               "bodySim = " + bodySim + "\n" + 
               "displayType = " + displayType + "\n" + 
               "displaySize = " + displaySize +"\n" + 
               "displayResolution = " + displayResolution + "\n" + 
               "featuresSensors = " + featuresSensors + "\n" + 
               "platformOs = " + platformOs + "\n";                
    }

    public static void main(String[] args) {
        HashMap<Integer, Cell> cellPhones = new HashMap<>();
        String line;
        int id = 1;

        try (BufferedReader br = new BufferedReader(new FileReader("cells.csv"))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);
                
                Cell cell = new Cell(data[0], data[1], data[2], data[3], data[4], data[5], 
                                    data[6], data[7], data[8], data[9], data[10], data[11]);
                        
                cellPhones.put(id++, cell);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        int count = 0;
        for (Map.Entry<Integer, Cell> entry : cellPhones.entrySet()) {
            if (count >= 3)
                break;
            System.out.println("ID: " + entry.getKey() + "\n" + entry.getValue());
            count++;
        }
    }
}