package observer;

public class LoggingObserver implements ConversionObserver {
    private final int LOG_INTERVAL = 50;
    private final int PERCENTAGE_CALCULATION = 100;
    @Override
    public void onConversionStarted(int totalRows) {
        System.out.println("[INFO] Starting conversion of " + totalRows + " rows");
    }

    @Override
    public void onProgressUpdate(int currentRow, int totalRows) {
        if (currentRow % LOG_INTERVAL == 0) {  // Log every LOG_INTERVAL rows
            int percent = (currentRow * PERCENTAGE_CALCULATION) / totalRows;
            System.out.println("[INFO] Progress: " + percent + "% (" + currentRow + "/" + totalRows + ")");
        }
    }

    @Override
    public void onConversionComplete(String result) {
        System.out.println("[INFO] Conversion complete! Generated " + result.length() + " characters");
        System.out.println("[INFO] Output lines: " + result.split("\n").length);
    }
}