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
import org.kyykka.logic.object.Karttu;

/**
 *
 * @author Admin
 */
public class KarttuSpriteTest {

    private KarttuSprite mainsprite;
    private Karttu mainkarttu;

    public KarttuSpriteTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mainkarttu = new Karttu(0, 0, 0, 0, 0, 0);
        this.mainsprite = new KarttuSprite(this.mainkarttu);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void dummySpriteReturnsTempImage() {
        assertEquals("karttu8.png", this.mainsprite.getImgName());
    }

    @Test
    public void dummySpriteAlphaIs1() {
        assertEquals(1, this.mainsprite.getAlpha(), 0.001);
    }
}
