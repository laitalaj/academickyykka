package org.kyykka.logic.object;

/**
 * Kyykka is a small, wooden piece that one is supposed to knock away with a
 * karttu. The Kyykka class is a very barebones copy of PhysicsEntity
 *
 * @author Julius Laitala
 */
public class Kyykka extends PhysicsEntity {

    private Kyykka link;

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
     * Links the kyykka to another kyykka. A linked kyykka unfreezes whenever
     * the kyykka it is linked to unfreezes.
     *
     * @param link the kyykka this kyykka will be linked to
     */
    public void setLink(Kyykka link) {
        this.link = link;
    }

    /**
     * Advances the kyykka by one physics tick. Also checks whether the possible
     * linked kyykka has unfrozen.
     *
     * @see setLink()
     * @see PhysicsEntity#tick()
     */
    @Override
    public void tick() {
        if (this.link != null) {
            if (!this.link.isFrozen()) {
                this.setFrozen(false);
            }
        }
        super.tick();
    }

}
