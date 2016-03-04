/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import javax.swing.JPanel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.io.Display;
import org.kyykka.io.forms.MenuPanel;
import org.kyykka.io.painter.GamePanel;

/**
 *
 * @author Admin
 */
public class GameInitializerTest {

    private Semaphore mainsema;
    private MenuPanel mainmenu;
    private GameInitializer maininitializer;

    public GameInitializerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mainmenu = new MenuPanel();
        this.mainsema = new Semaphore(1);
        HashMap<String, JPanel> dummypanels = new HashMap<>();
        dummypanels.put("game", new GamePanel(1200, 700, new Game(),
                new ImageContainer()));
        this.maininitializer = new GameInitializer(
                new Display(dummypanels, "game"), mainmenu,
                mainsema);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void initializationLocksSemaphore() {
        assertEquals(0, this.mainsema.availablePermits());
    }

    @Test
    public void eventReleasesSemaphore() {
        this.maininitializer.actionPerformed(new ActionEvent(this, 0, "test"));
        assertEquals(1, this.mainsema.availablePermits());
    }

    @Test
    public void gameInitializesCorrectly() {
        this.maininitializer.actionPerformed(new ActionEvent(this, 0, "test"));
        assertEquals(2, this.maininitializer.getGame().getPlayers().size());
    }
}
