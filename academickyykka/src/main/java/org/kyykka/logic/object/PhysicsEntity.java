package org.kyykka.logic.object;

import java.util.Objects;
import org.kyykka.graphics.Drawable;
import org.kyykka.graphics.TempSprite;
import org.kyykka.logic.shape.HitBox;
import org.kyykka.logic.shape.Point;

/**
 * PhysicsEntity is an abstract class that handles functionality relevant to
 * physics interaction
 *
 * @author Julius Laitala
 */
public abstract class PhysicsEntity implements Drawable {

    /*
     * GAME UNITS FOR PHYSICS:
     * Tick: 0.01s -> 100 ticks in one second
     * Distance: Millimetres
     * Momentum / velocity: Millimetres in one tick -> millimetres per 0.01s
     * Weight: Grams
     */
    private HitBox box;
    private int xmom;
    private int ymom;
    private int zmom;
    private int mass;
    private boolean frozen;
    //TODO: Make this drawing crap better
    private Drawable sprite;

    /**
     * Creates an entity with the specified parameters.
     *
     * @param x x-position of the entitys lower bottom left corner
     * @param y y-position of the entitys lower bottom left corner
     * @param z z-position of the entitys lower bottom left corner
     * @param width x-axis dimension of the entity
     * @param height y-axis dimension of the entity
     * @param depth z-axis dimension of the entity
     * @param mass mass of the entity, relevant to collisions
     */
    public PhysicsEntity(int x, int y, int z, int width, int height, int depth, int mass) {
        this.box = new HitBox(x, y, z, width, height, depth);
        this.mass = mass;
        this.xmom = 0;
        this.ymom = 0;
        this.zmom = 0;
        this.frozen = true;
        this.sprite = new TempSprite();
    }

    /**
     * Tests if this entity collides with another, e.g. if their hitboxes
     * overlap
     *
     * @param e The entity with which collision is tested
     *
     * @see
     * org.kyykka.logic.shape.HitBox#collidesWith(org.kyykka.logic.shape.HitBox)
     *
     * @return true if the entities collide, false otherwise
     */
    public boolean collidesWith(PhysicsEntity e) {
        return this.getBoundingBox().collidesWith(e.getBoundingBox());
    }

    /**
     * Moves the entitys position according to its x, y and z-momentum, bounces
     * from the ground if necessary, slides along the ground if necessary,
     * freezes the entity if necessary. Does nothing if the entity is frozen.
     *
     * @see org.kyykka.logic.object.PhysicsEntity#bounce()
     * @see org.kyykka.logic.object.PhysicsEntity#slide()
     * @see org.kyykka.logic.object.PhysicsEntity#checkFreeze()
     */
    public void move() {
        if (!frozen) {
            this.box.moveX(xmom);
            this.box.moveY(ymom);
            this.box.moveZ(zmom);
            bounce();
            slide();
            checkFreeze();
        }
    }

    /**
     * Freezes the entity if it's not moving. Unfreezes it if it is.
     */
    public void checkFreeze() {
        if ((getXmom() == 0 && getYmom() == 0 && getZmom() == 0)) {
            this.setFrozen(true);
        } else {
            this.setFrozen(false);
        }
    }

