/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.shape;

import org.kyykka.logic.shape.Point3D;
import org.kyykka.logic.shape.HitBox;
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
public class HitBoxTest {

    private HitBox mainbox;

    public HitBoxTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mainbox = new HitBox(2, 2, 4, 6, 6, 6);
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
    public void getCenterReturnsCenter() {
        Point3D c = this.mainbox.getCenter();
        assertEquals(5, c.getX());
        assertEquals(5, c.getY());
        assertEquals(7, c.getZ());
    }

    @Test
    public void getBottomCenterReturnsBottomCenter() {
        Point3D c = this.mainbox.getBottomCenter();
        assertEquals(5, c.getX());
        assertEquals(5, c.getY());
        assertEquals(4, c.getZ());
    }

    @Test
    public void getTopCenterReturnsTopCenter() {
        Point3D c = this.mainbox.getTopCenter();
        assertEquals(5, c.getX());
        assertEquals(5, c.getY());
        assertEquals(10, c.getZ());
    }

    @Test
    public void getCornersContainsCorrectAmountOfPoints() {
        Collection<Point3D> corners = this.mainbox.getCorners();
        assertEquals(8, corners.size());
    }

    @Test
    public void getCornersContainsOnlyCorners() {
        Collection<Point3D> corners = this.mainbox.getCorners();
        boolean onlycorners = true;
        for (Point3D p : corners) {
            if (p.getX() != 2 && p.getX() != 8) {
                onlycorners = false;
            } else if (p.getY() != 2 && p.getY() != 8) {
                onlycorners = false;
            } else if (p.getZ() != 4 && p.getZ() != 10) {
                onlycorners = false;
            }
            if (!onlycorners) {
                break;
            }
        }
        assertTrue(onlycorners);
    }

    @Test
    public void collisionWithPointWorks() {
        Point3D point = new Point3D(4, 4, 4);
        assertTrue(this.mainbox.collidesWith(point));
    }

    @Test
    public void noFalseCollisionWithPoint() {
        Point3D point = new Point3D(-3, -4, -5);
        assertFalse(this.mainbox.collidesWith(point));
    }

    @Test
    public void collidesWithBottomLowerLeft() {
        Point3D point = new Point3D(2, 2, 4);
        assertTrue(this.mainbox.collidesWith(point));
    }

    @Test
    public void collidesWithTopUpperRight() {
        Point3D point = new Point3D(8, 8, 10);
        assertTrue(this.mainbox.collidesWith(point));
    }

    @Test
    public void collisionWithPointCollectionWorks() {
        Collection<Point3D> points = new HashSet<>();
        points.add(new Point3D(1, 2, 3));
        points.add(new Point3D(3, 3, 5));
        assertTrue(this.mainbox.collidesWith(points));
    }

    @Test
    public void noFalseCollisionWithPointCollection() {
        Collection<Point3D> points = new HashSet<>();
        points.add(new Point3D(1, 1, 1));
        points.add(new Point3D(21, 33, 55));
        assertFalse(this.mainbox.collidesWith(points));
    }

    @Test
    public void collisionWithBoundingBoxWorks() {
        HitBox box2 = new HitBox(1, 1, 1, 3, 3, 3);
        assertTrue(this.mainbox.collidesWith(box2));
    }

    @Test
    public void noFalseCollisionWithBoundingBox() {
        HitBox box2 = new HitBox(11, 11, 11, 2, 1, 1);
        assertFalse(this.mainbox.collidesWith(box2));
    }

    @Test
    public void adjacentCollisionWorksX() {
        HitBox box2 = new HitBox(0, 2, 4, 2, 6, 6);
        assertTrue(this.mainbox.collidesWith(box2));
        HitBox box3 = new HitBox(8, 2, 4, 10, 10, 10);
        assertTrue(this.mainbox.collidesWith(box3));
    }

    @Test
    public void adjacentCollisionWorksY() {
        HitBox box2 = new HitBox(2, 0, 4, 6, 2, 6);
        assertTrue(this.mainbox.collidesWith(box2));
        HitBox box3 = new HitBox(2, 8, 4, 7, 7, 7);
        assertTrue(this.mainbox.collidesWith(box3));
    }

    @Test
    public void adjacentCollisionWorksZ() {
        HitBox box2 = new HitBox(2, 2, 0, 6, 6, 4);
        assertTrue(this.mainbox.collidesWith(box2));
        HitBox box3 = new HitBox(2, 2, 10, 3, 3, 3);
        assertTrue(this.mainbox.collidesWith(box3));
    }

    @Test
    public void moveXMovesCorrectly() {
        this.mainbox.moveX(-2);
        assertEquals(0, this.mainbox.getX());
    }

    @Test
    public void moveYMovesCorrectly() {
        this.mainbox.moveY(2);
        assertEquals(4, this.mainbox.getY());
    }

    @Test
    public void moveZMovesCorrectly() {
        this.mainbox.moveZ(3);
        assertEquals(7, this.mainbox.getZ());
    }

    @Test
    public void movingPositionMovesCorners() {
        this.mainbox.moveX(2);
        this.mainbox.moveY(4);
        this.mainbox.moveZ(-6);
        Collection<Point3D> corners = this.mainbox.getCorners();
        boolean cornersmoved = true;
        for (Point3D p : corners) {
            if (p.getX() != 4 && p.getX() != 10) {
                cornersmoved = false;
            } else if (p.getY() != 6 && p.getY() != 12) {
                cornersmoved = false;
            } else if (p.getZ() != -2 && p.getZ() != 4) {
                cornersmoved = false;
            }
            if (!cornersmoved) {
                break;
            }
        }
        assertTrue(cornersmoved);
    }

}
