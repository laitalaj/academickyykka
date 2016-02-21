package org.kyykka.graphics.sprite;

import org.kyykka.graphics.Drawable;
import org.kyykka.logic.object.Kyykka;

/**
 * A Drawable that returns images corresponding to a Kyykkas state.
 * 
 * @see Drawable
 * @see Kyykka
 * 
 * @author Admin
 */

public class KyykkaSprite implements Sprite{
    
    private Kyykka kyykka;
    
    /**
     * Creates a new KyykkaSprite and links it to a kyykka.
     * 
     * @param kyykka kyykka to link to
     */
    public KyykkaSprite(Kyykka kyykka) {
        this.kyykka = kyykka;
    } 
    
    @Override
    public String getImgName() {
        return "kyykka.png";
    }
    
    @Override
    public void tick(){}
    
}