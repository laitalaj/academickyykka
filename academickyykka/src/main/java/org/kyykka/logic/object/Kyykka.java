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
        super(x, y, z, 8, 8, 10, 100);
        this.frozen = true;
    }

    @Override
    public void tick() {
        if(!frozen){
            applyGravity();
        }
        move();
    }

    @Override
    public void collide(Entity e) {
        if(frozen){
            frozen = false;
        }
        super.collide(e);
    }

}