package Logic;
import java.util.List;

public class Goal {
    private int entityCounter;
    //private Equivalent equivalentEntity;
    
    public void updateCounter(List<Object> equivalentList) { // change list to equivalent class.
        for (Object o : equivalentList) { // change type to equivalent class.
            entityCounter++;
        }
    }
}
