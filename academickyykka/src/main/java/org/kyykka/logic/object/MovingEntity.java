package org.kyykka.logic.object;

import java.util.HashSet;
import java.util.Set;
import org.kyykka.logic.shape.HitBox;

/**
 * MovingEntity is a box that has momentum and the ability to move.
 *
 * @author Julius Laitala
 */
public abstract class MovingEntity {

    private HitBox box;
    private int xmom;
    private int ymom;
    private int zmom;

    /**
     * Creates an entity with the specified parameters.
     *
     * @param x x-position of the entitys lower bottom left corner
     * @param y y-position of the entitys lower bottom left corner
     * @param z z-position of the entitys lower bottom left corner
     * @param width x-axis dimension of the entity
     * @param height y-axis dimension of the entity
     * @param depth z-axis dimension of the entity
     */
    public MovingEntity(int x, int y, int z, int width, int height, int depth) {
        this.box = new HitBox(x, y, z, width, height, depth);
        this.xmom = 0;
        this.ymom = 0;
        this.zmom = 0;
    }

    /**
     * Tests if this entity collides with another. Two entities collide if their
     * current hitboxes or the hitboxes in places they were in just a moment ago
     * overlap.
     *
     * @param e The entity with which collision is tested
     *
     * @see
     * org.kyykka.logic.shape.HitBox#collidesWith(org.kyykka.logic.shape.HitBox)
     *
     * @return true if the entities collide, false otherwise
     */
    public boolean collidesWith(MovingEntity e) {
        Set<HitBox> hitBoxes1 = this.getHitBoxes();
        Set<HitBox> hitBoxes2 = e.getHitBoxes();
        for (HitBox b : hitBoxes1) {
            if (b.collidesWithAny(hitBoxes2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the entitys position according to its x, y and z-momentum.
     */
    public void move() {
        this.box.moveX(xmom);
        this.box.moveY(ymom);
        this.box.moveZ(zmom);
    }

    /**
     * Calculates the velocity of the entity according to the Pythagoras
     * theorem.
     *
     * @return current velocity of the entity
     */
    public int getVelocity() {
        return (int) Math.sqrt(xmom * xmom + ymom * ymom + zmom * zmom);
    }

    /**
     * Slows the entity down by given fraction. All directional momentums are
     * reduced to (fraction * momentum).
     *
     * @param fraction fraction by which to slow down
     */
    public void slowDown(double fraction) {
        double newXMom = fraction * this.xmom;
        double newYMom = fraction * this.ymom;
        double newZMom = fraction * this.zmom;
        this.setXmom((int) newXMom);
        this.setYmom((int) newYMom);
        this.setZmom((int) newZMom);
    }

    /**
     * Calculates hitboxes in locations that this entity has passed through
     * during previous tick. Is used in order to make sure that this entity
     * won't just pass though other entities like a phantom.
     *
     * @return a set of hitboxes
     */
    public Set<HitBox> getHitBoxes() {
        Set<HitBox> hitBoxes = new HashSet<>();
        hitBoxes.add(this.getHitBox());
        double xratio = Math.abs((double) this.xmom / this.getHitBox().getWidth());
        double yratio = Math.abs((double) this.ymom / this.getHitBox().getHeight());
        double zratio = Math.abs((double) this.zmom / this.getHitBox().getDepth());
        double maxratio = Math.max(xratio, yratio);
        maxratio = Math.max(maxratio, zratio);
        double changeAmount = 1;
        double boxesToAdd = 1;
        while (maxratio > 1) {
            int x = this.getX();
            int y = this.getY();
            int z = this.getZ();
            for (int i = 0; i < boxesToAdd; i++) {
                x -= changeAmount * this.xmom;
                y -= changeAmount * this.ymom;
                z -= changeAmount * this.zmom;
                HitBox b = this.getHitBox().copy();
                b.setX(x);
                b.setY(y);
                b.setZ(z);
                hitBoxes.add(b);
            }
            maxratio /= 2;
            changeAmount /= 2;
            boxesToAdd *= 2;
        }
        return hitBoxes;
    }

    /**
     * Updates the entity by one physics tick. Basically just moves it.
     */
    public void tick() {
        move();
    }

    public HitBox getHitBox() {
        return this.box;
    }

    public void setXmom(int xmom) {
        this.xmom = xmom;
    }

    public void setYmom(int ymom) {
        this.ymom = ymom;
    }

    public void setZmom(int zmom) {
        this.zmom = zmom;
    }

    public int getXmom() {
        return xmom;
    }

    public int getYmom() {
        return ymom;
    }

    public int getZmom() {
        return zmom;
    }

    public int getX() {
        return this.box.getX();
    }

    public int getY() {
        return this.box.getY();
    }

    public int getZ() {
        return this.box.getZ();
    }

    /**
     * Sets the x position of this entity to given value.
     *
     * @see HitBox#setX(int)
     *
     * @param x position to set
     */
    public void setX(int x) {
        this.box.setX(x);
    }

    /**
     * Sets the y position of this entity to given value.
     *
     * @see HitBox#setY(int)
     *
     * @param y position to set
     */
    public void setY(int y) {
        this.box.setY(y);
    }

    /**
     * Sets the z position of this entity to given value.
     *
     * @see HitBox#setZ(int)
     *
     * @param z position to set
     */
    public void setZ(int z) {
        this.box.setZ(z);
    }

    /**
     * Moves the x position of this entity by given value.
     *
     * @see HitBox#moveX(int)
     *
     * @param x amount to move
     */
    public void moveX(int x) {
        this.box.moveX(x);
    }

    /**
     * Moves the y position of this entity by given value.
     *
     * @see HitBox#moveY(int)
     *
     * @param y amount to move
     */
    public void moveY(int y) {
        this.box.moveY(y);
    }

    /**
     * Moves the z position of this entity by given value.
     *
     * @see HitBox#moveZ(int)
     *
     * @param z amount to move
     */
    public void moveZ(int z) {
        this.box.moveZ(z);
    }

    @Override
    public String toString() {
        return this.box.toString();
    }

}