    /**
     * Applies gravity, e.g. changes z-momentum by -1. If the entity is frozen
     * or "underground", does nothing.
     */
    public void applyGravity() {
        //TODO: Terminal velocity?
        if (this.getZ() > 0 && !frozen) {
            this.zmom -= 1; //9.81m/s**2 = 0.981 mm/(0.01s)**2 ~ 1 mm/(0.0001s)**2
        }
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
     * Bounces the entity off the ground if necessary (if it's "underground")
     * Basically just sets z to 0, changes z-momentum to opposite direction and
     * decreases it a little bit. Does nothing if the entity is above ground.
     */
    public void bounce() {
        if (this.getZ() >= 0) {
            return;
        }
        this.setZ(0);
        if (Math.abs(this.zmom) < 20) { //No too tiny bounces
            this.zmom = 0;
        } else {
            this.zmom *= -0.7;
        }
    }

    /**
     * Slows the entity down according to friction if it's sliding (if it's
     * z-position is 0)
     *
     * @see org.kyykka.logic.object.PhysicsEntity#applyFriction(int)
     */
    public void slide() {
        if (this.getZ() != 0) {
            return;
        }
        this.xmom = applyFriction(this.xmom);
        this.ymom = applyFriction(this.ymom);
    }

    /**
     * Applies friction to a given momentum. Stops sliding if momentum is too
     * low
     *
     * @param mom the momentum to be applied friction to
     *
     * @return momentum after applying friction
     */
    public static int applyFriction(int mom) {
        if (Math.abs(mom) < 2) { //got to stop sliding sometime
            return 0;
        }
        return (mom * 97) / 100;
    }

    /**
     * Performs an elastic collision with another entity; changes the momentums
     * of this entity accordingly. Also checks whether the entity should be
     * frozen Note: This method only changes the calling entitys momentum and
     * should be called seperately for both entities involved in the collision
     *
     * @param e the entity with which this entity is colliding
     *
     * @see org.kyykka.logic.object.PhysicsEntity#collisionVelocity(double,
     * double, double, double)
     * @see org.kyykka.logic.object.PhysicsEntity#checkFreeze()
     */
    public void collide(PhysicsEntity e) {
        this.xmom = (int) collisionVelocity(this.xmom, e.getXmom(), this.mass, e.getMass());
        this.ymom = (int) collisionVelocity(this.ymom, e.getYmom(), this.mass, e.getMass());
        this.zmom = (int) collisionVelocity(this.zmom, e.getZmom(), this.mass, e.getMass());
        checkFreeze();
    }

    /**
     * Calculates the new velocity of an object with the speed v1 and mass m1
     * after an elastic collision with an object with speed v2 and mass m2.
     *
     * @param v1 velocity of entity 1 (mm / cs)
     * @param v2 velocity of entity 2 (mm / cs)
     * @param m1 mass of entity 1 (grams)
     * @param m2 mass of entity 2 (grams)
     *
     * @return new v1
     */
    public static double collisionVelocity(double v1, double v2, double m1, double m2) {
        v1 /= 10; //All these are converted to SI units
        v2 /= 10; //Velocity: m/s
        m1 /= 1000; //Mass: kg
        m2 /= 1000;
        return 10 * ((v1 * (m1 - m2) + 2 * m2 * v2) / (m1 + m2)); //Wikipedia -> Elastic collision (*10 because of unit conversion)
    }

    @Override
    public String getImgName() {
        return this.sprite.getImgName();
    }

    /**
     * Tick updates the entity. Should be called once per game physics tick.
     */
    public abstract void tick();

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public HitBox getBoundingBox() {
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

    public void setX(int x) {
        this.box.setX(x);
    }

    public void setY(int y) {
        this.box.setY(y);
    }

    public void setZ(int z) {
        this.box.setZ(z);
    }

    public int getMass() {
        return mass;
    }
    
    @Override
    public String toString(){
        return this.box.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.box);
        hash = 97 * hash + this.xmom;
        hash = 97 * hash + this.ymom;
        hash = 97 * hash + this.zmom;
        hash = 97 * hash + this.mass;
        hash = 97 * hash + (this.frozen ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhysicsEntity other = (PhysicsEntity) obj;
        if (!Objects.equals(this.box, other.box)) {
            return false;
        }
        if (this.xmom != other.xmom) {
            return false;
        }
        if (this.ymom != other.ymom) {
            return false;
        }
        if (this.zmom != other.zmom) {
            return false;
        }
        if (this.mass != other.mass) {
            return false;
        }
        if (this.frozen != other.frozen) {
            return false;
        }
        return true;
    }

}
