/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.object;

import org.kyykka.logic.shape.Point;

/**
 *
 * @author Admin
 */
public class Thrower extends Entity{
    
    private Point target;

    public Thrower(int x, int y) {
        super(x, y, 0, 300, 300, 1700, 80000);
        this.target = new Point(x, y, 0);
    }
    
    public void setTarget(int x, int y){
        this.target.setX(x);
        this.target.setY(y);
    }
    
    public int calculateNextSpeed(){
        Point center = this.getBoundingBox().getBottomCenter();
        int distance = center.getDistance(this.target);
        if(distance > 3000){
            return 40;
        } else {
            int speed = 40 * (distance / 3000);
            if(speed < 5){
                speed = 5;
            }
            if(distance < speed){
                speed = distance;
            }
            return speed;
        }
    }
    
    public void updateSpeed(){
        //TODO: Not target positions, directions! Fix this!
        //(Instead of target.getX should be this.getX - target.getX or something...
        if(this.target.getX() == 0 && this.target.getY() == 0){
            return;
        }
        int nextspeed = calculateNextSpeed();
        if(this.target.getY() == 0){
            this.setXmom(nextspeed);
            this.setYmom(0);
        } else {
            double ratio = (double) this.target.getX() / this.target.getY();
            double xmom = nextspeed * ratio;
            double ymom = nextspeed - xmom;
            this.setXmom((int) xmom);
            this.setYmom((int) ymom);
        }
    }

    @Override
    public void tick() {
        updateSpeed();
        move();
    }
    
}
