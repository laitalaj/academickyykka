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
    
    /**
     * Parameters for a throw.
     * This constructor creates an empty ThrowParams; one should set the angle 
     * and force before passing it on
     */
    public ThrowParams() {
    }
    
    /**
     * Parameters for a throw.
     * 
     * @param angle angle in degrees to be thrown with (0 is straight ahead)
     * @param force velocity in degrees to be thrown with (mm / cs)
     */
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
        return this.force;
    }
}
