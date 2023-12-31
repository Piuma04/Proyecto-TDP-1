package Entities;

import Interfaces.Equivalent;

public class Jelly extends Modifier {

    public Jelly(int rowPosition, int columnPosition) {
        row = rowPosition;
        column = columnPosition;
        imagePath = "JELLY/JELLY.png";
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); } 
    @Override public boolean isEqual(Jelly j) { return true; }

    @Override public void destroy() {
        playGif("JELLY/explosion/JELLY.gif");
        setImage(null);
    }

    @Override public int getScore() { return 10; }

    public String toString() { return "J"; }
}