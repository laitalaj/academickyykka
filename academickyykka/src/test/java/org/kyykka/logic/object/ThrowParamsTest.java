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
public class ThrowParamsTest {

    public ThrowParamsTest() {
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
    public void emptyConstructorCreatesEmptyParams() {
        ThrowParams params = new ThrowParams();
        assertEquals(0, params.getAngle());
        assertEquals(0, params.getForce());
        assertEquals(0, params.getZangle(), 0.01);
    }

    @Test
    public void twoArgumentConstructorSetsAngleAndForce() {
        ThrowParams params = new ThrowParams(77, 88);
        assertEquals(77, params.getAngle());
        assertEquals(88, params.getForce());
        assertEquals(0, params.getZangle(), 0.01);
    }

    @Test
    public void threeArgumentConstructorSetsAngleForceAndZmom() {
        ThrowParams params = new ThrowParams(77, 88, 99);
        assertEquals(77, params.getAngle());
        assertEquals(88, params.getForce());
        assertEquals(99, params.getZangle(), 0.01);
    }
}
