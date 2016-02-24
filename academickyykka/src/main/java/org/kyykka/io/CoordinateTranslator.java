/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.awt.Point;
import java.awt.Rectangle;
import org.kyykka.logic.Game;
import org.kyykka.logic.shape.HitBox;
import org.kyykka.logic.shape.Point3D;

/**
 *
 * @author Admin
 */
public class CoordinateTranslator {
    
    private Game game;
    private int width;
    private int height;
    private double aspectRatio;

    public CoordinateTranslator(Game game, int width, int height) {
        this.game = game;
        this.width = width;
        this.height = height;
        this.aspectRatio = (double) height / width;
    }
    
    public double calculateFovsize(Point3D point){
        boolean home = this.game.getActiveTeam().isHomeTeam();
        double fovsize;
        if (home) {
            fovsize = (1250 + point.getY()) * 2;
        } else {
            fovsize = (1250 + (20000 - point.getY())) * 2;
        }
        return fovsize;
    }

    /**
     * Trandsorms a point thats set into game coordinates into a point that's
     * set to window coordinates.
     *
     * @param point the point to be transformed (game coordinates)
     *
     * @return transformed point (window coordinates)
     */
    public Point getPointPos(Point3D point) {
        boolean home = this.game.getActiveTeam().isHomeTeam();
        double fovsize = calculateFovsize(point);
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
        return new Point((int) x, (int) y);
    }
    
    public Point3D getPointPos(Point point){
        int horizon = this.height / 2 - this.height / 100;
        if(point.y < horizon){
            return null;
        }
        int y;
        if(2*point.y - this.height == 0){
            y = 0;
        }else{
            y = (3750*this.height - 2500*point.y)/(2*point.y - this.height);
        }
        if(!getHomeTeam()){
            y = 20000 - y;
        }
        Point3D gamepoint = new Point3D(0, y, 0);
        double fovsize = calculateFovsize(gamepoint);
        double ratio = (double) point.x / this.width;
        double x = ratio * fovsize - (fovsize - 5000)/2;
        if(!getHomeTeam()){
            x = 5000 - x;
        }
        gamepoint.setX((int) x);
        return gamepoint;
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
        Point3D topleft = box.getCenterTopLeft();
        double fovsize = calculateFovsize(topleft);
        Point topleft2D = getPointPos(topleft);
        if (topleft2D == null) {
            return null;
        }
        double w = this.width * (box.getWidth() / fovsize);
        double h = this.height * (box.getDepth() / (this.aspectRatio * fovsize));
        if (!home) {
            topleft2D.move(topleft2D.x - (int) w, topleft2D.y);
        }
        return new Rectangle(topleft2D.x, topleft2D.y, (int) w, (int) h);
    }

    /**
     * Returns a rectangle in window coordinates that corresponds to the
     * position of given boxes shadow.
     *
     * @param box box which shadow to calculate
     *
     * @return rectangle of shadow's position in window coordinates
     */
    public Rectangle getShadowPos(HitBox box) {
        boolean home = this.game.getActiveTeam().isHomeTeam();
        Point3D lowerleft = box.getLocation().copy();
        Point3D upperleft = box.getUpperBottomLeft();
        lowerleft.setZ(0);
        upperleft.setZ(0);
        Point3D main = null;
        Point3D secondary = null;
        if(home){
            main = lowerleft;
            secondary = upperleft;
        } else {
            main = upperleft;
            secondary = lowerleft;
        }
        double fovsize = calculateFovsize(main);
        Point main2D = getPointPos(main);
        Point secondary2D = getPointPos(secondary);
        if (main2D == null) {
            return null;
        }
        double w = this.width * (box.getWidth() / fovsize);
        double h = main2D.getY() - secondary2D.getY();
        if (!home) {
            main2D.move(main2D.x - (int) w, main2D.y);
        }
        return new Rectangle(main2D.x, main2D.y - (int) h, (int) w, (int) h);
    }
    
    public boolean getHomeTeam(){
        return this.game.getActiveTeam().isHomeTeam();
    }

}
