package org.kyykka.graphics;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * Preloads images from the img folder and stores them.
 *
 * @author Julius Laitala
 */
public class ImageContainer {

    private Map<String, Image> images;
    
    /**
     * Creates a new ImageContainer and loads images.
     */
    public ImageContainer() {
        File imgdir = new File("img");
        File[] imagelist = imgdir.listFiles();
        this.images = new HashMap<>();
        for (File f : imagelist) {
            this.images.put(f.getName(), new ImageIcon(f.getPath()).getImage());
        }
    }
    
    /**
     * Fetches an image with the specified name.
     * 
     * @param imgname name of the image (with file extension)
     * 
     * @return image corresponding to the name
     */
    public Image getImage(String imgname) {
        return this.images.get(imgname);
    }
    
    /**
     * Returns an image associated with a drawable.
     * 
     * @param d drawable which the image should represent
     * 
     * @see Drawable#getImgName()
     * 
     * @return image corresponding to the drawable
     */
    public Image getImage(Drawable d) {
        return getImage(d.getImgName());
    }

}
