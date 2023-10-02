package Logic;

import java.awt.EventQueue;

import Entities.Block;
import GUI.GUI;
import GUI.GraphicalBlock;

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
        myBoard = new Board(this);
        loadLevel(1);
        //myBoard.showMatrix();
        myGui = new GUI(this, myBoard.getRows(), myBoard.getColumns());
        assocciateLogicalGraphicBlocks();
        myGui.setVisible(true);
        //myBoard.setPlayerPosition(3, 3);
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
    
    private void assocciateLogicalGraphicBlocks() {
        Block block;
        GraphicalBlock graphicalBlock;
        
        for (int r = 0; r < myBoard.getRows(); r++) {
            for (int c = 0; c < myBoard.getColumns(); c++) {
                block = myBoard.getBlock(r, c);
                graphicalBlock = myGui.agregar_entidad(block);
                block.setGraphicBlock(graphicalBlock);
            }
        }
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
