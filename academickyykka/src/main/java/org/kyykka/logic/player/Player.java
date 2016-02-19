package org.kyykka.logic.player;

import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point;

/**
 * Classes that inherit Player are capable of controlling throwers and thus
 * playing the game.
 *
 * @author Julius Laitala
 */
public interface Player {

    /**
     * Gets a point towards which the thrower controlled by this player should
     * advance.
     *
     * @return a point to which the thrower should go
     */
    Point getTarget();

    /**
     * Asserts whether the player has determined parameters for a throw.
     *
     * @return true if parameters for a throw are ready, false otherwise
     */
    boolean throwReady();

    /**
     * Generates throw parameters based on player decisions.
     *
     * @return parameters for a throw
     */
    ThrowParams getThrow();

    /**
     * Takes actions that should be done whenever the thrower changes.
     */
    void nextThrower();

    /**
     * Takes actions that should be done whenever the player changes.
     */
    void endTurn();
}
