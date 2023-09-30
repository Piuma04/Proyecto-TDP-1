package Logic;

import java.util.List;
import java.util.LinkedList;

public class Level {
    private int remainingMoves;
    private Goal myGoal;
    private Clock myClock;
    
    public void update(List<Object> l) {
        
    }
    
    public boolean hasMove() {
        return remainingMoves > 0;
    }
}
