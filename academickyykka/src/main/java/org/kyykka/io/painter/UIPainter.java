package org.kyykka.io.painter;

import org.kyykka.io.painter.BasicPainter;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;
import org.kyykka.io.CoordinateTranslator;
import org.kyykka.logic.Game;
import org.kyykka.logic.TrajectoryCalculator;
import org.kyykka.logic.object.Karttu;
import org.kyykka.logic.object.PhysicsEntity;
import org.kyykka.logic.player.Player;
import org.kyykka.logic.shape.Point3D;

/**
 * The UI painter draws aiming-relevant information.
 * 
 * @author Julius Laitala
 */
public class UIPainter {
    
    private Game game;
    private CoordinateTranslator translator;
    private BasicPainter basicpainter;
    
    /**
     * Creates a new UIPainter.
     * 
     * @param game game which UI to draw
     * @param translator translator to be used in translations from game coords
     * to screen coords
     */
    public UIPainter(Game game, CoordinateTranslator translator){
        this.game = game;
        this.translator = translator;
        this.basicpainter = new BasicPainter(this.translator);
    }
    
    private void paintAngle(Graphics g){
        double angleradians = Math.toRadians(this.game.getActivePlayer().getAngle());
        double x = 2000 * Math.sin(angleradians);
        double y = 2000 * Math.cos(angleradians);
        if (!this.game.getActiveTeam().isHomeTeam()) {
            y *= -1;
        }
        Point3D p = this.game.getActiveThrower().getPos();
        Point pScreenPos = this.translator.getPointPos(p);
        if (pScreenPos.y > this.translator.getHeight()) {
            p.moveZ(this.game.getActiveThrower().getHitBox().getDepth() / 2);
        }
        Point3D p2 = p.copy();
        p2.moveX((int) x);
        p2.moveY((int) y);
        g.setColor(Color.red);
        this.basicpainter.drawLine(p, p2, g);
    }
    
    private void paintTarget(Graphics g){
        Player active = this.game.getActivePlayer();
        PhysicsEntity dummy = this.game.getActiveThrower().throwKarttu(active.getAngle(),
                active.getForce(), active.getZangle(), 0);
        Point3D landingpos = TrajectoryCalculator.calculateLanding(dummy);
        Point screenpos = this.translator.getPointPos(landingpos);
        g.setColor(Color.BLUE);
        g.fillOval(screenpos.x, screenpos.y,
                this.translator.getWidth() / 100, 
                this.translator.getHeight() / 100);
    }
    
    private void paintSpin(Graphics g){
        Player active = this.game.getActivePlayer();
        Karttu dummy = this.game.getActiveThrower().throwKarttu(active.getAngle(),
                active.getForce(), active.getZangle(), active.getSpin());
        List<Double> spins = TrajectoryCalculator.calculateDesiredSpins(dummy,
                180, 1, 7);
        Point3D topLeftGame = this.game.getActiveThrower().getHitBox().getLocation().copy();
        Point topleft = this.translator.getPointPos(topLeftGame);
        if (topleft.y > this.translator.getHeight()) {
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
    
    /**
     * Paints UI elements relevant to the active player (the state of given
     * players aim and force, whichever is currently happening).
     *
     * @param g graphics object to be drawn on
     */
    public void paintUI(Graphics g) {
        Player active = this.game.getActivePlayer();
        switch (active.getThrowState()) {
            case 1:
                paintAngle(g);
                break;
            case 2:
                paintTarget(g);
                break;
            case 3:
                paintSpin(g);
                break;
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Changes the translator that is to be used to paint to a new one. Also
     * changes it for attached BasicPainter.
     * 
     * @param translator translator to change to
     */
    public void setTranslator(CoordinateTranslator translator) {
        this.basicpainter.setTranslator(translator);
        this.translator = translator;
    }
    
}
