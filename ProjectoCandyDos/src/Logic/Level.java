package Logic;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;

public class Level {
    private int remainingMoves;
    private long timeLimit;
    private int currentLevel;
    private List<Goal> goals;
    private static final int lastLevel = 5;

    public Level(List<Goal> l, int remainingMoves, int timeLimit, int cR) {
        goals = l;
        this.remainingMoves = remainingMoves;
        currentLevel = cR;
        this.timeLimit = timeLimit;
    }

    /**
     * Updates level {@link Goal} and returns {@code true} if won.
     * 
     * @param l list of board destroyed objects {@link Equivalent}.
     * @return {@code true} if goal reached.
     */
    public boolean update(List<Equivalent> l) {
        boolean finished = true;
        int cont = 0;
        if (!l.isEmpty())
            remainingMoves--;
        for (Equivalent equivalent : l) { for (Goal g : goals) { g.updateCounter(equivalent); } }
        while (finished && cont < goals.size()) {
            finished = goals.get(cont).finished();
            cont++;
        }
        return finished;
    }

    public boolean hasMove()     { return remainingMoves > 0; }
    public int getMoves()        { return remainingMoves; }
    public long getTimeLimit()   { return timeLimit; }
    public boolean lost()        { return !hasMove(); }
    public boolean isLastLevel() { return currentLevel == lastLevel; }
    public int getCurrentLevel() { return currentLevel; }

    public List<Integer> getRemainingObjectives() {
        List<Integer> l = new LinkedList<>();
        for (Goal g : goals)
            l.add(g.amountMissing());
        return l;
    }

    public List<String> getObjectives() {
        List<String> l = new LinkedList<>();
        for (Goal g : goals)
            l.add(g.typeOfEntity());
        return l;
    }
}
