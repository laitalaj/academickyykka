/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.logic.object.Karttu;
import org.kyykka.logic.shape.Point3D;

/**
 *
 * @author Admin
 */
public class TrajectoryCalculatorTest {

    public TrajectoryCalculatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void trajectoryCalculatorCalculatesLandingCorrectly() {
        Karttu k = new Karttu(200, 200, 200, 50, 50, 20);
        Point3D landing = TrajectoryCalculator.calculateLanding(k);
        assertEquals(2650, landing.getX());
        assertEquals(2650, landing.getY());
        assertEquals(0, landing.getZ());
    }

    @Test
    public void trajectoryCalculatorCalculatesLandingCorrectly2() {
        Karttu k = new Karttu(2500, 15000, 500, 3, -50, 15);
        Point3D landing = TrajectoryCalculator.calculateLanding(k);
        assertEquals(2653, landing.getX());
        assertEquals(12450, landing.getY());
        assertEquals(0, landing.getZ());
    }
}
