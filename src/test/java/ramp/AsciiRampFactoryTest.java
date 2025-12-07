package ramp;

import org.junit.jupiter.api.Test;
import ramp.*;

import static org.junit.jupiter.api.Assertions.*;

public class AsciiRampFactoryTest {

    @Test
    public void testCreateSimpleRamp() {
        AsciiRamp ramp = AsciiRampFactory.createRamp(RampType.SIMPLE);
        assertNotNull(ramp, "Factory should create a ramp");
        assertTrue(ramp instanceof SimpleRamp, "Should create SimpleRamp instance");
        assertEquals(" .:-=+*#%@", ramp.getRampString());
    }

    @Test
    public void testCreateDetailedRamp() {
        AsciiRamp ramp = AsciiRampFactory.createRamp(RampType.DETAILED);
        assertNotNull(ramp, "Factory should create a ramp");
        assertTrue(ramp instanceof DetailedRamp, "Should create DetailedRamp instance");
        assertTrue(ramp.getRampString().length() > 50, "Detailed ramp should have many characters");
    }

    @Test
    public void testCreateBlockRamp() {
        AsciiRamp ramp = AsciiRampFactory.createRamp(RampType.BLOCKS);
        assertNotNull(ramp, "Factory should create a ramp");
        assertTrue(ramp instanceof BlockRamp, "Should create BlockRamp instance");
        assertEquals(" ░▒▓█", ramp.getRampString());
    }

    @Test
    public void testFactoryReturnsAsciiRampInterface() {
        // Test that factory returns interface type, not concrete type
        AsciiRamp ramp = AsciiRampFactory.createRamp(RampType.SIMPLE);
        // This proves we're coding to abstractions
        assertTrue(ramp instanceof AsciiRamp, "Should return AsciiRamp interface");
    }
}