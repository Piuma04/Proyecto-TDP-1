package Entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Enums.Colour;

import java.util.HashSet;
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

        String prefix = isHorizontal ? "H" : "V";
        imagePath = colour.toString() + "/" + prefix + colour.toString() + ".png";
        explosionGif = colour.toString() + "/explosion/" + prefix + colour.toString() + ".gif";
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
    @Override public boolean canReceive(MegaStripped m)      { return true; }
    @Override public boolean canReceive(Bomb b) { return true; }

    @Override public Set<Block> getSpecialDestroy(SpecialDestroy e, Board b){return e.getSpecialDestroyables(this, b);}
    @Override public Set<Block> getSpecialDestroyables(Stripped car, Board b){
    	 Set<Block> toDestroy = new HashSet<Block>();
    	 toDestroy.add(b.getBlock(this.row, this.column));
    	 toDestroy.add(b.getBlock(car.getRow(), car.getColumn()));
             for (int c = 0; c < Board.getColumns(); c++) {
                 toDestroy.addAll(b.getBlock(row, c).getEntity().getDestroyables(b));
             }
             
             for (int r = 0; r < Board.getRows(); r++) {
                 toDestroy.addAll(b.getBlock(r, column).getEntity().getDestroyables(b));
             }
         return toDestroy;
    }
    @Override public Set<Block> getSpecialDestroyables(Wrapped car, Board b){
    	 Set<Block> toDestroy = new HashSet<Block>();
    	 toDestroy.add(b.getBlock(this.row, this.column));
    	 toDestroy.add(b.getBlock(car.getRow(), car.getColumn()));
         for (int c = 0; c < Board.getColumns(); c++) {
        	 for(int widthR = row-1; widthR<=row+1;widthR++)
        		 if (!(c == column) && widthR>=0 && widthR<Board.getRows())
        			 toDestroy.add(b.getBlock(widthR, c));
         }
             
             for (int r = 0; r < Board.getRows(); r++) {
            	 for(int widthC = column-1; widthC<=column+1;widthC++)
                   if (!(r == row) && widthC>=0 && widthC<Board.getColumns())
                     toDestroy.add(b.getBlock(r, widthC));
             }
         return toDestroy;
    }

    @Override public Set<Block> getSpecialDestroyables(MegaStripped m,  Board b){
    	Set<Block> s = new HashSet<>();
    	if(m.getColour() == colour) {
    		s.addAll(m.getDestroyables(b));
    	}
    	return s;
    }
    
    @Override public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        toDestroy.add(b.getBlock(row, column));
    	if (isHorizontal)
            for (int c = 0; c < Board.getColumns(); c++) {
                toDestroy.add(b.getBlock(row, c));
            }
        else
            for (int r = 0; r < Board.getRows(); r++) {
                toDestroy.add(b.getBlock(r, column));
            }
    	// ahora destruye si hay glazed/ bombas en pos adyacentes
        int[] adyacentRows = { -1, 0, 1, 0 };
        int[] adyacentColumns = { 0, -1, 0, 1 };
        for (int i = 0; i < 4; i++) {
            int newRow = row + adyacentRows[i];
            int newColumn = column + adyacentColumns[i];
            if (Board.isValidBlockPosition(newRow, newColumn)) {
                Block block = b.getBlock(newRow, newColumn);
                if (block.getEntity().hasCollateralDamage())
                    toDestroy.add(block);
            }
        }

        return toDestroy;
    }

    @Override public int getScore() { return isHorizontal ? 45 : 35; }

    public String toString() { return super.setStringColor((isHorizontal ? "H" : "V")); }
}
