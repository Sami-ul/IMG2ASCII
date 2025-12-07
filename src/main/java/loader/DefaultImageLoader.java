package loader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class DefaultImageLoader implements ImageLoader {
    @Override
    public BufferedImage loadImage(String filePath) {
        File inputFile = new File(filePath);
        try {
            return ImageIO.read(inputFile);
        } catch (IOException e) {
            System.err.println("The image could not be read: " + filePath);
            e.printStackTrace();
            return null;
        }
    }
}