package Logic;

import java.awt.EventQueue;

import GUI.GUI;

public class Game {

    public static final int UP = 15000;
    public static final int DOWN = 15001;
    public static final int LEFT = 15002;
    public static final int RIGHT = 15003;

    private Board myBoard;
    private Level myLevel;
    private GUI myGui;
    private LevelGenerator myLevelGenerator;
    private int lives;

    public Game() {
        myBoard = new Board();
        myLevel = myLevelGenerator.generateLevel("src/Levels/Level1.txt", myBoard);
        //myGui = new GUI(this, myBoard.getRows(), myBoard.getColumns());
        // asociar_entidades_logicas_graficas();
    }

    public static void main(String[] args)
    {
        Game g = new Game();
    }
    
    public void update() {
        
    }

    public void loadLevel(int level) {

    }

    public void swap(int direction) {
        myBoard.swap(direction);
    }

    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }

    /*public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Game();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}
