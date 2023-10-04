package Logic;

import java.util.List;

import Entities.Entity;
import Interfaces.Equivalent;

public class Level {
    private int remainingMoves;
    private Goal myGoal;
    private Clock myClock;
    private long timeLimit;
    private int currentLevel;
    private static final int lastLevel = 5;

    public Level(Entity toDestroyEntityType, int amount, int remainingMoves, int timeLimit, int cR) {
        myGoal = new Goal(amount, toDestroyEntityType);
        myClock = new Clock();
        this.remainingMoves = remainingMoves;
        currentLevel = cR;
        System.out.println(currentLevel);
    }

    /**
     * Updates level {@link Goal} and returns {@code true} if won.
     * @param l list of board destroyed objects {@link Equivalent}.
     * @return {@code true} if goal reached.
     */
    public boolean update(List<Equivalent> l) {
    	remainingMoves--;
        return myGoal.updateCounter(l);
        
    }

    public boolean hasMove() {
        return remainingMoves > 0;
    }
    public int getMoves() {
    	return remainingMoves;
    }

    public boolean lost() {
        return !hasMove(); //|| !(myClock.getTime() < timeLimit * 1000l);
    }

	public boolean lastLevel() {
		
		return currentLevel == lastLevel;
	}
	public int getRemainingObjectives() {
		return myGoal.amountMissing();
	}
	public String getObjective() {
		return myGoal.typeOfEntity();
	}
}
