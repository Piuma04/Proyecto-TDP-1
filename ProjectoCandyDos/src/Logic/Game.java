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
    	lives = 3;
        myBoard = new Board(this);
        loadLevel(1);
        //myBoard.showMatrix();
        myGui = new GUI(this, myBoard.getRows(), myBoard.getColumns());
        assocciateLogicalGraphicBlocks();
        myBoard.setPlayerPosition(3, 3);
        myGui.updateMoves(myLevel.getRemainingMoves());
        myGui.updateLives(lives);
    }

    public void update() {
        boolean termino = myLevel.update(null);
        if(!termino) {
        	myGui.updateMoves(myLevel.getRemainingMoves());
        	
        	if(myLevel.lost()) {
        		lives--;
        		myGui.updateLives(lives);
        		if(lives<=0) {
        			myGui.gameOver();
        		}
        		else {
        			myGui.showLostScreen();
        			myBoard = new Board(this);
        			loadLevel(1);
        			myGui.reset();
        			
        			
        			assocciateLogicalGraphicBlocks();
        			myBoard.setPlayerPosition(3, 3);
        			myGui.updateMoves(myLevel.getRemainingMoves());
        		}
        	}
        	else {
        		
        	}
        }
        else {
        	
        }
    }

    public void loadLevel(int level) {
        myLevel = LevelGenerator.generateLevel("src/Levels/Level" + String.valueOf(level) + ".txt", myBoard);
    }

    public void swap(int direction) {
        myBoard.swap(direction);
        update();
        
    }

    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }
    
    private void assocciateLogicalGraphicBlocks() {
        Block e;
        GraphicalBlock eg;
        
        for (int r = 0; r < myBoard.getRows(); r++) {
            for (int c = 0; c < myBoard.getColumns(); c++) {
                e = myBoard.getBlock(r, c);
                eg = myGui.agregar_entidad(e);
                e.setGraphicBlock(eg);
            }
        }
        if(!myGui.isActive())
        myGui.setVisible(true);
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
