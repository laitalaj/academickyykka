/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.shape;

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
public class PointTest {

    private Point3D mainpoint;

    public PointTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mainpoint = new Point3D(-5, -5, 10);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getDistanceWorksCorrectly() {
        Point3D point2 = new Point3D(3, 4, 5);
        assertEquals(13, this.mainpoint.getDistance(point2));
    }

    @Test
    public void equalsWorksCorrectly() {
        Point3D point2 = new Point3D(-5, -5, 10);
        assertTrue(this.mainpoint.equals(point2));
    }

    @Test
    public void noFalseEquals() {
        Point3D point2 = new Point3D(-5, -5, -10);
        assertFalse(this.mainpoint.equals(point2));
    }
}
