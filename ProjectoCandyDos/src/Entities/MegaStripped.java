package Entities;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;
import Interfaces.SpecialDestroy;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

public class MegaStripped extends Entity {



	  public MegaStripped(int rowPosition, int columnPosition, Colour colour) {
		  super(rowPosition, columnPosition, colour);
	       
	        String[] p = imagePath.split("/");
	        imagePath = p[0] + "/" + "Z" + p[1];
	  }
	

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); }
    @Override public boolean isEqual(MegaStripped m)        { return m.getColour() == colour; }
    @Override public boolean isEqual(Candy c)         { return c.getColour() == colour; }

    @Override public boolean isSwappable(Swappable e)   { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c)        { return true; }
    @Override public boolean canReceive(Stripped s)     { return true; }
    @Override public boolean canReceive(Wrapped w)      { return true; }
    @Override public boolean canReceive(MegaStripped m)      { return true; }

    

    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        visited = true;
        toDestroy.add(b.getBlock(row, column));
        for (int c = 0; c < Board.getColumns(); c++) {
             if (!(c == column) && !b.getBlock(row, c).getEntity().isVisited())
                    toDestroy.addAll(b.getBlock(row, c).getEntity().getDestroyables(b));
        }
        for (int r = 0; r < Board.getRows(); r++) {
        	if (!(r == row) && !b.getBlock(r, column).getEntity().isVisited())
                    toDestroy.addAll(b.getBlock(r, column).getEntity().getDestroyables(b));
        }
       
        
        return toDestroy;
    }

    public int getScore()
    {
    	return 100;
    }
    public String toString() { return super.setStringColor("Z"); }

}
