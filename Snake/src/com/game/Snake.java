package com.game;

import java.util.ArrayList;

import static com.game.Game.*;

public class Snake {
    //Состояние - жива змея или нет.
    private boolean isAlive;
    //Список кусочков змеи.
    private ArrayList<SnakeSection> sections;



    public Snake(int x, int y, int radius) {
        sections = new ArrayList<>();
        sections.add(new SnakeSection(x, y,new Direction(0,1),radius));
        isAlive = true;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }

    public void move(double speed,double direction){

        if(sections.size()>1){
            for(int i = sections.size()-1;i>0;i--){
                sections.get(i).moveToTarget(sections.get(i-1));
            }
        }
        sections.get(0).turnDirection(direction);
        sections.get(0).moveInDirection(sections.get(0).getDirection(),speed);

        if(boundaries>0){
            sections.get(0).transferToOppositeBorder();
        }else{
            checkBorders(sections.get(0));
        }

        checkBody(sections.get(0));
        if(checkMouse(sections.get(0))) {
            double x = new Double(sections.get(0).getX());
            double y = new Double(sections.get(0).getY());
            Direction newDirect = new Direction(sections.get(0).getDirection().getA(),sections.get(0).getDirection().getB());
            createMouse();
            sections.add(0,new SnakeSection(x + newDirect.getA()*speed*(1),y+newDirect.getB()*(1),newDirect, DIAMETR));
        }



    }


    /**
     * Метод проверяет - находится ли голова в пределах комнаты
     */
    private void checkBorders(SnakeSection head) {
        if ((head.getX())< 0 || (head.getX()+ DIAMETR) >= WIDTH || (head.getY()) < 0 || (head.getY()+ DIAMETR) >= HEIGHT) {
            isAlive = false;
        }
    }

    /**
     * Метод проверяет - не совпадает ли голова с каким-нибудь участком тела змеи.
     */
    private void checkBody(SnakeSection head) {
        if(sections.size()>3){
            double x = new Double(head.getX()+head.getDirection().getA()*speed);
            double y = new Double(head.getY()+head.getDirection().getB()*speed);
            for(int i = 3; i < sections.size();i++){
                if(Math.sqrt((x-(sections.get(i).getX()))*(x-(sections.get(i).getX()))+(y-(sections.get(i).getY()))*(y-(sections.get(i).getY()))) <= speed) isAlive = false;
            }
        }
    }

    /**
     * Метод проверяет - не совпадает ли голова с мышкой.
     */
    private boolean checkMouse(SnakeSection head) {
        return (Math.sqrt((Game.mouse.getX()-head.getX())*(Game.mouse.getX()-head.getX())+(Game.mouse.getY()-head.getY())*(Game.mouse.getY()-head.getY())) <= DIAMETR);
    }
    /**
     * Метод проверяет - жыва ли змея.
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Создает новую мышь
     */
    public void createMouse() {
        boolean flag;
        double x;
        double y;
        do {
            flag = false;
            x = Math.random() * WIDTH;
            y = Math.random() * HEIGHT;
            if ((x)< DIAMETR || (x) >= WIDTH - 2* DIAMETR || (y) < DIAMETR || (y) >= HEIGHT - 2* DIAMETR) flag = true;
            for (SnakeSection s : sections) {
                if(Math.sqrt((x-(s.getX()))*(x-(s.getX()))+(y-(s.getY()))*(y-(s.getY()))) <= 3* DIAMETR) flag = true;
            }
        } while(flag);
        Game.mouse = new Mouse(x,y, DIAMETR) ;
        Game.speed+=0.05;
    }
}
