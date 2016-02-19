package org.kyykka.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.kyykka.logic.object.Kyykka;
import org.kyykka.logic.shape.Point;

/**
 * Contains a set of kyykkas for a team. Handles some interaction with kyykkas.
 *
 * @author Julius Laitala
 */
public class KyykkaContainer {

    private List<Kyykka> kyykkas;
    private boolean homeTeam;

    /**
     * Creates a new KyykkaContainer and sets up kyykkas.
     *
     * @param homeTeam whether the team this container belongs to is the home
     * team
     */
    public KyykkaContainer(boolean homeTeam) {
        this.kyykkas = new ArrayList<>();
        this.homeTeam = homeTeam;
        int y;
        if (homeTeam) {
            y = 15000;
        } else {
            y = 4840;
        }
        for (int x = 125; x < 5000; x += 250) {
            Kyykka under = new Kyykka(x, y, 0);
            Kyykka over = new Kyykka(x, y, 200);
            over.setLink(under);
            this.kyykkas.add(under);
            this.kyykkas.add(over);
        }
    }

    /**
     * Checks if a kyykka is outside the play square.
     *
     * @param k kyykka to be checked
     *
     * @return true if the kyykka is outside the play square, false otherwise
     */
    public boolean isOutOfBounds(Kyykka k) {
        int minx = 0;
        int maxx = 5000;
        int miny = 0;
        int maxy = 5000;
        if (this.homeTeam) {
            miny += 15000;
            maxy += 15000;
        }
        Point center = k.getHitBox().getCenter();
        boolean ans = center.getX() < minx || center.getX() > maxx;
        ans = ans || center.getY() < miny || center.getY() > maxy;
        return ans;
    }

    /**
     * Calculates how many negative points a kyykka is worth. A kyykka on the
     * front line or inside the square is worth -2 points, one on any other line
     * is worth -1 points and one outside the square is worth 0 points.
     *
     * @param k kyykka to be checked
     *
     * @return amount of points the kyykka is worth
     */
    public int calculateKyykkasPoints(Kyykka k) {
        int leftEdge = 100;
        int rightEdge = 4900;
        int frontEdge = 4900;
        int backEdge = 100;
        if (this.homeTeam) {
            frontEdge += 10000;
            backEdge += 19800;
        }
        //No negative score for kyykkas outside the box
        if (isOutOfBounds(k)) {
            return 0;
        }
        Point p = k.getHitBox().getCenter();
        //"Akka" or "Kuokkavieras", a kyykka on the front line
        //or one between the play squares, means -2 points
        //A kyykka on the back line is a "Pappi" and means -1 point
        if (!this.homeTeam) {
            if (p.getY() > frontEdge) {
                return -2;
            }
            if (p.getY() < backEdge) {
                return -1;
            }
        } else {
            if (p.getY() < frontEdge) {
                return -2;
            }
            if (p.getY() > backEdge) {
                return -1;
            }
        }
        //A kyykka on a side border is also a "Pappi"
        if (p.getX() < leftEdge || p.getX() > rightEdge) {
            return -1;
        }
        //If none of these happen, the kyykka is an "Akka".
        return -2;
    }

    /**
     * Clears all the kyykkas that are outside the play square.
     *
     * @see KyykkaContainer#isOutOfBounds(org.kyykka.logic.object.Kyykka)
     */
    public void clearKyykkas() {
        HashSet<Kyykka> toClear = new HashSet<>();
        for (Kyykka k : kyykkas) {
            if (isOutOfBounds(k)) {
                toClear.add(k);
            }
        }
        for (Kyykka k : toClear) {
            kyykkas.remove(k);
        }
    }

    /**
     * Checks the value total of all kyykkas.
     *
     * @see
     * KyykkaContainer#calculateKyykkasPoints(org.kyykka.logic.object.Kyykka)
     *
     * @return total score from kyykkas
     */
    public int calculateScore() {
        int score = 0;
        for (Kyykka k : kyykkas) {
            score += calculateKyykkasPoints(k);
        }
        return score;
    }

    public List<Kyykka> getKyykkas() {
        return kyykkas;
    }

    /**
     * Updates the physics of all kyykkas by one tick.
     *
     * @see Kyykka#tick()
     */
    public void tick() {
        for (Kyykka k : kyykkas) {
            k.tick();
        }
    }

    public void setHomeTeam(boolean homeTeam) {
        this.homeTeam = homeTeam;
    }

}
