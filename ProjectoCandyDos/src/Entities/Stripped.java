package Entities;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

public class Stripped extends Entity {
    protected boolean isHorizontal;

    public Stripped(int rowPosition, int columnPosition, Colour colour, boolean isHorizontal) {
        super(rowPosition, columnPosition, colour);
        this.isHorizontal = isHorizontal;
        String[] p = imagePath.split("/");
        imagePath = p[0] + "/" +  (isHorizontal ? "H" : "V") + p[1];
        //imagePath = (isHorizontal ? "H" : "V") + imagePath;
    }

    public Stripped(int rowPosition, int columnPosition, Colour colour) {
        this(rowPosition, columnPosition, colour, false);
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); } 
    //@Override public boolean equals(Candy c) { return false; }
    @Override public boolean isEqual(Stripped s) { return s.getColour() == colour; }

    @Override public boolean isSwappable(Swappable e) { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c) { return true; }
    @Override public boolean canReceive(Stripped s) { return true; }
    @Override public boolean canReceive(Wrapped w) { return true; }

    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        if (isHorizontal)
            for (int c = 0; c < Board.getColumns(); c++)
                toDestroy.add(b.getBlock(row, c));
        else
            for (int r = 0; r < Board.getRows(); r++)
                toDestroy.add(b.getBlock(r, column));
        return toDestroy;
    }

    public String toString() { return super.setStringColor((isHorizontal ? "H" : "V")); }
}
