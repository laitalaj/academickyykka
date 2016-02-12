package org.kyykka.logic.object;

/**
 * Container for parameters for a throw.
 *
 * @author Admin
 */
public class ThrowParams {

    private int angle;
    private int force;

    /**
     * This constructor creates an empty ThrowParams; one should set the angle
     * and force before passing it on
     */
    public ThrowParams() {
    }

    /**
     * This constructor sets the angle and force - the essential parameters
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
