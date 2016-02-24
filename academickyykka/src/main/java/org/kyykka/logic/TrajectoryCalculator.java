package org.kyykka.logic;

import org.kyykka.logic.object.PhysicsEntity;
import org.kyykka.logic.shape.Point3D;

/**
 * Trajectory calculator contains functions with which one can calculate where
 * a PhysicsEntity with certain parameters will land.
 * 
 * @author Admin
 */
public class TrajectoryCalculator {
    
    /**
     * Calculates the point where given PhysicsEntity will land.
     * 
     * @param e the physics entity which trajectory should be calculated
     * 
     * @return landing poisition
     */
    public static Point3D calculateLanding(PhysicsEntity e){
        double D = e.getZmom() * e.getZmom() + 2 * e.getZ();
        if(D < 0){
            return null;
        }
        D = Math.sqrt(D);
        double time = -e.getZmom() - D;
        time *= -1;
//        double t2 = -e.getZmom() - D;
        int x = e.getX() + e.getXmom() * (int) time;
        int y = e.getY() + e.getYmom() * (int) time;
        return new Point3D(x, y, 0);
    }
}
