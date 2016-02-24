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
    public void noFalseOutOfBoundsHome() {
        Kyykka k = new Kyykka(10, 15100, 100000);
        assertFalse(this.maincontainer.isOutOfBounds(k));
    }
}
