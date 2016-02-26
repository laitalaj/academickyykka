/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.graphics;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.graphics.sprite.DummySprite;
import org.kyykka.graphics.sprite.Sprite;

/**
 *
 * @author Admin
 */
public class ImageContainerTest {
    
    private ImageContainer maincontainer;
    
    public ImageContainerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.maincontainer = new ImageContainer();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void imageContainerLoadsAllImages(){
        for(String imgname: ImageContainer.IMGNAMES){
            assertNotNull(this.maincontainer.getImage(imgname));
        }
    }
    
    @Test
    public void imageContainerFindsDrawablesImage(){
        Sprite testsprite = new DummySprite();
        assertNotNull(this.maincontainer.getImage(testsprite));
    }
}
