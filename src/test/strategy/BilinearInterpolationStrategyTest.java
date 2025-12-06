package test.strategy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import model.ScaledPixel;
import strategy.BilinearInterpolationStrategy;

public class BilinearInterpolationStrategyTest {
    private BilinearInterpolationStrategy strategy;
    private BufferedImage testImage;

    @BeforeEach
    public void setUp() {
        strategy = new BilinearInterpolationStrategy();
        testImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
    }

    @Test
    public void testFloorBehavior() {
        // Test that coordinates are floored
        ScaledPixel pixel = strategy.getScaledPixel(testImage, 2.7, 3.9);
        assertEquals(2, pixel.getX(), "X should floor to 2");
        assertEquals(3, pixel.getY(), "Y should floor to 3");
    }

    @Test
    public void testBoundaryChecking() {
        ScaledPixel pixel = strategy.getScaledPixel(testImage, 15.0, 15.0);
        assertEquals(9, pixel.getX(), "X should be clamped to max");
        assertEquals(9, pixel.getY(), "Y should be clamped to max");
    }

    @Test
    public void testZeroCoordinates() {
        ScaledPixel pixel = strategy.getScaledPixel(testImage, 0.0, 0.0);
        assertEquals(0, pixel.getX());
        assertEquals(0, pixel.getY());
    }
}