/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.player;

import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point;

/**
 *
 * @author Admin
 */
public interface Player {
    
    Point getTarget();
    
    boolean throwReady();
    
    ThrowParams getThrow();
    
    void nextThrower();
    
    void endTurn();
}
