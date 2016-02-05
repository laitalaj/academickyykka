/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka;

import org.kyykka.logic.object.Thrower;

/**
 *
 * @author Admin
 */
public class Main {
    
    //Currently for debugging purposes only
    
    public static void main(String[] args){
        Thrower t = new Thrower(20, 20);
        t.setTarget(700, -200);
        t.tick();
    }
}
