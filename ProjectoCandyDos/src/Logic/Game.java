package Logic;

import java.awt.EventQueue;

import java.util.List;

import javax.swing.SwingUtilities;

import GUI.Gui;

import Animations.SoundPlayer;

import Interfaces.Equivalent;

public class Game {

    private static final SoundPlayer backgroundMusic = new SoundPlayer("ps/introMusic.wav");
    private static final SoundPlayer lostSound = new SoundPlayer("ps/ps2error.wav");

    public static final int UP = 15000;
    public static final int DOWN = 15001;
    public static final int LEFT = 15002;
    public static final int RIGHT = 15003;

    private Gui myGui;
    private Board myBoard;
    private Level myLevel;
    private Timer myTimer;
    private int lives;
    private boolean animationNextLevel;

    public Game() {
        myGui = new Gui(this);
        myTimer = new Timer(this, myGui);
        int level = 1;//= myGui.chooseLevel();
        lives = 3;
        loadLevel(level);
        myGui.setVisible(true);
        backgroundMusic.loop();
    }

    public void loadLevel(int level) {
        myGui.reset();
        myBoard = new Board(myGui);
        myLevel = LevelGenerator.generateLevel("level" + String.valueOf(level) + ".txt", myBoard);
        myGui.updateMoves(myLevel.getMoves());
        myGui.updateLives(lives);
        myGui.showObjective(myLevel.getObjectives(), myLevel.getRemainingObjectives());
        myGui.setCurrentLevel("Nivel " + myLevel.getCurrentLevel());
        myTimer.startTimer(myLevel.getTimeLimit());
    }

    public void swap(int direction) {
        List<Equivalent> destroyed = myBoard.swap(direction);

        update(destroyed);

    }

    public void move(int direction) {
        SwingUtilities.invokeLater(() -> { myBoard.movePlayerDirection(direction); });
    }

    public void timerEnded() { if (!animationNextLevel && !myLevel.lost()) { lost(); } }

    public void update(List<Equivalent> destroyed) {
        boolean finished = myLevel.update(destroyed);

        myGui.executeAfterAnimation(() -> {
            SwingUtilities.invokeLater( () -> {
                myGui.updateGraphicObjective(myLevel.getRemainingObjectives());
                myGui.updateMoves(myLevel.getMoves());
            });
        });

        if (finished)
            win();
        else if (myLevel.lost())
            lost();
    }

    public void win() {
        animationNextLevel = true;
        myGui.executeAfterAnimation(() -> {
            SwingUtilities.invokeLater( () -> {
                _win();
                animationNextLevel = false;
            });
        });
    }

    private void _win() {
        myTimer.stopTimer();
        if (myLevel.isLastLevel()) {
            myGui.showMessage("Felicitaciones! Ha ganado el juego");
            myGui.close();
        }
        else {
            myGui.showMessage("Siguiente nivel?");
            loadLevel(myLevel.getCurrentLevel()+1);
        }
    }

    public void lost() {
        animationNextLevel = true;
        myGui.executeAfterAnimation(() -> {
            SwingUtilities.invokeLater( () -> {
                _lost();
                animationNextLevel = false;
            });
        });
    }

    private void _lost() {
        lives--;
        myTimer.stopTimer();
        myGui.updateLives(lives);
        backgroundMusic.stop();
        lostSound.play();
        if (lives == 0) {
            myGui.showMessage("Perdio el juego");
            myGui.close();
        }
        else {
            myGui.showMessage("Perdio una vida, reintente!");
            loadLevel(myLevel.getCurrentLevel());
        }
        backgroundMusic.start();
    }

    public boolean isAnimating() { return animationNextLevel; }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try { new Game(); }catch (Exception e) { e.printStackTrace(); } }
        });
    }

}
