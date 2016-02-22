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
public class Input implements MouseInputListener{
    
    private Display display;
    private Point mousePos;
    private boolean isHeld;
    private int pendingClicks;

    public Input(Display display) {
        this.display = display;
        this.mousePos = MouseInfo.getPointerInfo().getLocation();
        this.isHeld = false;
        this.pendingClicks = 0;
    }
    
    public void processClick(){
        this.pendingClicks--;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.pendingClicks++;
        this.isHeld = true;
    }

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

    @Override
    public void mouseDragged(MouseEvent e) {
        this.mousePos = e.getPoint();
    }

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

    public int getPendingClicks() {
        return pendingClicks;
    }

    public void setPendingClicks(int pendingClicks) {
        this.pendingClicks = pendingClicks;
    }
    
}
