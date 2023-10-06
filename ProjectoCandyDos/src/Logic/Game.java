package Logic;

import java.awt.EventQueue;
import java.util.List;

import GUI.Gui;
import Interfaces.Equivalent;


public class Game {

    public static final int UP = 15000;
    public static final int DOWN = 15001;
    public static final int LEFT = 15002;
    public static final int RIGHT = 15003;

    private Gui myGui;
    private Board myBoard;
    private Level myLevel;
    private Clock myClock;
    private int lives;

    public Game() {
    	 myGui = new Gui(this);
         myBoard = new Board(myGui);
         int i = 1; //myGui.chooseLevel(); MUST CHECK IF USER INSERTED INTEGER.
         loadLevel(i);
        
         myBoard.setPlayerPosition(3, 3);
         lives = 3; 
         myClock = new Clock(myGui,myLevel.getTimeLimit(),this);
         myGui.updateLives(lives);
         myGui.showObjective(myLevel.getObjective(), myLevel.getRemainingObjectives());
         myGui.setCurrentLevel("Nivel "+myLevel.getCurrentLevel());
         myGui.setVisible(true);
         myClock.start();
    }

    public void loadLevel(int level) {
    	myLevel = LevelGenerator.generateLevel("src/Levels/Level" + String.valueOf(level) + ".txt", myBoard);
    	myGui.updateMoves(myLevel.getMoves());
    }

    public void swap(int direction) {
    	List<Equivalent> list = myBoard.swap(direction);
        boolean finished = myLevel.update(list);
        myGui.updateMoves(myLevel.getMoves());
        myGui.updateGraphicObjective(myLevel.getRemainingObjectives());
        if(finished) {
        	if(myLevel.lastLevel())
        		myGui.ending();
        	else {
        		myGui.ending(); //deberia pasar de lvl
        	}
        }
        else if(myLevel.lost()) lost();
        
    }

    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }
    
    public void lost() {
    		lives--;
    		myGui.updateLives(lives);
    		if(lives == 0) {
    			myGui.gameOver();
    		}
    		else {
    			myGui.gameOver(); //deberia reiniciar el level
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
