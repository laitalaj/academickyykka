package org.kyykka.logic.shape;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * A 3D box that handles positions and collision detection
 *
 * @author Julius Laitala
 */
public class HitBox {

    //The location represents lower bottom left corner
    //(Smallest x, y and z of the box)
    private Point location;

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
        this.location = new Point(x, y, z);
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    /**
     * Returns all corners of the box
     *
     * @return list of corners
     */
    public Collection<Point> getCorners() {
        Collection<Point> corners = new HashSet<>();
        int[] xcoords = new int[]{this.getX(), this.getX() + this.width};
        int[] ycoords = new int[]{this.getY(), this.getY() + this.height};
        int[] zcoords = new int[]{this.getZ(), this.getZ() + this.depth};
        for (int x : xcoords) {
            for (int y : ycoords) {
                for (int z : zcoords) {
                    corners.add(new Point(x, y, z));
                }
            }
        }
        return corners;
    }

    /**
     * Checks if the box collides with a point (if the point is inside the box)
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
     * Checks if the box collides with a point (if the point is inside the box)
     *
     * @param p point with which to check collision
     *
     * @return true if collision happens, false otherwise
     */
    public boolean collidesWith(Point p) {
        return collidesWith(p.getX(), p.getY(), p.getZ());
    }

    /**
     * Checks if the box collides with any point in a collection (if any of the
     * points is inside the box)
     *
     * @param points points to check for collisions
     *
     * @return true if any point collides with the box, false otherwise
     */
    public boolean collidesWith(Collection<Point> points) {
        for (Point p : points) {
            if (this.collidesWith(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if this box collides with another box (if either of the boxes
     * contain others corners). Note: not 100% accurate!
     *
     * @param box the box with which to check for collisions
     *
     * @return true if collision happens, false otherwise
     */
    public boolean collidesWith(HitBox box) {
        Collection<Point> mycorners = this.getCorners();
        boolean result = box.collidesWith(mycorners);
        if (result == true) {
            return true;
        } else {
            Collection<Point> hiscorners = box.getCorners();
            return this.collidesWith(hiscorners);
        }
    }

    /**
     * Calculates the center position of the box
     *
     * @return the center position
     */
    public Point getCenter() {
        int x = this.getX() + this.width / 2;
        int y = this.getY() + this.height / 2;
        int z = this.getZ() + this.depth / 2;
        return new Point(x, y, z);
    }

    /**
     * Calculates the bottom center position of the box (center on x and y axis,
     * low z-axis)
     *
     * @return the bottom center position
     */
    public Point getBottomCenter() {
        Point point = this.getCenter();
        point.setZ(this.getZ());
        return point;
    }

    /**
     * Calculates the top center position of the box (center on x and y axis,
     * high z-axis)
     *
     * @return the top center position
     */
    public Point getTopCenter() {
        Point point = this.getCenter();
        point.setZ(this.getZ() + this.depth);
        return point;
    }

    /**
     * Calculates the lower top left position of the box (x and y are equal to
     * position, z is position + depth)
     *
     * @return the lower top left position
     */
    public Point getLowerTopLeft() {
        return new Point(this.getX(), this.getY(), this.getZ() + this.depth);
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

    public void setX(int x) {
        this.location.setX(x);
    }

    public void setY(int y) {
        this.location.setY(y);
    }

    public void setZ(int z) {
        this.location.setZ(z);
    }

    public void moveX(int x) {
        this.location.moveX(x);
    }

    public void moveY(int y) {
        this.location.moveY(y);
    }

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
    
    @Override
    public String toString(){
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
