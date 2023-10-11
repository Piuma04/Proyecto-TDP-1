package Logic;

import java.awt.EventQueue;

import java.util.List;

import GUI.Gui;

import Animations.SoundPlayer;

import Interfaces.Equivalent;

public class Game {

    private static final String levelPath = "src/resources/levels/";
    public static final int UP = 15000;
    public static final int DOWN = 15001;
    public static final int LEFT = 15002;
    public static final int RIGHT = 15003;

    private Gui myGui;
    private Board myBoard;
    private Level myLevel;
    private Timer myTimer;
    private int lives;
    private boolean lostLive;
    
    private static SoundPlayer backgroundMusic = new SoundPlayer("ps/introMusic.wav");
    private static SoundPlayer lostSound = new SoundPlayer("ps/ps2error.wav");

    public Game() {
        myGui = new Gui(this);
        myTimer = new Timer(this, myGui);
        int level = 1; // myGui.chooseLevel(); MUST CHECK IF USER INSERTED INTEGER.
        lives = 3;
        lostLive = false;
        loadLevel(level);

        backgroundMusic.loop();
    }

    public void loadLevel(int level) {
        myGui.reset();
        myBoard = new Board(myGui);
        myLevel = LevelGenerator.generateLevel( levelPath + "level" + String.valueOf(level) + ".txt", myBoard);
        myGui.updateMoves(myLevel.getMoves());
        myGui.updateLives(lives);
        myGui.showObjective(myLevel.getObjective(), myLevel.getRemainingObjectives());
        myGui.setCurrentLevel("Nivel " + myLevel.getCurrentLevel());
        myGui.executeAfterAnimation( () -> { myBoard.setPlayerPosition(3, 3); });
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
        lostLive = true;
        myGui.executeAfterAnimation(() -> {
            lives--;
            myTimer.stopTimer();
            myGui.updateLives(lives);
            backgroundMusic.stop();
            lostSound.play();
            if (lives == 0)
                myGui.showMessage("Perdio el juego");
            else {
                myGui.showMessage("Perdio una vida, reintente!");
                loadLevel(myLevel.getCurrentLevel());
            }
            backgroundMusic.start();
            lostLive = false;
        });
    }

    public boolean isLost() { return lostLive; }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try { new Game(); }catch (Exception e) { e.printStackTrace(); } }
        });
    }

}
