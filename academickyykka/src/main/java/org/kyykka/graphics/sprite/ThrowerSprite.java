package org.kyykka.graphics.sprite;

import org.kyykka.graphics.Drawable;
import org.kyykka.logic.object.Thrower;

/**
 * A Drawable that returns images corresponding to a Throwers state.
 *
 * @see Drawable
 * @see Thrower
 *
 * @author Admin
 */
public class ThrowerSprite implements Sprite {

    private Thrower thrower;
    private String imgName;
    private int counter;

    /**
     * Creates a new ThrowerSprite and links it to a thrower.
     *
     * @param thrower thrower to link to
     */
    public ThrowerSprite(Thrower thrower) {
        this.thrower = thrower;
        this.counter = 0;
        this.imgName = "thrower_standby.png";
    }

    @Override
    public String getImgName() {
        if (this.thrower.isHomeTeam()) {
            return imgName;
        } else {
            return "away_" + imgName;
        }
    }

    @Override
    public float getAlpha() {
        if (this.thrower.getThrowState() == 0) {
            return 1;
        } else {
            return 0.6f;
        }
    }

    @Override
    public void tick() {
        int state = this.thrower.getThrowState();
        if (state == 0) {
            if (this.thrower.getVelocity() > 0) {
                this.counter++;
                if (counter >= 60) {
                    counter = 0;
                }
                if (counter < 15) {
                    this.imgName = "thrower_standby.png";
                } else if (counter < 30) {
                    this.imgName = "thrower_walk0.png";
                } else if (counter < 45) {
                    this.imgName = "thrower_standby.png";
                } else if (counter < 60) {
                    this.imgName = "thrower_walk1.png";
                }
            } else {
                this.imgName = "thrower_standby.png";
            }
        } else {
            if (state == 4) {
                state = 3;
            }
            this.imgName = "thrower_throw" + (state - 1) + ".png";
        }
    }

}
