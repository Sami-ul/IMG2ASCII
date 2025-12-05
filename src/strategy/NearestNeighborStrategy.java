package strategy;

import java.awt.image.BufferedImage;
import model.ScaledPixel;

public class NearestNeighborStrategy implements ScalingStrategy {
    @Override
    public ScaledPixel getScaledPixel(BufferedImage img, double x, double y) {
        int xRounded = (int) Math.round(x);
        int yRounded = (int) Math.round(y);

        // Boundary checking
        xRounded = Math.max(0, Math.min(xRounded, img.getWidth() - 1));
        yRounded = Math.max(0, Math.min(yRounded, img.getHeight() - 1));

        return new ScaledPixel(xRounded, yRounded);
    }
}