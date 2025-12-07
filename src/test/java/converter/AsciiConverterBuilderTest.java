package converter;

import converter.AsciiConverter;
import converter.AsciiConverterBuilder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import strategy.NearestNeighborStrategy;
import strategy.BilinearInterpolationStrategy;
import ramp.RampType;
import ramp.SimpleRamp;

public class AsciiConverterBuilderTest {

    @Test
    public void testBuilderCreatesConverter() {
        AsciiConverter converter = new AsciiConverterBuilder()
                .build();
        assertNotNull(converter, "Builder should create converter");
    }

    @Test
    public void testBuilderWithStrategy() {
        AsciiConverter converter = new AsciiConverterBuilder()
                .withScalingStrategy(new NearestNeighborStrategy())
                .build();
        assertNotNull(converter, "Builder should accept strategy");
    }

    @Test
    public void testBuilderWithRampType() {
        AsciiConverter converter = new AsciiConverterBuilder()
                .withRampType(RampType.DETAILED)
                .build();
        assertNotNull(converter, "Builder should accept ramp type");
    }

    @Test
    public void testBuilderWithScale() {
        AsciiConverter converter = new AsciiConverterBuilder()
                .withScale(0.5)
                .build();
        assertNotNull(converter, "Builder should accept scale");
    }

    @Test
    public void testBuilderChaining() {
        // Test that builder methods return builder for chaining
        AsciiConverterBuilder builder = new AsciiConverterBuilder();
        AsciiConverterBuilder result = builder
                .withScalingStrategy(new BilinearInterpolationStrategy())
                .withRampType(RampType.BLOCKS)
                .withScale(0.3);

        assertSame(builder.getClass(), result.getClass(),
                "Builder methods should return builder for chaining");
    }

    @Test
    public void testBuilderWithAllParameters() {
        AsciiConverter converter = new AsciiConverterBuilder()
                .withScalingStrategy(new NearestNeighborStrategy())
                .withRampType(RampType.SIMPLE)
                .withScale(0.4)
                .build();

        assertNotNull(converter, "Builder should handle all parameters");
    }
}