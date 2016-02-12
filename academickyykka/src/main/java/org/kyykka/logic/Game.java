package org.kyykka.logic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.Timer;
import org.kyykka.logic.object.Karttu;
import org.kyykka.logic.object.Kyykka;
import org.kyykka.logic.object.PhysicsEntity;
import org.kyykka.logic.object.Thrower;
import org.kyykka.logic.player.AIPlayer;
import org.kyykka.logic.player.Player;

/**
 * Handles interaction between various types of physics entities and players,
 * and the winning and losing of the game.
 * Here's where all the game events happen.
 * 
 * @author Julius Laitala
 */
public class Game implements Runnable{
    
    private Thrower activeThrower;
    private Team activeTeam;
    private Player activePlayer;
    private List<Team> teams;
    private int activeTeamIndex;
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
    }
    
    public void tick(){
        //TODO: Collisions, team changes
        this.activeThrower.tick();
        for(Team t: this.teams){
            t.tick();
        }
        for(Karttu k: this.karttus){
            k.tick();
        }
        this.activeThrower.setTarget(this.activePlayer.getTarget());
        if(this.activePlayer.throwReady()){
            this.karttus.add(this.activeThrower.throwKarttu(this.activePlayer.getThrow()));
            this.activeThrower = this.activeTeam.nextThrower();
            this.activePlayer.nextThrower();
        }
    }
    
    public List<PhysicsEntity> getEntities(){
        List<PhysicsEntity> entities = new ArrayList<>();
        entities.add(this.activeThrower);
        for(Team t: this.teams){
            entities.addAll(t.getKyykkas());
        }
        for(Karttu k: this.karttus){
            entities.add(k);
        }
        return entities;
    }
    
    public void nextTeam(){
        this.activeTeamIndex++;
        if(this.activeTeamIndex >= this.teams.size()){
            this.activeTeamIndex = 0;
        }
        this.activeTeam = this.teams.get(this.activeTeamIndex);
    }
    
    @Override
    public void run(){
        //Never stops at the moment
        while(true){
            long time = System.currentTimeMillis();
            tick();
            while(System.currentTimeMillis() - time < 10){
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
