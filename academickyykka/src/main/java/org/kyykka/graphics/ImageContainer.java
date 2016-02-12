/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.graphics;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author Admin
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
