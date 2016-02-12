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

}
