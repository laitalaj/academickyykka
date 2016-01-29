/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.shape;

import org.kyykka.logic.shape.Point;
import org.kyykka.logic.shape.BoundingBox;
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
    public void getCornersContainsCorrectAmountOfPoints(){
        Collection<Point> corners = this.mainbox.getCorners();
        assertEquals(8, corners.size());
    }
    
    @Test
    public void getCornersContainsOnlyCorners(){
        Collection<Point> corners = this.mainbox.getCorners();
        boolean onlycorners = true;
        for(Point p: corners){
            if(p.getX() != 2 && p.getX() != 8){
                onlycorners = false;
            } else if (p.getY() != 2 && p.getY() != 8){
                onlycorners = false;
            } else if (p.getZ() != 4 && p.getZ() != 10){
                onlycorners = false;
            }
            if(!onlycorners){
                break;
            }
        }
        assertTrue(onlycorners);
    }
    
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
    
    @Test
    public void adjacentCollisionWorksX(){
        BoundingBox box2 = new BoundingBox(0, 2, 4, 2, 6, 6);
        assertTrue(this.mainbox.collidesWith(box2));
        BoundingBox box3 = new BoundingBox(8, 2, 4, 10, 10, 10);
        assertTrue(this.mainbox.collidesWith(box3));
    }
    
    @Test
    public void adjacentCollisionWorksY(){
        BoundingBox box2 = new BoundingBox(2, 0, 4, 6, 2, 6);
        assertTrue(this.mainbox.collidesWith(box2));
        BoundingBox box3 = new BoundingBox(2, 8, 4, 7, 7, 7);
        assertTrue(this.mainbox.collidesWith(box3));
    }
    
    @Test
    public void adjacentCollisionWorksZ(){
        BoundingBox box2 = new BoundingBox(2, 2, 0, 6, 6, 4);
        assertTrue(this.mainbox.collidesWith(box2));
        BoundingBox box3 = new BoundingBox(2, 2, 10, 3, 3, 3);
        assertTrue(this.mainbox.collidesWith(box3));
    }
    
    @Test
    public void moveXMovesCorrectly(){
        this.mainbox.moveX(-2);
        assertEquals(0, this.mainbox.getX());
    }
    
    @Test
    public void moveYMovesCorrectly(){
        this.mainbox.moveY(2);
        assertEquals(4, this.mainbox.getY());
    }
    
    @Test
    public void moveZMovesCorrectly(){
        this.mainbox.moveZ(3);
        assertEquals(7, this.mainbox.getZ());
    }
    
    @Test
    public void movingPositionMovesCorners(){
        this.mainbox.moveX(2);
        this.mainbox.moveY(4);
        this.mainbox.moveZ(-6);
        Collection<Point> corners = this.mainbox.getCorners();
        boolean cornersmoved = true;
        for(Point p: corners){
            if(p.getX() != 4 && p.getX() != 10){
                cornersmoved = false;
            } else if (p.getY() != 6 && p.getY() != 12){
                cornersmoved = false;
            } else if (p.getZ() != -2 && p.getZ() != 4){
                cornersmoved = false;
            }
            if(!cornersmoved){
                break;
            }
        }
        assertTrue(cornersmoved);
    }
    
}
