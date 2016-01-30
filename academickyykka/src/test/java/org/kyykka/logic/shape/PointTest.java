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
    
    private Point mainpoint;
    
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
        this.mainpoint = new Point(-5, -5, 10);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void getDistanceWorksCorrectly(){
        Point point2 = new Point(3, 4, 5);
        assertEquals(13, this.mainpoint.getDistance(point2));
    }
}
