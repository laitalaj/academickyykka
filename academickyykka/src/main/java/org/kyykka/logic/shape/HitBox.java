package org.kyykka.logic.shape;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * A 3D box that handles positions and collision detection.
 *
 * @author Julius Laitala
 */
public class HitBox {

    //The location represents lower bottom left corner
    //(Smallest x, y and z of the box)
    private Point3D location;

    private int width;
    private int height;
    private int depth;

    /**
     * Creates a hitbox with the specified parameters.
     *
     * @param x x-position of the boxes lower bottom left corner
     * @param y y-position of the boxes lower bottom left corner
     * @param z z-position of the boxes lower bottom left corner
     * @param width x-axis dimension of the box
     * @param height y-axis dimension of the box
     * @param depth z-axis dimension of the box
     */
    public HitBox(int x, int y, int z, int width, int height, int depth) {
        this.location = new Point3D(x, y, z);
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    /**
     * Creates a hitbox with the specified parameters.
     *
     * @param location lower bottom left corner of the box
     * @param width x-axis dimension of the box
     * @param height y-axis dimension of the box
     * @param depth z-axis dimension of the box
     */
    public HitBox(Point3D location, int width, int height, int depth) {
        this(location.getX(), location.getY(), location.getZ(),
                width, height, depth);
    }

    /**
     * Returns all corners of the box.
     *
     * @return list of corners
     */
    public Collection<Point3D> getCorners() {
        Collection<Point3D> corners = new HashSet<>();
        int[] xcoords = new int[]{this.getX(), this.getX() + this.width};
        int[] ycoords = new int[]{this.getY(), this.getY() + this.height};
        int[] zcoords = new int[]{this.getZ(), this.getZ() + this.depth};
        for (int x : xcoords) {
            for (int y : ycoords) {
                for (int z : zcoords) {
                    corners.add(new Point3D(x, y, z));
                }
            }
        }
        return corners;
    }

    /**
     * Checks if the box collides with a point (if the point is inside the box).
     *
     * @param x x-coordinate of the point
     * @param y x-coordinate of the point
     * @param z x-coordinate of the point
     *
     * @return true if collision happens, false otherwise
     */
    public boolean collidesWith(int x, int y, int z) {
        boolean collides = x >= this.location.getX() && x <= this.location.getX() + this.width;
        collides = collides && y >= this.location.getY() && y <= this.location.getY() + this.height;
        collides = collides && z >= this.location.getZ() && z <= this.location.getZ() + this.depth;
        return collides;
    }

    /**
     * Checks if the box collides with a point (if the point is inside the box).
     *
     * @param p point with which to check collision
     *
     * @return true if collision happens, false otherwise
     */
    public boolean collidesWith(Point3D p) {
        return collidesWith(p.getX(), p.getY(), p.getZ());
    }

    /**
     * Checks if the box collides with any point in a collection (if any of the
     * points is inside the box).
     *
     * @param points points to check for collisions
     *
     * @return true if any point collides with the box, false otherwise
     */
    public boolean collidesWith(Collection<Point3D> points) {
        for (Point3D p : points) {
            if (this.collidesWith(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if this box collides with another box (if either of the boxes
     * contain parts of each other).
     *
     * @param box the box with which to check for collisions
     *
     * @return true if collision happens, false otherwise
     */
    public boolean collidesWith(HitBox box) {
        boolean collides = this.getX() <= box.getX() + box.getWidth();
        collides = collides && this.getX() + this.width >= box.getX();
        collides = collides && this.getY() <= box.getY() + box.getHeight();
        collides = collides && this.getY() + this.height >= box.getY();
        collides = collides && this.getZ() <= box.getZ() + box.getDepth();
        collides = collides && this.getZ() + this.depth >= box.getZ();
        return collides;
    }

    /**
     * Checks if this box collides with any of the boxes in given collection.
     *
     * @param boxes boxes to be checked collision with
     *
     * @return true if collision happens, false otherwise
     */
    public boolean collidesWithAny(Collection<HitBox> boxes) {
        for (HitBox b : boxes) {
            if (this.collidesWith(b)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the center position of the box.
     *
     * @return the center position
     */
    public Point3D getCenter() {
        int x = this.getX() + this.width / 2;
        int y = this.getY() + this.height / 2;
        int z = this.getZ() + this.depth / 2;
        return new Point3D(x, y, z);
    }

    /**
     * Sets the boxes center position to given point.
     *
     * @param point point to set center to
     */
    public void setCenter(Point3D point) {
        this.setX(point.getX() - this.width / 2);
        this.setY(point.getY() - this.height / 2);
        this.setZ(point.getZ() - this.depth / 2);
    }

    /**
     * Calculates the upper bottom left position of the box (x and z are equal
     * to position, y is position + height).
     *
     * @return the upper bottom left position
     */
    public Point3D getUpperBottomLeft() {
        return new Point3D(this.getX(), this.getY() + this.height, this.getZ());
    }

    /**
     * Calculates the bottom center position of the box (center on x and y axis,
     * low z-axis).
     *
     * @return the bottom center position
     */
    public Point3D getBottomCenter() {
        Point3D point = this.getCenter();
        point.setZ(this.getZ());
        return point;
    }

    /**
     * Calculates the top center position of the box (center on x and y axis,
     * high z-axis).
     *
     * @return the top center position
     */
    public Point3D getTopCenter() {
        Point3D point = this.getCenter();
        point.setZ(this.getZ() + this.depth);
        return point;
    }

    /**
     * Calculates the lower top left position of the box (x and y are equal to
     * position, z is position + depth).
     *
     * @return the lower top left position
     */
    public Point3D getLowerTopLeft() {
        return new Point3D(this.getX(), this.getY(), this.getZ() + this.depth);
    }

    /**
     * Calculates the lower top left position of the box (x is equal to
     * position, z is position + depth, y is (position + height) / 2).
     *
     * @return the center top left position
     */
    public Point3D getCenterTopLeft() {
        Point3D topCenter = getTopCenter();
        topCenter.setX(this.getX());
        return topCenter;
    }

    /**
     * Copies this box into another identical box.
     *
     * @return a box with the same parameters as this box
     */
    public HitBox copy() {
        return new HitBox(this.location.copy(), this.width, this.height,
                this.depth);
    }

    public int getX() {
        return this.location.getX();
    }

    public int getY() {
        return this.location.getY();
    }

    public int getZ() {
        return this.location.getZ();
    }

    /**
     * Sets the x-position of the box.
     *
     * @param x the position to set
     *
     * @see Point3D#setX(int)
     */
    public void setX(int x) {
        this.location.setX(x);
    }

    /**
     * Sets the y-position of the box.
     *
     * @param y the position to set
     *
     * @see Point3D#setY(int)
     */
    public void setY(int y) {
        this.location.setY(y);
    }

    /**
     * Sets the z-position of the box.
     *
     * @param z the position to set
     *
     * @see Point3D#setZ(int)
     */
    public void setZ(int z) {
        this.location.setZ(z);
    }

    /**
     * Moves the X-position of the box by given amount.
     *
     * @see Point3D#moveX(int)
     *
     * @param x the amount to move
     */
    public void moveX(int x) {
        this.location.moveX(x);
    }

    /**
     * Moves the Y-position of the box by given amount.
     *
     * @see Point3D#moveY(int)
     *
     * @param y the amount to move
     */
    public void moveY(int y) {
        this.location.moveY(y);
    }

    /**
     * Moves the Z-position of the box by given amount.
     *
     * @see Point3D#moveZ(int)
     *
     * @param z the amount to move
     */
    public void moveZ(int z) {
        this.location.moveZ(z);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public Point3D getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return this.location.toString() + ", w:" + this.width + ", h:"
                + this.height + ", d:" + this.depth;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.location);
        hash = 67 * hash + this.width;
        hash = 67 * hash + this.height;
        hash = 67 * hash + this.depth;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HitBox other = (HitBox) obj;
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (this.width != other.width) {
            return false;
        }
        if (this.height != other.height) {
            return false;
        }
        if (this.depth != other.depth) {
            return false;
        }
        return true;
    }

}
