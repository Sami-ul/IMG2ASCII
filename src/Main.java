import javax.swing.SwingUtilities;
import java.awt.image.BufferedImage;
import loader.ImageLoader;
import loader.DefaultImageLoader;
import ui.AsciiArtUI;
import converter.AsciiConverter;
import converter.AsciiConverterBuilder;
import strategy.NearestNeighborStrategy;
import ramp.RampType;
import singleton.AppConfig;

public class Main {
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("ASCII ART CONVERTER");
        System.out.println("=".repeat(70));


        AppConfig config = AppConfig.getInstance();
        config.load();


        System.out.println("Launching GUI...\n");


        ImageLoader imageLoader = new DefaultImageLoader();


        SwingUtilities.invokeLater(() -> {
            AsciiArtUI ui = new AsciiArtUI(imageLoader);
            ui.setVisible(true);
        });


    }

}