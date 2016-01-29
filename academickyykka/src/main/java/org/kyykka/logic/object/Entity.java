/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.object;

import org.kyykka.logic.BoundingBox;
import org.kyykka.logic.Point;

/**
 *
 * @author Admin
 */
public abstract class Entity {
    
    /**
     * GAME UNITS FOR PHYSICS:
     * Tick: 0.001s -> 100 ticks in one second
     * Distance: Millimetres
     * Momentum / velocity: Millimetres in one tick -> millimetres per 0.001s
     * Weight: Grams
     */
    
    private BoundingBox box;
    private int xmom;
    private int ymom;
    private int zmom;
    private int mass;

    public Entity(int x, int y, int z, int width, int height, int depth, int mass) {
        this.box = new BoundingBox(x, y, z, width, height, depth);
        this.mass = mass;
        this.xmom = 0;
        this.ymom = 0;
        this.zmom = 0;
    }
    
    public BoundingBox getBoundingBox(){
        return this.box;
    }
    
    public boolean collidesWith(Entity e){
        return this.getBoundingBox().collidesWith(e.getBoundingBox());
    }
    
    public void move(){
        this.box.moveX(xmom);
        this.box.moveY(ymom);
        this.box.moveZ(zmom);
        if(this.getZ() < 0){
            bounce();
        } else if(this.getZ() == 0){
            slide();
        }
    }
    
    public void applyGravity(){
        //TODO: Terminal velocity?
        if(this.getZ() > 0){
            this.zmom -= 1; //9.81m/s**2 = 0.981 mm/(0.001s)**2 ~ 1 mm/(0.001s)**2
        }
    }
    
    public int getVelocity(){
        return (int) Math.sqrt(xmom*xmom + ymom*ymom + zmom*zmom);
    }
    
    public void bounce(){
        if(this.getZ() >= 0){
            return;
        }
        this.setZ(0);
        if(Math.abs(this.zmom) < 20){ //No too tiny bounces
            this.zmom = 0;
        } else{
            this.zmom *= -0.7;
        }
        slide();
    }
    
    public void slide(){
        if(this.getZ() != 0){
            return;
        }
        this.xmom = applyFriction(this.xmom);
        this.ymom = applyFriction(this.ymom);
    }
    
    public static int applyFriction(int mom){
        if(mom < 2){ //got to stop sliding sometime
            return 0;
        }
        return (mom * 97) / 100;
    }
    
    public void collide(Entity e){
        //TODO: Make the entities change direction accordingly
        //Collide should be called seperately for both entities involved in collision
        double v1 = (double) getVelocity() / 10; //All these are converted to SI units
        double v2 = (double) e.getVelocity() / 10; //Velocity: m/s
        double m1 = (double) getMass() / 1000; //Mass: kg
        double m2 = (double) e.getMass() / 1000;
        double newvel = (v1 * (m1 - m2) + 2 * m2 * v2) / (m1 + m2); //Wikipedia -> Elastic collision
        if(v1 == 0){
            
        } else {
            double factor = newvel / v1;
            this.xmom *= factor;
            this.ymom *= factor;
            this.zmom *= factor;
        }
    }
        
    public abstract void tick();
    
    public void setXmom(int xmom) {
        this.xmom = xmom;
    }

    public void setYmom(int ymom) {
        this.ymom = ymom;
    }

    public void setZmom(int zmom) {
        this.zmom = zmom;
    }

    public int getXmom() {
        return xmom;
    }

    public int getYmom() {
        return ymom;
    }

    public int getZmom() {
        return zmom;
    }
    
    public int getX(){
        return this.box.getX();
    }
    
    public int getY(){
        return this.box.getY();
    }
    
    public int getZ(){
        return this.box.getZ();
    }
    
    public void setX(int x){
        this.box.setX(x);
    }
    
    public void setY(int y){
        this.box.setY(y);
    }
    
    public void setZ(int z){
        this.box.setZ(z);
    }

    public int getMass() {
        return mass;
    }
    
}
