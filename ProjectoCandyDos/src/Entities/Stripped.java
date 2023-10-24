package Entities;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;
import Interfaces.SpecialDestroy;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

public class Stripped extends Entity {
    protected boolean isHorizontal;

    public Stripped(int rowPosition, int columnPosition, Colour colour, boolean isHorizontal) {
        super(rowPosition, columnPosition, colour);
        this.isHorizontal = isHorizontal;
        String[] p = imagePath.split("/");
        imagePath = p[0] + "/" + (isHorizontal ? "H" : "V") + p[1];
    }

    public Stripped(int rowPosition, int columnPosition, Colour colour) {
        this(rowPosition, columnPosition, colour, false);
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); }
    @Override public boolean isEqual(Stripped s)        { return s.getColour() == colour; }
    @Override public boolean isEqual(Candy c)         { return c.getColour() == colour; }

    @Override public boolean isSwappable(Swappable e)   { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c)        { return true; }
    @Override public boolean canReceive(Stripped s)     { return true; }
    @Override public boolean canReceive(Wrapped w)      { return true; }

    @Override public boolean isSpecialSwap(SpecialDestroy e) { return e.hasSpecialExplosion(this); }
    @Override public boolean hasSpecialExplosion(Stripped c) { return true; }
    @Override public boolean hasSpecialExplosion(Wrapped c)  { return true; }

    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        visited = true;
        toDestroy.add(b.getBlock(row, column));
        if (isHorizontal)
            for (int c = 0; c < Board.getColumns(); c++) {
                if (!(c == column) && !b.getBlock(row, c).getEntity().isVisited())
                    toDestroy.addAll(b.getBlock(row, c).getEntity().getDestroyables(b));
            }
        else
            for (int r = 0; r < Board.getRows(); r++) {
                if (!(r == row) && !b.getBlock(r, column).getEntity().isVisited())
                    toDestroy.addAll(b.getBlock(r, column).getEntity().getDestroyables(b));
            }
        // ahora destruye si hay glazed en pos adyacentes
        int[] adyacentRows = { -1, 0, 1, 0 };
        int[] adyacentColumns = { 0, -1, 0, 1 };
        for (int i = 0; i < 4; i++) {
            int newRow = row + adyacentRows[i];
            int newColumn = column + adyacentColumns[i];
            if (Board.isValidBlock(newRow, newColumn) && b.getBlock(newRow, newColumn).getEntity()
                    .getColour() == Colour.GLAZED) {
                toDestroy.add(b.getBlock(newRow, newColumn));
            }
        }
        return toDestroy;
    }

    public int getScore()
    {
    	int score = 0;
    	if(isHorizontal)
    		score = 45;
    	else
    		score = 35;
    	return score;
    }
    public String toString() { return super.setStringColor((isHorizontal ? "H" : "V")); }
}
