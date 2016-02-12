/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;

/**
 *
 * @author Admin
 */
public class Display implements Runnable {
    
    private JFrame window;
    private JPanel painter;
    
    public Display(JPanel painter) throws HeadlessException {
        this.painter = painter;
    }
    
    @Override
    public void run(){
        window = new JFrame("ACADEMIC KYYKKA WORLD CUP");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(painter);
        window.pack();
        window.setVisible(true);
    }
    
}
