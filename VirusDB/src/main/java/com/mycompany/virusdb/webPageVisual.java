package com.mycompany.virusdb;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 *
 * @author Thijs Weenink
 * @version 2.0 <br>
 * 
 * Uses JavaFX to load a webpage into a JPanel.
 * It loads every page into the same JPanel.
 */
public class webPageVisual extends JFrame {

    private static JFrame mainFrame;

    public static void showWebPage(String url, String frameTitle, boolean firstRun) {
        if (firstRun) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    mainFrame = new JFrame();
                    mainFrame.setTitle(frameTitle);
                    AppPanel appPanel = new AppPanel(url);

                    double ratio = (2 / 3.0);

                    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                    int width = (int) Math.round((gd.getDisplayMode().getWidth()) * ratio);
                    int heigth = (int) Math.round(gd.getDisplayMode().getHeight()* ratio);

                    mainFrame.setSize(width, heigth);
                    mainFrame.add(appPanel);
                    mainFrame.setVisible(true);

                    WindowListener exitListener = new WindowAdapter() {

                        @Override
                        public void windowClosing(WindowEvent e) {
                            VirusDBGUI.resetRuns();
                        }
                    };
                    mainFrame.addWindowListener(exitListener);

                }
            });
        } else {
            AppPanel panel;
            panel = (AppPanel) mainFrame.getContentPane().getComponent(0);

            panel.setWebURL(url);
            panel.updateWebComponent();

        }

    }

    public static String getLoadedPage() {
        String page = "";

        AppPanel panel;
        try {
            panel = (AppPanel) mainFrame.getContentPane().getComponent(0);

            page = panel.getWebURL();
        } catch (Exception exc) {
        }

        return page;
    }
}

/**
 * Main panel used to display the webpage.
 */
class AppPanel extends JPanel {

    private String webURL;
    private String urlSwing;
    JFXPanel javafxPanel;
    WebView webComponent;

    JTextField urlField;
    JButton goButton;

    public AppPanel(String webURL) {
        this.webURL = webURL;
        javafxPanel = new JFXPanel();

        loadJavaFXScene();
        initSwingComponents();
    }

    public void removeAllThings() {
        this.removeAll();
    }

    public void setWebURL(String url) {
        this.webURL = url;
    }

    /**
     * Instantiate the Swing components to be used
     */
    private void initSwingComponents() {
        this.setLayout(new BorderLayout());
        this.add(javafxPanel, BorderLayout.CENTER);
        this.setOpaque(false);

        //JPanel urlPanel = new JPanel(new FlowLayout());
        //mainPanel.add(urlPanel, BorderLayout.NORTH);
    }

    /**
     * Instantiate the JavaFX Components in the JavaFX Application Thread.
     */
    private void loadJavaFXScene() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                BorderPane borderPane = new BorderPane();
                webComponent = new WebView();

                webComponent.getEngine().load(webURL);

                borderPane.setCenter(webComponent);
                Scene scene = new Scene(borderPane);
                javafxPanel.setScene(scene);
                javafxPanel.setVisible(true);
                //javafxPanel.setOpaque(true);
                javafxPanel.setBackground(Color.blue);
            }
        });
    }

    public void updateWebComponent() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                webComponent.getEngine().load(webURL);
                javafxPanel.repaint();
            }
        });

    }

    public String getWebURL() {
        setWebURLFX();
        return urlSwing;
    }

    private void setWebURLFX() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String title = webComponent.getEngine().getLocation();
                //System.out.println(title);
                urlSwing = title;
            }
        });

    }

}