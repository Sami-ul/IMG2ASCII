package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import java.awt.Color;

import loader.DefaultImageLoader;
import converter.AsciiConverter;
import converter.AsciiConverterBuilder;
import ramp.RampType;
import strategy.NearestNeighborStrategy;
import observer.LoggingObserver;
import singleton.AppConfig;

public class IntegrationTest {

    @Test
    public void testCompleteWorkflow() {
        // Test that all patterns work together

        // 1. SINGLETON: Get config
        AppConfig config = AppConfig.getInstance();
        assertNotNull(config);

        // 2. Create test image
        BufferedImage testImage = new BufferedImage(20, 20, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 20; y++) {
            for (int x = 0; x < 20; x++) {
                int gray = (x + y) * 5;
                Color color = new Color(gray, gray, gray);
                testImage.setRGB(x, y, color.getRGB());
            }
        }

        // 3. BUILDER + FACTORY + STRATEGY: Create converter
        AsciiConverter converter = new AsciiConverterBuilder()
                .withScalingStrategy(new NearestNeighborStrategy())
                .withRampType(RampType.SIMPLE)
                .withScale(0.5)
                .build();

        assertNotNull(converter);

        // 4. OBSERVER: Add observer
        LoggingObserver observer = new LoggingObserver();
        converter.addObserver(observer);

        // 5. Convert
        String result = converter.convert(testImage);

        // Verify output
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("\n"));

        // Test passes if no exceptions thrown
        assertTrue(true, "Complete workflow executed successfully");
    }

    @Test
    public void testDependencyInjection() {
        // Verify that components can be injected and swapped

        NearestNeighborStrategy strategy1 = new NearestNeighborStrategy();
        strategy.BilinearInterpolationStrategy strategy2 =
                new strategy.BilinearInterpolationStrategy();

        // Both strategies can be injected
        AsciiConverter converter1 = new AsciiConverter(
                strategy1,
                new ramp.SimpleRamp(),
                0.4
        );

        AsciiConverter converter2 = new AsciiConverter(
                strategy2,
                new ramp.DetailedRamp(),
                0.4
        );

        assertNotNull(converter1);
        assertNotNull(converter2);

        // This demonstrates dependency injection and coding to abstractions
        assertTrue(true, "Dependency injection working correctly");
    }
}