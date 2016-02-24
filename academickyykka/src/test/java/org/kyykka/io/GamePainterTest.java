/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.logic.Game;
import org.kyykka.logic.shape.HitBox;

/**
 *
 * @author Admin
 */
public class GamePainterTest {

    private Game maingame;
    private ImageContainer mainimgcon;
    private GamePainter mainpainter;

    public GamePainterTest() {
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
        this.mainimgcon = new ImageContainer();
        this.mainpainter = new GamePainter(1200, 700, this.maingame, this.mainimgcon);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void checkCamPosChangesHomecamIfActiveTeamChanged() {
        this.maingame.nextTeam();
        this.mainpainter.checkCamPos();
        assertEquals(false, this.mainpainter.getCompar().isHomecam());
    }
}
