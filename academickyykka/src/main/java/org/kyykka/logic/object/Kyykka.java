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

public class Kyykka extends PhysicsEntity{
    
    /**
     * Kyykka is a small, wooden piece that one is supposed to knock away with a
     * karttu. The Kyykka class is a very barebones copy of PhysicsEntity
     * 
     * @param x x-position of the kyykka
     * @param y y-position of the kyykka
     * @param z z-position of the kyykka
     */
    public Kyykka(int x, int y, int z) {
        super(x, y, z, 80, 80, 100, 100);
    }
    
    /**
     * Updates the kyykka by one physics tick.
     * Applies gravity and moves the kyykka.
     * 
     * @see org.kyykka.logic.object.PhysicsEntity#applyGravity() 
     * @see org.kyykka.logic.object.PhysicsEntity#move() 
     */
    @Override
    public void tick() {
        this.applyGravity();
        this.move();
    }

}
