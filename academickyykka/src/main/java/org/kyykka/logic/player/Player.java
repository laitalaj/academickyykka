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
     * Updates the player's state.
     */
    public void tick();

    /**
     * Asserts whether the player is specifying / has determined parameters for
     * a throw. The return values mean the following: 0 - the player is not
     * specifying a throw; 1 - the player is specifying throw angle; 2 - the
     * player is specifying throw force; 3 - the player is specifying throw spin;
     * 4 - the player is ready to throw; 5 - the thrower has just thrown and is 
     * cooling down (shouldn't be returned by this function - is actually 
     * determined by the game instead)
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
    
    /**
     * Should return the angle the player is currently aiming towards.
     * 
     * @return angle that's being aimed towards
     */
    int getAngle();
    
    /**
     * Should return the force the player is currently planning to throw with.
     * 
     * @return force that's planned
     */
    int getForce();
    
    /**
     * Should return the Z-momentum that's currently being planned to use in the
     * throw.
     * 
     * @return the planned z-direction momentum
     */
    int getZmom();
    
    /**
     * Should return the spin that's currently being planned to be used in the
     * throw.
     * 
     * @return the planned spin
     */
    double getSpin();
}
