package org.kyykka.logic.player;

import org.kyykka.io.CoordinateTranslator;
import org.kyykka.io.Input;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point3D;

/**
 * Human-controlled player.
 * 
 * @author Admin
 */
public class HumanPlayer implements Player {

    private Input input;
    private CoordinateTranslator translator;
    private Point3D target;
    private int throwState;
    private double angle;
    private double zangle;
    private int force;
    private double spin;
    
    /**
     * Creates a new HumanPlayer, adds a CoordinateTranslator to it and links it
     * to an Input.
     * 
     * @param input input according to which the player should move
     * @param translator CoordinateTranslator that will take care of translating
     * the input's mouse coordinates to game coordinates
     */
    public HumanPlayer(Input input, CoordinateTranslator translator) {
        this.input = input;
        this.translator = translator;
        this.target = new Point3D(0, 0, 0);
        this.throwState = 0;
    }

    @Override
    public void startTurn() {
    }

    @Override
    public Point3D getTarget() {
        Point3D tar = this.translator.getPointPos(this.input.getMousePos());
        if (tar != null) {
            if (this.translator.getHomeTeam()) {
                if (tar.getX() >= 0 && tar.getX() <= 5000
                        && tar.getY() >= 0 && tar.getY() <= 5000) {
                    this.target = tar;
                }
            } else {
                if (tar.getX() >= 0 && tar.getX() <= 5000
                        && tar.getY() >= 15000 && tar.getY() <= 20000) {
                    this.target = tar;
                }
            }
        }
        return this.target;
    }

    @Override
    public void tick() {
        if (this.throwState != 0) {
            switch (this.throwState) {
                case 1: {
                    if (determineAngle()) {
                        this.input.setPendingClicks(0);
                        this.throwState++;
                    }
                    break;
                }
                case 2: {
                    if (determineForce()) {
                        this.input.setPendingClicks(0);
                        this.throwState++;
                    }
                    break;
                }
                case 3: {
                    if (determineSpin()) {
                        this.input.setPendingClicks(0);
                        this.throwState++;
                    }
                }
            }
        } else {
            if (input.isHeld()) {
                this.throwState = 1;
                this.angle = -90;
                this.force = 10;
                this.zangle = 0;
                this.spin = 1;
                input.processClick();
            }
        }
    }

    @Override
    public int getThrowState() {
        return this.throwState;
    }
    
    /**
     * Advances angle determination process by one tick.
     * 
     * @return true if the process has finished, false otherwise
     */
    public boolean determineAngle() {
        this.angle += 0.5;
        if (angle >= 90) {
            return true;
        }
        return !input.isHeld();
    }
    
    /**
     * Advances force determination process by one tick.
     * 
     * @return true if the process has finished, false otherwise
     */
    public boolean determineForce() {
        this.zangle += 0.2;
        if (this.zangle <= 20) {
            this.force += 2;
            if (force > 150){
                force = 150;
            }
        } else {
            this.force -= 1;
            if (force <= 15) {
                return true;
            }
        }
        boolean finished = this.input.getPendingClicks() > 0;
        if (finished) {
            this.input.processClick();
        }
        return finished;
    }
    
    public boolean determineSpin() {
        this.spin += 0.05;
        if(this.spin > 7){
            return true;
        }
        boolean finished = this.input.getPendingClicks() > 0;
        if (finished) {
            this.input.processClick();
        }
        return finished;
    }

    @Override
    public ThrowParams getThrow() {
        this.throwState = 0;
        return new ThrowParams((int) this.angle, this.force, (int) this.zangle, 
                this.spin);
    }

    @Override
    public void nextThrower() {
    }

    @Override
    public void endTurn() {
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
    public double getZangle() {
        return (int) zangle;
    }
    
    @Override
    public double getSpin() {
        return this.spin;
    }

    public void setThrowState(int throwState) {
        this.throwState = throwState;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public void setZangle(double zangle) {
        this.zangle = zangle;
    }

}
