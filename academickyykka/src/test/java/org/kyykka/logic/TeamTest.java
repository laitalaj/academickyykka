/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.logic.object.Thrower;

/**
 *
 * @author Admin
 */
public class TeamTest {

    private Team mainteam;

    public TeamTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mainteam = new Team(true);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void teamContainsCorrectNumberOfThrowers() {
        assertEquals(4, this.mainteam.getThrowers().size());
    }

    @Test
    public void throwerPositionsCorrectForHome() {
        List<Thrower> throwers = this.mainteam.getThrowers();
        for (Thrower t : throwers) {
            assertEquals(0, t.getY());
            assertEquals(2500, t.getX());
        }
    }

    @Test
    public void throwerPositionsCorrectForAway() {
        Team away = new Team(false);
        List<Thrower> throwers = away.getThrowers();
        for (Thrower t : throwers) {
            assertEquals(20000, t.getY());
            assertEquals(2500, t.getX());
        }
    }

    @Test
    public void nextThrowerIncrementsIndex() {
        this.mainteam.nextThrower();
        assertEquals(1, this.mainteam.getNextThrowerIndex());
    }

    @Test
    public void nextThrowerLoopsBackCorrectly() {
        for (int i = 0; i < 4; i++) {
            this.mainteam.nextThrower();
        }
        assertEquals(0, this.mainteam.getNextThrowerIndex());
    }

    @Test
    public void nextThrowerResetsPositionsCorrectly() {
        Thrower t = this.mainteam.nextThrower();
        for (int i = 0; i < 4; i++) {
            t.setX((9876 * i) % 5000);
            t.setY((7777 * (i + 1)) % 5000);
            t = this.mainteam.nextThrower();
        }
        assertEquals(2500, t.getX());
        assertEquals(0, t.getY());
    }
}
