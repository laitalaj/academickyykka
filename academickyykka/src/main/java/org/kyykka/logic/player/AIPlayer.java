package org.kyykka.logic.player;

import java.util.Random;
import org.kyykka.logic.Game;
import org.kyykka.logic.object.ThrowParams;
import org.kyykka.logic.shape.Point;

/**
 * An AI controlled player. Plays the game using it's electronic intelligence
 * with varying degrees of success.
 *
 * @author Julius Laitala
 */
public class AIPlayer implements Player {

    private Game game;
    private boolean homeTeam;
    private Random random;
    private Point target;

    public AIPlayer(Game game, boolean homeTeam) {
        this.game = game;
        this.homeTeam = homeTeam;
        this.random = new Random();
        generateTarget();
    }

    public void generateTarget() {
        int tarx = this.random.nextInt(5000);
        int tary = this.random.nextInt(5000);
        if (!homeTeam) {
            tary = 20000 - tary;
        }
        this.target = new Point(tarx, tary, 0);
    }

    @Override
    public Point getTarget() {
        return this.target;
    }

    @Override
    public boolean throwReady() {
        int dist = this.target.getDistance(this.game.getActiveThrower().getBoundingBox().getBottomCenter());
        return dist < 250;
//        return this.game.getActiveThrower().getPos().equals(this.target);
//        return this.game.getActiveThrower().getXmom() == 0
//                && this.game.getActiveThrower().getYmom() == 0;
    }

    @Override
    public ThrowParams getThrow() {
        // TODO: Actual aiming
        int angle = -90 + this.random.nextInt(180);
        int force = 20 + this.random.nextInt(100);
        return new ThrowParams(angle, force);
    }

    @Override
    public void nextThrower() {
        generateTarget();
    }

    @Override
    public void endTurn() {
        return;
    }

}
