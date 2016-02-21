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
 * Handles drawing the current game state unto itself.
 *
 * @author Julius Laitala
 */
public class GamePainter extends JPanel implements ActionListener {

    private Game game;
    private ImageContainer imgs;
    private DrawOrderComparator compar;
    private Timer timer;
    private int width;
    private int height;
    private double aspectRatio;

    /**
     * Creates a new GamePainter with specified parameters.
     *
     * @param width the width of the panel
     * @param height the height of the panel
     * @param game the game object to be drawn
     * @param imgs the image container to use to fetch images.
     */
    public GamePainter(int width, int height, Game game, ImageContainer imgs) {
        this.width = width;
        this.height = height;
        this.aspectRatio = (double) height / width;
        this.game = game;
        this.imgs = imgs;
        this.compar = new DrawOrderComparator(this.game.getActiveTeam().isHomeTeam());
        this.timer = new Timer(25, this);
        this.timer.start();
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(width, height));
        setDoubleBuffered(true);
    }
    
    /**
     * Trandsorms a point thats set into game coordinates into a point that's
     * set to window coordinates.
     * 
     * @param point the point to be transformed (game coordinates)
     * 
     * @return transformed point (window coordinates)
     */
    public Point getPointPos(Point point){
        boolean home = this.game.getActiveTeam().isHomeTeam();
        double fovsize;
        if (home) {
            fovsize = (1250 + point.getY()) * 2;
        } else {
            fovsize = (1250 + (20000 - point.getY())) * 2;
        }
        if (fovsize <= 0) {
            return null;
        }
        double x = point.getX();
        double y = point.getZ();
        double translation = (fovsize - 5000) / 2;
        x += translation;
        y += this.aspectRatio * translation;
        x = (x / fovsize) * this.width;
        if (!home) {
            x = this.width - x;
        }
        y = this.height - (y / (this.aspectRatio * fovsize)) * this.height;
        return new Point((int) x, (int) y, 0);
    }

    /**
     * Transforms a HitBox thats set into game coordinates into a Rectangle that
     * represents the boxes camera-facing facet and is set to window
     * coordinates.
     *
     * @param box the box to transform
     *
     * @see getPointPos()
     * 
     * @return a rectangle set to window coordinates
     */
    public Rectangle getSpritePos(HitBox box) {
        boolean home = this.game.getActiveTeam().isHomeTeam();
        Point topleft = box.getCenterTopLeft();
        double fovsize;
        if (home) {
            fovsize = (1250 + topleft.getY()) * 2;
        } else {
            fovsize = (1250 + (20000 - topleft.getY())) * 2;
        }
        topleft = getPointPos(topleft);
        if (topleft == null) {
            return null;
        }
        double w = this.width * (box.getWidth() / fovsize);
        double h = this.height * (box.getDepth() / (this.aspectRatio * fovsize));
        if (!home) {
            topleft.moveX((int) -w);
        }
//        if(y < 200){
//            System.out.println(box + "->" + x + ", " + y);
//        }
        return new Rectangle(topleft.getX(), topleft.getY(), (int) w, (int) h);
    }
        
        /**
         * Returns a rectangle in window coordinates that corresponds to the
         * position of given boxes shadow.
         * 
         * @param box box which shadow to calculate
         * 
         * @return rectangle of shadow's position in window coordinates
         */
        public Rectangle getShadowPos(HitBox box){
        boolean home = this.game.getActiveTeam().isHomeTeam();
        Point bottomleft = box.getLocation().copy();
        bottomleft.setZ(0);
        double fovsize;
        if (home) {
            fovsize = (1250 + bottomleft.getY()) * 2;
        } else {
            fovsize = (1250 + (20000 - bottomleft.getY())) * 2;
        }
        bottomleft = getPointPos(bottomleft);
        if (bottomleft == null) {
            return null;
        }
        double w = this.width * (box.getWidth() / fovsize);
//        double h = this.height * (box.getDepth() / (this.aspectRatio * fovsize));
        if (!home) {
            bottomleft.moveX((int) -w);
        }
//        if(y < 200){
//            System.out.println(box + "->" + x + ", " + y);
//        }
        return new Rectangle(bottomleft.getX(), bottomleft.getY() - (int) w/8, (int) w, (int) w / 8);
    }

    /**
     * Changes camera position to behind home team or away team depending on
     * which one is currently active.
     */
    public void checkCamPos() {
        this.compar.setHomecam(this.game.getActiveTeam().isHomeTeam());
    }
    
    /**
     * Draws a line from point p1 to p2 on given graphics object. Both points 
     * should be in game coordinates
     * @param from point from which to draw in game coordinates
     * @param to point to which to draw in game coordinates
     * @param g graphics object on which to draw
     */
    public void drawLine(Point from, Point to, Graphics g){
        Point p1 = getPointPos(from);
        Point p2 = getPointPos(to);
        g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }
    
    /**
     * Draws lines representing the horizon and the playing field onto the given
     * graphics object.
     * 
     * @param g graphics object to be painted on
     */
    public void paintBackground(Graphics g){
        Point homebackleft = new Point(0, 0, 0);
        Point homebackright = new Point(5000, 0, 0);
        Point homefrontleft = new Point(0, 5000, 0);
        Point homefrontright = new Point(5000, 5000, 0);
        Point awaybackleft = new Point(5000, 20000, 0);
        Point awaybackright = new Point(0, 20000, 0);
        Point awayfrontleft = new Point(5000, 15000, 0);
        Point awayfrontright = new Point(0, 15000, 0);
        drawLine(homebackleft, homebackright, g);
        drawLine(homebackleft, homefrontleft, g);
        drawLine(homebackright, homefrontright, g);
        drawLine(homefrontleft, homefrontright, g);
        drawLine(homefrontleft, awayfrontright, g);
        drawLine(homefrontright, awayfrontleft, g);
        drawLine(awayfrontleft, awayfrontright, g);
        drawLine(awayfrontleft, awaybackleft, g);
        drawLine(awayfrontright, awaybackright, g);
        drawLine(awaybackleft, awaybackright, g);
        g.drawLine(0, this.height / 2, this.width, this.height / 2);
    }
    
    /**
     * Paints a shadow for every object in the game. Brings that authentic 3D 
     * feel.
     * 
     * @param g graphics object to draw on
     */
    public void paintShadows(Graphics g){
        List<PhysicsEntity> entities = this.game.getEntities();
        for (PhysicsEntity e : entities) {
            Rectangle shadowpos = getShadowPos(e.getHitBox());
            if (shadowpos == null || Math.max(shadowpos.width, shadowpos.height) < 5) {
                continue;
            }
//            if(spritepos.x < 0 || spritepos.y < 0 || spritepos.x > this.width
//                    || spritepos.y > this.height){
//                continue;
//            }
            g.fillOval(shadowpos.x, shadowpos.y,
                    shadowpos.width, shadowpos.height);
        }

    }
    
    /**
     * Draws everything drawable in the game.
     *
     * @param g graphics object on which to paint
     */
    public void paintGame(Graphics g){
        List<PhysicsEntity> entities = this.game.getEntities();
        Collections.sort(entities, this.compar);
        for (PhysicsEntity e : entities) {
            Rectangle spritepos = getSpritePos(e.getHitBox());
            if (spritepos == null || Math.max(spritepos.width, spritepos.height) < 3) {
                continue;
            }
//            if(spritepos.x < 0 || spritepos.y < 0 || spritepos.x > this.width
//                    || spritepos.y > this.height){
//                continue;
//            }
            g.drawImage(this.imgs.getImage(e), spritepos.x, spritepos.y,
                    spritepos.width, spritepos.height, null);
        }
    }

    /**
     * Draws the background, shadows for entities and the game.
     *
     * @param g graphics object on which to paint
     * 
     * @see GamePainter#paintBackground(java.awt.Graphics) 
     * @see GamePainter#paintShadows(java.awt.Graphics) 
     * @see GamePainter#paintGame(java.awt.Graphics) 
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        checkCamPos();
        paintBackground(g);
        paintShadows(g);
        paintGame(g);
    }

    /**
     * Repaints the panel. Is timed with a Timer to run 50 times in a second.
     *
     * @param ae action event performed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

    public DrawOrderComparator getCompar() {
        return compar;
    }

}
