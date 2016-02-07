/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import org.kyykka.logic.Game;
import org.kyykka.logic.shape.HitBox;
import org.kyykka.logic.shape.Point;

/**
 *
 * @author Admin
 */
public class GamePainter extends JPanel implements ActionListener{
    
    private Game game;

    public GamePainter(Game game) {
        this.game = game;
    }
    
    public static Rectangle getSpritePos(HitBox box){
        Point topleft = box.getLowerTopLeft();
        double multi = 5000 / (5000 + topleft.getY()); //Camera position 5m behind the field
        double x = topleft.getX();
        double y = topleft.getZ();
        double xdir = Math.signum(x - 5000); //Camera located at 5000 x
        double ydir = Math.signum(y - 2000); //Camera height 2m
        if(multi > 1){
            xdir *= -1;
            ydir *= -1;
        }
        x += x * xdir * multi * 0.5;
        y += y * ydir * multi * 0.5;
        double w = box.getWidth() * multi;
        double h = box.getHeight() * multi;
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }
    
}
