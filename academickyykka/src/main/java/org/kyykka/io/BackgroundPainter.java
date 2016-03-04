package org.kyykka.io;

import java.awt.Color;
import java.awt.Graphics;
import org.kyykka.logic.shape.Point3D;

/**
 * A class that knows how to paint the game's backgorund and playing field.
 * 
 * @author Julius Laitala
 */
public class BackgroundPainter {
    
    private BasicPainter basicpainter;
    private CoordinateTranslator translator;
    
    /**
     * Creates a new BackgroundPainter.
     * 
     * @param translator translator to be used in translations from game coords
     * to screen coords
     */
    public BackgroundPainter(CoordinateTranslator translator) {
        this.basicpainter = new BasicPainter(translator);
        this.translator = translator;
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
        this.basicpainter.drawLine(homefrontleft, awayfrontright, g);
        this.basicpainter.drawLine(homefrontright, awayfrontleft, g);
        g.drawLine(0, this.translator.getHeight() / 2, 
                this.translator.getWidth(), this.translator.getHeight() / 2);
        g.setColor(Color.GREEN);
        this.basicpainter.drawLine(homebackleft, homebackright, g);
        this.basicpainter.drawLine(homebackleft, homefrontleft, g);
        this.basicpainter.drawLine(homebackright, homefrontright, g);
        this.basicpainter.drawLine(homefrontleft, homefrontright, g);
        g.setColor(Color.ORANGE);
        this.basicpainter.drawLine(awayfrontleft, awayfrontright, g);
        this.basicpainter.drawLine(awayfrontleft, awaybackleft, g);
        this.basicpainter.drawLine(awayfrontright, awaybackright, g);
        this.basicpainter.drawLine(awaybackleft, awaybackright, g);
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
