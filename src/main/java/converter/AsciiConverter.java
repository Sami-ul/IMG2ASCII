package converter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import strategy.ScalingStrategy;
import ramp.AsciiRamp;
import model.ScaledPixel;
import observer.ConversionObserver;

public class AsciiConverter {
    private final double RED_SCALE = 0.3;
    private final double GREEN_SCALE = 0.6;
    private final double BLUE_SCALE = 0.11;
    private final double MAX_COLOR_VAL = 255.0;
    private final double PROGRESS_INTERVAL = 10.0;
    private final ScalingStrategy scalingStrategy;
    private final AsciiRamp ramp;
    private final double scale;
    private final List<ConversionObserver> observers;

    public AsciiConverter(ScalingStrategy scalingStrategy, AsciiRamp ramp, double scale) {
        this.scalingStrategy = scalingStrategy;
        this.ramp = ramp;
        this.scale = scale;
        this.observers = new ArrayList<>();
    }

    // Observer Pattern methods
    public void addObserver(ConversionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ConversionObserver observer) {
        observers.remove(observer);
    }

    private void notifyStarted(int totalRows) {
        for (ConversionObserver observer : observers) {
            observer.onConversionStarted(totalRows);
        }
    }

    private void notifyProgress(int currentRow, int totalRows) {
        for (ConversionObserver observer : observers) {
            observer.onProgressUpdate(currentRow, totalRows);
        }
    }

    private void notifyComplete(String result) {
        for (ConversionObserver observer : observers) {
            observer.onConversionComplete(result);
        }
    }

    private double weightRGB(int r, int g, int b) {
        return RED_SCALE * r + GREEN_SCALE * g + BLUE_SCALE * b;
    }

    private char brightnessToASCII(double brightness) {
        String rampStr = ramp.getRampString();
        int length = rampStr.length();
        double pieces = MAX_COLOR_VAL / length;
        double curBrightness = MAX_COLOR_VAL;

        for (int i = 0; i < length; i++) {
            if (brightness > curBrightness) {
                return rampStr.charAt(length - 1 - i);
            }
            curBrightness -= pieces;
        }
        return ' ';
    }

    public String convert(BufferedImage img) {
        if (img == null) {
            return "";
        }

        int originalWidth = img.getWidth();
        int originalHeight = img.getHeight();
        int scaledWidth = (int) (scale * originalWidth);
        int scaledHeight = (int) (scale * originalHeight);
        double mappingFactorWidth = (double) originalWidth / scaledWidth;
        double mappingFactorHeight = (double) originalHeight / scaledHeight;

        // Notify observers conversion is starting
        notifyStarted(scaledHeight);

        StringBuilder asciiOutput = new StringBuilder();
        for (int y = 0; y < scaledHeight; y++) {
            for (int x = 0; x < scaledWidth; x++) {
                double xOriginal = x * mappingFactorWidth;
                double yOriginal = y * mappingFactorHeight;

                ScaledPixel pixel = scalingStrategy.getScaledPixel(img, xOriginal, yOriginal);

                int color = img.getRGB(pixel.getX(), pixel.getY());
                int r = (color >> 16) & 0xff;
                int g = (color >> 8) & 0xff;
                int b = color & 0xff;
                double grayscale = weightRGB(r, g, b);
                asciiOutput.append(brightnessToASCII(grayscale));
            }
            asciiOutput.append('\n');

            // Notify progress every PROGRESS_INTERVAL rows
            if (y % PROGRESS_INTERVAL == 0 || y == scaledHeight - 1) {
                notifyProgress(y + 1, scaledHeight);
            }
        }

        String result = asciiOutput.toString();
        notifyComplete(result);
        return result;
    }
}