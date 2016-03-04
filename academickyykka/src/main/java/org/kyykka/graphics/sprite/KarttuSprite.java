package org.kyykka.graphics.sprite;

import org.kyykka.graphics.Drawable;
import org.kyykka.logic.object.Karttu;

/**
 * A Drawable that returns images corresponding to a Karttus state.
 *
 * @see Drawable
 * @see Karttu
 *
 * @author Admin
 */
public class KarttuSprite implements Sprite {

    private Karttu karttu;

    /**
     * Creates a new KarttuSprite and links it to a karttu.
     *
     * @param karttu karttu to link to
     */
    public KarttuSprite(Karttu karttu) {
        this.karttu = karttu;
    }

    @Override
    public String getImgName() {
        if (this.karttu.getAngle() > 90 && this.karttu.getAngle() < 270) {
            return "karttu0.png";
        }
        return "karttu8.png";
    }

    @Override
    public void tick() {
    }

    @Override
    public float getAlpha() {
        return 1;
    }

}
