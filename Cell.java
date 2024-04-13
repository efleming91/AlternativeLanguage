import java.io.*;
import java.util.*;
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.*;  


public class Cell {
    private String oem;
    private String model;
    private Integer launchAnnounced;
    private String launchStatus;
    private String bodyDimensions;
    private Float bodyWeight;
    private String bodySim;
    private String displayType;
    private Float displaySize;
    private String displayResolution;
    private String featuresSensors;
    private String platformOs;

    public Cell(String oem, String model, String launchAnnounced, String launchStatus,
            String bodyDimensions, String bodyWeight, String bodySim, String displayType,
            String displaySize, String displayResolution, String featuresSensors, String platformOs) {
        this.oem = cleanDefault(oem);
        this.model = cleanDefault(model);
        this.launchAnnounced = cleanLaunchAnnounced(cleanDefault(launchAnnounced));
        this.launchStatus = cleanLaunchStatus(cleanDefault(launchStatus));
        this.bodyDimensions = cleanDefault(bodyDimensions);
        this.bodyWeight = cleanBodyWeight(cleanDefault(bodyWeight));
        this.bodySim = cleanBodySim(bodySim);
        this.displayType = cleanDefault(displayType);
        this.displaySize = cleanDisplaySize(cleanDefault(displaySize));
        this.displayResolution = cleanDefault(displayResolution);
        this.featuresSensors = cleanFeatureSensors(featuresSensors);
        this.platformOs = cleanPlatformOs(cleanDefault(platformOs));
    }

    private String cleanDefault(String data) {
        if (data == null || data.trim().isEmpty() || data.equals("-")) {
            return null;
        }
        return data.trim();
    }

    private Integer cleanLaunchAnnounced(String data) {
        if (data != null && data.matches(".*\\d{4}.*")) {
            Matcher m = Pattern.compile("(\\d{4})").matcher(data);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }
        return null;
    }

    private String cleanLaunchStatus(String data) {
        if (data != null && (data.equals("Discontinued") || data.equals("Cancelled"))) {
            return data;
        } else {
            return cleanLaunchAnnounced(data) != null ? data : null;
        }
    }

    private Float cleanBodyWeight(String data) {
        if (data != null && data.matches("\\d+ g.*")) {
            return Float.parseFloat(data.substring(0, data.indexOf(' ')));
        }
        return null;
    }

    private String cleanBodySim(String data) {
        if (data != null && (data.equalsIgnoreCase("No") || data.equalsIgnoreCase("Yes"))) {
            return null;
        }
        return cleanDefault(data);
    }

    private Float cleanDisplaySize(String data) {
        if (data != null && data.matches("\\d+(\\.\\d+)? inches.*")) {
            return Float.parseFloat(data.substring(0, data.indexOf(' ')));
        }
        return null;
    }

    private String cleanFeatureSensors(String data) {
        if (data != null && data.matches("^\\d+(\\.\\d+)?$")) {
            return null;
        }
        return cleanDefault(data);
    }

    private String cleanPlatformOs(String data) {
        if (data != null) {
            if (data.matches("^\\d+(\\.\\d+)?$")) {
                return null;
            }
    
            int index = data.indexOf(',');
            if (index != -1) {
                return data.substring(0, index).trim();
            } else {
                return data.trim();
            }
        }
        return null;
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

        //Testing
        int count = 2;
        for (Map.Entry<Integer, Cell> entry : cellPhones.entrySet()) {
            if (count == 847)
                System.out.println("ID: " + entry.getKey() + "\n" + entry.getValue());
            count++;
        }
    }
}