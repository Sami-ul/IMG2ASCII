package test.ramp;

import org.junit.jupiter.api.Test;
import ramp.BlockRamp;
import ramp.DetailedRamp;
import ramp.SimpleRamp;

import static org.junit.jupiter.api.Assertions.*;

public class RampImplementationsTest {

    @Test
    public void testSimpleRampNotEmpty() {
        SimpleRamp ramp = new SimpleRamp();
        assertFalse(ramp.getRampString().isEmpty(), "Ramp string should not be empty");
    }

    @Test
    public void testSimpleRampStartsWithSpace() {
        SimpleRamp ramp = new SimpleRamp();
        assertEquals(' ', ramp.getRampString().charAt(0), "Should start with space for brightest");
    }

    @Test
    public void testDetailedRampIsLonger() {
        DetailedRamp detailed = new DetailedRamp();
        SimpleRamp simple = new SimpleRamp();
        assertTrue(detailed.getRampString().length() > simple.getRampString().length(),
                "Detailed ramp should have more characters than simple");
    }

    @Test
    public void testBlockRampHasUnicodeCharacters() {
        BlockRamp ramp = new BlockRamp();
        String rampStr = ramp.getRampString();
        assertTrue(rampStr.contains("░"), "Block ramp should contain light shade");
        assertTrue(rampStr.contains("▒"), "Block ramp should contain medium shade");
        assertTrue(rampStr.contains("▓"), "Block ramp should contain dark shade");
        assertTrue(rampStr.contains("█"), "Block ramp should contain full block");
    }
}