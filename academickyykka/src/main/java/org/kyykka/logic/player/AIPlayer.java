/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.player;

import java.util.Random;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point;

/**
 *
 * @author Admin
 */
public class AIPlayer implements Player {
    
    private Game game;
    private boolean homeTeam;
    private Random random;
    private Point target;

    public AIPlayer(Game game, boolean homeTeam) {
        this.game = game;
        this.homeTeam = homeTeam;
        this.random = new Random();
        generateTarget();
    }
    
    public void generateTarget(){
        int tarx = this.random.nextInt(5000);
        int tary = this.random.nextInt(5000);
        if(!homeTeam){
            tary = 20000 - tary;
        }
        this.target = new Point(tarx, tary, 0);
    }

    @Override
    public Point getTarget() {
        return this.target;
    }

    @Override
    public boolean throwReady() {
        return this.game.getActiveThrower().getPos().equals(this.target);
    }

    @Override
    public ThrowParams getThrow() {
        // TODO: Actual aiming
        int angle = -90 + this.random.nextInt(180);
        int force = this.random.nextInt(75);
        return new ThrowParams(angle, force);
    }

    @Override
    public void nextThrower() {
        generateTarget();
    }

    @Override
    public void endTurn() {
        return;
    }
    
}
