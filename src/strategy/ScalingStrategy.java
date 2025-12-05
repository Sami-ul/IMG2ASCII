package strategy;

import java.awt.image.BufferedImage;
import model.ScaledPixel;

public interface ScalingStrategy {
    ScaledPixel getScaledPixel(BufferedImage img, double x, double y);
}