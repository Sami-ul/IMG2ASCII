package observer;

import javax.swing.*;

public class UIProgressObserver implements ConversionObserver {
    private final JProgressBar progressBar;
    private final JLabel statusLabel;

    public UIProgressObserver(JProgressBar progressBar, JLabel statusLabel) {
        this.progressBar = progressBar;
        this.statusLabel = statusLabel;
    }

    @Override
    public void onConversionStarted(int totalRows) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setMaximum(totalRows);
            progressBar.setValue(0);
            statusLabel.setText("Converting...");
        });
    }

    @Override
    public void onProgressUpdate(int currentRow, int totalRows) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(currentRow);
            int percent = (currentRow * 100) / totalRows;
            statusLabel.setText("Converting: " + percent + "%");
        });
    }

    @Override
    public void onConversionComplete(String result) {
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(progressBar.getMaximum());
            statusLabel.setText("Conversion complete!");
        });
    }
}