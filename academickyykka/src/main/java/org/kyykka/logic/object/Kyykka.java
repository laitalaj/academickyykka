package org.kyykka.logic.object;

/**
 * Kyykka is a small, wooden piece that one is supposed to knock away with a
 * karttu. The Kyykka class is a very barebones copy of PhysicsEntity
 *
 * @author Julius Laitala
 */
public class Kyykka extends PhysicsEntity {

    /**
     * Creates a kyykka with a specific position.
     *
     * @param x x-position of the kyykka
     * @param y y-position of the kyykka
     * @param z z-position of the kyykka
     */
    public Kyykka(int x, int y, int z) {
        super(x, y, z, 160, 160, 200, 100); //Double-sized kyykkas for visibility
    }

    /**
     * Updates the kyykka by one physics tick. Applies gravity and moves the
     * kyykka.
     *
     * @see org.kyykka.logic.object.PhysicsEntity#applyGravity()
     * @see org.kyykka.logic.object.PhysicsEntity#move()
     */
    @Override
    public void tick() {
        this.applyGravity();
        this.move();
    }

}
