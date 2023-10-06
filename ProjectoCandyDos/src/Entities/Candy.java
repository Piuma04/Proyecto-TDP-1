package Entities;

import java.util.List;
import java.util.LinkedList;

import Logic.Block;
import Logic.Board;

public class Candy extends Entity {

    public Candy(int rowPosition, int columnPosition, Colour colour) {
        super(rowPosition, columnPosition, colour);
    }

    @Override public boolean isEquivalent(Entity e) { return e.equals(this); }
    @Override public boolean equals(Candy c) { return c.getColour() == this.colour; }
    @Override public boolean equals(Glazed g) { return false; }
    @Override public boolean equals(Wrapped w) { return w.getColour() == this.colour;}
    @Override public boolean equals(Stripped s) { return s.getColour() == this.colour; }
    @Override public boolean equals(Jelly j) { return false; }
    @Override public boolean equals(Empty e) { return false; }

    @Override public boolean isSwappable(Entity e) { return e.canReceive(this);}
    @Override public boolean canReceive(Candy c) { return true; }
    @Override public boolean canReceive(Glazed g) { return false;}
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

            // isVAlidPosition. TODO
            if (newRow >= 0 && newRow < Board.getRows() && newColumn >= 0 && newColumn < Board.getColumns()
                    && b.getBlock(newRow, newColumn).getEntity().getColour() == Colour.GLAZED) {
                // pregunta si hay algun glaseado alrededor
                // *podria ser b.getBlock(newRow,newColumn).getEntity()==Colour.GLAZED
                toDestroy.add(b.getBlock(newRow, newColumn));
            }
        }
        return toDestroy;
    }

    public String toString() { return super.setStringColor("C"); }
}
