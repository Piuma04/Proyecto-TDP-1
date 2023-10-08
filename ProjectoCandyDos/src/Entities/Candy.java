package Entities;

import java.util.List;

import Interfaces.Equivalent;
import Interfaces.Swappable;

import java.util.LinkedList;

import Logic.Block;
import Logic.Board;

public class Candy extends Entity {

    public Candy(int rowPosition, int columnPosition, Colour colour) {
        super(rowPosition, columnPosition, colour);
    }

    
    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); } 
    @Override public boolean isEqual(Candy c) { return this.colour == c.getColour(); }
    @Override public boolean isEqual(Wrapped w) { return this.colour == w.getColour(); }
    @Override public boolean isEqual(Stripped s) { return this.colour == s.getColour(); }

    @Override public boolean isSwappable(Swappable e) { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c) { return true; }
    @Override public boolean canReceive(Stripped s) { return true; }
    @Override public boolean canReceive(Wrapped w) { return true; }

    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        toDestroy.add(b.getBlock(row, column));
        int[] adyacentRows = { -1, 0, 1, 0 };
        int[] adyacentColumns = { 0, -1, 0, 1 };

        for (int i = 0; i < 4; i++) {
            int newRow = row + adyacentRows[i];
            int newColumn = column + adyacentColumns[i];

            if (Board.isValidBlock(newRow, newColumn)
                    && b.getBlock(newRow, newColumn).getEntity().getColour() == Colour.GLAZED) {
                // pregunta si hay algun glaseado alrededor
                // *podria ser b.getBlock(newRow,newColumn).getEntity()==Colour.GLAZED
                toDestroy.add(b.getBlock(newRow, newColumn));
            }
        }
        return toDestroy;
    }
    @Override public void destroy() {
        if (colour != Colour.GREEN) playGif("explosion.gif");
        else playGif("GREENG.gif");
        setImage(null);
    }
    public String toString() { return super.setStringColor("C"); }
}
