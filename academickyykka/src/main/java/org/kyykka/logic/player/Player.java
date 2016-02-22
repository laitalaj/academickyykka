package org.kyykka.logic.player;

import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point3D;

/**
 * Classes that inherit Player are capable of controlling throwers and thus
 * playing the game.
 *
 * @author Julius Laitala
 */
public interface Player {
    
    /**
     * Takes actions that should be done whenever this player's turn starts.
     */
    void startTurn();
    
    /**
     * Gets a point towards which the thrower controlled by this player should
     * advance.
     *
     * @return a point to which the thrower should go
     */
    Point3D getTarget();

    /**
     * Asserts whether the player is specifying / has determined parameters for
     * a throw. The return values mean the following:
     * 0 - the player is not specifying a throw
     * 1 - the player is specifying throw angle
     * 2 - the player is specifying throw force
     * 3 - the player is ready to throw
     * 4 - the thrower has just thrown and is cooling down (shouldn't be
     * returned by this function - is actually determined by the game instead)
     *
     * @return the throw state
     */
    int getThrowState();

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
