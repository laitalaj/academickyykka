package org.kyykka.logic;

import java.util.ArrayList;
import java.util.List;
import org.kyykka.logic.object.Karttu;
import org.kyykka.logic.object.PhysicsEntity;
import org.kyykka.logic.object.Thrower;
import org.kyykka.logic.player.AIPlayer;
import org.kyykka.logic.player.Player;

/**
 * Handles interaction between various types of physics entities and players,
 * and the winning and losing of the game. Here's where all the game events
 * happen.
 *
 * @author Julius Laitala
 */
public class Game implements Runnable {

    private Thrower activeThrower;
    private Team activeTeam;
    private Player activePlayer;
    private List<Team> teams;
    private int activeTeamIndex;
    private int activePlayerIndex;
    private int karttusThrown;
    private int roundsPlayed;
    private List<Player> players;
    private List<Karttu> karttus;

    public Game() {
        this.teams = new ArrayList<>();
        this.players = new ArrayList<>();
        this.karttus = new ArrayList<>();
        Team home = new Team(true);
        Team away = new Team(false);
        this.teams.add(home);
        this.teams.add(away);
        //PLACEHOLDER
        Player homeplayer = new AIPlayer(this, true);
        Player awayplayer = new AIPlayer(this, false);
        this.players.add(homeplayer);
        this.players.add(awayplayer);
        this.activeTeam = home;
        this.activePlayer = homeplayer;
        this.activeThrower = home.nextThrower();
        this.activeTeamIndex = 0;
        this.activePlayerIndex = 0;
        this.karttusThrown = 0;
        this.roundsPlayed = 0;
    }

    public void tick() {
        collideAll();
        for (Team t : this.teams) {
            t.tick();
        }
        for (Karttu k : this.karttus) {
            k.tick();
        }
        tickInteraction();
    }
    
    private void tickInteraction(){
        if(this.activeThrower == null){
            if(!karttusAreActive()){
                if(karttusThrown >= 4){
                    this.activeTeam.clearKyykkas();
                    nextTeam();
                    nextPlayer();
                    karttusThrown = 0;
                    this.karttus.clear();
                }
                this.activeThrower = this.activeTeam.nextThrower();
                this.activePlayer.nextThrower();
            }
        }else{
            this.activeThrower.setTarget(this.activePlayer.getTarget());
            this.activeThrower.tick();
            if (this.activePlayer.throwReady()) {
                this.karttus.add(this.activeThrower.throwKarttu(this.activePlayer.getThrow()));
                karttusThrown++;
                if(karttusThrown % 2 == 0){ //Next player always after 2 throws
                    this.activeThrower = null;
                }
            }
        }
    }
    
    private boolean tickWinstate(){
        //TODO: Winning by getting rid of all kyykkas, team names?
        if(roundsPlayed == 2){
            int bestScore = Integer.MIN_VALUE;
            Team bestTeam = teams.get(0);
            for(Team t: teams){
                int score = t.calculateScore();
                if(score > bestScore){
                    bestScore = score;
                    bestTeam = t;
                }
            }
            System.out.println("Team " + bestTeam.isHomeTeam() + " wins with " 
                    + bestScore + " points!");
            return true;
        }
        return false;
    }
    
    public void collideAll(){
        List<PhysicsEntity> entities = this.getEntities();
        for(PhysicsEntity e1: entities){
            for(PhysicsEntity e2: entities){
                if(e1.equals(e2)){
                    continue;
                }
                if(e1.collidesWith(e2)){
                    e1.collide(e2);
                }
            }
        }
    }
    
    public List<PhysicsEntity> getEntities() {
        List<PhysicsEntity> entities = new ArrayList<>();
        if(this.activeThrower != null){
            entities.add(this.activeThrower);
        }
        for (Team t : this.teams) {
            entities.addAll(t.getKyykkas());
        }
        for (Karttu k : this.karttus) {
            entities.add(k);
        }
        return entities;
    }
    
    public boolean karttusAreActive(){
        for(Karttu k: this.karttus){
            if(!k.isFrozen()){
                return true;
            }
        }
        return false;
    }

    public void nextTeam() {
        this.activeTeamIndex++;
        if (this.activeTeamIndex >= this.teams.size()) {
            roundsPlayed++;
            this.activeTeamIndex = 0;
        }
        this.activeTeam = this.teams.get(this.activeTeamIndex);
    }
    
    public void nextPlayer() {
        this.activePlayerIndex++;
        if (this.activePlayerIndex >= this.players.size()) {
            this.activePlayerIndex = 0;
        }
        this.activePlayer = this.players.get(this.activePlayerIndex);
    }

    @Override
    public void run() {
        //TODO: maybe move win check to tick?
        while (true) {
            long time = System.currentTimeMillis();
            tick();
            if(tickWinstate()){
                break;
            }
            while (System.currentTimeMillis() - time < 10) {
                continue;
            }
        }
    }

    public Thrower getActiveThrower() {
        return activeThrower;
    }

    public Team getActiveTeam() {
        return activeTeam;
    }

}
