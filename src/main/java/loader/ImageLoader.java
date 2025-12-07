package loader;

import java.awt.image.BufferedImage;

public interface ImageLoader {
    BufferedImage loadImage(String filePath);
}