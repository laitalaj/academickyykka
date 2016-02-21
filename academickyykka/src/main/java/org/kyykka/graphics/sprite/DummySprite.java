package org.kyykka.graphics.sprite;

import org.kyykka.graphics.Drawable;

/**
 * A temporary sprite class used for testing. Always represents temp.png
 *
 * @author Julius Laitala
 */
public class DummySprite implements Sprite {

    @Override
    public String getImgName() {
        return "temp.png";
    }

    @Override
    public void tick() {
    }

}
