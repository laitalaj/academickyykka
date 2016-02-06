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
public class Thrower extends PhysicsEntity{
    
    private Point target;

    public Thrower(int x, int y) {
        super(x, y, 0, 300, 300, 1700, 80000);
        this.target = new Point(x, y, 0);
        this.setFrozen(false);
    }
    
    public void setTarget(int x, int y){
        this.target.setX(x);
        this.target.setY(y);
    }
    
    public int calculateNextSpeed(){
        Point center = this.getBoundingBox().getBottomCenter();
        int distance = center.getDistance(this.target);
        if(distance >= 3000){
            return 40;
        } else {
            double speed = 40 * ((double) distance / 3000);
            if(speed < 5){
                speed = 5;
            }
            if(distance < speed){
                speed = distance;
            }
            return (int) speed;
        }
    }
    
    public void updateSpeed(){
        int vx = this.target.getX() - this.getX();
        int vy = this.target.getY() - this.getY();
        if(vx == 0 && vy == 0){
            this.setXmom(0);
            this.setYmom(0);
            return;
        }
        int nextspeed = calculateNextSpeed();
        if(vx == 0){
            this.setYmom(nextspeed * (int) Math.signum(vy));
            this.setXmom(0);
        } else {
            double ratio = (double) Math.abs(vy) / Math.abs(vx);
            double xmom = (nextspeed / Math.sqrt(ratio * ratio + 1)) * Math.signum(vx);
            //Calculations are basically Pythagorean theorem
            double ymom = Math.abs(xmom) * ratio * Math.signum(vy);
            this.setXmom((int) xmom);
            this.setYmom((int) ymom);
        }
    }
    
    public Karttu throwKarttu(int angle, int force){
        double angleradians = Math.toRadians(angle);
        double xmom = force * Math.sin(angleradians);
        double ymom = force * Math.cos(angleradians);
        Point throwpos = this.getBoundingBox().getCenter();
        return new Karttu(throwpos.getX(), throwpos.getY(), throwpos.getZ(), (int) xmom, (int) ymom, 0);
    }
    
    @Override
    public void setFrozen(boolean frozen){
        super.setFrozen(false);
    }

    @Override
    public void tick() {
        updateSpeed();
        move();
    }
    
}
