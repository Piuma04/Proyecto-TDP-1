package Interfaces;



public interface GameOverOnly {

    public void finalLost();

    public void addPausableObserver(PausableObserver toAdd);
    public void removePausableObserver(PausableObserver poToRemove);
}
