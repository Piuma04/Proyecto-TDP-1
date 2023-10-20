package Entities;

import java.util.List;
import java.util.Set;

import Interfaces.Equivalent;
import Interfaces.SpecialDestroy;
import Interfaces.Swappable;

import java.util.HashSet;
import java.util.LinkedList;
import Logic.Block;
import Logic.Board;

public class Wrapped extends Entity {
    public Wrapped(int rowPosition, int columnPosition, Colour colour) {
        super(rowPosition, columnPosition, colour);
        String p[] = imagePath.split("/");
        imagePath = p[0] + "/w" + p[1];
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); }
    @Override public boolean isEqual(Wrapped w)         { return w.getColour() == colour; }
    @Override public boolean isSwappable(Swappable e)   { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c)        { return true; }
    @Override public boolean canReceive(Stripped s)     { return true; }
    @Override public boolean canReceive(Wrapped w)      { return true; }

    @Override public Set<Block> getSpecialDestroy(SpecialDestroy e, Board b){return e.getSpecialDestroyables(this, b);}
    @Override public Set<Block> getSpecialDestroyables(Stripped car, Board b)
    {
    	Set<Block> toDestroy = new HashSet<Block>();
    	toDestroy.add(b.getBlock(this.row, this.column));
   	 	visited = true;
   	 	toDestroy.add(b.getBlock(car.getRow(), car.getColumn()));
   	 	car.visited();
            for (int c = 0; c < Board.getColumns(); c++) {
           	 for(int widthR = row-1; widthR<=row+1;widthR++)
           		 if (!(c == column) && widthR>=0 && widthR<Board.getRows() && !b.getBlock(widthR, c).getEntity().isVisited())
           			 toDestroy.add(b.getBlock(widthR, c));
            }
            
            for (int r = 0; r < Board.getRows(); r++) {
           	 for(int widthC = column-1; widthC<=column+1;widthC++)
                  if (!(r == row) && widthC>=0 && widthC<Board.getColumns() && !b.getBlock(r, widthC).getEntity().isVisited())
                    toDestroy.add(b.getBlock(r, widthC));
            }
        return toDestroy;

    }
    @Override public Set<Block> getSpecialDestroyables(Wrapped c, Board b)	
    {
    	Set<Block> toDestroy = new HashSet<Block>();
    	toDestroy.add(b.getBlock(this.row, this.column));
   	 	visited = true;
   	 	toDestroy.add(b.getBlock(c.getRow(), c.getColumn()));
   	 	c.visited();
        for (int j = column - 2; j <= column + 2; j++)
            for (int i = row - 2; i <= row + 2; i++) 
                if (i >= 0 && i < Board.getRows() && j >= 0 && j < Board.getColumns() && !b.getBlock(i, j).getEntity().isVisited())
                    toDestroy.add(b.getBlock(i, j));
        return toDestroy;
    }
    
    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        visited = true;
        toDestroy.add(b.getBlock(row, column));
        if(!visited)
        {
        	 for (int j = column - 1; j <= column + 1; j++)
                 for (int i = row - 1; i <= row + 1; i++) 
                     if (i >= 0 && i < Board.getRows() && j >= 0 && j < Board.getColumns() && !b.getBlock(i, j).getEntity().isVisited())
                         toDestroy.add(b.getBlock(i, j));
        }
        return toDestroy;
    }

    public String toString() { return super.setStringColor("W"); }
}