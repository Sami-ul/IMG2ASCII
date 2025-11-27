import java.awt.image.BufferedImage;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        BufferedImage image = ImageReader.readImage("landscape.jpg");
        System.out.println(AsciiConverter.convertImg(image, " .:-=+*#%@", 0.4));
    }
}