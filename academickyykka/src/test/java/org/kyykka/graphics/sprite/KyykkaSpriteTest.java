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
import org.kyykka.logic.object.Kyykka;

/**
 *
 * @author Admin
 */
public class KyykkaSpriteTest {
    
    private Kyykka mainkyykka;
    private KyykkaSprite mainsprite;
    
    public KyykkaSpriteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mainkyykka = new Kyykka(0, 0, 0);
        this.mainsprite = new KyykkaSprite(mainkyykka);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void dummySpriteReturnsTempImage(){
        assertEquals("kyykka.png", this.mainsprite.getImgName());
    }
    
    @Test
    public void dummySpriteAlphaIs1(){
        assertEquals(1, this.mainsprite.getAlpha(), 0.001);
    }
}
