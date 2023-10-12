package Entities;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;
import Logic.Block;
import Logic.Board;


public class Empty extends Entity {

    public Empty(int rowPosition, int columnPosition) {
        super(rowPosition, columnPosition, Colour.NONE);
    }

    public Empty() { this(0, 0); }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEqual(this); } 
    @Override public boolean isEqual(Empty e) { return true; }

    @Override public void destroy() {}
    
    @Override public List<Block> getDestroyables(Board b) { return new LinkedList<Block>(); }

    @Override public String getImage() { return null; }

    // DO NOT UNCOMMENT NEXT 2 LINES.
    //@Override public void setImage(String imageName) { }
    //@Override public void changePosition(int newRow, int newColumn) { row = newRow; column = newColumn; }

    public String toString() { return super.setStringColor("*"); }

}
