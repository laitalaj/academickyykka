/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

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
        Set<Kyykka> kyykkas = this.maincontainer.getKyykkas();
        for (Kyykka k : kyykkas) {
            assertEquals(15000, k.getY());
            assertTrue(k.getZ() == 0 || k.getZ() == 10);
            assertTrue(k.getX() > 120 && k.getX() < 4880);
        }
    }

    @Test
    public void kyykkaPositionsAreCorrectForAway() {
        KyykkaContainer container = new KyykkaContainer(false);
        Set<Kyykka> kyykkas = container.getKyykkas();
        for (Kyykka k : kyykkas) {
            assertEquals(5000, k.getY());
            assertTrue(k.getZ() == 0 || k.getZ() == 10);
            assertTrue(k.getX() > 120 && k.getX() < 4880);
        }
    }
}
