/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.object;

import org.kyykka.logic.Point;

/**
 *
 * @author Admin
 */
public abstract class Entity {
    
    private Point location;

    public Entity(int x, int y, int z) {
        this.location = new Point(x, y, z);
    }
    
}
