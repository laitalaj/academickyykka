package org.kyykka.logic;

import java.util.ArrayList;
import java.util.List;
import org.kyykka.logic.object.Kyykka;
import org.kyykka.logic.object.Thrower;

/**
 * Handles the throwers that a team consists of and contains the kyykkas for the
 * team (in a KyykkaContainer).
 *
 * @author Julius Laitala
 */
public class Team {

    private KyykkaContainer kyykkas;
    private List<Thrower> throwers;
    private int nextThrowerIndex;
    private int initx;
    private int inity;
    private boolean homeTeam;
    private boolean firstKyykkaOut;

    /**
     * Creates a new team. Also creates players for the team.
     *
     * @param homeTeam whether this team is the home team
     */
    public Team(boolean homeTeam) {
        this.homeTeam = homeTeam;
        this.kyykkas = new KyykkaContainer(homeTeam);
        this.initx = 2500;
        if (homeTeam) {
            this.inity = 0;
        } else {
            this.inity = 20000;
        }
        this.throwers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.throwers.add(new Thrower(initx, inity, homeTeam));
        }
        this.nextThrowerIndex = 0;
        this.firstKyykkaOut = false;
    }

    /**
     * Returns the thrower that's next up for throwing. Resets the positions of
     * all throwers to initial.
     *
     * @see resetThrowerPositions()
     *
     * @return thrower that's next up
     */
    public Thrower nextThrower() {
        resetThrowerPositions();
        Thrower thrower = throwers.get(nextThrowerIndex);;
        this.nextThrowerIndex++;
        if (nextThrowerIndex >= this.throwers.size()) {
            nextThrowerIndex = 0;
        }
        return thrower;
    }

    /**
     * Resets the positions of all throwers in this team to initial.
     */
    public void resetThrowerPositions() {
        for (Thrower t : this.throwers) {
            t.setX(initx);
            t.setY(inity);
        }
    }

    /**
     * Calculates the current score of this team.
     *
     * @see KyykkaContainer#calculateScore()
     *
     * @return current score
     */
    public int calculateScore() {
        return this.kyykkas.calculateScore();
    }

    /**
     * Clears kyykkas that are outside the play square.
     *
     * @see KyykkaContainer#clearKyykkas()
     */
    public void clearKyykkas() {
        this.kyykkas.clearKyykkas();
    }

    /**
     * Advances the kyykkas of this team by one physics tick.
     *
     * @see KyykkaContainer#tick()
     */
    public void tick() {
        this.kyykkas.tick();
        if (!firstKyykkaOut && this.kyykkas.anyOutOfBounds()) {
            firstKyykkaOut = true;
            for (Thrower t : this.throwers) {
                if (this.isHomeTeam()) {
                    t.setYLimit(5000);
                } else {
                    t.setYLimit(15000);
                }
            }
        }
    }

    public List<Thrower> getThrowers() {
        return throwers;
    }

    /**
     * Lists all kyykkas of this team.
     *
     * @see KyykkaContainer#getKyykkas()
     *
     * @return list of kyykkas that belong to this team
     */
    public List<Kyykka> getKyykkas() {
        List<Kyykka> kyykkalist = new ArrayList<>(this.kyykkas.getKyykkas());
        return kyykkalist;
    }

    public boolean isHomeTeam() {
        return homeTeam;
    }

    public int getNextThrowerIndex() {
        return nextThrowerIndex;
    }

    public int getInitx() {
        return initx;
    }

    public int getInity() {
        return inity;
    }

}
