import java.awt.image.BufferedImage;

public class AsciiConverter {
    private static double weightRGB(int r, int g, int b) {
        return 0.3*r + 0.6*g + 0.11*b;
    }
    private static char brightnessToASCII(double brightness, String ramp) {
        int length = ramp.length();
        double pieces = 255.0/length;
        double curBrightness = 255.0;
        for  (int i = 0; i < length; i++) {
            if (brightness > curBrightness) {
                return ramp.charAt(length-1 - i);
            }
            curBrightness -= pieces;
        }
        return ' ';
    }
    private static int nearestNeighborInterpolation() {
        return 0;
    }
    public static String convertImg(BufferedImage img, String ramp, double scale) {
        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();
        int scaledWidth = (int) (scale * originalWidth);
        int scaledHeight = (int) (scale * originalHeight);
        double mappingFactorWidth = (double) originalWidth / scaledWidth;
        double mappingFactorHeight = (double) originalHeight / scaledHeight;

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < scaledHeight; y++) {
            for (int x = 0; x < scaledWidth; x++) {
                double xOriginal = x * mappingFactorWidth;
                double yOriginal = y * mappingFactorHeight;
                long xO = Math.round(xOriginal);
                long yO =  Math.round(yOriginal);

                int color = img.getRGB((int) xO, (int) yO);
                int r =  (color >> 16) & 0xff;
                int g =  (color >> 8) & 0xff;
                int b =  color & 0xff;
                double grayscale = weightRGB(r, g, b);
                sb.append(brightnessToASCII(grayscale, ramp));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
