package org.kyykka.io;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.event.MouseInputListener;

/**
 * The Input class handles - as the name suggests - player input.
 *
 * @author Admin
 */
public class Input implements MouseInputListener {

    private Display display;
    private Point mousePos;
    private boolean isHeld;
    private int pendingClicks;
    
    /**
     * Creates a new Input class and links it to given display.
     * 
     * @param display display to be linked to
     */
    public Input(Display display) {
        this.display = display;
        this.mousePos = MouseInfo.getPointerInfo().getLocation();
        this.isHeld = false;
        this.pendingClicks = 0;
    }
    
    /**
     * Decreases the amount of pending clicks by one.
     */
    public void processClick() {
        this.pendingClicks--;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    /**
     * Increases the number of pending clicks by one, sets isHeld to true.
     * 
     * @param e the MouseEvent that happened
     */
    @Override
    public void mousePressed(MouseEvent e) {
        this.pendingClicks++;
        this.isHeld = true;
    }
    
    /**
     * Sets isHeld to false.
     * 
     * @param e the MouseEvent that happened
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        this.isHeld = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    /**
     * Updates mouse position.
     * 
     * @param e the MouseEvent that happened
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        this.mousePos = e.getPoint();
    }
    
    /**
     * Updates mouse position.
     * 
     * @param e the MouseEvent that happened
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        this.mousePos = e.getPoint();
    }

    public boolean isHeld() {
        return isHeld;
    }

    public Point getMousePos() {
        return mousePos;
    }

    public void setMousePos(Point mousePos) {
        this.mousePos = mousePos;
    }

    public int getPendingClicks() {
        return pendingClicks;
    }

    public void setPendingClicks(int pendingClicks) {
        this.pendingClicks = pendingClicks;
    }

    public void setIsHeld(boolean isHeld) {
        this.isHeld = isHeld;
    }

}
