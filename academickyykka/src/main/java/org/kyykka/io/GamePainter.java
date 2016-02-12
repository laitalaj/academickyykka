/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.PhysicsEntity;
import org.kyykka.logic.shape.HitBox;
import org.kyykka.logic.shape.Point;

/**
 *
 * @author Admin
 */
public class GamePainter extends JPanel implements ActionListener{
    
    private Game game;
    private ImageContainer imgs;
    private DrawOrderComparator compar;
    private Timer timer;
    private int width;
    private int height;

    public GamePainter(int width, int height, Game game, ImageContainer imgs) {
        this.width = width;
        this.height = height;
        this.game = game;
        this.imgs = imgs;
        this.compar = new DrawOrderComparator(this.game.getActiveTeam().isHomeTeam());
        this.timer = new Timer(25, this);
        this.timer.start();
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);
    }
    
    public Rectangle getSpritePos(HitBox box){
        //TODO: Home/away cam
        //Here's hoping this is not horribly broken
        //I should probably write tests...
        Point topleft = box.getLowerTopLeft();
        double multi = (double) 10000 / (10000 + topleft.getY()); //Camera position 10m behind the field
        double x = topleft.getX();
        double y = topleft.getZ();
        double xdir = Math.signum(x - 5000); //Camera located at 5000 x
        double ydir = Math.signum(y - 3000); //Camera height 3m
        if(multi > 1){
            xdir *= -1;
            ydir *= -1;
        }
        x += x * xdir * multi * 0.5;
        y += y * ydir * multi * 0.5;
        x = this.width * (x / 10000); //View dimensions 10m x 6m
        y = this.height * (y / 6000);
        double w = box.getWidth() * multi;
        double h = box.getDepth() * multi;
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.compar.setHomecam(this.game.getActiveTeam().isHomeTeam());
        List<PhysicsEntity> entities = this.game.getEntities();
        Collections.sort(entities, this.compar);
        for(PhysicsEntity e: entities){
            Rectangle spritepos = getSpritePos(e.getBoundingBox());
            g.drawImage(this.imgs.getImage(e), spritepos.x, spritepos.y, 
                    spritepos.width, spritepos.height, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }
    
}
