package org.kyykka;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.io.Display;
import org.kyykka.logic.EndHandler;
import org.kyykka.io.forms.EndPanel;
import org.kyykka.logic.GameInitializer;
import org.kyykka.io.painter.GamePanel;
import org.kyykka.io.forms.MenuPanel;
import org.kyykka.logic.Game;

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
        ImageContainer container = new ImageContainer();
        Game game = new Game();
        GamePanel gamePainter = new GamePanel(1200, 700, game, container);
        MenuPanel menu = new MenuPanel();
        EndPanel end = new EndPanel();
        Map<String, JPanel> panels = new HashMap<>();
        panels.put("menu", menu);
        panels.put("game", gamePainter);
        panels.put("end", end);
        Display display = new Display(panels, "menu");
        SwingUtilities.invokeLater(display);
        Semaphore lock = new Semaphore(1);
        while (true) { //Main loop
            GameInitializer init = new GameInitializer(display, menu, lock);
            lock.acquireUninterruptibly();
            lock.release();
            game = init.getGame();
            display.switchPanel("game");
            gamePainter.setGame(game);
            gamePainter.setActive(true);
            game.setWinningTeam(game.getActiveTeam());
            game.run();
            gamePainter.setActive(false);
            display.switchPanel("end");
            EndHandler endHandler = new EndHandler(game.getWinningTeam(), end,
                    lock);
            lock.acquireUninterruptibly();
            lock.release();
            display.switchPanel("menu");
        }
//        System.exit(0);
    }
}
