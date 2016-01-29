/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.object;

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
public class KarttuTest {
    
    private Karttu mainkarttu;
    
    public KarttuTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mainkarttu = new Karttu(0, 0, 0, 20, 20, 20);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void constructorSetsMomentum(){
        assertEquals(20, this.mainkarttu.getXmom());
        assertEquals(20, this.mainkarttu.getYmom());
        assertEquals(20, this.mainkarttu.getZmom());
    }

    @Test
    public void tickMovesCorrectly(){
        this.mainkarttu.tick();
        assertEquals(20, this.mainkarttu.getX());
        assertEquals(20, this.mainkarttu.getY());
        assertEquals(20, this.mainkarttu.getZ());
    }
    
    @Test
    public void karttuBouncesCorrectly(){
        this.mainkarttu.setZmom(-100);
        this.mainkarttu.tick();
        assertEquals(70, this.mainkarttu.getZmom());
    }
    
    @Test
    public void karttuDoesntBounceIfZIsZero(){
        this.mainkarttu.setZ(101); //Account for gravity
        this.mainkarttu.setZmom(-100);
        this.mainkarttu.tick();
        assertEquals(-101, this.mainkarttu.getZmom());
        this.mainkarttu.bounce();
        assertEquals(-101, this.mainkarttu.getZmom());
    }
    
    /**
     * TESTS FOR MOSTLY Entity FUNCTIONALITY
     */
    
    @Test
    public void collisionWorks(){
        Kyykka kyykka = new Kyykka(0, 0, 0);
        assertTrue(this.mainkarttu.collidesWith(kyykka));
    }
    
    @Test
    public void noFalseCollision(){
        Kyykka kyykka = new Kyykka(-2000, 2000, 2000);
        assertFalse(this.mainkarttu.collidesWith(kyykka));
    }
    
    @Test
    public void getVelocityReturnsCorrectVelocity(){
        assertEquals(34, this.mainkarttu.getVelocity());
    }
    
    @Test
    public void collisionWithStaticObjectModifiesMomentumCorrectly(){
        Kyykka kyykka = new Kyykka(0, 0, 0);
        this.mainkarttu.collide(kyykka);
        assertEquals(18, this.mainkarttu.getXmom());
        assertEquals(18, this.mainkarttu.getYmom());
        assertEquals(18, this.mainkarttu.getZmom());
    }
    
    @Test
    public void bounceResetsZPos(){
        this.mainkarttu.setZmom(-100);
        this.mainkarttu.setZ(-1);
        this.mainkarttu.bounce();
        assertEquals(0, this.mainkarttu.getZ());
    }
    
    @Test
    public void entityWontSlideIfZIsntZero(){
        this.mainkarttu.setZmom(0);
        this.mainkarttu.setZ(5);
        this.mainkarttu.slide();
        assertEquals(20, this.mainkarttu.getXmom());
        assertEquals(20, this.mainkarttu.getYmom());
    }
    
    @Test
    public void entitySlidesCorrectly(){
        this.mainkarttu.setZmom(0);
        this.mainkarttu.setZ(0);
        this.mainkarttu.slide();
        assertEquals(19, this.mainkarttu.getXmom());
        assertEquals(19, this.mainkarttu.getYmom());
    }
    
    @Test
    public void gravityWorksCorrectly(){
        this.mainkarttu.setZ(1);
        this.mainkarttu.applyGravity();
        assertEquals(19, this.mainkarttu.getZmom());
    }
    
}
