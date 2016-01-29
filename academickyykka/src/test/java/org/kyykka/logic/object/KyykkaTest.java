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
public class KyykkaTest {
    
    private Kyykka mainkyykka;
    
    public KyykkaTest() {
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
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void staticKyykkaFreezes(){
        this.mainkyykka.setFrozen(false);
        this.mainkyykka.tick();
        assertTrue(this.mainkyykka.isFrozen());
    }
    
    @Test
    public void kyykkaBouncesCorrectly(){
        this.mainkyykka.setZ(-1);
        this.mainkyykka.setZmom(-9800);
        this.mainkyykka.bounce();
        assertEquals(6860, this.mainkyykka.getZmom());
    }
    
    /**
     * GENERAL Entity TESTS
     */
    
    @Test
    public void collisionUnfreezes(){
        Karttu karttu = new Karttu(0, 0, 0, 10, 10, 10);
        this.mainkyykka.collide(karttu);
        assertFalse(this.mainkyykka.isFrozen());
    }
    
    @Test
    public void staticCollisionDoesntUnfreeze(){
        Karttu karttu = new Karttu(0, 0, 0, 0, 0, 0);
        this.mainkyykka.collide(karttu);
        assertTrue(this.mainkyykka.isFrozen());
    }
}
