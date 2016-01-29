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
public class Karttu extends Entity{

    public Karttu(int x, int y, int z, int width, int height, int depth, int mass, int xmom, int ymom, int zmom) {
        super(x, y, z, width, height, depth, mass);
        this.setXmom(xmom);
        this.setYmom(ymom);
        this.setZmom(zmom);
        this.setFrozen(false);
    }
    
    public Karttu(int x, int y, int z, int xmom, int ymom, int zmom){
        this(x, y, z, 850, 80, 80, 2000, xmom, ymom, zmom);
    }

    @Override
    public void tick() {
        applyGravity();
        move();
    }
 
}
