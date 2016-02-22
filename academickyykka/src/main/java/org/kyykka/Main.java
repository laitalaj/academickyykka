package org.kyykka;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.io.CoordinateTranslator;
import org.kyykka.io.Display;
import org.kyykka.io.GamePainter;
import org.kyykka.io.Input;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.object.Thrower;
import org.kyykka.logic.player.AIPlayer;
import org.kyykka.logic.player.HumanPlayer;
import org.kyykka.logic.shape.HitBox;

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
        //TODO: Rearrange all this shit sensibly!
        ImageContainer i = new ImageContainer();
        Game g = new Game();
        GamePainter p = new GamePainter(1200, 700, g, i);
        Display display = new Display(p);
        Input in = new Input(display);
        p.addMouseListener(in);
        p.addMouseMotionListener(in);
        CoordinateTranslator t = new CoordinateTranslator(g, 1200, 700);
        g.addPlayer(new HumanPlayer(in, t));
        g.addPlayer(new AIPlayer(g, false));
        SwingUtilities.invokeLater(display);
        g.run();
        System.exit(0);
    }
}
