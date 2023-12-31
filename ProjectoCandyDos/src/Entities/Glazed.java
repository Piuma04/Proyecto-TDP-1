package Entities;

import java.util.List;

import Interfaces.Equivalent;

import Enums.Colour;

import java.util.LinkedList;

import Logic.Block;
import Logic.Board;

public class Glazed extends Entity {
    public Glazed(int rowPosition, int columnPosition) { super(rowPosition, columnPosition, Colour.GLAZED); }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); }
    @Override public boolean isEqual(Glazed g) 			{ return true; }
    @Override public boolean hasCollateralDamage()		{ return true;}

    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        toDestroy.add(b.getBlock(row, column));
        return toDestroy;
    }

    @Override public int getScore(){ return 25; }
    
    public String toString() { return super.setStringColor("M"); }
}
