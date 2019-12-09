package com.main;

import com.game.Game;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Object[] options = {"Yes, please", "No, thanks"};
        int n = JOptionPane.showOptionDialog(null,
                "Would you like include the boundaries of the playing field?",
                "Game options",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]);

        Game snake = new Game(n);
        snake.start();
    }
}
