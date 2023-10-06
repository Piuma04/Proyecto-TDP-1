package Entities;

public class Jelly extends Modifier {

    public Jelly(int rowPosition, int columnPosition) {
        row = rowPosition;
        column = columnPosition;
        imagePath = "a.png";
    }

    public boolean equals(Jelly j) { return true; }
}
