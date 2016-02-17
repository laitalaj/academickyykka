package org.kyykka.logic;

import java.util.HashSet;
import java.util.Set;
import org.kyykka.logic.object.Kyykka;
import org.kyykka.logic.shape.Point;

/**
 * Contains a set of kyykkas for a team. Handles some interaction with kyykkas.
 *
 * @author Julius Laitala
 */
public class KyykkaContainer {

    private Set<Kyykka> kyykkas;
    private boolean homeTeam;

    public KyykkaContainer(boolean homeTeam) {
        this.kyykkas = new HashSet<>();
        this.homeTeam = homeTeam;
        int y;
        if (homeTeam) {
            y = 15000;
        } else {
            y = 4840;
        }
        for (int z = 0; z <= 200; z += 190) {
            for (int x = 125; x < 5000; x += 250) {
                this.kyykkas.add(new Kyykka(x, y, z));
            }
        }
    }
    
    public boolean isOutOfBounds(Kyykka k){
        int minx = 0;
        int maxx = 5000;
        int miny = 0;
        int maxy = 5000;
        if(this.homeTeam){
            miny += 15000;
            maxy += 15000;
        }
        Point center = k.getHitBox().getCenter();
        boolean ans = center.getX() < minx || center.getX() > maxx;
        ans = ans || center.getY() < miny || center.getY() > maxy;
        return ans;
    }
    
    public int calculateKyykkasPoints(Kyykka k){
        int leftEdge = 100;
        int rightEdge = 4900;
        int frontEdge = 4900;
        int backEdge = 100;
        if(this.homeTeam){
            frontEdge += 10000;
            backEdge += 19800;
        }
        //No negative score for kyykkas outside the box
        if(isOutOfBounds(k)){
            return 0;
        }
        Point p = k.getHitBox().getCenter();
        //"Akka" or "Kuokkavieras", a kyykka on the front line
        //or one between the play squares, means -2 points
        //A kyykka on the back line is a "Pappi" and means -1 point
        if(!this.homeTeam){
            if(p.getY() > frontEdge){
                return -2;
            }
            if(p.getY() < backEdge){
                return -1;
            }
        } else {
            if(p.getY() < frontEdge){
                return -2;
            }
            if(p.getY() > backEdge){
                return -1;
            }
        }
        //A kyykka on a side border is also a "Pappi"
        if(p.getX() < leftEdge || p.getX() > rightEdge){
            return -1;
        }
        //If none of these happen, the kyykka is an "Akka".
        return -2;
    }
    
    public void clearKyykkas(){
        //TODO: Fix this! Write tests!
        HashSet<Kyykka> toClear = new HashSet<>();
        for(Kyykka k: kyykkas){
            if(isOutOfBounds(k)){
                toClear.add(k);
            }
        }
        for(Kyykka k: toClear){
            kyykkas.remove(k);
        }
    }
    
    public int calculateScore(){
        int score = 0;
        for(Kyykka k: kyykkas){
            score += calculateKyykkasPoints(k);
        }
        return score;
    }

    public Set<Kyykka> getKyykkas() {
        return kyykkas;
    }

    public void tick() {
        for (Kyykka k : kyykkas) {
            k.tick();
        }
    }

}
