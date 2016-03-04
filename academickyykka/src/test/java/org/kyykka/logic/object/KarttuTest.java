/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.object;

import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.logic.shape.HitBox;

/**
 *
 * @author Admin
 */
public class KarttuTest {

    private Karttu mainkarttu;

    public KarttuTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mainkarttu = new Karttu(0, 0, 0, 20, 20, 20);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void constructorSetsMomentum() {
        assertEquals(20, this.mainkarttu.getXmom());
        assertEquals(20, this.mainkarttu.getYmom());
        assertEquals(20, this.mainkarttu.getZmom());
    }

    @Test
    public void tickMovesCorrectly() {
        this.mainkarttu.tick();
        assertEquals(20, this.mainkarttu.getX());
        assertEquals(20, this.mainkarttu.getY());
        assertEquals(20, this.mainkarttu.getZ());
    }

    @Test
    public void karttuBouncesCorrectly() {
        this.mainkarttu.setZmom(-20);
        this.mainkarttu.tick();
        assertEquals(14, this.mainkarttu.getZmom());
    }

    @Test
    public void karttuDoesntBounceIfZIsZero() {
        this.mainkarttu.setZ(101); //Account for gravity
        this.mainkarttu.setZmom(-100);
        this.mainkarttu.tick();
        assertEquals(-101, this.mainkarttu.getZmom());
        this.mainkarttu.bounce();
        assertEquals(-101, this.mainkarttu.getZmom());
    }

    /**
     * TESTS FOR MOSTLY Entity FUNCTIONALITY
     */
    @Test
    public void collisionWorks() {
        Kyykka kyykka = new Kyykka(0, 0, 0);
        assertTrue(this.mainkarttu.collidesWith(kyykka));
    }

    @Test
    public void noFalseCollision() {
        Kyykka kyykka = new Kyykka(-2000, 2000, 2000);
        assertFalse(this.mainkarttu.collidesWith(kyykka));
    }

    @Test
    public void noCollidingWithEntitiesCurrentlyColliding() {
        Kyykka kyykka = new Kyykka(0, 0, 0);
        this.mainkarttu.collide(kyykka);
        int xmom = this.mainkarttu.getXmom();
        int ymom = this.mainkarttu.getYmom();
        int zmom = this.mainkarttu.getZmom();
        this.mainkarttu.collide(kyykka);
        assertTrue(this.mainkarttu.getXmom() == xmom);
        assertTrue(this.mainkarttu.getYmom() == ymom);
        assertTrue(this.mainkarttu.getZmom() == zmom);
    }

    @Test
    public void noCollidingWithEntitiesJustCollidedWith() {
        Kyykka kyykka = new Kyykka(0, 0, 0);
        this.mainkarttu.collide(kyykka);
        int xmom = this.mainkarttu.getXmom();
        int ymom = this.mainkarttu.getYmom();
        int zmom = this.mainkarttu.getZmom();
        this.mainkarttu.tick();
        this.mainkarttu.collide(kyykka);
        assertTrue(this.mainkarttu.getXmom() == xmom);
        assertTrue(this.mainkarttu.getYmom() == ymom);
        assertTrue(this.mainkarttu.getZmom() == zmom);
    }

    @Test
    public void getHitBoxesReturnsCorrectBoxes() {
        this.mainkarttu.setYmom(200);
        Set<HitBox> boxes = this.mainkarttu.getHitBoxes();
        for (HitBox b : boxes) {
            assertTrue(b.getX() == 0 || b.getX() == -20);
            assertTrue(b.getY() == 0 || b.getY() == -200);
            assertTrue(b.getZ() == 0 || b.getZ() == -20);
            assertEquals(850, b.getWidth());
            assertEquals(160, b.getHeight());
            assertEquals(160, b.getDepth());
        }
    }

    @Test
    public void getVelocityReturnsCorrectVelocity() {
        assertEquals(34, this.mainkarttu.getVelocity());
    }

