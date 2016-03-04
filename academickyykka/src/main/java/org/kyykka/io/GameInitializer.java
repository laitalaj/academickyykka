package org.kyykka.io;

import org.kyykka.io.forms.MenuPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import org.kyykka.logic.Game;
import org.kyykka.logic.player.AIPlayer;
import org.kyykka.logic.player.HumanPlayer;
import org.kyykka.logic.player.Player;

/**
 * This class initializes a game according to input given from a MenuPanel.
 * 
 * @author Julius Laitala
 */
public class GameInitializer implements ActionListener {

    private Display display;
    private MenuPanel menu;
    private Semaphore lock;
    private Game game;

    /**
     * Creates a new GameInitializer that takes input from given MenuPanel.
     * It basically adds new Players to the game according to said input.
     * 
     * @param display display that will be used to draw the game
     * @param menu form this class will take input from
     * @param lock a binary Semaphore the parent thread should try to acquire 
     * after creating this GameInitializer and with which this GameInitializer
     * will lock the main thread
     */
    public GameInitializer(Display display, MenuPanel menu, Semaphore lock) {
        lock.acquireUninterruptibly();
        this.display = display;
        this.menu = menu;
        this.menu.getStartButton().addActionListener(this);
        this.lock = lock;
    }

    private void addPlayer(int player, Input input,
            CoordinateTranslator translator, boolean homeTeam) {
        switch (player) {
            case 0: {
                this.game.addPlayer(new HumanPlayer(input, translator));
                break;
            }
            case 1: {
                this.game.addPlayer(new AIPlayer(game, homeTeam));
                break;
            }
        }
    }
    
    /**
     * Creates a new game according to choices on given MenuPanel. Unlocks the
     * thread locked by Semaphore.
     * 
     * @param e event that triggered this method call
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int player1 = this.menu.getHomePlayerChoice().getSelectedIndex();
        int player2 = this.menu.getAwayPlayerChoice().getSelectedIndex();
        this.game = new Game();
        Input input = new Input(this.display);
        //TODO: Fit size!
        CoordinateTranslator translator = new CoordinateTranslator(game, 1200, 700);
        addPlayer(player1, input, translator, true);
        addPlayer(player2, input, translator, false);
        this.menu.getStartButton().removeActionListener(this);
        this.lock.release();
    }
    
    /**
     * Returns the game that was initialized by this class.
     * 
     * @return initialized game
     */
    public Game getGame() {
        return game;
    }

}
