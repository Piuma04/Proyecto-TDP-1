package Entities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        if(!visited)
        {
        	  for (int c = 0; c < Board.getColumns(); c++) {
                  if (!(c == column) && !b.getBlock(row, c).getEntity().isVisited())
                         toDestroy.addAll(b.getBlock(row, c).getEntity().getDestroyables(b));
             }
             for (int r = 0; r < Board.getRows(); r++) {
             	if (!(r == row) && !b.getBlock(r, column).getEntity().isVisited())
                         toDestroy.addAll(b.getBlock(r, column).getEntity().getDestroyables(b));
             }	
        }  
        visited = true;
        return toDestroy;
    }

    public int getScore()
    {
    	return 100;
    }
    public String toString() { return super.setStringColor("Z"); }

}
