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
    	myLevel = myLevelGenerator.generateLevel("str", myBoard);
		myGui = new GUI(this, myBoard.getRows(), myBoard.getColumns());
		assocciateLogicalGraphicBlocks();
	}
    
    private void assocciateLogicalGraphicBlocks() {
    	Block e;
		GraphicalBlock eg;
		
		for (int r=0; r<myBoard.getRows(); r++) {
			for (int c=0; c<myBoard.getColumns(); c++) {
				e = myBoard.getBlock(r, c);
				eg = myGui.agregar_entidad(e);
				e.setGraphicBlock(eg);
			}
		}
		myGui.setVisible(true);
		
	}

	public void update() {
        
    }

    public void loadLevel(int level) {
        
    }
    
    public void swap(int direction){
        myBoard.swap(direction);
    }
    
    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }
    public static void main(String [] args) {
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

