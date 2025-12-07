package observer;

public interface ConversionObserver {
    void onConversionStarted(int totalRows);
    void onProgressUpdate(int currentRow, int totalRows);
    void onConversionComplete(String result);
}