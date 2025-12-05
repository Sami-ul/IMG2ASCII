package strategy;

import java.awt.image.BufferedImage;
import model.ScaledPixel;

public class BilinearInterpolationStrategy implements ScalingStrategy {
    @Override
    public ScaledPixel getScaledPixel(BufferedImage img, double x, double y) {
        // Using floor for bilinear approximation
        int xFloor = (int) Math.floor(x);
        int yFloor = (int) Math.floor(y);

        xFloor = Math.max(0, Math.min(xFloor, img.getWidth() - 1));
        yFloor = Math.max(0, Math.min(yFloor, img.getHeight() - 1));

        return new ScaledPixel(xFloor, yFloor);
    }
}