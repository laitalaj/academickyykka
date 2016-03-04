package org.kyykka.logic;

import java.util.ArrayList;
import java.util.List;
import org.kyykka.logic.object.Karttu;
import org.kyykka.logic.object.PhysicsEntity;
import org.kyykka.logic.shape.Point3D;

/**
 * Trajectory calculator contains functions with which one can calculate where a
 * PhysicsEntity with certain parameters will land.
 *
 * @author Admin
 */
public class TrajectoryCalculator {

    /**
     * Calculates the number of ticks to the landing of given PhysicsEntity.
     *
     * @param e the physics entity which trajectory should be calculated
     *
     * @return number of ticks until first bounce
     */
    public static int calculateLandingTime(PhysicsEntity e) {
        double d = e.getZmom() * e.getZmom() + 2 * e.getZ();
        if (d < 0) {
            return -1;
        }
        d = Math.sqrt(d);
        double time = -e.getZmom() - d;
        time *= -1;
        return (int) time + 1;
    }

    /**
     * Calculates the point where given PhysicsEntity will land.
     *
     * @param e the physics entity which trajectory should be calculated
     *
     * @return landing poisition
     */
    public static Point3D calculateLanding(PhysicsEntity e) {
        int time = calculateLandingTime(e);
//        double t2 = -e.getZmom() - D;
        int x = e.getX() + e.getXmom() * time;
        int y = e.getY() + e.getYmom() * time;
        return new Point3D(x, y, 0);
    }

    /**
     * Calculates the angle given karttu will have when it lands.
     *
     * @param k karttu which landing angle to calculate
     *
     * @return angle of landing
     */
    public static double calculateLandingAngle(Karttu k) {
        int time = calculateLandingTime(k);
        double angle = k.getAngle() + k.getSpin() * time;
        angle %= 360;
        return angle;
    }

    public static double calculateDesiredSpin(Karttu k, double angle) {
        int time = calculateLandingTime(k);
        double spin = (((angle - k.getAngle()) % 360 + 360) % 360) / time;
        return spin;
    }

    public static List<Double> calculateDesiredSpins(Karttu k, double angle,
            double min, double max) {
        List<Double> spins = new ArrayList<>();
        double firstSpin = calculateDesiredSpin(k, angle);
        if (firstSpin < 0) {
            return null;
        }
        double spin = firstSpin;
        while (spin < min) {
            spin += firstSpin;
        }
        while (spin < max) {
            spins.add(spin);
            spin += firstSpin;
        }
        return spins;
    }

}
