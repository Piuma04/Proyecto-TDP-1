package Logic;

import java.awt.EventQueue;

import Entities.Entity;
import GUI.Gui;
import GUI.GraphicalEntity;

public class Game {

    public static final int UP = 15000;
    public static final int DOWN = 15001;
    public static final int LEFT = 15002;
    public static final int RIGHT = 15003;

    private Gui myGui;
    private Board myBoard;
    private Level myLevel;
    private int lives;

    public Game() {
        myGui = new Gui(this, Board.getRows(), Board.getColumns());
        myBoard = new Board(this, myGui);
        loadLevel(1);
        myGui.setVisible(true);
        myBoard.setPlayerPosition(3, 3);
    }

    public void loadLevel(int level) {
        myLevel = LevelGenerator.generateLevel("src/Levels/Level" + String.valueOf(level) + ".txt", myBoard);
    }

    public void swap(int direction) {
        myBoard.swap(direction);
    }

    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Game();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
