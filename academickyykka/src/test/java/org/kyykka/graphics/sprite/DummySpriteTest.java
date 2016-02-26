/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.graphics.sprite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class DummySpriteTest {
    
    private DummySprite mainsprite;
    
    public DummySpriteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mainsprite = new DummySprite();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void dummySpriteReturnsTempImage(){
        assertEquals("temp.png", this.mainsprite.getImgName());
    }
    
    @Test
    public void dummySpriteAlphaIs1(){
        assertEquals(1, this.mainsprite.getAlpha(), 0.001);
    }
}
