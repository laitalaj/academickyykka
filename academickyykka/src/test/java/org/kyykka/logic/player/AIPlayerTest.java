/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.player;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point3D;

/**
 *
 * @author Admin
 */
public class AIPlayerTest {

    private AIPlayer mainplayer;
    private Game game;

    public AIPlayerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.game = new Game();
        this.mainplayer = new AIPlayer(this.game, true);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void generateTargetGeneratesNewTarget() {
        Point3D p1 = this.mainplayer.getTarget();
        this.mainplayer.generateTarget();
        Point3D p2 = this.mainplayer.getTarget();
        this.mainplayer.generateTarget();
        Point3D p3 = this.mainplayer.getTarget();
        //We give this test the benefit of doubt that either p2 or p3 has the
        //exact same coordinate
        assertTrue(p1.getX() != p2.getX() || p1.getX() != p3.getX());
        assertTrue(p1.getY() != p2.getY() || p1.getY() != p3.getY());
    }

    @Test
    public void targetIsWithinLimitsHomeTeam() {
        for (int i = 0; i < 10000; i++) {
            Point3D target = this.mainplayer.getTarget();
            assertTrue(target.getX() >= 0);
            assertTrue(target.getX() <= 5000);
            assertTrue(target.getY() >= 0);
            assertTrue(target.getY() <= 5000);
            assertEquals(0, target.getZ());
            this.mainplayer.generateTarget();
        }
    }

    @Test
    public void targetIsWithinLimitsAwayTeam() {
        AIPlayer player2 = new AIPlayer(this.game, false);
        for (int i = 0; i < 10000; i++) {
            Point3D target = player2.getTarget();
            assertTrue(target.getX() >= 0);
            assertTrue(target.getX() <= 5000);
            assertTrue(target.getY() >= 15000);
            assertTrue(target.getY() <= 20000);
            assertEquals(0, target.getZ());
            player2.generateTarget();
        }
    }

    @Test
    public void getThrowReturnsValidParameters() {
        for (int i = 0; i < 10000; i++) {
            ThrowParams params = this.mainplayer.getThrow();
            assertTrue(params.getAngle() >= -40 && params.getAngle() <= 40);
            assertTrue(params.getForce() >= 80 && params.getForce() <= 140);
        }
    }

    @Test
    public void throwIsInitiatedWhenNearTarget() {
        this.game.getActiveThrower().setX(this.mainplayer.getTarget().getX() - 500);
        this.game.getActiveThrower().setY(this.mainplayer.getTarget().getY() - 150);
        this.game.getActiveThrower().setZ(this.mainplayer.getTarget().getZ());
        this.mainplayer.tick();
        assertEquals(1, this.mainplayer.getThrowState());
    }

    @Test
    public void throwStateAdvancesWhenAngleIsLargeEnough() {
        this.mainplayer.setThrowState(1);
        this.mainplayer.setAngle(this.mainplayer.getTargetAngle());
        this.mainplayer.tick();
        assertEquals(2, this.mainplayer.getThrowState());
    }

    @Test
    public void throwStateAdvancesWhenForceIsLargeEnough() {
        this.mainplayer.setThrowState(2);
        this.mainplayer.setForce(this.mainplayer.getTargetForce());
        this.mainplayer.tick();
        assertEquals(3, this.mainplayer.getThrowState());
    }

    @Test
    public void throwStateStays3UntilThrow() {
        this.mainplayer.setThrowState(3);
        for (int i = 0; i < 1000; i++) {
            this.mainplayer.tick();
        }
        assertEquals(3, this.mainplayer.getThrowState());
        this.mainplayer.getThrow();
        assertEquals(0, this.mainplayer.getThrowState());
    }

}
