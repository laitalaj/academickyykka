/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka;

import java.awt.EventQueue;
import javax.swing.JFrame;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.io.Display;
import org.kyykka.io.GamePainter;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.object.Thrower;

/**
 *
 * @author Admin
 */
public class Main {
    
    public static void main(String[] args){
        //DOES NOT WORK AT THE MOMENT!
        Game g = new Game();
        ImageContainer i = new ImageContainer();
        GamePainter p = new GamePainter(800, 600, g, i);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame display = new Display(p);
                display.setVisible(true);
            }
        });
        g.run();
    }
}
