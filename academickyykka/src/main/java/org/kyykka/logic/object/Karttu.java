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
    }

    @Override
    public void tick() {
        applyGravity();
        move();
        //TODO: Stay aboveground (to entity)
    }

    @Override
    public void collide(Entity e) {
        throw new UnsupportedOperationException("");
    }
 
}
