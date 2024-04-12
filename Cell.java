import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.apache.commons.csv.*;

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
        int id = 1;

        try (Reader in = Files.newBufferedReader(Paths.get("cells.csv"))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);
                
            for (CSVRecord record : records) {
                Cell cell = new Cell(
                    record.get("oem"), record.get("model"), record.get("launch_announced"), 
                    record.get("launch_status"), record.get("body_dimensions"), 
                    record.get("body_weight"), record.get("body_sim"), record.get("display_type"), 
                    record.get("display_size"), record.get("display_resolution"), 
                    record.get("features_sensors"), record.get("platform_os")
                );
                
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