package Logic;

import java.util.LinkedList;
import java.util.List;

import GUI.Gui;
import Interfaces.Equivalent;
import Interfaces.EventListener;

public class Level implements EventListener{
    private int remainingMoves;
    private long timeLimit;
    private int currentLevel;
    private List<Goal> goals;
    private Gui myGui;
    private Game myGame;
    
    private static final int lastLevel = 5;

    public Level(List<Goal> l, int remainingMoves, int timeLimit, int cR, Gui myGui, Game myGame) {
        goals = l;
        this.remainingMoves = remainingMoves;
        currentLevel = cR;
        this.timeLimit = timeLimit;
        this.myGame = myGame;
        this.myGui = myGui;
        myGui.showObjective(getObjectives(), getRemainingObjectives());
        myGui.setCurrentLevel("Nivel " + currentLevel);
        myGui.updateMoves(remainingMoves);
    }

    /**
     * Updates level {@link Goal} and returns {@code true} if won.
     * 
     * @param l list of board destroyed objects {@link Equivalent}.
     * @return {@code true} if goal reached.
     */
    public void update(List<Equivalent> l) {
        boolean finished = true;
        int cont = 0;
        if (!l.isEmpty())
            remainingMoves--;
        for (Equivalent equivalent : l) { for (Goal g : goals) { g.updateCounter(equivalent); } }
        while (finished && cont < goals.size()) {
            finished = goals.get(cont).finished();
            cont++;
        }
        myGui.executeAfterAnimation(() -> {
			myGui.updateMoves(remainingMoves);
			myGui.updateGraphicObjective(getRemainingObjectives());
        });
        if(finished) {
        	myGame.win();
        }else if(remainingMoves <= 0){
        	myGame.lost();
        }
        
    }

    public boolean hasMove()     { return remainingMoves > 0; }
    public int getMoves()        { return remainingMoves; }
    public long getTimeLimit()   { return timeLimit; }
    public boolean lost()        { return !hasMove(); }
    public boolean isLastLevel() { return currentLevel == lastLevel; }
    public int getCurrentLevel() { return currentLevel; }

    private List<Integer> getRemainingObjectives() {
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
