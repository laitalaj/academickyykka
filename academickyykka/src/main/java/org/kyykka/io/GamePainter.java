package org.kyykka.io;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Collections;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.kyykka.graphics.ImageContainer;
import org.kyykka.logic.Game;
import org.kyykka.logic.TrajectoryCalculator;
import org.kyykka.logic.object.Karttu;
import org.kyykka.logic.object.PhysicsEntity;
import org.kyykka.logic.player.Player;
import org.kyykka.logic.shape.HitBox;
import org.kyykka.logic.shape.Point3D;

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
    private CoordinateTranslator translator;
    private int width;
    private int height;
    private double aspectRatio;
    private boolean active;

    /**
     * Creates a new GamePainter with specified parameters.
     *
     * @param width the width of the panel
     * @param height the height of the panel
     * @param game the game object to be drawn
     * @param imgs the image container to use to fetch images.
     */
    public GamePainter(int width, int height, Game game, ImageContainer imgs) {
        this.translator = new CoordinateTranslator(game, width, height);
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
        this.active = false;
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
     *
     * @param from point from which to draw in game coordinates
     * @param to point to which to draw in game coordinates
     * @param g graphics object on which to draw
     */
    public void drawLine(Point3D from, Point3D to, Graphics g) {
        Point p1 = this.translator.getPointPos(from);
        Point p2 = this.translator.getPointPos(to);
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }

    /**
     * Draws lines representing the horizon and the playing field onto the given
     * graphics object.
     *
     * @param g graphics object to be painted on
     */
    public void paintBackground(Graphics g) {
        Point3D homebackleft = new Point3D(0, 0, 0);
        Point3D homebackright = new Point3D(5000, 0, 0);
        Point3D homefrontleft = new Point3D(0, 5000, 0);
        Point3D homefrontright = new Point3D(5000, 5000, 0);
        Point3D awaybackleft = new Point3D(5000, 20000, 0);
        Point3D awaybackright = new Point3D(0, 20000, 0);
        Point3D awayfrontleft = new Point3D(5000, 15000, 0);
        Point3D awayfrontright = new Point3D(0, 15000, 0);
        g.setColor(Color.BLACK);
        drawLine(homefrontleft, awayfrontright, g);
        drawLine(homefrontright, awayfrontleft, g);
        g.drawLine(0, this.height / 2, this.width, this.height / 2);
        g.setColor(Color.GREEN);
        drawLine(homebackleft, homebackright, g);
        drawLine(homebackleft, homefrontleft, g);
        drawLine(homebackright, homefrontright, g);
        drawLine(homefrontleft, homefrontright, g);
        g.setColor(Color.ORANGE);
        drawLine(awayfrontleft, awayfrontright, g);
        drawLine(awayfrontleft, awaybackleft, g);
        drawLine(awayfrontright, awaybackright, g);
        drawLine(awaybackleft, awaybackright, g);
    }

    /**
     * Paints UI elements relevant to the active player (the state of given
     * players aim and force, whichever is currently happening).
     *
     * @param g graphics object to be drawn on
     */
    public void paintPlayer(Graphics g) {
        //TODO: CLEAN!!! REFACTOR!!!
        Player active = this.game.getActivePlayer();
        int state = active.getThrowState();
        if (state == 1) {
            double angleradians = Math.toRadians(active.getAngle());
            double x = 2000 * Math.sin(angleradians);
            double y = 2000 * Math.cos(angleradians);
            if (!this.game.getActiveTeam().isHomeTeam()) {
                y *= -1;
            }
            Point3D p = this.game.getActiveThrower().getPos();
            Point pScreenPos = this.translator.getPointPos(p);
            if (pScreenPos.y > this.height) {
                p.moveZ(this.game.getActiveThrower().getHitBox().getDepth() / 2);
            }
            Point3D p2 = p.copy();
            p2.moveX((int) x);
            p2.moveY((int) y);
            g.setColor(Color.red);
            drawLine(p, p2, g);
        } else if (state == 2) {
            PhysicsEntity dummy = this.game.getActiveThrower().throwKarttu(active.getAngle(),
                    active.getForce(), active.getZangle(), 0);
            Point3D landingpos = TrajectoryCalculator.calculateLanding(dummy);
            Point screenpos = this.translator.getPointPos(landingpos);
            g.setColor(Color.BLUE);
            g.fillOval(screenpos.x, screenpos.y, width / 100, height / 100);
        } else if (state == 3) {
            Karttu dummy = this.game.getActiveThrower().throwKarttu(active.getAngle(),
                    active.getForce(), active.getZangle(), active.getSpin());
            List<Double> spins = TrajectoryCalculator.calculateDesiredSpins(dummy,
                    180, 1, 7);
            Point3D topLeftGame = this.game.getActiveThrower().getHitBox().getLocation().copy();
            Point topleft = this.translator.getPointPos(topLeftGame);
            if (topleft.y > this.height) {
                topLeftGame.moveZ(this.game.getActiveThrower().getHitBox().getDepth());
                topleft = this.translator.getPointPos(topLeftGame);
            }
            Point3D topRightGame = topLeftGame.copy();
            topRightGame.moveX(this.game.getActiveThrower().getHitBox().getWidth());
            Point topright = this.translator.getPointPos(topRightGame);
            int width;
            if (this.game.getActiveTeam().isHomeTeam()) {
                width = topright.x - topleft.x;
            } else {
                width = topleft.x - topright.x;
            }
            g.setColor(Color.RED);
            int tx = topleft.x;
            if (!this.game.getActiveTeam().isHomeTeam()) {
                tx -= width;
            }
            g.fillRect(tx, topleft.y, width, 10);
            g.setColor(Color.GREEN);
            for (double spin : spins) {
                double ratio = (spin - 1) / 6;
                double x = topleft.x + width * ratio;
                if (!this.game.getActiveTeam().isHomeTeam()) {
                    x -= width;
                }
                g.fillRect((int) x, topleft.y, 3, 10);
            }
            double ratio = (active.getSpin() - 1) / 6;
            double x = topleft.x + width * ratio;
            if (!this.game.getActiveTeam().isHomeTeam()) {
                x -= width;
            }
            g.setColor(Color.BLUE);
            g.fillRect((int) x, topleft.y, 2, 10);
        }
    }

    /**
     * Paints a shadow for every object in the game. Brings that authentic 3D
     * feel.
     *
     * @param g graphics object to draw on
     */
    public void paintShadows(Graphics g) {
        List<PhysicsEntity> entities = this.game.getEntities();
        g.setColor(Color.DARK_GRAY);
        for (PhysicsEntity e : entities) {
            Rectangle shadowpos = this.translator.getShadowPos(e.getHitBox());
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
    public void paintGame(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        List<PhysicsEntity> entities = this.game.getEntities();
        Collections.sort(entities, this.compar);
        for (PhysicsEntity e : entities) {
            Rectangle spritepos = this.translator.getSpritePos(e.getHitBox());
            if (spritepos == null || Math.max(spritepos.width, spritepos.height) < 3) {
                continue;
            }
//            if(spritepos.x < 0 || spritepos.y < 0 || spritepos.x > this.width
//                    || spritepos.y > this.height){
//                continue;
//            }
            AlphaComposite transparency = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    e.getAlpha());
            g2d.setComposite(transparency);
            g2d.drawImage(this.imgs.getImage(e), spritepos.x, spritepos.y,
                    spritepos.width, spritepos.height, null);
        }
        g2d.dispose();
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
        paintPlayer(g);
        paintGame(g);
    }

    /**
     * Repaints the panel. Is timed with a Timer to run 50 times in a second.
     *
     * @param ae action event performed
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (this.active) {
            repaint();
        }
    }

    public DrawOrderComparator getCompar() {
        return compar;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setGame(Game game) {
        this.game = game;
        this.translator = new CoordinateTranslator(game, width, height);
    }

}
