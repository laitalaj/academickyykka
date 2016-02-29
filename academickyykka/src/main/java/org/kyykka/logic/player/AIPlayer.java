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
    private double spin;
    private int targetAngle;
    private int targetForce;
    private int targetSpin;

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
    }

    @Override
    public void startTurn() {
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
        this.target = new Point3D(tarx, tary, 0);
    }

    /**
     * Pre-generates target angle and force for the next throw. Also resets the
     * angle- and force-counters.
     */
    public void generateThrow() {
        this.angle = -90;
        this.force = 0;
        this.spin = 1;
        int distance;
        int left = this.target.getX();
        int right = 5000 - this.target.getX();
        if(this.homeTeam){
            distance = 15000 - this.target.getY();
        } else {
            distance = this.target.getY() - 5000;
        }
        double minAngle = Math.toDegrees(Math.atan((double) left / distance));
        double maxAngle = Math.toDegrees(Math.atan((double) right / distance));
        targetAngle = - (int) minAngle + this.random.nextInt((int) (minAngle + maxAngle));
        targetForce = 80 + this.random.nextInt(60);
        targetSpin = 1 + this.random.nextInt(6);
    }

    @Override
    public Point3D getTarget() {
        return this.target;
    }

    public void setTarget(Point3D target) {
        this.target = target;
    }

    @Override
    public void tick() {
        if (throwState != 0) {
            if (this.throwState == 4) {
                return;
            } else if (this.spin >= this.targetSpin){
                this.throwState = 4;
            } else if (this.force >= this.targetForce) {
                this.throwState = 3;
                this.spin += 0.05;
            } else if (this.angle >= this.targetAngle) {
                this.throwState = 2;
                this.force++;
            } else {
                this.angle += 0.5;
            }
        } else {
            if (this.target.getDistance(this.game.getActiveThrower().getPos()) < 50) {
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
        // TODO: Actual aiming. Getting there...
        this.throwState = 0;
        ThrowParams params = new ThrowParams(this.targetAngle, this.targetForce, 
                10, this.targetSpin);
        generateTarget();
        generateThrow();
        return params;
    }

    @Override
    public void nextThrower() {
        generateTarget();
        generateThrow();
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

    @Override
    public int getZmom() {
        return 10;
    }
    
    @Override
    public double getSpin(){
        return this.spin;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public int getTargetAngle() {
        return targetAngle;
    }

    public int getTargetForce() {
        return targetForce;
    }

    public void setThrowState(int throwState) {
        this.throwState = throwState;
    }

}
