/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.util.List;
import java.util.Set;
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
public class KyykkaContainerTest {

    private KyykkaContainer maincontainer;

    public KyykkaContainerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.maincontainer = new KyykkaContainer(true);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void containerContainsCorrectNumberOfKyykkas() {
        assertEquals(40, this.maincontainer.getKyykkas().size());
    }

    @Test
    public void kyykkaPositionsAreCorrectForHome() {
        List<Kyykka> kyykkas = this.maincontainer.getKyykkas();
        for (Kyykka k : kyykkas) {
            assertEquals(15000, k.getY());
            assertTrue(k.getZ() == 0 || k.getZ() == 201);
            assertTrue(k.getX() > 120 && k.getX() < 4880);
        }
    }

    @Test
    public void kyykkaPositionsAreCorrectForAway() {
        KyykkaContainer container = new KyykkaContainer(false);
        List<Kyykka> kyykkas = container.getKyykkas();
        for (Kyykka k : kyykkas) {
            assertEquals(4840, k.getY());
            assertTrue(k.getZ() == 0 || k.getZ() == 201);
            assertTrue(k.getX() > 120 && k.getX() < 4880);
        }
    }

    @Test
    public void outOfBoundsWorksAway() {
        this.maincontainer.setHomeTeam(false);
        Kyykka k = new Kyykka(-200, 30, 30);
        assertTrue(this.maincontainer.isOutOfBounds(k));
    }

    @Test
    public void noFalseOutOfBoundsAway() {
        this.maincontainer.setHomeTeam(false);
        Kyykka k = new Kyykka(200, 100, 30);
        assertFalse(this.maincontainer.isOutOfBounds(k));
    }

    @Test
    public void outOfBoundsWorksHome() {
        Kyykka k = new Kyykka(30, 21000, 2000);
        assertTrue(this.maincontainer.isOutOfBounds(k));
    }
    
    @Test
    public void outOfBoundsWorksHome2() {
        Kyykka k = new Kyykka(30, 14000, 2000);
        assertTrue(this.maincontainer.isOutOfBounds(k));
    }

    @Test
    public void noFalseOutOfBoundsHome() {
        Kyykka k = new Kyykka(10, 15100, 100000);
        assertFalse(this.maincontainer.isOutOfBounds(k));
    }
    
    @Test
    public void initialPointsIsMinus80Home(){
        assertEquals(-80, this.maincontainer.calculateScore());
    }
    
    @Test
    public void initialPointsIsMinus80Away(){
        KyykkaContainer awaycontainer = new KyykkaContainer(false);
        assertEquals(-80, awaycontainer.calculateScore());
    }
    
    @Test
    public void scoreForKyykkaOnLeftEdgeIsMinus1Home(){
        Kyykka k = new Kyykka(4900, 17000, 0);
        assertEquals(-1, this.maincontainer.calculateKyykkasPoints(k));
    }
    
    @Test
    public void scoreForKyykkaOnLeftEdgeIsMinus1Away(){
        Kyykka k = new Kyykka(0, 1000, 0);
        this.maincontainer.setHomeTeam(false);
        assertEquals(-1, this.maincontainer.calculateKyykkasPoints(k));
    }
    
    @Test
    public void scoreForKyykkaOnRightEdgeIsMinus1Home(){
        Kyykka k = new Kyykka(2, 19200, 0);
        assertEquals(-1, this.maincontainer.calculateKyykkasPoints(k));
    }
    
    @Test
    public void scoreForKyykkaOnRightEdgeIsMinus1Away(){
        Kyykka k = new Kyykka(4850, 4100, 0);
        this.maincontainer.setHomeTeam(false);
        assertEquals(-1, this.maincontainer.calculateKyykkasPoints(k));
    }
    
    @Test
    public void scoreForKyykkaOnBackEdgeIsMinus1Home(){
        Kyykka k = new Kyykka(2500, 19910, 0);
        assertEquals(-1, this.maincontainer.calculateKyykkasPoints(k));
    }
    
    @Test
    public void scoreForKyykkaOnBackEdgeIsMinus1Away(){
        Kyykka k = new Kyykka(4000, 0, 0);
        this.maincontainer.setHomeTeam(false);
        assertEquals(-1, this.maincontainer.calculateKyykkasPoints(k));
    }
    
    @Test
    public void kyykkaContainersTickTicksKyykkas(){
        List<Kyykka> kyykkas = this.maincontainer.getKyykkas();
        Kyykka checkKyykka = kyykkas.get(7);
        checkKyykka.setHasInteracted(true);
        checkKyykka.setFrozen(false);
        checkKyykka.setXmom(70);
        int x1 = checkKyykka.getX();
        this.maincontainer.tick();
        assertFalse(x1 == checkKyykka.getX());
    }
    
    @Test
    public void kyykkaContainersClearClearsKyykkasCorrectly(){
        List<Kyykka> kyykkas = this.maincontainer.getKyykkas();
        Kyykka checkKyykka = kyykkas.get(13);
        checkKyykka.setX(-1000);
        checkKyykka.setY(22000);
        this.maincontainer.clearKyykkas();
        assertEquals(39, this.maincontainer.getKyykkas().size());
    }
}
