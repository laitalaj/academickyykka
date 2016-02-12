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

    public ImageContainer() {
        File imgdir = new File("img");
        File[] imagelist = imgdir.listFiles();
        this.images = new HashMap<>();
        for(File f: imagelist){
            this.images.put(f.getName(), new ImageIcon(f.getPath()).getImage());
        }
    }
    
    public Image getImage(String imgname){
        return this.images.get(imgname);
    }
    
    public Image getImage(Drawable d){
        return getImage(d.getImgName());
    }
    
}
