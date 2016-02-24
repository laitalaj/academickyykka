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
import org.kyykka.logic.player.AIPlayer;
import org.kyykka.logic.player.HumanPlayer;
import org.kyykka.logic.shape.Point3D;

/**
 *
 * @author Admin
 */
public class GameTest {

    private Game maingame;

    public GameTest() {
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
        this.maingame.addPlayer(new AIPlayer(this.maingame, true));
        this.maingame.addPlayer(new AIPlayer(this.maingame, false));
        this.maingame.setActivePlayer(0);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void tickMovesActiveThrower() {
        this.maingame.getActiveThrower().setTarget(2500, 2500);
        Point3D start = this.maingame.getActiveThrower().getPos();
        this.maingame.tick();
        assertTrue(start != this.maingame.getActiveThrower().getPos());
    }

    @Test
    public void tickGoesToNextTeamIfConditionsMet() {
        this.maingame.setActiveThrower(null);
        this.maingame.setKarttusThrown(4);
        this.maingame.tick();
        assertFalse(this.maingame.getActiveTeam().isHomeTeam());
    }

    @Test
    public void noKarttusActiveInitially() {
        assertFalse(this.maingame.karttusAreActive());
    }

    @Test
    public void throwReadyPlayerThrowsDuringTick() {
        AIPlayer player = (AIPlayer) this.maingame.getActivePlayer();
//        player.setTarget(this.maingame.getActiveThrower().getPos());
        player.setThrowState(3);
        this.maingame.tick();
        assertTrue(this.maingame.karttusAreActive());
    }

    @Test
    public void gameReturnsCorrectNumberOfInitialEntities() {
        assertEquals(81, this.maingame.getEntities().size());
    }
}
