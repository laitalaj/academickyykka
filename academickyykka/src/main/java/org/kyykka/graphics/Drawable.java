package org.kyykka.graphics;

/**
 * Interface for classes that can be drawn. Used for interaction between
 * PhysicsEntities, GamePainter and ImageContainer
 *
 * @author Julius Laitala
 */
public interface Drawable {

    /**
     * Should return the name of the image file that is wanted to represent the
     * drawable object. The image file must be present in the img folder.
     *
     * @return image name with file extension (not path)
     */
    public String getImgName();
    
    /**
     * Should return the amount of transparency the image is to be drawn with.
     * 1 means fully opaque, 0 is completely transparent.
     * 
     * @return amount of transparency
     */
    public float getAlpha();
}
