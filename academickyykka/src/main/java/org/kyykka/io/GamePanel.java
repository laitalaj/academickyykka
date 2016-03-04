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
public class GamePanel extends JPanel implements ActionListener {

    private Game game;
    private ImageContainer imgs;
    private DrawOrderComparator compar;
    private Timer timer;
    private CoordinateTranslator translator;
    private UIPainter uipainter;
    private BackgroundPainter bgpainter;
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
    public GamePanel(int width, int height, Game game, ImageContainer imgs) {
        this.translator = new CoordinateTranslator(game, width, height);
        this.width = width;
        this.height = height;
        this.aspectRatio = (double) height / width;
        this.game = game;
        this.imgs = imgs;
        this.compar = new DrawOrderComparator(this.game.getActiveTeam().isHomeTeam());
        this.uipainter = new UIPainter(game, translator);
        this.bgpainter = new BackgroundPainter(translator);
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
     * Draws lines representing the horizon and the playing field onto the given
     * graphics object.
     *
     * @param g graphics object to be painted on
     * 
     * @see BackgroundPainter#paintBackground(java.awt.Graphics) 
     */
    public void paintBackground(Graphics g) {
        this.bgpainter.paintBackground(g);
    }

    /**
     * Paints UI elements relevant to the active player (the state of given
     * players aim and force, whichever is currently happening).
     *
     * @param g graphics object to be drawn on
     * 
     * @see UIPainter#paintUI(java.awt.Graphics) 
     */
    public void paintPlayer(Graphics g) {
        this.uipainter.paintUI(g);
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
     * @see GamePanel#paintBackground(java.awt.Graphics)
     * @see GamePanel#paintShadows(java.awt.Graphics)
     * @see GamePanel#paintGame(java.awt.Graphics)
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
    
    /**
     * Sets the game to be painted to given game. Re-initializes the coordinate
     * translator and painters attached to this object.
     * 
     * @param game game to draw
     */
    public void setGame(Game game) {
        this.game = game;
        this.uipainter.setGame(game);
        this.setTranslator(new CoordinateTranslator(game, width, height));
    }

    /**
     * Changes the translator that is to be used to paint to a new one. Also
     * changes it for attached painters.
     * 
     * @param translator translator to change to
     */
    public void setTranslator(CoordinateTranslator translator) {
        this.translator = translator;
        this.uipainter.setTranslator(translator);
        this.bgpainter.setTranslator(translator);
    }
    
}
