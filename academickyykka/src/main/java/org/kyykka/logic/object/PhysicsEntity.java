package org.kyykka.logic.object;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.Random;
import org.kyykka.graphics.Drawable;
import org.kyykka.graphics.sprite.DummySprite;
import org.kyykka.graphics.sprite.Sprite;
import org.kyykka.logic.shape.HitBox;

/**
 * PhysicsEntity is an abstract class that handles functionality relevant to
 * physics interaction. In addition to MovingEntity's functionality, the
 * PhysicsEntity can interact with the world and other entities, and can also be
 * drawn as it implements the Drawable interface.
 *
 * @author Julius Laitala
 */
public abstract class PhysicsEntity extends MovingEntity implements Drawable {

    /*
     * GAME UNITS FOR PHYSICS:
     * Tick: 0.01s -> 100 ticks in one second
     * Distance: Millimetres
     * Momentum / velocity: Millimetres in one tick -> millimetres per 0.01s
     * Weight: Grams
     */
    private Random random;
    private int mass;
    private boolean frozen;
    private List<PhysicsEntity> isColliding;
    private List<PhysicsEntity> wasColliding;
    private boolean hasCollided;
    private Sprite sprite;

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
        super(x, y, z, width, height, depth);
        this.random = new Random();
        this.mass = mass;
        this.frozen = true;
        this.isColliding = new ArrayList<>();
        this.wasColliding = new ArrayList<>();
        this.sprite = new DummySprite();
    }

    /**
     * Moves the entitys position according to its x, y and z-momentum, bounces
     * from the ground if necessary, slides along the ground if necessary,
     * freezes the entity if necessary. Does nothing if the entity is frozen.
     *
     * @see MovingEntity#move()
     * @see org.kyykka.logic.object.PhysicsEntity#bounce()
     * @see org.kyykka.logic.object.PhysicsEntity#slide()
     * @see org.kyykka.logic.object.PhysicsEntity#checkFreeze()
     */
    @Override
    public void move() {
        if (!frozen) {
            super.move();
            bounce();
            slide();
            checkFreeze();
        }
    }

    /**
     * Freezes the entity if it's not moving. Unfreezes it if it is.
     */
    public void checkFreeze() {
        if ((getXmom() == 0 && getYmom() == 0 && getZmom() == 0) && getZ() == 0) {
            this.setFrozen(true);
        } else {
            this.setFrozen(false);
        }
    }

    /**
     * Applies gravity. Changes the entitys z-momentum by -1. If the entity is
     * frozen or "underground", does nothing.
     */
    public void applyGravity() {
        if (this.getZ() > 0 && !frozen) {
            this.setZmom(getZmom() - 1);
            //9.81m/s**2 = 0.981 mm/(0.01s)**2 ~ 1 mm/(0.0001s)**2
        }
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
        if (Math.abs(this.getZmom()) < 20) { //No too tiny bounces
            this.setZmom(0);
        } else {
            this.setZmom(-(this.getZmom() * 7) / 10);
        }
    }

    /**
     * Slows the entity down according to friction if it's sliding (if it's
     * z-position is 0).
     *
     * @see org.kyykka.logic.object.PhysicsEntity#applyFriction(int)
     */
    public void slide() {
        if (this.getZ() != 0) {
            return;
        }
        this.setXmom(applyFriction(this.getXmom()));
        this.setYmom(applyFriction(this.getYmom()));
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
        return (mom * 99) / 100;
    }

    /**
     * Performs an semi-elastic collision with another entity; changes the
     * momentums of this entity accordingly (with a bit of randomness). Also
     * checks whether the entity should be frozen. If this entity has been
     * colliding with the other entity during previous ticks, does nothing.
     * Note: This method only changes the calling entitys momentum and should be
     * called seperately for both entities involved in the collision.
     *
     * @param e the entity with which this entity is colliding
     *
     * @see org.kyykka.logic.object.PhysicsEntity#collisionVelocity(double,
     * double, double, double)
     * @see org.kyykka.logic.object.PhysicsEntity#checkFreeze()
     */
    public void collide(PhysicsEntity e) {
        if (isColliding.contains(e)) {
            return;
        } else if (wasColliding.contains(e)) {
            isColliding.add(e);
            return;
        }
        double newxmom = collisionVelocity(this.getXmom(), e.getXmom(), this.mass, e.getMass());
        double newymom = collisionVelocity(this.getYmom(), e.getYmom(), this.mass, e.getMass());
        double newzmom = collisionVelocity(this.getZmom(), e.getZmom(), this.mass, e.getMass());
        this.setXmom((int) newxmom);
        this.setYmom((int) newymom);
        this.setZmom((int) newzmom);
        if (!hasCollided) {
            this.hasCollided = true;
            applyCollisionRandomness();
            slowDown(0.70);
        }
        isColliding.add(e);
        checkFreeze();
    }

    private void applyCollisionRandomness() {
        if (Math.abs(this.getXmom() / 5) > 0) {
            this.setXmom(this.getXmom() + this.random.nextInt(
                    Math.abs(this.getXmom() / 5)) - this.getXmom() / 20);
        }
        if (Math.abs(this.getYmom() / 5) > 0) {
            this.setYmom(this.getYmom() + this.random.nextInt(
                    Math.abs(this.getYmom() / 5)) - this.getYmom() / 20);
        }
        if (Math.abs(this.getZmom() / 5) > 0) {
            this.setZmom(this.getZmom() + this.random.nextInt(
                    Math.abs(this.getZmom() / 5)) - this.getZmom() / 20);
        }
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

    @Override
    public float getAlpha() {
        return this.sprite.getAlpha();
    }

    /**
     * Updates the entity by one physics tick. Applies gravity, moves the entity
     * and updates the sprite.
     *
     * @see org.kyykka.logic.object.PhysicsEntity#applyGravity()
     * @see org.kyykka.logic.object.PhysicsEntity#move()
     * @see PhysicsEntity#updateSprite()
     * @see MovingEntity#tick()
     */
    @Override
    public void tick() {
        this.hasCollided = false;
        this.wasColliding.clear();
        this.wasColliding.addAll(this.isColliding);
        this.isColliding.clear();
        applyGravity();
        move();
        updateSprite();
    }

    /**
     * Ticks the sprite.
     *
     * @see Sprite#tick()
     */
    public void updateSprite() {
        this.sprite.tick();
    }

    protected void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getMass() {
        return mass;
    }

}
