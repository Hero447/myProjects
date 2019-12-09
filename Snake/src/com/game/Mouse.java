package com.game;

public class Mouse {
    private double x;
    private double y;
    private int radius;


    public Mouse(double x, double y,int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
