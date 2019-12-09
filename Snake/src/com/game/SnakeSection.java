package com.game;

import static com.game.Game.HEIGHT;
import static com.game.Game.DIAMETR;
import static com.game.Game.WIDTH;

public class SnakeSection {
    private double x;
    private double y;
    private Direction direction;
    private int radius;


    public SnakeSection(double x, double y, Direction direction,int radius) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.radius = radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void turnDirection(double degree){
        double aNew = this.direction.getA() * Math.cos(degree) - this.direction.getB() * Math.sin(degree);
        double bNew = this.direction.getA() * Math.sin(degree) + this.direction.getB() * Math.cos(degree);

        this.direction.setA(aNew);
        this.direction.setB(bNew);
    }

//    public void changeDirectionToAnotherSection(SnakeSection target){
//        double aNew = target.x - this.x;
//        double bNew = target.y - this.y;
//
//        double scaleAB = Math.sqrt(aNew*aNew + bNew*bNew);
//        this.direction.setA(aNew/scaleAB);
//        this.direction.setB(bNew/scaleAB);
//    }

    public void moveToTarget(SnakeSection target) {
        this.x = Double.valueOf(target.x);
        this.y = Double.valueOf(target.y);
    }

    public void moveInDirection(Direction direction, double speed) {
        this.x = this.x + direction.getA()*speed;
        this.y = this.y + direction.getB()*speed;
    }

    public void transferToOppositeBorder(){
        if(this.x + DIAMETR /2 < 0){
            this.x = WIDTH  - DIAMETR /2 - 1;
        } else if(this.x + DIAMETR /2 > WIDTH){
            this.x = -DIAMETR /2 - 1;
        }
        if(this.y + DIAMETR /2 < 0){
            this.y = HEIGHT  - DIAMETR /2 - 1;
        } else if(this.y + DIAMETR /2 > HEIGHT){
            this.y = -DIAMETR /2 - 1;
        }
    }

    public Direction getDirection() {
        return direction;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SnakeSection that = (SnakeSection) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        double result = x;
        result = 31 * result + y;
        return (int)result;
    }

}
