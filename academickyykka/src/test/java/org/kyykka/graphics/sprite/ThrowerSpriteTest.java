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
import org.kyykka.logic.object.Thrower;

/**
 *
 * @author Admin
 */
public class ThrowerSpriteTest {
    
    private Thrower mainthrower;
    private ThrowerSprite mainsprite;
    
    public ThrowerSpriteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mainthrower = new Thrower(0, 0, true);
        this.mainsprite = new ThrowerSprite(mainthrower);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void throwerSpriteReturnsCorrectImageWhenThrowstate0(){
        this.mainsprite.tick();
        assertEquals("thrower_standby.png", this.mainsprite.getImgName());
    }
    
    @Test
    public void throwerSpriteAlphaIsCorrectWhenThrowstate0(){
        assertEquals(1, this.mainsprite.getAlpha(), 0.001);
    }
    
    @Test
    public void throwerSpriteReturnsCorrectImageWhenThrowstate1(){
        this.mainthrower.setThrowState(1);
        this.mainsprite.tick();
        assertEquals("thrower_throw0.png", this.mainsprite.getImgName());
    }
    
    @Test
    public void throwerSpriteAlphaIsCorrectWhenThrowstate1(){
        this.mainthrower.setThrowState(1);
        assertEquals(0.6, this.mainsprite.getAlpha(), 0.001);
    }
    
    @Test
    public void throwerSpriteReturnsCorrectImageWhenThrowstate2(){
        this.mainthrower.setThrowState(2);
        this.mainsprite.tick();
        assertEquals("thrower_throw1.png", this.mainsprite.getImgName());
    }
    
    @Test
    public void throwerSpriteAlphaIsCorrectWhenThrowstate2(){
        this.mainthrower.setThrowState(2);
        assertEquals(0.6, this.mainsprite.getAlpha(), 0.001);
    }
    
    @Test
    public void throwerSpriteReturnsCorrectImageWhenThrowstate3or4(){
        this.mainthrower.setThrowState(3);
        this.mainsprite.tick();
        assertEquals("thrower_throw2.png", this.mainsprite.getImgName());
        this.mainthrower.setThrowState(4);
        this.mainsprite.tick();
        assertEquals("thrower_throw2.png", this.mainsprite.getImgName());
    }
    
    @Test
    public void throwerSpriteAlphaIsCorrectWhenThrowstate3or4(){
        this.mainthrower.setThrowState(3);
        assertEquals(0.6, this.mainsprite.getAlpha(), 0.001);
        this.mainthrower.setThrowState(4);
        assertEquals(0.6, this.mainsprite.getAlpha(), 0.001);
    }
    
    @Test
    public void walkAnimationWorksCorrectly(){
        this.mainthrower.setXmom(9);
        for(int i = 0; i < 14; i++){
            this.mainsprite.tick();
        }
        assertEquals("thrower_standby.png", this.mainsprite.getImgName());
        this.mainsprite.tick();
        assertEquals("thrower_walk0.png", this.mainsprite.getImgName());
        for(int i = 0; i < 14; i++){
            this.mainsprite.tick();
        }
        assertEquals("thrower_walk0.png", this.mainsprite.getImgName());
        this.mainsprite.tick();
        assertEquals("thrower_standby.png", this.mainsprite.getImgName());
        for(int i = 0; i < 14; i++){
            this.mainsprite.tick();
        }
        assertEquals("thrower_standby.png", this.mainsprite.getImgName());
        this.mainsprite.tick();
        assertEquals("thrower_walk1.png", this.mainsprite.getImgName());
        for(int i = 0; i < 14; i++){
            this.mainsprite.tick();
        }
        assertEquals("thrower_walk1.png", this.mainsprite.getImgName());
        this.mainsprite.tick();
        assertEquals("thrower_standby.png", this.mainsprite.getImgName());
    }
    
    @Test
    public void awayThrowerGetsAwayImage(){
        this.mainthrower.setHomeTeam(false);
        assertEquals("away_thrower_standby.png", this.mainsprite.getImgName());
    }
}
