/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.util.ArrayList;
import java.util.List;
import org.kyykka.logic.object.Thrower;

/**
 *
 * @author Admin
 */
public class Team {
    
    private KyykkaContainer kyykkas;
    private List<Thrower> throwers;
    private int nextThrowerIndex;
    private int initx;
    private int inity;

    public Team(boolean homeTeam) {
        this.kyykkas = new KyykkaContainer(homeTeam);
        this.initx = 2500;
        if(homeTeam){
            this.inity = 0;
        } else {
            this.inity = 20000;
        }
        this.throwers = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            this.throwers.add(new Thrower(initx, inity));
        }
        this.nextThrowerIndex = 0;
    }
    
    public Thrower nextThrower(){
        resetThrowerPositions();
        Thrower thrower = throwers.get(nextThrowerIndex);;
        this.nextThrowerIndex++;
        if(nextThrowerIndex >= this.throwers.size()){
            nextThrowerIndex = 0;
        }
        return thrower;
    }
    
    public void resetThrowerPositions(){
        for(Thrower t: this.throwers){
            t.setX(initx);
            t.setY(inity);
        }
    }
    
    public void tick(){
        this.kyykkas.tick();
    }

    public List<Thrower> getThrowers() {
        return throwers;
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
