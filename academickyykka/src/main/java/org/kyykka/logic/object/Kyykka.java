/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.object;

/**
 *
 * @author Admin
 */

public class Kyykka extends Entity{
    
    private boolean frozen;

    public Kyykka(int x, int y, int z) {
        super(x, y, z, 80, 80, 100, 100);
        this.frozen = true;
    }

    @Override
    public void tick() {
        if(!frozen){
            applyGravity();
            move();
            if(getXmom() == 0 && getYmom() == 0 && getZmom() == 0){
                frozen = true;
            }
        }
    }

    @Override
    public void collide(Entity e) {
        if(frozen){
            frozen = false;
        }
        super.collide(e);
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

}
