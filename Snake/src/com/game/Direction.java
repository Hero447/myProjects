package com.game;

public class Direction {
    private double a;
    private double b;

    public Direction(double a, double b) {
        //нормализированый вектор направления
        double scaleAB = Math.sqrt(a*a + b*b);
        this.a = a/scaleAB;
        this.b = b/scaleAB;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }
}
