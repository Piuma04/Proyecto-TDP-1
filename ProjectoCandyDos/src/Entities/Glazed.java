package Entities;

import java.util.List;

import Interfaces.Equivalent;

import java.util.LinkedList;
import Logic.Block;
import Logic.Board;

public class Glazed extends Entity {

    public Glazed(int rowPosition, int columnPosition) {
        super(rowPosition, columnPosition, Colour.GLAZED);
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.equals(this); }
    @Override public boolean isEqual(Glazed g) { return true; }

    // TODO
    @Override
    public List<Block> getDestroyables(Board b) {
        List<Block> toDestroy = new LinkedList<Block>();
        
        return toDestroy;
    }

    public String toString() { return super.setStringColor("G"); }
}
