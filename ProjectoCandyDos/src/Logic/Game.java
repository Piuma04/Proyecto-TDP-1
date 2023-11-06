package Logic;

import java.util.ArrayList;
import java.util.List;

import Entities.Bomb;
import GUI.Gui;
import Interfaces.Equivalent;
import Interfaces.GameOverOnly;
import Interfaces.PausableObserver;
import VisualPlayers.SoundPlayer;

public class Game implements GameOverOnly{

    private static int label_size = 80;
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
    private Score score;
    private List<PausableObserver> pausableObservers;

    public Game() {
        myGui = new Gui(this);
        myTimer = new Timer(this, myGui);
        score = new Score(myGui);
        lives = 3;
        pausableObservers = new ArrayList<>();
    }

    public void startBackgroundMusic() { backgroundMusic.loop(); }
    public void stopBackgroundMusic() { backgroundMusic.stop();}

    public void loadLevel(int level) {
        myGui.reset();
        myBoard = new Board(myGui);
        myLevel = LevelGenerator.generateLevel("level" + String.valueOf(level) + ".txt", myBoard, this, myGui);
        myGui.updateLives(lives);
        myTimer.startTimer(myLevel.getTimeLimit());
    }

    public void reloadLevel() {
        loadLevel(myLevel.getCurrentLevel());
        myTimer.update(true);
    }

    public void swap(int direction) {
        List<Equivalent> destroyed = myBoard.swap(direction);
        update(destroyed);
    }

    public void move(int direction) {
        myBoard.movePlayerDirection(direction);
    }

    public void timerEnded() { if (!animationNextLevel && !myLevel.lost()) { lost(); } }

    public void update(List<Equivalent> destroyed) {
    	
        myLevel.update(destroyed);
        score.update(destroyed);
    }

    public void win() {
        animationNextLevel = true;
        myGui.executeAfterAnimation(() -> {
            _win();
            animationNextLevel = false;
        });
    }

    private void _win() {
    	myTimer.update(true);
        if (myLevel.isLastLevel()) {
            myGui.showMessage("Felicitaciones! Ha ganado el juego");
            score.setNewScores();
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
            _lost();
            animationNextLevel = false;
        });
    }

    private void _lost() {
        lives--;
        myTimer.update(true);
        myGui.updateLives(lives);
        backgroundMusic.stop();
        lostSound.play();
        if (lives == 0) {
        	myGui.showMessage("Perdio el juego");
            score.setNewScores();
            myGui.close();
        }
        else {
            myGui.showMessage("Perdio una vida, reintente!");
            loadLevel(myLevel.getCurrentLevel());
        }
        backgroundMusic.start();
    }

    public void finalLost() {
    	backgroundMusic.stop();
        lostSound.play();
    	pause(true);
    	myGui.showMessage("Perdio el juego");
        score.setNewScores();
        myGui.close();
		
	}

	public void pause(boolean isPaused) { 
		myTimer.update(isPaused);
		for(PausableObserver e : pausableObservers) {
			e.update(isPaused);
		}
		System.out.println(pausableObservers.size());
	}
	public void removePausableObserver(PausableObserver poToRemove) {
		pausableObservers.remove(poToRemove);
	}
	
    public boolean isAnimating() { return animationNextLevel; }
    public void addPausableObserver(PausableObserver toAdd) {
    	pausableObservers.add(toAdd);
    }

    public static int getLabelSize() { return label_size; }
    public static void setLabelSize(int size) { label_size = size; }

    public boolean isLoaded() { return myLevel != null; }
    public void resetScore() { score.resetScore(); }
}
