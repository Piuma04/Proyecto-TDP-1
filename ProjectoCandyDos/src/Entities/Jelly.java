package Entities;

public class Jelly extends Modifier {

    public Jelly(int rowPosition, int columnPosition) {
        row = rowPosition;
        column = columnPosition;
        imagePath = "JELLY.png";
    }

    @Override
    public void destroy() { setImage(null); }

    public boolean equals(Jelly j) { return true; }

    public String toString() {
        return "J";
    }
}