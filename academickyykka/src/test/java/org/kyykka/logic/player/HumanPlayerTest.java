/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.player;

import java.awt.Point;
import java.util.HashMap;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.io.CoordinateTranslator;
import org.kyykka.io.Display;
import org.kyykka.io.GamePainter;
import org.kyykka.io.Input;
import org.kyykka.logic.Game;
import org.kyykka.logic.shape.Point3D;

/**
 *
 * @author Admin
 */
public class HumanPlayerTest {

    private Game maingame;
    private ImageContainer maincontainer;
    private CoordinateTranslator maintrans;
    private Input maininput;
    private HumanPlayer mainplayer;

    public HumanPlayerTest() {
        this.maincontainer = new ImageContainer();
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
        this.maintrans = new CoordinateTranslator(maingame, 1200, 700);
        HashMap<String, JPanel> dummypanels = new HashMap<>();
        dummypanels.put("game", new GamePainter(1200, 700, maingame, 
                maincontainer));
        this.maininput = new Input(new Display(dummypanels, "dummy"));
        this.mainplayer = new HumanPlayer(maininput, maintrans);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getTargetChangesTarget() {
        Point3D target1 = this.mainplayer.getTarget();
        this.maininput.setMousePos(new Point(600, 500));
        Point3D target2 = this.mainplayer.getTarget();
        assertFalse(target1.equals(target2));
    }

    @Test
    public void getTargetWontChangeTargetIfOutsideBoundsHome() {
        Point3D target1 = this.mainplayer.getTarget();
        this.maininput.setMousePos(new Point(450, 1100));
        Point3D target2 = this.mainplayer.getTarget();
        assertTrue(target1.equals(target2));
    }

    @Test
    public void getTargetWontChangeTargetIfOutsideBoundsAway() {
        this.maingame.nextTeam();
        Point3D target1 = this.mainplayer.getTarget();
        this.maininput.setMousePos(new Point(450, 1100));
        Point3D target2 = this.mainplayer.getTarget();
        assertTrue(target1.equals(target2));
    }

    @Test
    public void tickAdvancesThrowstateWhenMousePressed() {
        this.maininput.setIsHeld(true);
        this.mainplayer.tick();
        assertEquals(1, this.mainplayer.getThrowState());
    }

    @Test
    public void tickInitializesParametresWhenMousePressed() {
        this.maininput.setIsHeld(true);
        this.mainplayer.setAngle(200);
        this.mainplayer.setForce(200);
        this.mainplayer.setZmom(200);
        this.mainplayer.tick();
        assertEquals(-90, this.mainplayer.getAngle());
        assertEquals(10, this.mainplayer.getForce());
        assertEquals(0, this.mainplayer.getZmom());
    }

    @Test
    public void determineForceReturnsFalseIfNotFinished() {
        assertFalse(this.mainplayer.determineForce());
    }

    @Test
    public void determineForceReturnsTrueIfFinished() {
        this.maininput.setPendingClicks(1);
        assertTrue(this.mainplayer.determineForce());
    }

    @Test
    public void determineAngleReturnsFalseIfNotFinished() {
        this.maininput.setIsHeld(true);
        assertFalse(this.mainplayer.determineAngle());
    }

    @Test
    public void determineAngleReturnsTrueIfFinished() {
        this.maininput.setIsHeld(false);
        assertTrue(this.mainplayer.determineAngle());
    }
    
    @Test
    public void throwStateGoesTo3WithTime(){
        this.mainplayer.setThrowState(1);
        for(int i = 0; i < 10000; i++){
            this.mainplayer.tick();
        }
        assertEquals(3, this.mainplayer.getThrowState());
    }
    
    @Test
    public void throwSetsThrowStateToZero(){
        this.mainplayer.setThrowState(3);
        this.mainplayer.getThrow();
        assertEquals(0, this.mainplayer.getThrowState());
    }
}
