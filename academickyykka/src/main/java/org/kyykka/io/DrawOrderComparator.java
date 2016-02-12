/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.io;

import java.util.Comparator;
import org.kyykka.logic.object.PhysicsEntity;

/**
 *
 * @author Admin
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
    
}
