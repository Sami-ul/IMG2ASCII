package test.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import model.ScaledPixel;
import strategy.NearestNeighborStrategy;

public class NearestNeighborStrategyTest {
    private NearestNeighborStrategy strategy;
    private BufferedImage testImage;

    @BeforeEach
    public void setUp() {
        strategy = new NearestNeighborStrategy();
        testImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    public void testRoundingBehavior() {
        // Test that 2.4 rounds to 2
        ScaledPixel pixel = strategy.getScaledPixel(testImage, 2.4, 3.6);
        assertEquals(2, pixel.getX(), "X should round to 2");
        assertEquals(4, pixel.getY(), "Y should round to 4");
    }

    @Test
    public void testRoundingUp() {
        // Test that 2.6 rounds to 3
        ScaledPixel pixel = strategy.getScaledPixel(testImage, 2.6, 3.7);
        assertEquals(3, pixel.getX(), "X should round to 3");
        assertEquals(4, pixel.getY(), "Y should round to 4");
    }

    @Test
    public void testBoundaryChecking_TooHigh() {
        // Test that coordinates beyond image bounds are clamped
        ScaledPixel pixel = strategy.getScaledPixel(testImage, 15.0, 15.0);
        assertEquals(9, pixel.getX(), "X should be clamped to 9 (max width - 1)");
        assertEquals(9, pixel.getY(), "Y should be clamped to 9 (max height - 1)");
    }

    @Test
    public void testBoundaryChecking_Negative() {
        // Test that negative coordinates are clamped to 0
        ScaledPixel pixel = strategy.getScaledPixel(testImage, -5.0, -3.0);
        assertEquals(0, pixel.getX(), "Negative X should be clamped to 0");
        assertEquals(0, pixel.getY(), "Negative Y should be clamped to 0");
    }

    @Test
    public void testExactCoordinates() {
        // Test that exact integer coordinates work correctly
        ScaledPixel pixel = strategy.getScaledPixel(testImage, 5.0, 7.0);
        assertEquals(5, pixel.getX());
        assertEquals(7, pixel.getY());
    }
}