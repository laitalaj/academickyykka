package org.kyykka.logic.object;

/**
 * Container for parameters for a throw.
 *
 * @author Admin
 */
public class ThrowParams {

    private int angle;
    private int force;
    private double zangle;
    private double spin;

    /**
     * This constructor creates an empty ThrowParams; one should set the angle
     * and force before passing it on.
     */
    public ThrowParams() {
    }

    /**
     * This constructor sets the angle and force - the essential parameters.
     *
     * @param angle angle in degrees to be thrown with (0 is straight ahead)
     * @param force velocity in degrees to be thrown with (mm / cs)
     */
    public ThrowParams(int angle, int force) {
        this.angle = angle;
        this.force = force;
    }

    /**
     * This constructor sets the angle, the force and the z-momentum.
     *
     * @param angle angle in degrees to be thrown with (0 is straight ahead)
     * @param force velocity to be thrown with (mm / cs)
     * @param zangle z-angle of the throw (0 is level with the ground)
     */
    public ThrowParams(int angle, int force, double zangle) {
        this.angle = angle;
        this.force = force;
        this.zangle = zangle;
    }

    /**
     * This constructor sets the angle, the force and the z-momentum.
     *
     * @param angle angle in degrees to be thrown with (0 is straight ahead)
     * @param force velocity to be thrown with (mm / cs)
     * @param zangle z-angle of the throw (0 is level with the ground)
     * @param spin spin of the throw (degrees / cs)
     */
    public ThrowParams(int angle, int force, double zangle, double spin) {
        this.angle = angle;
        this.force = force;
        this.zangle = zangle;
        this.spin = spin;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public void setZangle(double zangle) {
        this.zangle = zangle;
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

    public double getZangle() {
        return zangle;
    }

    public int getAngle() {
        return this.angle;
    }

    public int getForce() {
        return this.force;
    }

    public double getSpin() {
        return spin;
    }

}
