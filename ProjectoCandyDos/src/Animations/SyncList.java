package Animations;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class SyncList<T extends Identifiable> {

    protected List<T> list;
    protected Map<Integer, Integer> typeCount;
    protected int currentType;

    public SyncList() {
        list = new LinkedList<T>();
        typeCount = new HashMap<Integer, Integer>();
        currentType = -1;
    }

    /**
     * @param element
     * @return {@code true} if there are NO different elements on the list.
     */
    synchronized public boolean add(T element) {

        final int id = element.id();
        if (list.isEmpty())
            currentType = id;

        Integer amount = typeCount.get(id);
        amount = amount == null ? 1 : amount+1;
        typeCount.put(id, amount);

        list.add(element);
        return amount == list.size();
    }

    synchronized public T peek() {
        T first = list.isEmpty() ? null : list.get(0);
        if (first != null)
            first = (currentType == first.id()) ? first : null; 
        return first;
    }

    synchronized public List<T> peekSameTypes() {
        List<T> sameTypes = new LinkedList<T>();

        for (T iter : list) {
            if (currentType != iter.id())
                break;
            sameTypes.add(iter);
        }

        return sameTypes;
    }

    synchronized public void remove(T element) {
        if (list.remove(element)) {
            final int id = element.id();
            Integer amount = typeCount.get(id) - 1;
            typeCount.put(id, amount);
        }
    }

    synchronized public void updateType() { currentType = !list.isEmpty() ? list.get(0).id() : -1; }
    synchronized public int getCurrentType() { return currentType; }
    synchronized public boolean isEmpty() { return list.isEmpty(); }
    synchronized public int size() { return list.size(); }

    synchronized public String toString() { return list.toString(); }
}
