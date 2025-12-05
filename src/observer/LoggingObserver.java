package observer;

public class LoggingObserver implements ConversionObserver {
    @Override
    public void onConversionStarted(int totalRows) {
        System.out.println("[INFO] Starting conversion of " + totalRows + " rows");
    }

    @Override
    public void onProgressUpdate(int currentRow, int totalRows) {
        if (currentRow % 50 == 0) {  // Log every 50 rows
            int percent = (currentRow * 100) / totalRows;
            System.out.println("[INFO] Progress: " + percent + "% (" + currentRow + "/" + totalRows + ")");
        }
    }

    @Override
    public void onConversionComplete(String result) {
        System.out.println("[INFO] Conversion complete! Generated " + result.length() + " characters");
        System.out.println("[INFO] Output lines: " + result.split("\n").length);
    }
}