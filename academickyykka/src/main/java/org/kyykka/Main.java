package org.kyykka;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.io.Display;
import org.kyykka.io.GamePainter;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.object.Thrower;

/**
 * The Main class of Academic Kyykka World Cup. Handles the interaction between
 * all other classes.
 *
 * @author Julius Laitala
 */
public class Main {

    /**
     * The main method. This is where the magic starts. Creates a new game,
     * loads images, initializes display and runs everything.
     *
     * @param args command line arguments (do nothing at the moment)
     */
    public static void main(String[] args) {
        //Works somewhat. Still very buggy and has only AI playing.
        Game g = new Game();
        ImageContainer i = new ImageContainer();
        GamePainter p = new GamePainter(1200, 700, g, i);
        Display display = new Display(p);
        SwingUtilities.invokeLater(display);
        g.run();
    }
}
