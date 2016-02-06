/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.object;

import org.kyykka.logic.shape.HitBox;
import org.kyykka.logic.shape.Point;

/**
 *
 * @author Admin
 */
public abstract class PhysicsEntity {
    
    /**
     * GAME UNITS FOR PHYSICS:
     * Tick: 0.01s -> 100 ticks in one second
     * Distance: Millimetres
     * Momentum / velocity: Millimetres in one tick -> millimetres per 0.01s
     * Weight: Grams
     */
    
    private HitBox box;
    private int xmom;
    private int ymom;
    private int zmom;
    private int mass;
    private boolean frozen;

    public PhysicsEntity(int x, int y, int z, int width, int height, int depth, int mass) {
        this.box = new HitBox(x, y, z, width, height, depth);
        this.mass = mass;
        this.xmom = 0;
        this.ymom = 0;
        this.zmom = 0;
        this.frozen = true;
    }
    
    public HitBox getBoundingBox(){
        return this.box;
    }
    
    public boolean collidesWith(PhysicsEntity e){
        return this.getBoundingBox().collidesWith(e.getBoundingBox());
    }
    
    public void move(){
        if(!frozen){
            this.box.moveX(xmom);
            this.box.moveY(ymom);
            this.box.moveZ(zmom);
            bounce();
            slide();
            checkFreeze();
        }
    }
    
    public void checkFreeze(){
        if((getXmom() == 0 && getYmom() == 0 && getZmom() == 0)){
            this.setFrozen(true);
        } else {
            this.setFrozen(false);
        }
    }
    
    public void applyGravity(){
        //TODO: Terminal velocity?
        if(this.getZ() > 0 && !frozen){
            this.zmom -= 1; //9.81m/s**2 = 0.981 mm/(0.01s)**2 ~ 1 mm/(0.0001s)**2
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
    }
    
    public void slide(){
        if(this.getZ() != 0){
            return;
        }
        this.xmom = applyFriction(this.xmom);
        this.ymom = applyFriction(this.ymom);
    }
    
    public static int applyFriction(int mom){
        if(Math.abs(mom) < 2){ //got to stop sliding sometime
            return 0;
        }
        return (mom * 97) / 100;
    }
    
    public void collide(PhysicsEntity e){
        //Collide should be called seperately for both entities involved in collision
        this.xmom = (int) collisionVelocity(this.xmom, e.getXmom(), this.mass, e.getMass());
        this.ymom = (int) collisionVelocity(this.ymom, e.getYmom(), this.mass, e.getMass());
        this.zmom = (int) collisionVelocity(this.zmom, e.getZmom(), this.mass, e.getMass());
        checkFreeze();
    }
    
    public static double collisionVelocity(double v1, double v2, double m1, double m2){
        v1 /= 10; //All these are converted to SI units
        v2 /= 10; //Velocity: m/s
        m1 /= 1000; //Mass: kg
        m2 /= 1000;
        return 10 * ((v1 * (m1 - m2) + 2 * m2 * v2) / (m1 + m2)); //Wikipedia -> Elastic collision (*10 because of unit conversion)
    }
        
    public abstract void tick();
    
    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
    
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
