/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.kyykka.logic.object.Karttu;
import org.kyykka.logic.object.Kyykka;
import org.kyykka.logic.object.Thrower;
import org.kyykka.logic.player.AIPlayer;
import org.kyykka.logic.player.Player;

/**
 *
 * @author Admin
 */
public class Game {
    
    private Thrower activeThrower;
    private Team activeTeam;
    private Player activePlayer;
    private List<Team> teams;
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

    public Thrower getActiveThrower() {
        return activeThrower;
    }

    public Team getActiveTeam() {
        return activeTeam;
    }

}
