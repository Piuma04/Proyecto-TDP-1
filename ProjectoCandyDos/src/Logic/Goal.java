package Logic;

import java.util.List;

import Interfaces.Equivalent;
import Entities.Entity;

public class Goal {
    private int counter;
    private Entity type;

    public Goal(int amount, Entity toDestroyEntityType) {
        counter = amount;
        type = toDestroyEntityType;
    }

    /**
     * 
     * @param equivalentList list of {@link Equivalent} to compare ALL of them to {@code this.type}
     * @return {@code true} if goal is reached.
     */
    public boolean updateCounter(List<Equivalent> equivalentList) {
        for (Equivalent entity : equivalentList)
            if (entity.isEquivalent(type))
                counter--;
        return counter <= 0;
    }
}
