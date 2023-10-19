package Logic;

import Interfaces.Equivalent;

public class Goal {
    private int counter;
    private Equivalent type;

    public Goal(int amount, Equivalent toDestroyEntityType) {
        counter = amount;
        type = toDestroyEntityType;
    }

    /**
     * 
     * @param equivalentList list of {@link Equivalent} to compare ALL of them to
     * {@code this.type}
     * @return {@code true} if goal is reached.
     */
    public void updateCounter(Equivalent equivalent) {
        // System.out.println(equivalentList.toString());
        if (counter > 0 && type.isEquivalent(equivalent))
            counter--;
    }

    public boolean finished() { return counter <= 0; }
    public int amountMissing() { return counter; }
    public String typeOfEntity() { return type.getImage(); }
}
