package Entities;

public class Jelly extends Modifier {

    public Jelly(int rowPosition, int columnPosition) {
        row = rowPosition;
        column = columnPosition;
        imagePath = "a.png";
    }
    
    @Override
    public void destroy() {
        imagePath = null;
        getGraphicalEntity().notifyChangeState();
    }

    public boolean equals(Jelly j) { return true; }
}
