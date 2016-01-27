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
    }
    
    public void applyGravity(){
        if(this.getZ() > 0){
            this.zmom -= 2; //TODO: Decide on exact amount of gravity
        }
    }
    
    public int getVelocity(){
        return (int) Math.sqrt(xmom*xmom + ymom*ymom + zmom*zmom);
    }
    
    public void collide(Entity e){
        //Collide should be called seperately for both entities involved in collision
        int v1 = getVelocity();
        int v2 = e.getVelocity();
        int m1 = getMass();
        int m2 = e.getMass();
        int newvel = (v1 * (m1 - m2) + 2 * m2 * v2) / (m1 * m2); //Wikipedia -> Elastic collision
        double factor = (double) newvel / v1;
        this.xmom *= factor;
        this.ymom *= factor;
        this.zmom *= factor;
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
    
    public int getX(){
        return this.box.getX();
    }
    
    public int getY(){
        return this.box.getY();
    }
    
    public int getZ(){
        return this.box.getZ();
    }

    public int getMass() {
        return mass;
    }
    
}
