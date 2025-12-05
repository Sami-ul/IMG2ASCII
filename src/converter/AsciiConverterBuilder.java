package converter;

import strategy.ScalingStrategy;
import strategy.NearestNeighborStrategy;
import ramp.AsciiRamp;
import ramp.AsciiRampFactory;
import ramp.RampType;
import singleton.AppConfig;

public class AsciiConverterBuilder {
    private ScalingStrategy scalingStrategy = new NearestNeighborStrategy();
    private AsciiRamp ramp;
    private double scale;

    public AsciiConverterBuilder() {

        AppConfig config = AppConfig.getInstance();
        this.ramp = AsciiRampFactory.createRamp(config.getDefaultRamp());
        this.scale = config.getDefaultScale();
    }

    public AsciiConverterBuilder withScalingStrategy(ScalingStrategy strategy) {
        this.scalingStrategy = strategy;
        return this;
    }

    public AsciiConverterBuilder withRamp(AsciiRamp ramp) {
        this.ramp = ramp;
        return this;
    }

    public AsciiConverterBuilder withRampType(RampType type) {
        this.ramp = AsciiRampFactory.createRamp(type);
        return this;
    }

    public AsciiConverterBuilder withScale(double scale) {
        this.scale = scale;
        return this;
    }

    public AsciiConverter build() {
        return new AsciiConverter(scalingStrategy, ramp, scale);
    }
}