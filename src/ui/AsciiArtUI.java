package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import loader.ImageLoader;
import converter.AsciiConverter;
import converter.AsciiConverterBuilder;
import ramp.RampType;
import strategy.NearestNeighborStrategy;
import strategy.BilinearInterpolationStrategy;
import observer.UIProgressObserver;
import observer.LoggingObserver;
import singleton.AppConfig;

public class AsciiArtUI extends JFrame {
    private final ImageLoader imageLoader;

    private JTextArea outputArea;
    private JComboBox<RampType> rampSelector;
    private JSlider scaleSlider;
    private JComboBox<String> strategySelector;
    private JLabel imageLabel;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private BufferedImage currentImage;
    private String currentImagePath;

    public AsciiArtUI(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;

        setTitle("ASCII Art Converter");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        loadUserPreferences();
        setLocationRelativeTo(null);


        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveUserPreferences();
            }
        });
    }

    private void initComponents() {
        // Top Panel - Controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

        JButton loadButton = new JButton("Load Image");
        loadButton.addActionListener(e -> loadImage());

        controlPanel.add(new JLabel("Ramp Type:"));
        rampSelector = new JComboBox<>(RampType.values());
        controlPanel.add(rampSelector);

        controlPanel.add(new JLabel("Scaling Strategy:"));
        strategySelector = new JComboBox<>(new String[]{"Nearest Neighbor", "Bilinear"});
        controlPanel.add(strategySelector);

        controlPanel.add(new JLabel("Scale:"));
        scaleSlider = new JSlider(10, 100, 40);
        scaleSlider.setMajorTickSpacing(10);
        scaleSlider.setPaintTicks(true);
        scaleSlider.setPaintLabels(true);
        scaleSlider.setPreferredSize(new Dimension(200, 50));
        controlPanel.add(scaleSlider);

        JButton convertButton = new JButton("Convert to ASCII");
        convertButton.addActionListener(e -> convertImage());
        controlPanel.add(convertButton);

        controlPanel.add(loadButton);
        add(controlPanel, BorderLayout.NORTH);

        // Progress Panel
        JPanel progressPanel = new JPanel(new BorderLayout(5, 5));
        progressPanel.setBorder(BorderFactory.createTitledBorder("Progress"));
        statusLabel = new JLabel("Ready");
        progressBar = new JProgressBar();
        progressPanel.add(statusLabel, BorderLayout.NORTH);
        progressPanel.add(progressBar, BorderLayout.CENTER);

        // Center - Split pane with image preview and ASCII output
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Left - Image preview
        imageLabel = new JLabel("No image loaded", SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(400, 500));
        imageLabel.setBorder(BorderFactory.createTitledBorder("Image Preview"));
        JScrollPane imageScroll = new JScrollPane(imageLabel);
        splitPane.setLeftComponent(imageScroll);

        // Right - ASCII output
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 8));
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("ASCII Output"));
        JScrollPane outputScroll = new JScrollPane(outputArea);
        splitPane.setRightComponent(outputScroll);

        splitPane.setDividerLocation(400);

        // Combine panels
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(progressPanel, BorderLayout.NORTH);
        centerPanel.add(splitPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void loadUserPreferences() {

        AppConfig config = AppConfig.getInstance();
        config.load();

        rampSelector.setSelectedItem(config.getDefaultRamp());
        scaleSlider.setValue((int)(config.getDefaultScale() * 100));

        if (config.getDefaultStrategy().equals("Bilinear")) {
            strategySelector.setSelectedIndex(1);
        }

        statusLabel.setText("Preferences loaded");
    }

    private void saveUserPreferences() {

        AppConfig config = AppConfig.getInstance();
        config.setDefaultRamp((RampType) rampSelector.getSelectedItem());
        config.setDefaultScale(scaleSlider.getValue() / 100.0);
        config.setDefaultStrategy((String) strategySelector.getSelectedItem());
        if (currentImagePath != null) {
            config.setLastUsedImagePath(currentImagePath);
        }
        config.save();
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();


        AppConfig config = AppConfig.getInstance();
        if (!config.getLastUsedImagePath().isEmpty()) {
            fileChooser.setCurrentDirectory(new java.io.File(config.getLastUsedImagePath()).getParentFile());
        }

        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Image files", "jpg", "jpeg", "png", "gif", "bmp"));

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            currentImagePath = fileChooser.getSelectedFile().getAbsolutePath();
            currentImage = imageLoader.loadImage(currentImagePath);

            if (currentImage != null) {
                ImageIcon icon = new ImageIcon(currentImage.getScaledInstance(
                        400, -1, Image.SCALE_SMOOTH));
                imageLabel.setIcon(icon);
                imageLabel.setText("");
                statusLabel.setText("Image loaded successfully");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to load image!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void convertImage() {
        if (currentImage == null) {
            JOptionPane.showMessageDialog(this, "Please load an image first!",
                    "No Image", JOptionPane.WARNING_MESSAGE);
            return;
        }


        var strategy = strategySelector.getSelectedIndex() == 0
                ? new NearestNeighborStrategy()
                : new BilinearInterpolationStrategy();


        AsciiConverter converter = new AsciiConverterBuilder()
                .withScalingStrategy(strategy)
                .withRampType((RampType) rampSelector.getSelectedItem())
                .withScale(scaleSlider.getValue() / 100.0)
                .build();


        converter.addObserver(new UIProgressObserver(progressBar, statusLabel));
        converter.addObserver(new LoggingObserver());


        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return converter.convert(currentImage);
            }

            @Override
            protected void done() {
                try {
                    String asciiArt = get();
                    outputArea.setText(asciiArt);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(AsciiArtUI.this,
                            "Error during conversion: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }
}