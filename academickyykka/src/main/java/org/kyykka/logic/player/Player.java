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

    Point getTarget();

    boolean throwReady();

    ThrowParams getThrow();

    void nextThrower();

    void endTurn();
}
