// Import necessary libraries
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void testMean() {
        assertEquals("Mean of empty list should be 0.0", 0.0, Cell.calculateMean(new ArrayList<>()), 0.001);
        assertEquals("Mean of null should be 0.0", 0.0, Cell.calculateMean(null), 0.001);
        assertEquals("Mean of test numbers should be 2.0", 2.0, Cell.calculateMean(Arrays.asList(1f, 2f, 3f)), 0.001);
    }

    @Test
    public void testMedian() {
        assertNull("Median of null list should be null", Cell.calculateMedian(null));
        assertNull("Median of empty list should be null", Cell.calculateMedian(new ArrayList<>()));
        assertEquals("Median of odd list should be 2", Float.valueOf(2f), Cell.calculateMedian(Arrays.asList(1f, 2f, 3f)));
        assertEquals("Median of even list should be 2.5", Float.valueOf(2.5f), Cell.calculateMedian(Arrays.asList(1f, 2f, 3f, 4f)));
    }

    @Test
    public void testUnique() {
        assertEquals("Unique count of null list should be 0", 0, Cell.countUniqueValues(null));
        assertEquals("Unique count of empty list should be 0", 0, Cell.countUniqueValues(new ArrayList<>()));
        assertEquals("Unique count of list with duplicates should be correct", 2, Cell.countUniqueValues(Arrays.asList("apple", "banana", "apple")));
        assertEquals("Unique count of list with no duplicates should be correct", 3, Cell.countUniqueValues(Arrays.asList("apple", "banana", "orange")));
    }

    @Test
    public void testOemHighestAvg() {
        HashMap<Integer, Cell> cells = new HashMap<>();
        cells.put(1, new Cell("Ethan", "X1", "2024", "Discontinued", "135 x 28 x 7 mm", "60 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        cells.put(2, new Cell("Gary", "X2", "2024", "Discontinued", "135 x 28 x 7 mm", "169 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        cells.put(3, new Cell("Ethan", "X3", "2024", "Discontinued", "135 x 28 x 7 mm", "80 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        
        String highestAvgOem = Cell.oemHighestAvg(cells);
        assertEquals("OEM with highest average weight should be Gary", "Gary", highestAvgOem);
    }

    @Test
    public void testAnnouncedDifferentYears() {
        HashMap<Integer, Cell> cells = new HashMap<>();
        cells.put(1, new Cell("Ethan", "X1", "2022", "2023", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        cells.put(2, new Cell("Gary", "X2", "2022", "2022", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        
        List<String> differentYears = Cell.announcedDifferentYears(cells);
        assertThat("List should contain models with different announced years", differentYears, hasItem("Ethan - X1"));
        assertThat("List should not contain models with same announced years", differentYears, not(hasItem("Gary - X2")));
    }

    @Test
    public void testFeaturesSingle() {
        HashMap<Integer, Cell> cells = new HashMap<>();
        cells.put(1, new Cell("Ethan", "X1", "2024", "Discontinued", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        cells.put(2, new Cell("Gary", "X1", "2024", "Discontinued", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint, V1", "Android 1.0, upgradable to Android 1.1"));
        
        int singleFeatureCount = Cell.featuresSingle(cells);
        assertEquals("Number of phones with only one feature sensor should be 1", 1, singleFeatureCount);
    }

    @Test
    public void testLaunchedMost() {
        HashMap<Integer, Cell> cells = new HashMap<>();
        cells.put(1, new Cell("Ethan", "X1", "2022", "2023", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        cells.put(2, new Cell("Gary", "X2", "2022", "2023", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        cells.put(3, new Cell("Ethan", "X3", "2022", "2022", "135 x 28 x 7 mm", "69 g (2.43 oz)", "Mini-SIM", "65K colors", "5.2 inches", "1080 x 1920 pixels", "Fingerprint", "Android 1.0, upgradable to Android 1.1"));
        
        int mostPhonesYear = Cell.launchedMost(cells);
        assertEquals("Year with most phones launched after 1999 should be 2023", 2023, mostPhonesYear);
    }
}
