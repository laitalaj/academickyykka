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
    }

    @Override
    public void tick() {
        this.applyGravity();
        this.move();
    }

}
