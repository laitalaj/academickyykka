package org.kyykka.logic.player;

import java.util.Random;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point;

/**
 * An AI controlled player. Plays the game using it's electronic intelligence
 * with varying degrees of success.
 *
 * @author Julius Laitala
 */
public class AIPlayer implements Player {

    private Game game;
    private boolean homeTeam;
    private Random random;
    private Point target;
    private int throwState;
    private int counter;

    /**
     * Creates a new AI player. Generates a target for the thrower it's
     * controlling.
     *
     * @param game the game in which the player is playing
     * @param homeTeam whether this AI player is playing the home team
     */
    public AIPlayer(Game game, boolean homeTeam) {
        this.game = game;
        this.homeTeam = homeTeam;
        this.random = new Random();
        generateTarget();
        this.counter = 0;
        this.throwState = 0;
    }

    /**
     * Generates a random target, a point to be returned when getTarget is
     * called. The target is within this players play square.
     */
    public void generateTarget() {
        int tarx = this.random.nextInt(5000);
        int tary = this.random.nextInt(5000);
        if (!homeTeam) {
            tary = 20000 - tary;
        }
        this.target = new Point(tarx, tary, 0);
    }

    @Override
    public Point getTarget() {
        return this.target;
    }

    public void setTarget(Point target) {
        this.target = target;
    }

    @Override
    public int getThrowState() {
        //TODO: Fix thrower stopping too early
        if(throwState != 0){
            counter++;
            if(counter > 150){
                throwState++;
                counter = 0;
                if(throwState > 3){
                    throwState = 3;
                }
            }
        } else {
            int dist = this.target.getDistance(this.game.getActiveThrower().getHitBox().getBottomCenter());
            if(dist < 750){
                throwState = 1;
            }
        }
        return throwState;
//        return this.game.getActiveThrower().getPos().equals(this.target);
//        return this.game.getActiveThrower().getXmom() == 0
//                && this.game.getActiveThrower().getYmom() == 0;
    }

    @Override
    public ThrowParams getThrow() {
        // TODO: Actual aiming
        int angle = -40 + this.random.nextInt(80);
        int force = 80 + this.random.nextInt(60);
        this.throwState = 0;
        generateTarget();
        return new ThrowParams(angle, force);
    }

    @Override
    public void nextThrower() {
        return;
    }

    @Override
    public void endTurn() {
        return;
    }

}
