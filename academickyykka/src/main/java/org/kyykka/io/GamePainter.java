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

    public Rectangle getSpritePos(HitBox box) {
        //TODO: Home/away cam
        boolean home = this.game.getActiveTeam().isHomeTeam();
        Point topleft = box.getLowerTopLeft();
        double fovsize = (1250 + topleft.getY()) * 2;
        double x = topleft.getX();
        double y = topleft.getZ();
        double translation = (fovsize - 5000) / 2;
        x += translation;
        y += 0.75 * translation;
        x = (x / fovsize) * this.width;
        y = this.height - (y / (0.75 * fovsize)) * this.height;
        double w = this.width * (box.getWidth() / fovsize);
        double h = this.height * (box.getDepth() / (0.75 * fovsize));
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }

    public void checkCamPos() {
        this.compar.setHomecam(this.game.getActiveTeam().isHomeTeam());
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        checkCamPos();
        List<PhysicsEntity> entities = this.game.getEntities();
        Collections.sort(entities, this.compar);
        for (PhysicsEntity e : entities) {
            Rectangle spritepos = getSpritePos(e.getBoundingBox());
            g.drawImage(this.imgs.getImage(e), spritepos.x, spritepos.y,
                    spritepos.width, spritepos.height, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

    public DrawOrderComparator getCompar() {
        return compar;
    }

}
