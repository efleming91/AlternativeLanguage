// Import necessary libraries
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CellTest {

    // Testing if file exists or is empty
    @Test
    public void testFile() throws Exception {
        assertTrue("File should exist", Files.exists(Paths.get("cells.csv")));
        assertTrue("File should not be empty", Files.size(Paths.get("cells.csv")) > 0);
    }

    // Testing that final transformations of columns work as intended
    @Test
    public void testTransformations() {
        Cell cell = new Cell("Ethan", "X1", "2024", "Discontinued", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1");
        
        assertNotNull("Launch year should not be null", cell.getLaunchAnnounced());
        assertThat("Launch year should be Integer", cell.getLaunchAnnounced(), isA(Integer.class));
        assertEquals("Launch year should be 2024", (Integer)2024, cell.getLaunchAnnounced());

        assertNotNull("Body weight should not be null", cell.getBodyWeight());
        assertThat("Body weight should be Float", cell.getBodyWeight(), isA(Float.class));
        assertEquals("Body weight should be 69.0", (Float)69.0f, cell.getBodyWeight());

        assertNotNull("Display size should not be null", cell.getDisplaySize());
        assertThat("Display size should be Float", cell.getDisplaySize(), isA(Float.class));
        assertEquals("Display size should be 5.2", (Float)5.2f, cell.getDisplaySize());
    }

    // Testing empty to null transformations
    @Test
    public void testEmpty() {
        Cell cell = new Cell("", "", "", "", "", "", "", "", "", "", "", "");
        
        assertNull("oem should be null for empty input", cell.getOem());
        assertNull("model should be null for empty input", cell.getModel());
        assertNull("launchAnnounced should be null for empty input", cell.getLaunchAnnounced());
        assertNull("launchStatus should be null for empty input", cell.getLaunchStatus());
        assertNull("bodyDimensions should be null for empty input", cell.getBodyDimensions());
        assertNull("bodyWeight should be null for empty input", cell.getBodyWeight());
        assertNull("bodySim should be null for empty input", cell.getBodySim());
        assertNull("displayType should be null for empty input", cell.getDisplayType());
        assertNull("displaySize should be null for empty input", cell.getDisplaySize());
        assertNull("displayResolution should be null for empty input", cell.getDisplayResolution());
        assertNull("featuresSensors should be null for empty input", cell.getFeaturesSensors());
        assertNull("platformOs should be null for empty input", cell.getPlatformOs());
    }

    // Testing '-' to null transformations
    @Test
    public void testDash() {
        Cell cell = new Cell("-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-", "-");
        
        assertNull("OEM should be null for '-' input", cell.getOem());
        assertNull("Model should be null for '-' input", cell.getModel());
        assertNull("Launch Announced should be null for '-' input", cell.getLaunchAnnounced());
        assertNull("Launch Status should be null for '-' input", cell.getLaunchStatus());
        assertNull("Body Dimensions should be null for '-' input", cell.getBodyDimensions());
        assertNull("Body Weight should be null for '-' input", cell.getBodyWeight());
        assertNull("Body Sim should be null for '-' input", cell.getBodySim());
        assertNull("Display Type should be null for '-' input", cell.getDisplayType());
        assertNull("Display Size should be null for '-' input", cell.getDisplaySize());
        assertNull("Display Resolution should be null for '-' input", cell.getDisplayResolution());
        assertNull("Features Sensors should be null for '-' input", cell.getFeaturesSensors());
        assertNull("Platform OS should be null for '-' input", cell.getPlatformOs());
    }
}
