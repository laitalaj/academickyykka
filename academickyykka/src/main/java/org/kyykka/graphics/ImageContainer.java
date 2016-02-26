package org.kyykka.graphics;

import java.awt.Image;
import java.io.File;
import java.net.URL;
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
    private static final String[] IMGNAMES = new String[]{
        "away_thrower_standby.png", "away_thrower_throw0.png",
        "away_thrower_throw1.png", "away_thrower_throw2.png",
        "away_thrower_walk0.png", "away_thrower_walk1.png",
        "karttu0.png", "karttu1.png", "karttu2.png", "karttu8.png",
        "kyykka.png", "temp.png", "thrower_standby.png",
        "thrower_throw0.png", "thrower_throw1.png",
        "thrower_throw2.png", "thrower_walk0.png",
        "thrower_walk1.png"
    };

    /**
     * Creates a new ImageContainer and loads images.
     */
    public ImageContainer() {
        this.images = new HashMap<>();
        for(String name: IMGNAMES){
            URL location = ImageContainer.class.getResource("/img/" + name);
            this.images.put(name, new ImageIcon(location).getImage());
        }
//        File imgdir = new File("img");
//        File[] imagelist = imgdir.listFiles();
//        for (File f : imagelist) {
//            System.out.println(f.getName());
//            this.images.put(f.getName(), new ImageIcon(f.getPath()).getImage());
//        }
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
