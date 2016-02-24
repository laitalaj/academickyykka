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

    /**
     * Creates a new game. Creates new teams and an initial game state.
     */
    public Game() {
        this.teams = new ArrayList<>();
        this.players = new ArrayList<>();
        this.karttus = new ArrayList<>();
        Team home = new Team(true);
        Team away = new Team(false);
        this.teams.add(home);
        this.teams.add(away);
        this.activeTeam = home;
        this.activeThrower = home.nextThrower();
        this.activeTeamIndex = 0;
        this.activePlayerIndex = 0;
        this.karttusThrown = 0;
        this.roundsPlayed = 0;
    }
    
    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * Progresses the game physics and progression by one tick.
     */
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

    private void tickInteraction() {
        boolean karttusActive = karttusAreActive();
        if (this.activeThrower == null) {
            if (!karttusActive) {
                if (karttusThrown >= 4) {
                    this.activeTeam.clearKyykkas();
                    nextTeam();
                    this.activePlayer.endTurn();
                    nextPlayer();
                    this.activePlayer.startTurn();
                    karttusThrown = 0;
                    this.karttus.clear();
                }
                this.activeThrower = this.activeTeam.nextThrower();
                this.activePlayer.nextThrower();
            }
        } else {
            this.activeThrower.setTarget(this.activePlayer.getTarget());
            if(!karttusActive){
                this.activePlayer.tick();
                this.activeThrower.setThrowState(this.activePlayer.getThrowState());
            }
            this.activeThrower.tick();
            if (this.activeThrower.getThrowState() == 3) {
                this.karttus.add(this.activeThrower.throwKarttu(this.activePlayer.getThrow()));
                karttusThrown++;
                this.activeThrower.setThrowState(4);
                if (karttusThrown % 2 == 0) { //Next player always after 2 throws
                    this.activeThrower = null;
                }
            }
        }
    }

    private boolean tickWinstate() {
        //TODO: Winning by getting rid of all kyykkas, team names?
        if (roundsPlayed == 4) {
            int bestScore = Integer.MIN_VALUE;
            Team bestTeam = teams.get(0);
            for (Team t : teams) {
                int score = t.calculateScore();
                if (score > bestScore) {
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

    /**
     * Checks collisisions between all collidable entities and makes them
     * interact if a collision happens.
     *
     * @see PhysicsEntity#collidesWith(org.kyykka.logic.object.PhysicsEntity)
     * @see PhysicsEntity#collide(org.kyykka.logic.object.PhysicsEntity)
     */
    public void collideAll() {
        List<PhysicsEntity> entities = this.getColliders();
        for (PhysicsEntity e1 : entities) {
            for (PhysicsEntity e2 : entities) {
                if (e1.equals(e2)) {
                    continue;
                }
                if (e1.collidesWith(e2)) {
                    e1.collide(e2);
                    e2.collide(e1);
                }
            }
        }
    }

    /**
     * Returns a list of all physics entities in the game. Includes kyykkas,
     * karttus and throwers.
     *
     * @return list of entities in the game
     */
    public List<PhysicsEntity> getEntities() {
        List<PhysicsEntity> entities = new ArrayList<>();
        entities.addAll(this.getColliders());
        if (this.activeThrower != null) {
            entities.add(this.activeThrower);
        }
        return entities;
    }

    private List<PhysicsEntity> getColliders() {
        List<PhysicsEntity> entities = new ArrayList<>();
        for (Team t : this.teams) {
            entities.addAll(t.getKyykkas());
        }
        for (Karttu k : this.karttus) {
            entities.add(k);
        }
        return entities;
    }

    /**
     * Checks if there are karttus that are still moving.
     *
     * @return true if some karttus are still moving, false otherwise.
     */
    public boolean karttusAreActive() {
        for (Karttu k : this.karttus) {
            if (!k.isFrozen()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Changes the active team and counts rounds played.
     */
    public void nextTeam() {
        this.activeTeamIndex++;
        if (this.activeTeamIndex >= this.teams.size()) {
            roundsPlayed++;
            this.activeTeamIndex = 0;
        }
        this.activeTeam = this.teams.get(this.activeTeamIndex);
    }

    /**
     * Changes the active player to the next one.
     */
    public void nextPlayer() {
        this.activePlayerIndex++;
        if (this.activePlayerIndex >= this.players.size()) {
            this.activePlayerIndex = 0;
        }
        this.activePlayer = this.players.get(this.activePlayerIndex);
    }

    /**
     * Runs the game. Ticks 100 times in a second until the game ends.
     *
     * @see tick()
     */
    @Override
    public void run() {
        //TODO: maybe move win check to tick?
        this.activePlayer = this.players.get(0);
        //TODO: Do active player selection somewhere sensible
        while (true) {
            long time = System.currentTimeMillis();
            tick();
            if (tickWinstate()) {
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

    public void setActiveThrower(Thrower activeThrower) {
        this.activeThrower = activeThrower;
    }

    public Team getActiveTeam() {
        return activeTeam;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setKarttusThrown(int karttusThrown) {
        this.karttusThrown = karttusThrown;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

}
