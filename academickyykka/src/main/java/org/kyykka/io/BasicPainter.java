package org.kyykka.io;

import java.awt.Graphics;
import java.awt.Point;
import org.kyykka.logic.shape.Point3D;

/**
 * BasicPainter handles drawing simple geometrical shapes that are set in game
 * coordinates.
 * 
 * @author Julius Laitala
 */
public class BasicPainter {
    
    private CoordinateTranslator translator;
    
    /**
     * Creates a new BasicPainter.
     * 
     * @param translator translator to be used in translations from game coords
     * to screen coords
     */
    public BasicPainter(CoordinateTranslator translator) {
        this.translator = translator;
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

    public void setTranslator(CoordinateTranslator translator) {
        this.translator = translator;
    }

}
