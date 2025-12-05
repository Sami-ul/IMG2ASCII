package ramp;

public class AsciiRampFactory {
    public static AsciiRamp createRamp(RampType type) {
        switch(type) {
            case SIMPLE:
                return new SimpleRamp();
            case DETAILED:
                return new DetailedRamp();
            case BLOCKS:
                return new BlockRamp();
            default:
                return new SimpleRamp();
        }
    }
}