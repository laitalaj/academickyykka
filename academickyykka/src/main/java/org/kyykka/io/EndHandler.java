package org.kyykka.io;

import org.kyykka.io.forms.EndPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import org.kyykka.logic.Team;

/**
 * The EndHandler is an action listener that locks the thread until the "Play
 * again!"-button of the EndPanel given in its constructor is pressed.
 * 
 * @author Julius Laitala
 */
public class EndHandler implements ActionListener {

    private Semaphore lock;
    private EndPanel panel;
    
    /**
     * Creates a new EndHandler, locks the thread with Semaphore and writes the
     * winner to given EndPanel. Also adds the handler as an action listener to
     * given EndPanel's playAgainButton.
     * 
     * @param winningTeam the Team that won last game
     * @param panel the EndPanel that's currently being shown
     * @param lock a binary Semaphore the parent thread should try to acquire 
     * after creating this EndHandler and with which this EndHandler will lock
     * the thread
     */
    public EndHandler(Team winningTeam, EndPanel panel, Semaphore lock) {
        lock.acquireUninterruptibly();
        this.lock = lock;
        this.panel = panel;
        setText(winningTeam);
        this.panel.getPlayAgainButton().addActionListener(this);
    }
    
    private void setText(Team winningTeam) {
        String victoryText = "The ";
        if (winningTeam.isHomeTeam()) {
            victoryText += "HOME";
        } else {
            victoryText += "AWAY";
        }
        victoryText += " team wins with ";
        victoryText += winningTeam.calculateScore();
        victoryText += " points!";
        this.panel.getVictoryTextLabel().setText(victoryText);
    }
    
    /**
     * Unlocks the thread and removes this from playAgainButton's listeners.
     * 
     * @param e event that triggered the call of this function
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getPlayAgainButton().removeActionListener(this);
        lock.release();
    }

}
