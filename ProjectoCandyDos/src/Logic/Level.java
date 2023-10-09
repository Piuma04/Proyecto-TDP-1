package Logic;

import java.util.List;

import Interfaces.Equivalent;

public class Level {
    private int remainingMoves;
    private Goal myGoal;
    
    private long timeLimit;
    private int currentLevel;
    private static final int lastLevel = 5;

    public Level(Equivalent toDestroyEntityType, int amount, int remainingMoves, int timeLimit, int cR) {
        myGoal = new Goal(amount, toDestroyEntityType);
        this.remainingMoves = remainingMoves;
        currentLevel = cR;
        this.timeLimit = timeLimit;
    }

    /**
     * Updates level {@link Goal} and returns {@code true} if won.
     * @param l list of board destroyed objects {@link Equivalent}.
     * @return {@code true} if goal reached.
     */
    public boolean update(List<Equivalent> l) {
    	if(!l.isEmpty()) remainingMoves--;
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

	public boolean isLastLevel() {
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
