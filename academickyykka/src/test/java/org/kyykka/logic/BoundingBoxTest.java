/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.util.Collection;
import java.util.HashSet;
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
public class BoundingBoxTest {
    
    private BoundingBox mainbox;
    
    public BoundingBoxTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.mainbox = new BoundingBox(2, 2, 4, 6, 6, 6);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void collisionWithPointWorks(){
        Point point = new Point(4, 4, 4);
        assertTrue(this.mainbox.collidesWith(point));
    }
    
    @Test
    public void noFalseCollisionWithPoint(){
        Point point = new Point(-3, -4, -5);
        assertFalse(this.mainbox.collidesWith(point));
    }
    
    @Test
    public void collisionWithPointCollectionWorks(){
        Collection<Point> points = new HashSet<>();
        points.add(new Point(1, 2, 3));
        points.add(new Point(3, 3, 5));
        assertTrue(this.mainbox.collidesWith(points));
    }
    
    @Test
    public void noFalseCollisionWithPointCollection(){
        Collection<Point> points = new HashSet<>();
        points.add(new Point(1, 1, 1));
        points.add(new Point(21, 33, 55));
        assertFalse(this.mainbox.collidesWith(points));
    }
    
    @Test
    public void collisionWithBoundingBoxWorks(){
        BoundingBox box2 = new BoundingBox(1, 1, 1, 3, 3, 3);
        assertTrue(this.mainbox.collidesWith(box2));
    }
    
    @Test
    public void noFalseCollisionWithBoundingBox(){
        BoundingBox box2 = new BoundingBox(11, 11, 11, 2, 1, 1);
        assertFalse(this.mainbox.collidesWith(box2));
    }
    
}
