/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.awt.event.ActionEvent;
import java.util.concurrent.Semaphore;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.kyykka.io.forms.EndPanel;

/**
 *
 * @author Admin
 */
public class EndHandlerTest {

    private EndHandler mainhandler;
    private Semaphore mainsema;

    public EndHandlerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.mainsema = new Semaphore(1);
        this.mainhandler = new EndHandler(new Team(true), new EndPanel(),
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
        this.mainhandler.actionPerformed(new ActionEvent(this, 0, "test"));
        assertEquals(1, this.mainsema.availablePermits());
    }
}
