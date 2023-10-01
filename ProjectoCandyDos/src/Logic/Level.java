package Logic;

import java.util.List;

import Entities.Entity;
import Interfaces.Equivalent;

public class Level {
    private int remainingMoves;
    private Goal myGoal;
    private Clock myClock;
    private long timeLimit;

    public Level(Entity toDestroyEntityType, int amount, int remainingMoves, int timeLimit) {
        myGoal = new Goal(amount, toDestroyEntityType);
        myClock = new Clock();
    }

    /**
     * Updates level {@link Goal} and returns {@code true} if won.
     * @param l list of board destroyed objects {@link Equivalent}.
     * @return {@code true} if goal reached.
     */
    public boolean update(List<Equivalent> l) {
        return myGoal.updateCounter(l);
    }

    public boolean hasMove() {
        return remainingMoves > 0;
    }

    public boolean lost() {
        return !hasMove() || !(myClock.getTime() < timeLimit * 1000l);
    }

}
