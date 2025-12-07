package singleton;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import ramp.RampType;
import singleton.AppConfig;

import java.io.File;

public class AppConfigTest {

    @AfterEach
    public void cleanup() {
        // Clean up test file
        File testFile = new File("app.properties");
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSingletonReturnsInstance() {
        AppConfig config = AppConfig.getInstance();
        assertNotNull(config, "Singleton should return instance");
    }

    @Test
    public void testSingletonReturnsSameInstance() {
        AppConfig config1 = AppConfig.getInstance();
        AppConfig config2 = AppConfig.getInstance();

        assertSame(config1, config2, "Singleton should return same instance");
    }

    @Test
    public void testDefaultValues() {
        AppConfig config = AppConfig.getInstance();
        assertNotNull(config.getDefaultRamp(), "Should have default ramp");
        assertTrue(config.getDefaultScale() > 0, "Should have positive default scale");
        assertNotNull(config.getDefaultStrategy(), "Should have default strategy");
    }

    @Test
    public void testSetAndGetRamp() {
        AppConfig config = AppConfig.getInstance();
        config.setDefaultRamp(RampType.DETAILED);
        assertEquals(RampType.DETAILED, config.getDefaultRamp(),
                "Should store and retrieve ramp type");
    }

    @Test
    public void testSetAndGetScale() {
        AppConfig config = AppConfig.getInstance();
        config.setDefaultScale(0.7);
        assertEquals(0.7, config.getDefaultScale(), 0.01,
                "Should store and retrieve scale");
    }

    @Test
    public void testSetAndGetStrategy() {
        AppConfig config = AppConfig.getInstance();
        config.setDefaultStrategy("Bilinear");
        assertEquals("Bilinear", config.getDefaultStrategy(),
                "Should store and retrieve strategy");
    }

    @Test
    public void testSetAndGetImagePath() {
        AppConfig config = AppConfig.getInstance();
        config.setLastUsedImagePath("/test/path/image.jpg");
        assertEquals("/test/path/image.jpg", config.getLastUsedImagePath(),
                "Should store and retrieve image path");
    }

    @Test
    public void testInvalidScaleRejected() {
        AppConfig config = AppConfig.getInstance();
        double originalScale = config.getDefaultScale();

        config.setDefaultScale(-0.5);  // Invalid: negative
        assertEquals(originalScale, config.getDefaultScale(),
                "Should reject negative scale");

        config.setDefaultScale(2.0);  // Invalid: > 1.0
        assertEquals(originalScale, config.getDefaultScale(),
                "Should reject scale > 1.0");
    }

    @Test
    public void testSaveAndLoad() {
        AppConfig config = AppConfig.getInstance();

        // Set some values
        config.setDefaultRamp(RampType.BLOCKS);
        config.setDefaultScale(0.6);
        config.setDefaultStrategy("NearestNeighbor");
        config.setLastUsedImagePath("/test/image.png");

        // Save to file
        config.save();

        // Verify file was created
        File propertiesFile = new File("app.properties");
        assertTrue(propertiesFile.exists(), "Properties file should be created");

        // Modify values
        config.setDefaultRamp(RampType.SIMPLE);
        config.setDefaultScale(0.3);

        // Load from file
        config.load();

        // Verify values were restored
        assertEquals(RampType.BLOCKS, config.getDefaultRamp(),
                "Loaded ramp should match saved value");
        assertEquals(0.6, config.getDefaultScale(), 0.01,
                "Loaded scale should match saved value");
    }
}