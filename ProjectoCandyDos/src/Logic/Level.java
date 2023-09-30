package Logic;

import java.util.List;
import Interfaces.Equivalent;

public class Level {
    private int remainingMoves;
    private Goal myGoal;
    private Clock myClock;
    
    
    public void update(List<Equivalent> l) {
        myGoal.updateCounter(l);
    }
    
    public boolean hasMove() {
        return remainingMoves > 0;
    }
}
