// Import necessary libraries
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;  // Not really sure why this isn't covered by util.* call
import java.util.regex.Pattern;  // ^^
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.apache.commons.csv.*;  // Requires commons-csv-1.10.0.jar library file

// Class representing full details of a cell phone 
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

    // Constructor initializing data via cleaning methods
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
        this.featuresSensors = cleanFeaturesSensors(featuresSensors);
        this.platformOs = cleanPlatformOs(cleanDefault(platformOs));
    }

    // Getters and Setters
    public String getOem() { return oem; }
    public void setOem(String oem) { this.oem = cleanDefault(oem); }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = cleanDefault(model); }

    public Integer getLaunchAnnounced() { return launchAnnounced; }
    public void setLaunchAnnounced(String launchAnnounced) { this.launchAnnounced = cleanLaunchAnnounced(cleanDefault(launchAnnounced)); }

    public String getLaunchStatus() { return launchStatus; }
    public void setLaunchStatus(String launchStatus) { this.launchStatus = cleanLaunchStatus(cleanDefault(launchStatus)); }

    public String getBodyDimensions() { return bodyDimensions; }
    public void setBodyDimensions(String bodyDimensions) { this.bodyDimensions = cleanDefault(bodyDimensions); }

    public Float getBodyWeight() { return bodyWeight; }
    public void setBodyWeight(String bodyWeight) { this.bodyWeight = cleanBodyWeight(cleanDefault(bodyWeight)); }

    public String getBodySim() { return bodySim; }
    public void setBodySim(String bodySim) { this.bodySim = cleanBodySim(bodySim); }

    public String getDisplayType() { return displayType; }
    public void setDisplayType(String displayType) { this.displayType = cleanDefault(displayType); }

    public Float getDisplaySize() { return displaySize; }
    public void setDisplaySize(String displaySize) { this.displaySize = cleanDisplaySize(cleanDefault(displaySize)); }

    public String getDisplayResolution() { return displayResolution; }
    public void setDisplayResolution(String displayResolution) { this.displayResolution = cleanDefault(displayResolution); }

    public String getFeaturesSensors() { return featuresSensors; }
    public void setFeaturesSensors(String featuresSensors) { this.featuresSensors = cleanFeaturesSensors(featuresSensors); }

    public String getPlatformOs() { return platformOs; }
    public void setPlatformOs(String platformOs) { this.platformOs = cleanPlatformOs(cleanDefault(platformOs)); }

    // Basic cleaning of input string
    // Checks for known null values and sets null if column is empty or filled by '-'
    private String cleanDefault(String data) {
        if (data == null || data.trim().isEmpty() || data.equals("-")) {
            return null;
        }

        return data.trim();
    }

    // Cleaner for launch_announced column
    // Checks for 4 digit-length year
    private Integer cleanLaunchAnnounced(String data) {
        if (data != null && data.matches(".*\\d{4}.*")) {
            Matcher m = Pattern.compile("(\\d{4})").matcher(data);
            if (m.find()) {
                return Integer.parseInt(m.group(1));
            }
        }

        return null;
    }

    // Cleaner for launch_status column
    // Checks for "Discontinued" and "Cancelled" inputs, 
    // otherwise sends to cleanLaunchAnnounced for further cleaning
    private String cleanLaunchStatus(String data) {
        if (data != null && (data.equals("Discontinued") || data.equals("Cancelled"))) {
            return data;
        } else {
            return Integer.toString(cleanLaunchAnnounced(data));
        }
    }

    // Cleaner for body_weight column
    // Checks for number preceding a 'g' character
    private Float cleanBodyWeight(String data) {
        if (data != null && data.matches("\\d+ g.*")) {
            return Float.parseFloat(data.substring(0, data.indexOf(' ')));
        }

        return null;
    }

    // Cleaner for body_sim column
    // Checks for "No" or "Yes" inputs, 
    // otherwise sends to cleanDefault for typical cleaning
    private String cleanBodySim(String data) {
        if (data != null && (data.equalsIgnoreCase("No") || data.equalsIgnoreCase("Yes"))) {
            return null;
        }

        return cleanDefault(data);
    }

    // Cleaner for display_size column
    // Checks for number preceding 'inches' text
    private Float cleanDisplaySize(String data) {
        if (data != null && data.matches("\\d+(\\.\\d+)? inches.*")) {
            return Float.parseFloat(data.substring(0, data.indexOf(' ')));
        }

        return null;
    }

    // Cleaner for features_sensors column
    // Checks if a number is all that is input, 
    // otherwise sends to cleanDefault for typical cleaning
    private String cleanFeaturesSensors(String data) {
        if (data != null && data.matches("^\\d+(\\.\\d+)?$")) {
            return null;
        }

        return cleanDefault(data);
    }

    // Cleaner for platform_os column
    // First checks if a number is all that is input
    // Then strips everything after first comma
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

    // Calculates mean of list of inputs
    // Returns 0.0 if list is null or unpopulated
    public static double calculateMean(List<Float> data) {
        if (data == null || data.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Float num : data) {
            sum += num;
        }
        return sum / data.size();
    }

    // Calculates median of list of inputs
    // Returns null if list is null or unpopulated
    public static Float calculateMedian(List<Float> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        Collections.sort(data);
        int median = data.size() / 2;
        if (data.size() % 2 == 0) {
            return (data.get(median - 1) + data.get(median)) / 2.0f;
        } else {
            return data.get(median);
        }
    }

    // Sums unique values of list of inputs
    public static int countUniqueValues(List<String> data) {
        return new HashSet<>(data).size();
    }

    // Calculates highest average weight by oem
    public static String oemHighestAvg(HashMap<Integer, Cell> cells) {
        Map<String, List<Float>> weights = new HashMap<>();
        for (Cell cell : cells.values()) {
            if (cell.bodyWeight != null) {
                weights.putIfAbsent(cell.oem, new ArrayList<>());
                weights.get(cell.oem).add(cell.bodyWeight);
            }
        }

        String highestOem = null;
        double highestAvg = 0;
        for (Map.Entry<String, List<Float>> entry : weights.entrySet()) {
            double avg = calculateMean(new ArrayList<>(entry.getValue())); 
            if (avg > highestAvg) {
                highestAvg = avg;
                highestOem = entry.getKey();
            }
        }

        return highestOem;
    }

    // Finds any instances where cell launch_announced and launch_status were in different years
    // Accounts for Discontinue and Cancelled statuses
    public static List<String> announcedDifferentYears(HashMap<Integer, Cell> cells) {
        List<String> result = new ArrayList<>();
        for (Cell cell : cells.values()) {
            if (cell.launchAnnounced != null && cell.launchStatus != null &&
                !cell.launchStatus.equals("Discontinued") && 
                !cell.launchStatus.equals("Cancelled") && 
                !Integer.toString(cell.launchAnnounced).equals(cell.launchStatus)) {
                result.add(cell.oem + " - " + cell.model);
            }
        }

        return result;
    }

    // Counts objects that have only one features_sensor input
    public static int featuresSingle(HashMap<Integer, Cell> cells) {
        int count = 0;
        for (Cell cell : cells.values()) {
            if (cell.featuresSensors != null && !cell.featuresSensors.contains(",")) {
                count++;
            }
        }

        return count;
    }

    // Finds year that had most phones launched post-1999
    public static int launchedMost(HashMap<Integer, Cell> cells) {
        Map<Integer, Integer> count = new HashMap<>();
        for (Cell cell : cells.values()) {
            if (cell.launchAnnounced != null && cell.launchAnnounced > 1999) {
                count.put(cell.launchAnnounced, count.getOrDefault(cell.launchAnnounced, 0) + 1);
            }
        }

        int maxYear = 0, maxCount = 0;
        for (Map.Entry<Integer, Integer> entry : count.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxYear = entry.getKey();
            }
        }
        return maxYear;
    }

    // Converts object details to string for printing
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
        Path path = Paths.get("cells.csv");
        HashMap<Integer, Cell> cellPhones = new HashMap<>();
        int id = 1;

        // Check for issues with file
        if (!Files.exists(path)) {
            System.out.println("Error: The file does not exist.");
            return;
        }
        else if (Files.exists(path) && new File(path.toString()).length() == 0) {
            System.out.println("Error: The file is empty.");
            return;
        }

        // Read in .csv file and handle each line
        try (Reader in = Files.newBufferedReader(path)) {
            // Use Apache Commons CSV library to simplify csv parsing
            CSVFormat format = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build();

            Iterable<CSVRecord> records = format.parse(in);

            // Check for issues with file
            if (!records.iterator().hasNext()) {
                System.out.println("Error: The file contains only headers or is empty.");
                return;
            }
            
            // Each line is turned into a Cell object and added to the HashMap    
            for (CSVRecord record : records) {
                Cell cell = new Cell(
                    record.get("oem"), 
                    record.get("model"), 
                    record.get("launch_announced"), 
                    record.get("launch_status"), 
                    record.get("body_dimensions"), 
                    record.get("body_weight"), 
                    record.get("body_sim"), 
                    record.get("display_type"), 
                    record.get("display_size"), 
                    record.get("display_resolution"), 
                    record.get("features_sensors"), 
                    record.get("platform_os")
                );
                
                cellPhones.put(id++, cell);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Validate final transformation types
        boolean validData = true;
        
        for (Cell cell : cellPhones.values()) {
            if (cell.getLaunchAnnounced() != null && !(cell.getLaunchAnnounced() instanceof Integer)) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") launchedAnnounced is not an Integer.");
                validData = false;
                break;
            }
            if (cell.getBodyWeight() != null && !(cell.getBodyWeight() instanceof Float)) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") bodyWeight is not a Float.");
                validData = false;
                break;
            }
            if (cell.getDisplaySize() != null && !(cell.getDisplaySize() instanceof Float)) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") displaySize is not a Float.");
                validData = false;
                break;
            }
        }
        if (!validData) {
            return;
        }
        
        // Validate missing or "-" data is replaced with null
        for (Cell cell : cellPhones.values()) {
            if (cell.getOem() != null && (cell.getOem().isEmpty() || cell.getOem().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") oem is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getModel() != null && (cell.getModel().isEmpty() || cell.getModel().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") model is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getLaunchAnnounced() != null && cell.getLaunchAnnounced() <= 0) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") launchAnnounced contains a failed transformation.");
                validData = false;
                break;
            }
            if (cell.getLaunchStatus() != null && (cell.getLaunchStatus().isEmpty() || cell.getLaunchStatus().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") launchStatus is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getBodyDimensions() != null && (cell.getBodyDimensions().isEmpty() || cell.getBodyDimensions().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") bodyDimensions is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getBodyWeight() != null && cell.getBodyWeight().isNaN()) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") bodyWeight contains non-numeric values.");
                validData = false;
                break;
            }
            if (cell.getBodySim() != null && (cell.getBodySim().isEmpty() || cell.getBodySim().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") bodySim is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getDisplayType() != null && (cell.getDisplayType().isEmpty() || cell.getDisplayType().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") displayType is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getDisplaySize() != null && cell.getDisplaySize().isNaN()) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") displaySize contains non-numeric values.");
                validData = false;
                break;
            }
            if (cell.getDisplayResolution() != null && (cell.getDisplayResolution().isEmpty() || cell.getDisplayResolution().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") displayResolution is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getFeaturesSensors() != null && (cell.getFeaturesSensors().isEmpty() || cell.getFeaturesSensors().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") featuresSensors is empty or '-'.");
                validData = false;
                break;
            }
            if (cell.getPlatformOs() != null && (cell.getPlatformOs().isEmpty() || cell.getPlatformOs().equals("-"))) {
                System.out.println("Data validation error: (" + cell.getOem() + " - " + cell.getModel() + ") platformOs is empty or '-'.");
                validData = false;
                break;
            }
        }
        if (!validData) {
            return;
        }        

        //Testing

        // Checking cleaning
        
        int count = 2;
        for (Map.Entry<Integer, Cell> entry : cellPhones.entrySet()) {
            if (count == 888) {
                System.out.println("ID: " + entry.getKey() + "\n" + entry.getValue());
                break;
            }
            count++;
        }
        

        // Checking math
        
        List<Float> displaySize = new ArrayList<>();
        List<String> oem = new ArrayList<>();
        for (Cell cell : cellPhones.values()) {
            if (cell.displaySize != null) displaySize.add(cell.displaySize);
            if (cell.oem != null) oem.add(cell.oem);
        }

        System.out.println("Mean: " + calculateMean(displaySize));
        System.out.println("Median: " + calculateMedian(displaySize));
        System.out.println("Unique: " + countUniqueValues(oem));

        System.out.println("");  // Just making output look nicer
        

        // Checking Report questions
        
        String highestAvgOem = oemHighestAvg(cellPhones);
        System.out.println("Company with highest average weight: " + highestAvgOem);

        List<String> diffYearReleases = announcedDifferentYears(cellPhones);
        System.out.println("Phones announced and released in different years: " + diffYearReleases);

        int singleFeatureCount = featuresSingle(cellPhones);
        System.out.println("Number of phones with only one feature sensor: " + singleFeatureCount);

        int mostPhonesYear = launchedMost(cellPhones);
        System.out.println("Year with most phones launched after 1999: " + mostPhonesYear);
        
    }
}