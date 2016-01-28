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
    public void tickMovesCorrectly(){
        this.mainkarttu.tick();
        assertEquals(20, this.mainkarttu.getX());
        assertEquals(20, this.mainkarttu.getY());
        assertEquals(20, this.mainkarttu.getZ());
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
       
}
