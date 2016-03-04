package org.kyykka.io;

import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 * Display handles the display window and changing between shown JPanels.
 *
 * @author Julius Laitala
 */
public class Display implements Runnable {

    private JFrame window;
    private JPanel panelContainer;
    private CardLayout layout;
    private Map<String, JPanel> panels;
    private String defaultPanel;

    /**
     * Creates a new display and stores the given JPanels to be used with
     * CardLayout.
     *
     * @param panels Map connecting strings that identify JPanels to JPanels
     *
     * @throws HeadlessException when (sic) code that is dependent on a
     * keyboard, display, or mouse is called in an environment that does not
     * support a keyboard, display, or mouse
     */
    public Display(Map<String, JPanel> panels, String defaultPanel) throws
            HeadlessException {
        this.panels = panels;
        this.defaultPanel = defaultPanel;
    }

    /**
     * Changes the JPanel shown in the window.
     *
     * @param name string identifier (as given in map given in constructor) for
     * wanted JPanel
     */
    public void switchPanel(String name) {
        layout.show(panelContainer, name);
    }

    public JPanel getPanel(String name) {
        return this.panels.get(name);
    }

    /**
     * Runs the display; creates a new JFrame with CardLayout, adds JPanels
     * given in the constructor and shows the given default panel.
     */
    @Override
    public void run() {
        window = new JFrame("ACADEMIC KYYKKA WORLD CUP");
        layout = new CardLayout();
        panelContainer = new JPanel(layout);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (String s : this.panels.keySet()) {
            panelContainer.add(this.panels.get(s), s);
        }
        layout.show(panelContainer, defaultPanel);
        window.add(panelContainer);
        window.pack();
        window.setVisible(true);
    }

}
