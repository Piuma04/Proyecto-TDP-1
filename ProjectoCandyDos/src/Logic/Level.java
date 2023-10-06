package Logic;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Entities.Entity;
import Interfaces.Equivalent;

public class Level {
    private int remainingMoves;
    private Goal myGoal;
    
    private long timeLimit;
    private int currentLevel;
    private static final int lastLevel = 5;

    public Level(Entity toDestroyEntityType, int amount, int remainingMoves, int timeLimit, int cR) {
        myGoal = new Goal(amount, toDestroyEntityType);
        this.remainingMoves = remainingMoves;
        currentLevel = cR;
        //DEBUG System.out.println(currentLevel);
        this.timeLimit = timeLimit;
    }

    /**
     * Updates level {@link Goal} and returns {@code true} if won.
     * @param l list of board destroyed objects {@link Equivalent}.
     * @return {@code true} if goal reached.
     */
    public boolean update(List<Equivalent> l) {
    	if(!l.isEmpty()) {
    		remainingMoves--;
    		try {
   	         AudioInputStream a = AudioSystem.getAudioInputStream(new File("src/music/expsound.wav"));
   	         Clip clip = AudioSystem.getClip();
   	         clip.open(a);
   	         clip.start();
   	     }catch(LineUnavailableException | IOException | UnsupportedAudioFileException e) {System.out.println(e.getMessage());}
    		 
    	}
        return myGoal.updateCounter(l);
        
    }

    public boolean hasMove() {
        return remainingMoves > 0;
    }
    public int getMoves() {
    	return remainingMoves;
    }
    public long getTimeLimit() {
    	return timeLimit;
    }

    public boolean lost() {
        return !hasMove();
    }

	public boolean lastLevel() {
		return currentLevel == lastLevel;
	}
	public int getCurrentLevel() {
		return currentLevel;
	}
	public int getRemainingObjectives() {
		return myGoal.amountMissing();
	}
	public String getObjective() {
		return myGoal.typeOfEntity();
	}
}
