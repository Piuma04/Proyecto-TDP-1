package Logic;

import java.awt.EventQueue;
import java.util.List;

import Entities.Entity;
import GUI.Gui;
import Interfaces.Equivalent;
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
         int l = 1; //myGui.chooseLevel();
         loadLevel(l);
         myGui.setVisible(true);
         myBoard.setPlayerPosition(3, 3);
         lives = 3; 
         myGui.updateLives(lives);
         myGui.showObjective(myLevel.getObjective(), myLevel.getRemainingObjectives());
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
        	else
        		System.out.println("se tendria que avanzar de nivel");
        }
        else {
        	if(myLevel.lost()) {
        		lives--;
        		myGui.updateLives(lives);
        		if(lives == 0) {
        			myGui.gameOver();
        		}
        		else {
        			//reiniciar
        		}
        	}
        }
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
