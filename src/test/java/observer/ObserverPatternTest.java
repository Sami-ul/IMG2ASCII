package observer;

import observer.ConversionObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import converter.AsciiConverter;
import strategy.NearestNeighborStrategy;
import ramp.SimpleRamp;

public class ObserverPatternTest {
    private BufferedImage testImage;
    private TestObserver testObserver;

    // Test observer to verify notifications
    private static class TestObserver implements ConversionObserver {
        public boolean startedCalled = false;
        public boolean progressCalled = false;
        public boolean completeCalled = false;
        public int totalRows = 0;
        public String result = null;

        @Override
        public void onConversionStarted(int totalRows) {
            this.startedCalled = true;
            this.totalRows = totalRows;
        }

        @Override
        public void onProgressUpdate(int currentRow, int totalRows) {
            this.progressCalled = true;
        }

        @Override
        public void onConversionComplete(String result) {
            this.completeCalled = true;
            this.result = result;
        }
    }

    @BeforeEach
    public void setUp() {
        testImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                testImage.setRGB(x, y, Color.WHITE.getRGB());
            }
        }
        testObserver = new TestObserver();
    }

    @Test
    public void testObserverCanBeAdded() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );

        // Should not throw exception
        converter.addObserver(testObserver);
        assertTrue(true, "Observer should be added without error");
    }

    @Test
    public void testObserverReceivesStartNotification() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );
        converter.addObserver(testObserver);

        converter.convert(testImage);

        assertTrue(testObserver.startedCalled, "Observer should receive start notification");
        assertTrue(testObserver.totalRows > 0, "Total rows should be set");
    }

    @Test
    public void testObserverReceivesProgressNotification() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );
        converter.addObserver(testObserver);

        converter.convert(testImage);

        assertTrue(testObserver.progressCalled, "Observer should receive progress notification");
    }

    @Test
    public void testObserverReceivesCompleteNotification() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );
        converter.addObserver(testObserver);

        converter.convert(testImage);

        assertTrue(testObserver.completeCalled, "Observer should receive complete notification");
        assertNotNull(testObserver.result, "Result should be provided to observer");
        assertFalse(testObserver.result.isEmpty(), "Result should not be empty");
    }

    @Test
    public void testMultipleObservers() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );

        TestObserver observer1 = new TestObserver();
        TestObserver observer2 = new TestObserver();

        converter.addObserver(observer1);
        converter.addObserver(observer2);

        converter.convert(testImage);

        assertTrue(observer1.completeCalled, "First observer should be notified");
        assertTrue(observer2.completeCalled, "Second observer should be notified");
    }

    @Test
    public void testObserverCanBeRemoved() {
        AsciiConverter converter = new AsciiConverter(
                new NearestNeighborStrategy(),
                new SimpleRamp(),
                0.5
        );

        converter.addObserver(testObserver);
        converter.removeObserver(testObserver);

        converter.convert(testImage);

        assertFalse(testObserver.startedCalled,
                "Removed observer should not receive notifications");
    }
}