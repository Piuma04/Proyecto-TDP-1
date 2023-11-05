package Entities;

import java.util.List;
import java.util.Set;

import Enums.Colour;

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
        toDestroy.addAll(getBlockColumn(this.column,b));
        toDestroy.addAll(getBlockColumn(this.column+1,b));
        toDestroy.addAll(getBlockColumn(this.column-1,b));
        toDestroy.addAll(getBlockRow(this.row,b));
        toDestroy.addAll(getBlockRow(this.row+1,b));
        toDestroy.addAll(getBlockRow(this.row-1,b));
        return toDestroy;
    }
    @Override public Set<Block> getSpecialDestroyables(Wrapped c, Board b)	
    {
    	Set<Block> toDestroy = new HashSet<Block>();
        toDestroy.addAll(getBlockSquare(this.row,this.column,2,b));
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
        toDestroy.addAll(getBlockSquare(this.row, this.column, 1, b));	
        return toDestroy;
    }

    @Override public int getScore() { return 50; }

    public String toString() { return super.setStringColor("W"); }
}