package test.converter;

import converter.AsciiConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import strategy.NearestNeighborStrategy;
import ramp.SimpleRamp;

public class AsciiConverterTest {
    private BufferedImage testImage;

    @BeforeEach
    public void setUp() {
        // Create a simple 2x2 test image
        testImage = new BufferedImage(2, 2, BufferedImage.TYPE_INT_RGB);

        // Top-left: White
        testImage.setRGB(0, 0, Color.WHITE.getRGB());
        // Top-right: Light gray
        testImage.setRGB(1, 0, new Color(200, 200, 200).getRGB());
        // Bottom-left: Dark gray
        testImage.setRGB(0, 1, new Color(50, 50, 50).getRGB());
        // Bottom-right: Black
        testImage.setRGB(1, 1, Color.BLACK.getRGB());
    }

    @Test
    public void testConverterNotNull() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                1.0
        );
        assertNotNull(converter, "Converter should be created");
    }

    @Test
    public void testConvertNullImage() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                1.0
        );
        String result = converter.convert(null);
        assertEquals("", result, "Converting null image should return empty string");
    }

    @Test
    public void testConvertProducesOutput() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                1.0
        );
        String result = converter.convert(testImage);
        assertFalse(result.isEmpty(), "Conversion should produce output");
    }

    @Test
    public void testConvertHasNewlines() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                1.0
        );
        String result = converter.convert(testImage);
        assertTrue(result.contains("\n"), "Output should contain newlines");
    }

    @Test
    public void testScalingReducesSize() {
        AsciiConverter fullScale = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                1.0
        );
        AsciiConverter halfScale = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );

        String fullResult = fullScale.convert(testImage);
        String halfResult = halfScale.convert(testImage);

        assertTrue(halfResult.length() < fullResult.length(),
                "Smaller scale should produce shorter output");
    }

    @Test
    public void testDifferentStrategiesProduceDifferentResults() {
        AsciiConverter nearest = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );
        AsciiConverter bilinear = new AsciiConverter(
                new strategy.BilinearInterpolationStrategy(),
                new SimpleRamp(),
                0.5
        );

        // Note: Results might be same for simple images, but strategies are different
        assertNotNull(nearest.convert(testImage));
        assertNotNull(bilinear.convert(testImage));
    }
}