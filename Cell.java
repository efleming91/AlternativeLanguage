// Import necessary libraries
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;  // Not really sure why this isn't covered by util.* call
import java.util.regex.Pattern;  // ^^

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
            Integer year = cleanLaunchAnnounced(data);
            return (year != null) ? String.valueOf(year) : null; 
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
        if (data == null) {
            return 0;
        }

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
            if (cell.getLaunchStatus() != null && !cell.getLaunchStatus().equals("Discontinued") 
                && !cell.getLaunchStatus().equals("Cancelled")) {
                try {
                    int year = Integer.parseInt(cell.getLaunchStatus());
                    if (year > 1999) {
                        count.put(year, count.getOrDefault(year, 0) + 1);
                    }
                } catch (NumberFormatException e) {
                    // Do nothing
                    // Catches and skips non-year lines
                }
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
        String filePath = "cells.csv";
        File csvFile = new File(filePath);
        HashMap<Integer, Cell> cellPhones = new HashMap<>();
        int id = 1;

        // Check for issues with file
        if (!csvFile.exists()) {
            System.out.println("Error: The file does not exist.");
            return;
        } else if (csvFile.length() == 0) {
            System.out.println("Error: The file is empty.");
            return;
        }

        // Read in .csv file and handle each line
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine(); // skip header
            if (line == null || (line = br.readLine()) == null) {
                System.out.println("Error: The file contains only headers or is empty.");
                return;
            }
            do {
                List<String> record = parseCsvLine(line);
                Cell cell = new Cell(
                    record.get(0), 
                    record.get(1), 
                    record.get(2), 
                    record.get(3),
                    record.get(4), 
                    record.get(5), 
                    record.get(6), 
                    record.get(7),
                    record.get(8), 
                    record.get(9), 
                    record.get(10), 
                    record.get(11)
                );
                cellPhones.put(id++, cell);
            } while ((line = br.readLine()) != null);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Checks

        // Checking cleaning methods        
        int count = 2;
        for (Map.Entry<Integer, Cell> entry : cellPhones.entrySet()) {
            if (count == 888) {
                System.out.println("ID: " + entry.getKey() + "\n" + entry.getValue());
                break;
            }
            count++;
        }        

        // Checking math methods        
        List<Float> displaySize = new ArrayList<>();
        List<String> oem = new ArrayList<>();
        for (Cell cell : cellPhones.values()) {
            if (cell.displaySize != null) displaySize.add(cell.displaySize);
            if (cell.oem != null) oem.add(cell.oem);
        }

        System.out.println("Mean of displaySize: " + calculateMean(displaySize));
        System.out.println("Median of displaySize: " + calculateMedian(displaySize));
        System.out.println("Unique values in oem: " + countUniqueValues(oem));

        System.out.println();  // Just making output look nicer        

        // Checking Report question methods        
        String highestAvgOem = oemHighestAvg(cellPhones);
        System.out.println("Company with highest average weight: " + highestAvgOem);

        List<String> diffYearReleases = announcedDifferentYears(cellPhones);
        System.out.println("Phones announced and released in different years: " + diffYearReleases);

        int singleFeatureCount = featuresSingle(cellPhones);
        System.out.println("Number of phones with only one feature sensor: " + singleFeatureCount);

        int mostPhonesYear = launchedMost(cellPhones);
        System.out.println("Year with most phones launched after 1999: " + mostPhonesYear);        
    }    

    // Manual parsing as Apache Commons CSV is not available for Replit
    // Tried everything before giving up and adding this
    private static List<String> parseCsvLine(String line) {
        List<String> tokens = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder buffer = new StringBuilder();
        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                tokens.add(buffer.toString().trim());
                buffer = new StringBuilder();
            } else {
                buffer.append(c);
            }
        }
        tokens.add(buffer.toString().trim());
        return tokens;
    }
}