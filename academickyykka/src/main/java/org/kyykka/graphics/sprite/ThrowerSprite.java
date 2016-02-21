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
    
    /**
     * Creates a new ThrowerSprite and links it to a thrower.
     * 
     * @param thrower thrower to link to
     */
    public ThrowerSprite(Thrower thrower) {
        this.thrower = thrower;
    }
    
    
    @Override
    public String getImgName() {
        return "thrower_standby.png";
    }
    
    @Override
    public void tick(){}
    
}
