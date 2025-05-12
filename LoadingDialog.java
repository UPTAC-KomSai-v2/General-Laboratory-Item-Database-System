import java.awt.*;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.*;

/**
 * A singleton loading dialog class that runs in a background thread.
 * It displays a non-closeable dialog with a progress bar.
 */
public class LoadingDialog {
    private static LoadingDialog instance;
    private final JDialog dialog;
    private final JProgressBar progressBar;
    private final JLabel statusLabel;
    private final AtomicInteger progress = new AtomicInteger(0);
    private Thread backgroundThread;
    private boolean isRunning = false;
    private int maxProgress = 100;

    /**
     * Private constructor to enforce singleton pattern
     */
    private LoadingDialog() {
        // Create dialog
        dialog = new JDialog((Frame) null, "Loading...", true);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // Cannot be closed by user
        dialog.setSize(400, 120);
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout(10, 10));

        // Create components
        progressBar = new JProgressBar(0, maxProgress);
        progressBar.setStringPainted(true);
        
        statusLabel = new JLabel("Loading...");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add components to dialog
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(statusLabel, BorderLayout.NORTH);
        contentPanel.add(progressBar, BorderLayout.CENTER);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Gets the singleton instance of LoadingDialog
     * @return The LoadingDialog instance
     */
    public static synchronized LoadingDialog getInstance() {
        if (instance == null) {
            instance = new LoadingDialog();
        }
        return instance;
    }

    /**
     * Shows the loading dialog with a specified maximum progress value
     * @param maxValue The maximum value for the progress bar
     * @param initialMessage The initial status message
     */
    public void show(int maxValue, String initialMessage) {
        maxProgress = maxValue;
        progressBar.setMaximum(maxValue);
        progressBar.setValue(0);
        progress.set(0);
        statusLabel.setText(initialMessage);
        
        if (isRunning) {
            return; // Already running
        }
        
        isRunning = true;
        
        // Make the dialog visible immediately on the EDT
        // This is crucial - we need to make the dialog visible first
        SwingUtilities.invokeLater(() -> {
            dialog.setVisible(true);
        });
        
        // Then start a background thread to update the progress
        backgroundThread = new Thread(() -> {
            // Keep the thread alive while the dialog is visible
            while (isRunning) {
                try {
                    Thread.sleep(100);
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(progress.get());
                        // Auto-hide when complete
                        if (progress.get() >= maxProgress && isRunning) {
                            hide();
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }
    
    /**
     * Shows the loading dialog with default maximum value (100)
     * @param initialMessage The initial status message
     */
    public void show(String initialMessage) {
        show(100, initialMessage);
    }

    /**
     * Increments the progress by a specified amount
     * @param increment Amount to increase progress by
     */
    public void incrementProgress(int increment) {
        int newValue = progress.addAndGet(increment);
        if (newValue > maxProgress) {
            progress.set(maxProgress);
        }
    }
    
    /**
     * Sets the progress to a specific value
     * @param value The new progress value
     */
    public void setProgress(int value) {
        if (value >= 0 && value <= maxProgress) {
            progress.set(value);
        }
    }
    
    /**
     * Updates the status message
     * @param message The new status message
     */
    public void updateStatus(String message) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(message);
        });
    }
    
    /**
     * Hides the loading dialog
     */
    public void hide() {
        isRunning = false;
        SwingUtilities.invokeLater(() -> {
            dialog.setVisible(false);
        });
    }
    
    /**
     * Resets the progress bar to zero
     */
    public void reset() {
        progress.set(0);
        SwingUtilities.invokeLater(() -> {
            progressBar.setValue(0);
        });
    }
}