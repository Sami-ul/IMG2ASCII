import javax.swing.SwingUtilities;

import loader.ImageLoader;
import loader.DefaultImageLoader;
import ui.AsciiArtUI;
import singleton.AppConfig;

public class AppRunner {
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