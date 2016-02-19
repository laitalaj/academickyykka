/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

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
public class DrawOrderComparatorTest {

    private DrawOrderComparator maincomp;
    private Kyykka entity1;
    private Kyykka entity2;

    public DrawOrderComparatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.maincomp = new DrawOrderComparator(true);
        this.entity1 = new Kyykka(20, 20, 20);
        this.entity2 = new Kyykka(200, 200, 200);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void comparesCorrectlyEqual() {
        assertEquals(0, this.maincomp.compare(this.entity1, this.entity1));
    }

    @Test
    public void comparesCorrectlyHome() {
        assertTrue(this.maincomp.compare(this.entity1, this.entity2) > 0);
    }

    @Test
    public void comparesCorrectlyAway() {
        this.maincomp.setHomecam(false);
        assertTrue(this.maincomp.compare(this.entity1, this.entity2) < 0);
    }
}
