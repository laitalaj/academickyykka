package org.kyykka.io;

import java.util.Comparator;
import org.kyykka.logic.object.PhysicsEntity;

/**
 * Used to sort a list of PhysicsEntities depending on their y-coordinates.
 * After sorting, if used correctly, the first entry in the list should be drawn
 * first (is the furthest away)
 * 
 * @author Julius Laitala
 */
public class DrawOrderComparator implements Comparator<PhysicsEntity>{
    
    private boolean homecam;

    public DrawOrderComparator(boolean homecam) {
        this.homecam = homecam;
    }

    @Override
    public int compare(PhysicsEntity o1, PhysicsEntity o2) {
        if(!homecam){
            return o1.getY() - o2.getY();
        } else {
            return o2.getY() - o1.getY();
        }
    }

    public void setHomecam(boolean homecam) {
        this.homecam = homecam;
    }

    public boolean isHomecam() {
        return homecam;
    }
    
}
