package com.game;

import com.display.Display;
import com.io.Input;
import com.utils.Time;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Game implements Runnable{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "Snake";
    public static final int CLEAR_COLOR = 0xff000000;
    public static final int NUM_BUFFERS = 3;

    public static final float UPDATE_RATE = 60.0f;
    public static final float UPDATE_INTERVAL = Time.SECOND/UPDATE_RATE;
    public static final long IDEL_TIME = 1;
    public static final int DIAMETR = 20;

    private boolean running;
    private Thread gameThread;
    private Graphics2D graphics;
    private Input input;

    private Snake snake;
    private double direction = 0.0;
    public static double speed = 2.0;
    public static Mouse mouse;
    public static int boundaries;

    public Game(int b) {
        running = false;
        Display.create(WIDTH,HEIGHT,TITLE, CLEAR_COLOR, NUM_BUFFERS);
        graphics = Display.getGraphics();
        input = new Input();
        Display.addInputListener(input);

        boundaries = b;
        snake = new Snake(WIDTH/2- DIAMETR,HEIGHT/2- DIAMETR, DIAMETR);
        snake.createMouse();
    }

    public synchronized void start(){
        if(running) return;
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public synchronized void stop(){
        if(!running) return;
        running = false;

//        try{
//            gameThread.join();
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        cleanUp();
    }

    private void update(){
        if(input.getKey(KeyEvent.VK_LEFT)){
            direction = (-speed*2)/(UPDATE_RATE);
        }else if(input.getKey(KeyEvent.VK_RIGHT)){
            direction = (speed*2)/(UPDATE_RATE);
        }else {
            direction = 0.0;
        }
        snake.move(speed,direction);

        if(!snake.isAlive()) this.stop();
    }

    private void render(){
        Display.clear();
        graphics.setColor(Color.red);
        for(SnakeSection s : snake.getSections()){
            graphics.fillOval((int)s.getX(),(int)s.getY(),DIAMETR,DIAMETR);
        }
        graphics.setColor(Color.yellow);
        graphics.fillOval((int)snake.getSections().get(0).getX(),(int)snake.getSections().get(0).getY(),DIAMETR,DIAMETR);

//        for(int i = snake.getSections().size()-1;i>=0;i--){
//            switch(i%7){
//                case 0:
//                    graphics.setColor(Color.red);
//                    break;
//                case 1:
//                    graphics.setColor(Color.orange);
//                    break;
//                case 2:
//                    graphics.setColor(Color.yellow);
//                    break;
//                case 3:
//                    graphics.setColor(Color.green);
//                    break;
//                case 4:
//                    graphics.setColor(Color.cyan);
//                    break;
//                case 5:
//                    graphics.setColor(Color.blue);
//                    break;
//                case 6:
//                    graphics.setColor(Color.magenta);
//                    break;
//            }
//            graphics.fillOval((int)snake.getSections().get(i).getX(),(int)snake.getSections().get(i).getY(), DIAMETR, DIAMETR);
//        }

        graphics.setColor(Color.white);
        graphics.fillOval((int)mouse.getX(),(int)mouse.getY(), DIAMETR, DIAMETR);
        Display.swapBuffers();
    }

    @Override
    public void run() {
        long count = 0;
        float delta = 0;
        int GamingTimeInSeconds = 1;

        long lastTime = Time.get();
        while(running){
            long now = Time.get();
            long elapsedTime = now - lastTime;
            lastTime = now;

            count += elapsedTime;

            boolean render = false;
            delta += (elapsedTime/UPDATE_INTERVAL);
            while(delta>1){
                update();

                delta--;


                    render = true;


            }
            if(render){
                render();

            }else{
                try{
                    Thread.sleep(IDEL_TIME);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            if(count>=Time.SECOND){
                Display.setTitle(TITLE+" || SCORE: " + (snake.getSections().size()-1) + " || SPEED LVL: " + String.format("%.1f",speed) + " || PLAYED TIME: " + GamingTimeInSeconds+"sec.");
                count = 0;
                GamingTimeInSeconds++;
            }
        }
    }

    private void cleanUp(){
        Display.destroy();
    }





}
