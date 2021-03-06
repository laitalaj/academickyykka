package org.kyykka.logic.shape;

/**
 * A 3D cartesian coordinate.
 *
 * @author Julius Laitala
 */
public class Point3D {

    private int x;
    private int y;
    private int z;

    /**
     * Creates a point with specified parameters.
     *
     * @param x x-position
     * @param y y-position
     * @param z z-position
     */
    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Calculates the cartesian distance between this point and another point.
     *
     * @param p point to which to calculate distance
     *
     * @return the distance
     */
    public int getDistance(Point3D p) {
        double d = Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2) + Math.pow(this.z - p.getZ(), 2);
        return (int) Math.sqrt(d);
    }

    /**
     * Copies this point into a point with identical parameters.
     *
     * @return a point that's a copy of this one
     */
    public Point3D copy() {
        return new Point3D(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + this.x;
        hash = 31 * hash + this.y;
        hash = 31 * hash + this.z;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point3D other = (Point3D) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        if (this.z != other.z) {
            return false;
        }
        return true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Moves the points X-position by given amount (adds the amount to the
     * position).
     *
     * @param x the amount to move
     */
    public void moveX(int x) {
        this.x += x;
    }

    /**
     * Moves the points Y-position by given amount (adds the amount to the
     * position).
     *
     * @param y the amount to move
     */
    public void moveY(int y) {
        this.y += y;
    }

    /**
     * Moves the points Z-position by given amount (adds the amount to the
     * position).
     *
     * @param z the amount to move
     */
    public void moveZ(int z) {
        this.z += z;
    }

}
