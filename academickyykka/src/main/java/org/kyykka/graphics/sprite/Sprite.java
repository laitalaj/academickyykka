package org.kyykka.graphics.sprite;

import org.kyykka.graphics.Drawable;

/**
 * Sprite is a drawable which images may update depending on game state changes.
 * 
 * @author Admin
 */
public interface Sprite extends Drawable {
    
    /**
     * Tick should update the sprites information. Should be called once per
     * physics tick.
     */
    public void tick();
    
}
