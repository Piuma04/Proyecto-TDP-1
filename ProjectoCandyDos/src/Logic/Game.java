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
    private Timer myTimer;
    private int lives;

    public Game() {
        myGui = new Gui(this);
        myTimer = new Timer(this, myGui);
        
        int level = 1; // myGui.chooseLevel(); MUST CHECK IF USER INSERTED INTEGER.
        lives = 2;
        loadLevel(level);
    }

    public void loadLevel(int level) {
        myGui.reset();
        myBoard = new Board(myGui);
        myLevel = LevelGenerator.generateLevel("src/Levels/Level" + String.valueOf(level) + ".txt", myBoard);
        myGui.updateMoves(myLevel.getMoves());
        myBoard.setPlayerPosition(3, 3);
        myGui.updateLives(lives);
        myGui.showObjective(myLevel.getObjective(), myLevel.getRemainingObjectives());
        myGui.setCurrentLevel("Nivel " + myLevel.getCurrentLevel());
        myGui.setVisible(true);
        myTimer.startTimer(myLevel.getTimeLimit());
    }

    public void swap(int direction) {
        List<Equivalent> list = myBoard.swap(direction);
        boolean finished = myLevel.update(list);
        myGui.updateMoves(myLevel.getMoves());
        myGui.executeAfterAnimation(() -> {
            myGui.updateGraphicObjective(myLevel.getRemainingObjectives());
            if (finished) {
                myTimer.stopTimer();
                if (myLevel.isLastLevel()) {
                    myGui.showMessage("Felicitaciones! Ha ganado el juego");
                    //myGui.reset();
                    // MUST ADD WIN WINDOW!
                    
                }
                else {
                    myGui.showMessage("Siguiente nivel?");
                    loadLevel(myLevel.getCurrentLevel()+1);
                }
            } else if (myLevel.lost()) {
                lost();
            }
        });
    }

    public void move(int direction) { myBoard.movePlayerDirection(direction); }

    public void timerEnded() { if (myLevel.getRemainingObjectives() > 0) { lost(); } }

    public void lost() {
        lives--;
        myGui.updateLives(lives);
        if (lives == 0)
            myGui.showMessage("Perdio el juego");
        else {
            myGui.showMessage("Perdio una vida, reintente!");
            loadLevel(myLevel.getCurrentLevel());
        }

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try { new Game(); }catch (Exception e) { e.printStackTrace(); } }
        });
    }

}
