package org.kyykka;

import java.awt.EventQueue;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.io.CoordinateTranslator;
import org.kyykka.io.Display;
import org.kyykka.io.GameInitializer;
import org.kyykka.io.GamePainter;
import org.kyykka.io.Input;
import org.kyykka.io.MenuPanel;
import org.kyykka.logic.Game;
import org.kyykka.logic.TrajectoryCalculator;
import org.kyykka.logic.object.Karttu;
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
        //Works!
        //TODO: Rearrange all this shit sensibly!
        ImageContainer i = new ImageContainer();
        Game g = new Game();
        GamePainter p = new GamePainter(1200, 700, g, i);
        MenuPanel menu = new MenuPanel();
        Map<String, JPanel> panels = new HashMap<>();
        panels.put("menu", menu);
        panels.put("game", p);
        Display display = new Display(panels, "menu");
        SwingUtilities.invokeLater(display);
        Semaphore lock = new Semaphore(1);
        try{
            GameInitializer init = new GameInitializer(display, menu, lock);
            lock.acquire();
            g = init.getGame();
        } catch(InterruptedException e){}
        display.switchPanel("game");
        p.setGame(g);
        p.setActive(true);
//        Input in = new Input(display);
//        p.addMouseListener(in);
//        p.addMouseMotionListener(in);
//        CoordinateTranslator t = new CoordinateTranslator(g, 1200, 700);
//        g.addPlayer(new HumanPlayer(in, t));
//        g.addPlayer(new AIPlayer(g, false));
//        g.addPlayer(new HumanPlayer(in, t));
        g.run();
        System.exit(0);
    }
}
