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
import org.kyykka.logic.shape.Point;

/**
 *
 * @author Admin
 */
public class ThrowerTest {
    
    private Thrower mainthrower;
    
    public ThrowerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mainthrower = new Thrower(20, 20);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void throwKarttuCreatesKarttuWithCorrectSpeed(){
        Karttu karttu = this.mainthrower.throwKarttu(20, 50);
        assertEquals(46, karttu.getYmom());
        assertEquals(17, karttu.getXmom());
        assertEquals(0, karttu.getZmom());
    }
    
    @Test
    public void throwKarttuCreatesKarttuWithCorrectSpeedIfAngleIsNegative(){
        Karttu karttu = this.mainthrower.throwKarttu(-20, 50);
        assertEquals(46, karttu.getYmom());
        assertEquals(-17, karttu.getXmom());
        assertEquals(0, karttu.getZmom());
    }
    
    @Test
    public void throwKarttuCreatesKarttuWithCorrectPos(){
        Karttu karttu = this.mainthrower.throwKarttu(-70, 40);
        Point center = this.mainthrower.getBoundingBox().getCenter();
        assertEquals(center.getY(), karttu.getY());
        assertEquals(center.getX(), karttu.getX());
        assertEquals(center.getZ(), karttu.getZ());
    }
    
    @Test
    public void calculateNextSpeedReturnsMaximumWhenDistanceLong(){
        this.mainthrower.setTarget(20000, 20000);
        assertEquals(40, this.mainthrower.calculateNextSpeed());
    }
    
    @Test
    public void calculateNextSpeedReturnsCorrectSpeedWhenDistanceShort(){
        this.mainthrower.setTarget(-300, -300);
        assertEquals(8, this.mainthrower.calculateNextSpeed());
    }
    
    @Test
    public void calculateNextSpeedMakesSureThrowerWontMissTarget(){
        Point center = this.mainthrower.getBoundingBox().getBottomCenter();
        this.mainthrower.setTarget(center.getX() + 3, center.getY());
        assertEquals(3, this.mainthrower.calculateNextSpeed());
    }
    
    @Test
    public void updateSpeedWontChangeSpeedIfAtTarget(){
        this.mainthrower.updateSpeed();
        assertEquals(0, this.mainthrower.getXmom());
        assertEquals(0, this.mainthrower.getYmom());
    }
    
    @Test
    public void updateSetSpeedToZeroIfAtTarget(){
        this.mainthrower.setXmom(200);
        this.mainthrower.setYmom(-200);
        this.mainthrower.updateSpeed();
        assertEquals(0, this.mainthrower.getXmom());
        assertEquals(0, this.mainthrower.getYmom());
    }
    
    @Test
    public void updateSpeedResultsInCorrectMomentum(){
        this.mainthrower.setTarget(-30000, 20000);
        this.mainthrower.updateSpeed();
        assertEquals(-33, this.mainthrower.getXmom());
        assertEquals(22, this.mainthrower.getYmom());
    }
    
    @Test
    public void updateSpeedResultsInCorrectMomentum2(){
        this.mainthrower.setTarget(20000, 20000);
        this.mainthrower.updateSpeed();
        assertEquals(28, this.mainthrower.getXmom());
        assertEquals(28, this.mainthrower.getYmom());
    }
    
    @Test
    public void allSpeedGoesToXWhenYIsAlreadyAtTargetLevel(){
        this.mainthrower.setXmom(-99);
        this.mainthrower.setYmom(-120);
        this.mainthrower.setTarget(20000, 20);
        this.mainthrower.updateSpeed();
        assertEquals(40, this.mainthrower.getXmom());
        assertEquals(0, this.mainthrower.getYmom());
    }
    
    @Test
    public void allSpeedGoesToYWhenXIsAlreadyAtTargetLevel(){
        this.mainthrower.setXmom(-99);
        this.mainthrower.setYmom(120);
        this.mainthrower.setTarget(20, 20000);
        this.mainthrower.updateSpeed();
        assertEquals(0, this.mainthrower.getXmom());
        assertEquals(40, this.mainthrower.getYmom());
    }
    
    @Test
    public void tickMovesCorrectly(){
        this.mainthrower.setTarget(700, -200);
        this.mainthrower.tick();
        assertEquals(27, this.mainthrower.getX());
        assertEquals(18, this.mainthrower.getY());
    }
    
    @Test
    public void noMomentumAfterReachingTarget(){
        this.mainthrower.setTarget(700, -200);
        this.mainthrower.tick();
        this.mainthrower.setTarget(27, 18);
        this.mainthrower.tick();
        assertEquals(0, this.mainthrower.getXmom());
        assertEquals(0, this.mainthrower.getYmom());
    }
}
