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

    public Entity(int x, int y, int z, int width, int height, int depth, int xmom, int ymom, int zmom) {
        this.box = new BoundingBox(x, y, z, width, height, depth);
        this.xmom = xmom;
        this.ymom = ymom;
        this.zmom = zmom;
    }
    
    public Entity(int x, int y, int z, int width, int height, int depth) {
        this(x, y, z, width, height, depth, 0, 0, 0);
    }
    
    public BoundingBox getBoundingBox(){
        return this.box;
    }
    
    public boolean collidesWith(Entity e){
        return this.getBoundingBox().collidesWith(e.getBoundingBox());
    }
    
    public void tick(){
        this.box.moveX(xmom);
        this.box.moveY(ymom);
        this.box.moveZ(zmom);
    }
    
}
