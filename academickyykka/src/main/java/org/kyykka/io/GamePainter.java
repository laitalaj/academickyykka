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

    public GamePainter(int width, int height, Game game, ImageContainer imgs) {
        this.width = width;
        this.height = height;
        this.aspectRatio = (double) height / width;
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
        boolean home = this.game.getActiveTeam().isHomeTeam();
        Point topleft = box.getLowerTopLeft();
        double fovsize;
        if(home){
            fovsize = (1250 + topleft.getY()) * 2;
        } else {
            fovsize = (1250 + (20000 - topleft.getY())) * 2;
        }
        if(fovsize <= 0){
            return null;
        }
        double x = topleft.getX();
        double y = topleft.getZ();
        if(!home){
            x += box.getWidth();
        }
        double translation = (fovsize - 5000) / 2;
        x += translation;
        y += this.aspectRatio * translation;
        x = (x / fovsize) * this.width;
        if(!home){
            x = this.width - x;
        }
        y = this.height - (y / (this.aspectRatio * fovsize)) * this.height;
        double w = this.width * (box.getWidth() / fovsize);
        double h = this.height * (box.getDepth() / (this.aspectRatio * fovsize));
//        if(y < 200){
//            System.out.println(box + "->" + x + ", " + y);
//        }
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
            Rectangle spritepos = getSpritePos(e.getHitBox());
            if(spritepos == null){
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

    @Override
    public void actionPerformed(ActionEvent ae) {
        repaint();
    }

    public DrawOrderComparator getCompar() {
        return compar;
    }

}
