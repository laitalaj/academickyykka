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

    /**
     * Calculates a spin with which the given karttu will be at given angle when
     * it lands.
     *
     * @param k karttu which desired spin should be calculated
     * @param angle angle at which the karttu should land
     *
     * @return spin at which Karttu k will land at angle angle
     */
    public static double calculateDesiredSpin(Karttu k, double angle) {
        int time = calculateLandingTime(k);
        double spin = (((angle - k.getAngle()) % 360 + 360) % 360) / time;
        return spin;
    }

    /**
     * Calculates a sorted list of possible spins with which the given karttu
     * will land at desired angle. All the spins are between min and max values.
     * The minimum value must be larger than 0.
     *
     * @param k karttu which desired spins should be calculated
     * @param angle angle at which a landing is desired
     * @param min minimum spin (exclusive)
     * @param max maximum spin (exclusive)
     *
     * @see
     * TrajectoryCalculator#calculateDesiredSpin(org.kyykka.logic.object.Karttu,
     * double)
     *
     * @return a sorted (from smallest to largest) list of desired spins
     */
    public static List<Double> calculateDesiredSpins(Karttu k, double angle,
            double min, double max) {
        List<Double> spins = new ArrayList<>();
        double firstSpin = calculateDesiredSpin(k, angle);
        if (firstSpin < 0) {
            return null;
        } else if (firstSpin == 0) {
            firstSpin = 0.2;
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
