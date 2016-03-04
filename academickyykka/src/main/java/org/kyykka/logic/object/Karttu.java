package org.kyykka.logic.object;

import org.kyykka.graphics.sprite.KarttuSprite;
import org.kyykka.logic.shape.HitBox;

/**
 * Karttu is what Kyykka is played with - a wooden "bat" that is thrown at the
 * kyykkas.
 *
 * @author Admin
 */
public class Karttu extends PhysicsEntity {

    private double angle;
    private double spin;

    /**
     * Creates a karttu with very specific parameters and dimensions.
     *
     * @param x x-position of the karttu
     * @param y y-position of the karttu
     * @param z z-position of the karttu
     * @param width x-dimension of the karttu
     * @param height y-dimension of the karttu
     * @param depth z-dimension of the karttu
     * @param mass mass of the karttu
     * @param xmom x-momentum of the karttu
     * @param ymom y-momentum of the karttu
     * @param zmom z-momentum of the karttu
     * @param spin how much the karttu spins each tick
     */
    public Karttu(int x, int y, int z, int width, int height, int depth,
            int mass, int xmom, int ymom, int zmom, double spin) {
        super(x, y, z, width, height, depth, mass);
        this.setXmom(xmom);
        this.setYmom(ymom);
        this.setZmom(zmom);
        this.setFrozen(false);
        this.setSprite(new KarttuSprite(this));
        this.spin = spin;
        this.angle = 0;
    }

    /**
     * This constructor creates a karttu with standard mass and dimensions.
     *
     * @param x x-position of the karttu
     * @param y y-position of the karttu
     * @param z z-position of the karttu
     * @param xmom x-momentum of the karttu
     * @param ymom y-momentum of the karttu
     * @param zmom z-momentum of the karttu
     */
    public Karttu(int x, int y, int z, int xmom, int ymom, int zmom) {
        this(x, y, z, 850, 160, 160, 2000, xmom, ymom, zmom, 0);
    }

    /**
     * This constructor creates a karttu with standard mass and dimensions.
     *
     * @param x x-position of the karttu
     * @param y y-position of the karttu
     * @param z z-position of the karttu
     * @param xmom x-momentum of the karttu
     * @param ymom y-momentum of the karttu
     * @param zmom z-momentum of the karttu
     * @param spin how much the karttu spins each tick
     */
    public Karttu(int x, int y, int z, int xmom, int ymom, int zmom, double spin) {
        this(x, y, z, 850, 160, 160, 2000, xmom, ymom, zmom, spin);
    }

    @Override
    public HitBox getHitBox() {
        HitBox trueBox = super.getHitBox();
        double angleInRadians = Math.toRadians(angle);
        double angleSin = Math.abs(Math.sin(angleInRadians));
        double angleCos = Math.abs(Math.cos(angleInRadians));
        double width = trueBox.getWidth() * angleCos
                + trueBox.getHeight() * angleSin;
        double height = trueBox.getHeight() * angleCos
                + trueBox.getWidth() * angleSin;
        HitBox spunBox = new HitBox(0, 0, 0, (int) width, (int) height, trueBox.getDepth());
        spunBox.setCenter(trueBox.getCenter());
        return spunBox;
    }

    private void checkSpin() {
        if (this.spin < 0.7) {
            this.spin = 0;
        }
    }

    @Override
    public void slide() {
        if (this.getZ() == 0) {
            this.spin *= 0.9;
            checkSpin();
        }
        super.slide();
    }

    @Override
    public void bounce() {
        if (this.getZ() < 0) {
            this.spin *= 0.75;
            checkSpin();
        }
        super.bounce();
    }

    @Override
    public void checkFreeze() {
        super.checkFreeze();
        if (this.spin > 0 && this.isFrozen()) {
            this.setFrozen(false);
        }
    }

    @Override
    public void tick() {
        this.angle += this.spin;
        if (this.angle > 360) {
            this.angle -= 360;
        }
        super.tick();
    }

    public double getAngle() {
        return angle;
    }

    public double getSpin() {
        return spin;
    }

    public void setSpin(double spin) {
        this.spin = spin;
    }

}
