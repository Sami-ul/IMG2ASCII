import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageReader {
    public static BufferedImage readImage(String filePath) {
        File inputFile = new File(filePath);
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(inputFile);
        } catch (IOException e) {
            System.err.println("The image could not be read");
            e.printStackTrace();
        }
        return bufferedImage;
    }
}
