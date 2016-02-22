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

    public HumanPlayer(Input input, CoordinateTranslator translator) {
        this.input = input;
        this.translator = translator;
        this.target = new Point3D(0, 0, 0);
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
        return 0;
    }

    @Override
    public ThrowParams getThrow() {
        return null;
    }

    @Override
    public void nextThrower() {}

    @Override
    public void endTurn() {}
    
}
