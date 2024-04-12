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
        this.oem = oem;
        this.model = model;
        this.launchAnnounced = launchAnnounced;
        this.launchStatus = launchStatus;
        this.bodyDimensions = bodyDimensions;
        this.bodyWeight = bodyWeight;
        this.bodySim = bodySim;
        this.displayType = displayType;
        this.displaySize = displaySize;
        this.displayResolution = displayResolution;
        this.featuresSensors = featuresSensors;
        this.platformOs = platformOs;
    }

    
}