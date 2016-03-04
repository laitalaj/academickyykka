package org.kyykka.logic.object;

import org.kyykka.graphics.sprite.ThrowerSprite;
import org.kyykka.logic.shape.Point3D;

/**
 * Thrower is the guy throwing the karttu, the character playing kyykka.
 *
 * @author Julius Laitala
 */
public class Thrower extends PhysicsEntity {
    
    private Point3D target;
    private int yLimit;
    private int throwState;
    private boolean homeTeam;

    /**
     * Creates a thrower with specified position and allegiance.
     *
     * @param x x-position of the thrower
     * @param y y-position of the thrower
     * @param homeTeam whether this thrower belongs to the home team or not
     */
    public Thrower(int x, int y, boolean homeTeam) {
        super(x, y, 0, 1000, 300, 1500, 80000);
        this.target = new Point3D(2500, 0, 0);
        this.homeTeam = homeTeam;
        this.throwState = 0;
        if (homeTeam) {
            this.yLimit = 0;
        } else {
            this.yLimit = 20000;
        }
        this.setFrozen(false);
        this.setSprite(new ThrowerSprite(this));
    }

    /**
     * Creates a thrower with specified position. Defaults to a hometeam
     * thrower.
     *
     * @param x x-position of the thrower
     * @param y y-position of the thrower
     */
    public Thrower(int x, int y) {
        this(x, y, true);
    }

    /**
     * Sets the coordinates to which the thrower tries to move.
     *
     * @param x x-coordinate to be moved to
     * @param y y-coordinate to be moved to
     */
    public void setTarget(int x, int y) {
        if (x > 5000) {
            this.target.setX(5000);
        } else if (x < 0) {
            this.target.setX(0);
        } else {
            this.target.setX(x);
        }
        if ((y > this.yLimit && this.homeTeam) || (y < this.yLimit && !this.homeTeam)) {
            this.target.setY(yLimit);
        } else if (y < 0 && this.homeTeam) {
            this.target.setY(0);
        } else if (y > 20000 && !this.homeTeam) {
            this.target.setY(20000);
        } else {
            this.target.setY(y);
        }
    }

    /**
     * Sets the coordinates to which the thrower tries to move.
     *
     * @param p point to be moved to, z-coordinate should be 0
     */
    public void setTarget(Point3D p) {
        setTarget(p.getX(), p.getY());
    }

    /**
     * Calculates the speed with which the thrower should move towards its
     * target (the thrower slows down when close to the point).
     *
     * @return the speed with which the thrower should move
     */
    public int calculateNextSpeed() {
        int distance = this.getPos().getDistance(this.target);
        if (distance >= 2000) {
            return 40;
        } else {
            double speed = 40 * ((double) distance / 2000);
            if (speed < 5) {
                speed = 5;
            }
            if (distance < speed) {
                speed = distance;
            }
            return (int) speed;
        }
    }

    /**
     * Updates the thrower's speed according to it's distance to its target.
     *
     * @see org.kyykka.logic.object.Thrower#calculateNextSpeed()
     */
    public void updateSpeed() {
        int vx = this.target.getX() - this.getPos().getX();
        int vy = this.target.getY() - this.getPos().getY();
        if (vx == 0 && vy == 0) {
            this.setXmom(0);
            this.setYmom(0);
            return;
        }
        int nextspeed = calculateNextSpeed();
        if (vx == 0) {
            this.setYmom(nextspeed * (int) Math.signum(vy));
            this.setXmom(0);
        } else {
            double ratio = (double) Math.abs(vy) / Math.abs(vx);
            double xmom = (nextspeed / Math.sqrt(ratio * ratio + 1)) * Math.signum(vx);
            //Calculations are basically Pythagorean theorem
            double ymom = Math.abs(xmom) * ratio * Math.signum(vy);
            this.setXmom((int) xmom);
            this.setYmom((int) ymom);
        }
    }

    /**
     * Creates a karttu at the throwers position with given initial angle, force
     * and z-direction momentum.
     *
     * @param angle angle of the throw in degrees (0 = straight ahead)
     * @param force velocity of the throw in mm / cs
     * @param zangle the z-direction angle of the throw (0 = straight ahead)
     * @param spin the spin of the karttu in degrees/cs
     *
     * @return the karttu that was thrown
     */
    public Karttu throwKarttu(int angle, int force, double zangle, double spin) {
        double angleradians = Math.toRadians(angle);
        double zangleradians = Math.toRadians(zangle);
        double zmom = force * Math.sin(zangleradians);
        double levelforce = force * Math.cos(zangleradians);
        double xmom = levelforce * Math.sin(angleradians);
        double ymom = levelforce * Math.cos(angleradians);
        if (!homeTeam) {
            ymom *= -1;
        }
        Point3D throwpos = this.getHitBox().getCenter();
        if (homeTeam) {
            throwpos.moveY(this.getHitBox().getHeight() / 2 + 100);
        } else {
            throwpos.moveY(-this.getHitBox().getHeight() / 2 - 100);
        }
        return new Karttu(throwpos.getX(), throwpos.getY(), throwpos.getZ(),
                (int) xmom, (int) ymom, (int) zmom, spin);
    }

    /**
     * Creates a karttu at the throwers position with given initial parameters.
     *
     * @param p the parameters of the throw
     *
     * @see org.kyykka.logic.object.ThrowParams
     *
     * @return the karttu that was thrown
     */
    public Karttu throwKarttu(ThrowParams p) {
        return this.throwKarttu(p.getAngle(), p.getForce(), p.getZangle(), p.getSpin());
    }

    /**
     * Override of PhysicsEntity's setFrozen; always sets frozen to false.
     *
     * @param frozen does nothing, necessary for the override
     */
    @Override
    public void setFrozen(boolean frozen) {
        super.setFrozen(false);
    }

    /**
     * Updates the thrower by one tick. Updates its speed and moves it.
     *
     * @see org.kyykka.logic.object.Thrower#updateSpeed()
     * @see org.kyykka.logic.object.PhysicsEntity#move()
     */
    @Override
    public void tick() {
        updateSprite();
        if (this.throwState == 0) {
            updateSpeed();
            move();
        }
    }
    
    @Override
    public void move() {
        this.moveX(this.getXmom());
        this.moveY(this.getYmom());
        this.moveZ(this.getZmom());
    }

    /**
     * Gets the bottom center point of this thrower.
     *
     * @see HitBox#getBottomCenter()
     *
     * @return bottom center point of the throwers hitbox
     */
    public Point3D getPos() {
        return this.getHitBox().getBottomCenter();
    }
    
    public int getThrowState() {
        return throwState;
    }
    
    public void setThrowState(int throwState) {
        this.throwState = throwState;
    }
    
    public boolean isHomeTeam() {
        return homeTeam;
    }
    
    public void setHomeTeam(boolean homeTeam) {
        this.homeTeam = homeTeam;
    }

    public int getYLimit() {
        return yLimit;
    }
    
    public void setYLimit(int yLimit) {
        this.yLimit = yLimit;
    }
    
}