    @Test
    public void collisionWithStaticObjectModifiesMomentumCorrectly() {
        Kyykka kyykka = new Kyykka(0, 0, 0);
        this.mainkarttu.collide(kyykka);
        assertEquals(12, this.mainkarttu.getXmom(), 3);
        assertEquals(12, this.mainkarttu.getYmom(), 3);
        assertEquals(12, this.mainkarttu.getZmom(), 3);
    }

    @Test
    public void collisionWithDynamicObjectModifiesMomentumCorrectly() {
        Karttu karttu2 = new Karttu(0, 0, 0, 850, 80, 80, 3000, -15, 10, -10, 0);
        this.mainkarttu.collide(karttu2);
        assertEquals(-14, this.mainkarttu.getXmom(), 4);
        assertEquals(6, this.mainkarttu.getYmom(), 2);
        assertEquals(-11, this.mainkarttu.getZmom(), 4);
    }

    @Test
    public void bounceResetsZPos() {
        this.mainkarttu.setZmom(-100);
        this.mainkarttu.setZ(-1);
        this.mainkarttu.bounce();
        assertEquals(0, this.mainkarttu.getZ());
    }

    @Test
    public void noTinyBounces() {
        this.mainkarttu.setZmom(-15);
        this.mainkarttu.tick();
        assertEquals(0, this.mainkarttu.getZmom());
    }

    @Test
    public void bouncingAlsoSlides() {
        this.mainkarttu.setZmom(-200);
        this.mainkarttu.tick();
        assertEquals(19, this.mainkarttu.getXmom());
        assertEquals(19, this.mainkarttu.getYmom());
    }

    @Test
    public void entityWontSlideIfZIsntZero() {
        this.mainkarttu.setZmom(0);
        this.mainkarttu.setZ(5);
        this.mainkarttu.slide();
        assertEquals(20, this.mainkarttu.getXmom());
        assertEquals(20, this.mainkarttu.getYmom());
    }

    @Test
    public void entitySlidesCorrectly() {
        this.mainkarttu.setZmom(0);
        this.mainkarttu.setXmom(2);
        this.mainkarttu.setYmom(2);
        this.mainkarttu.setZ(0);
        this.mainkarttu.slide();
        assertEquals(1, this.mainkarttu.getXmom());
        assertEquals(1, this.mainkarttu.getYmom());
    }

    @Test
    public void noEternalSlides() {
        this.mainkarttu.setZmom(0);
        this.mainkarttu.setXmom(-1);
        this.mainkarttu.setYmom(-1);
        this.mainkarttu.setZ(0);
        this.mainkarttu.slide();
        assertEquals(0, this.mainkarttu.getXmom());
        assertEquals(0, this.mainkarttu.getYmom());
    }

    @Test
    public void bouncingTest() {
        this.mainkarttu.setZmom(0);
        this.mainkarttu.setXmom(-5);
        this.mainkarttu.setYmom(30);
        this.mainkarttu.setZ(5050);
        for (int i = 0; i < 100; i++) {
            this.mainkarttu.tick();
        }
        assertEquals(0, this.mainkarttu.getZ());
        assertEquals(-100, this.mainkarttu.getZmom());
        this.mainkarttu.tick();
        assertEquals(0, this.mainkarttu.getZ());
        assertEquals(70, this.mainkarttu.getZmom());
        this.mainkarttu.tick();
        assertEquals(70, this.mainkarttu.getZ());
        assertEquals(70, this.mainkarttu.getZmom());
        for (int i = 0; i < 70; i++) {
            this.mainkarttu.tick();
        }
        assertEquals(2485, this.mainkarttu.getZ());
        assertEquals(0, this.mainkarttu.getZmom());
    }

    @Test
    public void gravityWorksCorrectly() {
        this.mainkarttu.setZ(1);
        this.mainkarttu.applyGravity();
        assertEquals(19, this.mainkarttu.getZmom());
    }

}
