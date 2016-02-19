package org.kyykka.logic.object;

/**
 * Karttu is what Kyykka is played with - a wooden "bat" that is thrown at the
 * kyykkas.
 *
 * @author Admin
 */
public class Karttu extends PhysicsEntity {

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
     */
    public Karttu(int x, int y, int z, int width, int height, int depth, int mass, int xmom, int ymom, int zmom) {
        super(x, y, z, width, height, depth, mass);
        this.setXmom(xmom);
        this.setYmom(ymom);
        this.setZmom(zmom);
        this.setFrozen(false);
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
        this(x, y, z, 850, 80, 80, 2000, xmom, ymom, zmom);
    }

}
