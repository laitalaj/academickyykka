/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import org.kyykka.logic.Game;
import org.kyykka.logic.player.AIPlayer;
import org.kyykka.logic.player.HumanPlayer;
import org.kyykka.logic.player.Player;

/**
 *
 * @author Admin
 */
public class GameInitializer implements ActionListener {
    
    private Display display;
    private MenuPanel menu;
    private Semaphore lock;
    private Game game;

    public GameInitializer(Display display, MenuPanel menu, Semaphore lock)
            throws InterruptedException{
        this.display = display;
        this.menu = menu;
        this.menu.getStartButton().addActionListener(this);
        this.lock = lock;
        this.lock.acquire();
    }
    
    private void addPlayer(int player, Input input, 
            CoordinateTranslator translator, boolean homeTeam){
        switch(player){
            case 0:{
                this.game.addPlayer(new HumanPlayer(input, translator));
                break;
            }
            case 1: {
                this.game.addPlayer(new AIPlayer(game, homeTeam));
                break;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int player1 = this.menu.getHomePlayerChoice().getSelectedIndex();
        int player2 = this.menu.getHomePlayerChoice().getSelectedIndex();
        this.game = new Game();
        Input input = new Input(this.display);
        //TODO: Fit size!
        CoordinateTranslator translator = new CoordinateTranslator(game, 1200, 700);
        addPlayer(player1, input, translator, true);
        addPlayer(player2, input, translator, false);
        this.lock.release();
    }

    public Game getGame() {
        return game;
    }
    
}
