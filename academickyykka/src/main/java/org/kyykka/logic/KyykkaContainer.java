/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.util.HashSet;
import java.util.Set;
import org.kyykka.logic.object.Kyykka;

/**
 *
 * @author Admin
 */
public class KyykkaContainer {
    
    private Set<Kyykka> kyykkas;
    
    public KyykkaContainer(boolean homeTeam){
        this.kyykkas = new HashSet<>();
        int y;
        if(homeTeam){
            y = 15000;
        } else {
            y = 5000;
        }
        for(int z = 0; z <= 10; z += 10){
            for(int x = 125; x < 5000; x += 250){
                this.kyykkas.add(new Kyykka(x, y, z));
            }
        }
    }

    public Set<Kyykka> getKyykkas() {
        return kyykkas;
    }
    
    public void tick(){
        //TODO: Kyykkien poistuminen pelialueelta, etc.
        for(Kyykka k: kyykkas){
            k.tick();
        }
    }
    
}
