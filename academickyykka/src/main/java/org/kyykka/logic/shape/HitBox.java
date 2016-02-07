/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic.shape;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Admin
 */
public class HitBox {
    
    //The location represents lower bottom left corner
    //(Smallest x, y and z of the box)
    private Point location;
    
    private int width;
    private int height;
    private int depth;

    public HitBox(int x, int y, int z, int width, int height, int depth) {
        this.location = new Point(x, y, z);
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
    
    public Collection<Point> getCorners(){
        Collection<Point> corners = new HashSet<>();
        int[] xcoords = new int[] {this.getX(), this.getX() + this.width};
        int[] ycoords = new int[] {this.getY(), this.getY() + this.height};
        int[] zcoords = new int[] {this.getZ(), this.getZ() + this.depth};
        for(int x: xcoords){
            for(int y: ycoords){
                for(int z: zcoords){
                    corners.add(new Point(x, y, z));
                }
            }
        }
        return corners;
    }
    
    public boolean collidesWith(int x, int y, int z){
        boolean collides = x >= this.location.getX() && x <= this.location.getX() + this.width;
        collides = collides && y >= this.location.getY() && y <= this.location.getY() + this.height;
        collides = collides && z >= this.location.getZ() && z <= this.location.getZ() + this.depth;
        return collides;
    }
    
    public boolean collidesWith(Point p){
        return collidesWith(p.getX(), p.getY(), p.getZ());
    }
    
    public boolean collidesWith(Collection<Point> points){
        for(Point p: points){
            if(this.collidesWith(p)){
                return true;
            }
        }
        return false;
    }
    
    public boolean collidesWith(HitBox box){
        Collection<Point> mycorners = this.getCorners();
        boolean result = box.collidesWith(mycorners);
        if(result == true){
            return true;
        } else {
            Collection<Point> hiscorners = box.getCorners();
            return this.collidesWith(hiscorners);
        }
    }
    
    public Point getCenter(){
        int x = this.getX() + this.width / 2;
        int y = this.getY() + this.height / 2;
        int z = this.getZ() + this.depth / 2;
        return new Point(x, y, z);
    }
    
    public Point getBottomCenter(){
        Point point = this.getCenter();
        point.setZ(this.getZ());
        return point;
    }
    
    public Point getTopCenter(){
        Point point = this.getCenter();
        point.setZ(this.getZ() + this.depth);
        return point;
    }
    
    public Point getLowerTopLeft(){
        return new Point(this.getX(), this.getY(), this.getZ() + this.depth);
    }
    
    public int getX(){
        return this.location.getX();
    }
    
    public int getY(){
        return this.location.getY();
    }
    
    public int getZ(){
        return this.location.getZ();
    }
    
    public void setX(int x){
        this.location.setX(x);
    }
    
    public void setY(int y){
        this.location.setY(y);
    }
    
    public void setZ(int z){
        this.location.setZ(z);
    }
    
    public void moveX(int x){
        this.location.moveX(x);
    }
    
    public void moveY(int y){
        this.location.moveY(y);
    }
    
    public void moveZ(int z){
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
    
    
}
