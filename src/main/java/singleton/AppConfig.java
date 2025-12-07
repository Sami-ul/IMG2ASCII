package singleton;

import ramp.RampType;
import java.io.*;
import java.util.Properties;

public class AppConfig {
    private final double DEFAULT_SCALE_VALUE = 0.4;
    private final double MAX_SCALE_VALUE = 1.0;
    private static final AppConfig INSTANCE = new AppConfig();


    private AppConfig() {
        this.defaultRamp = RampType.SIMPLE;
        this.defaultScale = DEFAULT_SCALE_VALUE;
        this.defaultStrategy = "NearestNeighbor";
        this.lastUsedImagePath = "";
    }


    public static AppConfig getInstance() {
        return INSTANCE;
    }


    private RampType defaultRamp;
    private double defaultScale;
    private String defaultStrategy;
    private String lastUsedImagePath;

    public RampType getDefaultRamp() {
        return defaultRamp;
    }

    public void setDefaultRamp(RampType ramp) {
        this.defaultRamp = ramp;
    }

    public double getDefaultScale() {
        return defaultScale;
    }

    public void setDefaultScale(double scale) {
        if (scale > 0 && scale <= MAX_SCALE_VALUE) {
            this.defaultScale = scale;
        }
    }

    public String getDefaultStrategy() {
        return defaultStrategy;
    }

    public void setDefaultStrategy(String strategy) {
        this.defaultStrategy = strategy;
    }

    public String getLastUsedImagePath() {
        return lastUsedImagePath;
    }

    public void setLastUsedImagePath(String path) {
        this.lastUsedImagePath = path;
    }

    public void save() {
        try (FileOutputStream out = new FileOutputStream("app.properties")) {
            Properties props = new Properties();
            props.setProperty("defaultRamp", defaultRamp.toString());
            props.setProperty("defaultScale", String.valueOf(defaultScale));
            props.setProperty("defaultStrategy", defaultStrategy);
            props.setProperty("lastUsedImagePath", lastUsedImagePath);
            props.store(out, "ASCII Art Converter Settings");
            System.out.println("[INFO] Settings saved successfully");
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save settings: " + e.getMessage());
        }
    }

    public void load() {
        try (FileInputStream in = new FileInputStream("app.properties")) {
            Properties props = new Properties();
            props.load(in);
            defaultRamp = RampType.valueOf(props.getProperty("defaultRamp", "SIMPLE"));
            defaultScale = Double.parseDouble(props.getProperty("defaultScale", "0.4"));
            defaultStrategy = props.getProperty("defaultStrategy", "NearestNeighbor");
            lastUsedImagePath = props.getProperty("lastUsedImagePath", "");
            System.out.println("[INFO] Settings loaded successfully");
        } catch (IOException e) {
            System.out.println("[INFO] No settings file found, using defaults");
        } catch (Exception e) {
            System.err.println("[ERROR] Failed to load settings: " + e.getMessage());
        }
    }
}