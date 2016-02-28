package org.kyykka.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import org.kyykka.logic.Team;

/**
 *
 * @author Admin
 */
public class EndHandler implements ActionListener {
    
    private Semaphore lock;
    private EndPanel panel;

    public EndHandler(Team winningTeam, EndPanel panel, Semaphore lock) {
        lock.acquireUninterruptibly();
        this.lock = lock;
        this.panel = panel;
        setText(winningTeam);
        this.panel.getPlayAgainButton().addActionListener(this);
    }
    
    private void setText(Team winningTeam){
        String victoryText = "The ";
        if(winningTeam.isHomeTeam()){
            victoryText += "HOME";
        } else {
            victoryText += "AWAY";
        }
        victoryText += " team wins with ";
        victoryText += winningTeam.calculateScore();
        victoryText += " points!";
        this.panel.getVictoryTextLabel().setText(victoryText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.panel.getPlayAgainButton().removeActionListener(this);
        lock.release();
    }
    
}
