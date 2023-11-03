package Entities;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import Interfaces.Equivalent;
import Interfaces.SpecialDestroy;
import Interfaces.Swappable;
import java.util.LinkedList;
import Logic.Block;
import Logic.Board;

public class Wrapped extends Entity {

    public Wrapped(int rowPosition, int columnPosition, Colour colour) {
        super(rowPosition, columnPosition, colour);

        String prefix = "w";
        imagePath = colour.toString() + "/" + prefix + colour.toString() + ".png";
        explosionGif = colour.toString() + "/explosion/" + prefix + colour.toString() + ".gif";
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); }
    @Override public boolean isEqual(Wrapped w)         { return w.getColour() == colour; }
    @Override public boolean isEqual(Candy c)         { return c.getColour() == colour; }

    @Override public boolean isSwappable(Swappable e)   { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c)        { return true; }
    @Override public boolean canReceive(Stripped s)     { return true; }
    @Override public boolean canReceive(Wrapped w)      { return true; }
    @Override public boolean canReceive(MegaStripped m)      { return true; }
    @Override public boolean canReceive(Bomb b) { return true; }

    @Override public Set<Block> getSpecialDestroy(SpecialDestroy e, Board b){return e.getSpecialDestroyables(this, b);}
    @Override public Set<Block> getSpecialDestroyables(Stripped car, Board b)
    {
    	Set<Block> toDestroy = new HashSet<Block>();
    	toDestroy.add(b.getBlock(this.row, this.column));
   	 	toDestroy.add(b.getBlock(car.getRow(), car.getColumn()));
        for (int c = 0; c < Board.getColumns(); c++) {
       	 for(int widthR = row-1; widthR<=row+1;widthR++)
       		 if ((c != column) && widthR>=0 && widthR<Board.getRows())
       			 toDestroy.add(b.getBlock(widthR, c));
        }
        
        for (int r = 0; r < Board.getRows(); r++) {
       	 for(int widthC = column-1; widthC<=column+1;widthC++)
              if ((r != row) && widthC>=0 && widthC<Board.getColumns())
                toDestroy.add(b.getBlock(r, widthC));
        }
        return toDestroy;

    }
    @Override public Set<Block> getSpecialDestroyables(Wrapped c, Board b)	
    {
    	Set<Block> toDestroy = new HashSet<Block>();
    	toDestroy.add(b.getBlock(this.row, this.column));
   	 	toDestroy.add(b.getBlock(c.getRow(), c.getColumn()));
        for (int j = column - 2; j <= column + 2; j++)
            for (int i = row - 2; i <= row + 2; i++) 
                if (i >= 0 && i < Board.getRows() && j >= 0 && j < Board.getColumns())
                    toDestroy.add(b.getBlock(i, j));
        return toDestroy;
    }
    
    public Set<Block> getSpecialDestroyables(MegaStripped m,  Board b){
    	Set<Block> s = new HashSet<>();
    	if(m.getColour() == colour) {
    		s.addAll(m.getDestroyables(b));
    	}
    	return s;
    }
    
    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        toDestroy.add(b.getBlock(row, column));
    	 for (int j = column - 1; j <= column + 1; j++)
             for (int i = row - 1; i <= row + 1; i++) 
                 if (i >= 0 && i < Board.getRows() && j >= 0 && j < Board.getColumns())
                     toDestroy.add(b.getBlock(i, j));	
        return toDestroy;
    }

    @Override public int getScore() { return 50; }

    public String toString() { return super.setStringColor("W"); }
}