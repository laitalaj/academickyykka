/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.awt.Point;
import java.awt.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.logic.Game;
import org.kyykka.logic.shape.HitBox;
import org.kyykka.logic.shape.Point3D;

/**
 *
 * @author Admin
 */
public class CoordinateTranslatorTest {

    private CoordinateTranslator maintranslator;
    private Game maingame;

    public CoordinateTranslatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.maingame = new Game();
        this.maintranslator = new CoordinateTranslator(maingame, 1200, 700);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getSpritePosReturnsCorrectPosHome() {
        HitBox b = new HitBox(2000, 2000, 50, 200, 200, 200);
        Rectangle r = this.maintranslator.getSpritePos(b);
        assertEquals(510, r.getX(), 0.001);
        assertEquals(566, r.getY(), 0.001);
        assertEquals(35, r.getWidth(), 0.001);
        assertEquals(35, r.getHeight(), 0.001);
    }

    @Test
    public void getSpritePosReturnsCorrectPosAway() {
        HitBox b = new HitBox(1500, 9000, 50, 3000, 3000, 3000);
        this.maingame.nextTeam();
        Rectangle r = this.maintranslator.getSpritePos(b);
        assertEquals(488, r.getX(), 0.001);
        assertEquals(261, r.getY(), 0.001);
        assertEquals(167, r.getWidth(), 0.001);
        assertEquals(167, r.getHeight(), 0.001);
    }

    @Test
    public void getSpritePosReturnsNullWhenRidiculous() {
        HitBox b = new HitBox(-9001, -9001, -9001, 2, 2, 2);
        assertEquals(null, this.maintranslator.getSpritePos(b));
    }

    @Test
    public void getShadowPosReturnsCorrectPosHome() {
        HitBox b = new HitBox(1500, 5000, 200, 99, 99, 99);
        Rectangle r = this.maintranslator.getShadowPos(b);
        assertEquals(504, r.getX(), 0.001);
        assertEquals(487, r.getY(), 0.001);
        assertEquals(9, r.getWidth(), 0.001);
        assertEquals(3, r.getHeight(), 0.001);
    }

    @Test
    public void getShadowPosReturnsCorrectPosAway() {
        HitBox b = new HitBox(3000, 15000, 1000, 500, 500, 500);
        this.maingame.nextTeam();
        Rectangle r = this.maintranslator.getShadowPos(b);
        assertEquals(495, r.getX(), 0.001);
        assertEquals(490, r.getY(), 0.001);
        assertEquals(52, r.getWidth(), 0.001);
        assertEquals(12, r.getHeight(), 0.001);
    }

    @Test
    public void getPointPosReturnsCorrectGameCoordinateHome() {
        Point p = new Point(400, 400);
        Point3D p2 = this.maintranslator.getPointPos(p);
        assertEquals(-3333, p2.getX());
        assertEquals(16250, p2.getY());
    }

    @Test
    public void getPointPosReturnsCorrectGameCoordinateAway() {
        Point p = new Point(800, 600);
        this.maingame.nextTeam();
        Point3D p2 = this.maintranslator.getPointPos(p);
        assertEquals(1333, p2.getX());
        assertEquals(17750, p2.getY());
    }

    @Test
    public void getPointPosReturnsNullIfAboveHorizon() {
        Point p = new Point(800, 200);
        Point3D p2 = this.maintranslator.getPointPos(p);
        assertEquals(null, p2);
    }
}
