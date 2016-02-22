package org.kyykka.logic.player;

import org.kyykka.io.CoordinateTranslator;
import org.kyykka.io.Input;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point3D;

/**
 *
 * @author Admin
 */
public class HumanPlayer implements Player{
    
    private Input input;
    private CoordinateTranslator translator;
    private Point3D target;
    private int throwState;
    private double angle;
    private int force;

    public HumanPlayer(Input input, CoordinateTranslator translator) {
        this.input = input;
        this.translator = translator;
        this.target = new Point3D(0, 0, 0);
        this.throwState = 0;
    }
    
    @Override
    public void startTurn() {}

    @Override
    public Point3D getTarget() {
        Point3D tar = this.translator.getPointPos(this.input.getMousePos());
        if(tar != null){
            if(tar.getX() >= 0 && tar.getX() <= 5000 
                    && tar.getY() >= 0 && tar.getY() <= 5000){
                this.target = tar;
            }
        }
        return this.target;
    }

    @Override
    public int getThrowState() {
        if(this.throwState != 0){
            switch(this.throwState){
                case 1: {
                    if(determineAngle()){
                        this.input.setPendingClicks(0);
                        this.throwState++;
                    }
                    break;
                }
                case 2: {
                    if(determineForce()){
                        this.input.setPendingClicks(0);
                        this.throwState++;
                    }
                    break;
                }
            }
        } else {
            if(input.isHeld()){
                this.throwState = 1;
                this.angle = -90;
                this.force = 10;
                input.processClick();
            }
        }
        return this.throwState;
    }
    
    public boolean determineAngle(){
        this.angle += 0.5;
        if(angle >= 90){
            return true;
        }
        return !input.isHeld();
    }
    
    public boolean determineForce(){
        this.force += 1;
        if(force >= 150){
            return true;
        }
        boolean finished = this.input.getPendingClicks() > 0;
        if(finished){
            this.input.processClick();
        }
        return finished;
    }

    @Override
    public ThrowParams getThrow() {
        this.throwState = 0;
        return new ThrowParams((int) this.angle, this.force);
    }

    @Override
    public void nextThrower() {}

    @Override
    public void endTurn() {}
    
}
