package Entities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Enums.Colour;
import Interfaces.Equivalent;
import Interfaces.SpecialDestroy;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

public class MegaStripped extends Entity {

    public MegaStripped(int rowPosition, int columnPosition, Colour colour) {
        super(rowPosition, columnPosition, colour);
        
        String prefix = "MS";
        imagePath = colour.toString() + "/" + prefix + colour.toString() + ".png";
        explosionGif = colour.toString() + "/explosion/" + prefix + colour.toString() + ".gif";
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); }
    @Override public boolean isEqual(MegaStripped m)    { return this.colour == m.getColour(); }
    @Override public boolean isEqual(Candy c)           { return this.colour == c.getColour(); }

    @Override public boolean isSwappable(Swappable e)   { return e.canReceive(this); }
    @Override public boolean canReceive(Candy c)        { return true; }
    @Override public boolean canReceive(Stripped s)     { return true; }
    @Override public boolean canReceive(Wrapped w)      { return true; }
    @Override public boolean canReceive(MegaStripped m) { return true; }
    @Override public boolean canReceive(Bomb b)         { return true; }

    @Override public Set<Block> getSpecialDestroy(SpecialDestroy e, Board b){return e.getSpecialDestroyables(this, b);}
    public Set<Block> getSpecialDestroyables(Candy c, Board b){
    	return specialCase(c, b);
    	
    }
    public Set<Block> getSpecialDestroyables(Stripped c, Board b){
    	return specialCase(c, b);
    	
    }
    public Set<Block> getSpecialDestroyables(Wrapped c, Board b){
    	return specialCase(c,b);
    	
    }
    public Set<Block> getSpecialDestroyables(MegaStripped m,  Board b){
    	return specialCase(m, b);
    }
    
    private Set<Block> specialCase(Entity e, Board b){
    	Set<Block> s = new HashSet<>();
    	if(e.getColour() == colour) s.addAll(getDestroyables(b));
    	return s;
    }
    

    
    @Override
     public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        toDestroy.add(b.getBlock(row, column));
    	  for (int c = 0; c < Board.getColumns(); c++) {
              if (c != column)
                     toDestroy.add(b.getBlock(row, c));
         }
         for (int r = 0; r < Board.getRows(); r++) {
         	if (r != row)
                     toDestroy.add(b.getBlock(r, column));
         }	
       
        return toDestroy;
    }

    @Override public int getScore() { return 100; }

    public String toString() { return super.setStringColor("M"); }
}
