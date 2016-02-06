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
public class ThrowParams {
    
    private int angle;
    private int force;

    public ThrowParams() {
    }

    public ThrowParams(int angle, int force) {
        this.angle = angle;
        this.force = force;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setForce(int force) {
        this.force = force;
    }
    
    public int getAngle() {
        return this.angle;
    }
    
    public int getForce() {
        return this.angle;
    }
}
