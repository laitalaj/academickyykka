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

public class ThrowerSprite implements Sprite{
    
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
        return imgName;
    }
    
    @Override
    public void tick() {
        int state = this.thrower.getThrowState();
        if(state == 0){
            if(this.thrower.getVelocity() > 0){
                this.counter++;
                if(counter >= 133){
                    counter = 0;
                }
                if(counter < 33){
                    this.imgName = "thrower_standby.png";
                } else if (counter < 66){
                    this.imgName = "thrower_walk0.png";
                } else if (counter < 99){
                    this.imgName = "thrower_standby.png";
                } else if (counter < 133) {
                    this.imgName = "thrower_walk1.png";
                }
            } else {
                this.imgName = "thrower_standby.png";
            }
        } else {
            if(state == 4){
                state = 3;
            }
            this.imgName = "thrower_throw" + (state - 1) + ".png";
        }
    }
    
}
