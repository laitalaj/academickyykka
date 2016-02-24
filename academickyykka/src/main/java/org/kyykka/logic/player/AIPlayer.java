package org.kyykka.logic.player;

import java.util.Random;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point3D;

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
    private Point3D target;
    private int throwState;
    private int counter;
    private double angle;
    private int force;
    private int targetAngle;
    private int targetForce;

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
        generateThrow();
        this.counter = 0;
        this.throwState = 0;
        this.angle = 0;
        this.force = 0;
    }
    
    @Override
    public void startTurn(){}

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
        this.target = new Point3D(tarx, tary, 0);
    }
    
    /**
     * Pre-generates target angle and force for the next throw. Also resets the
     * angle- and force-counters.
     */
    public void generateThrow() {
        this.angle = -90;
        this.force = 0;
        targetAngle = -40 + this.random.nextInt(80);
        targetForce = 80 + this.random.nextInt(60);
    }

    @Override
    public Point3D getTarget() {
        return this.target;
    }

    public void setTarget(Point3D target) {
        this.target = target;
    }
    
    @Override
    public void tick(){
        if(throwState != 0){
            if(this.throwState == 3){
            }else if(this.force >= this.targetForce){
                this.throwState = 3;
            }else if(this.angle >= this.targetAngle){
                this.throwState = 2;
                this.force++;
            } else {
                this.angle += 0.5;
            }
        } else {
            if(this.target.getDistance(this.game.getActiveThrower().getPos()) < 50){
                throwState = 1;
            }
        }
    }

    @Override
    public int getThrowState() {
        return throwState;
    }

    @Override
    public ThrowParams getThrow() {
        // TODO: Actual aiming
        this.throwState = 0;
        ThrowParams params = new ThrowParams(this.targetAngle, this.targetForce);
        generateTarget();
        generateThrow();
        return params;
    }

    @Override
    public void nextThrower() {
        return;
    }

    @Override
    public void endTurn() {
        return;
    }
    
    @Override
    public int getAngle() {
        return (int) angle;
    }
    
    @Override
    public int getForce() {
        return force;
    }

    public void setThrowState(int throwState) {
        this.throwState = throwState;
    }

}
