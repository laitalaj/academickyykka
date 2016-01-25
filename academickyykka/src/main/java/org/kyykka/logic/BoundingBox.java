/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kyykka.logic;

import java.util.Collection;
import java.util.HashSet;

/**
 *
 * @author Admin
 */
public class BoundingBox {
    
    //The location represents lower bottom left corner
    //(Smallest x, y and z of the box)
    private Point location;
    
    private int width;
    private int height;
    private int depth;

    public BoundingBox(int x, int y, int z, int width, int height, int depth) {
        this.location = new Point(x, y, z);
        this.width = width;
        this.height = height;
        this.depth = depth;
    }
    
    public Collection<Point> getCorners(){
        Collection<Point> corners = new HashSet<>();
        int[] xcoords = new int[] {this.location.getX(), this.location.getX() + this.width};
        int[] ycoords = new int[] {this.location.getY(), this.location.getY() + this.height};
        int[] zcoords = new int[] {this.location.getZ(), this.location.getZ() + this.depth};
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
    
    public boolean collidesWith(BoundingBox box){
        Collection<Point> mycorners = this.getCorners();
        boolean result = box.collidesWith(mycorners);
        if(result == true){
            return true;
        } else {
            Collection<Point> hiscorners = box.getCorners();
            return this.collidesWith(hiscorners);
        }
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
}
