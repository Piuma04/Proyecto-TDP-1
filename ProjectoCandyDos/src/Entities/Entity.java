package Entities;

import java.util.List;
import java.util.LinkedList;
import java.util.Set;

import Enums.Colour;

import java.util.ArrayList;
import java.util.HashSet;

import Interfaces.SpecialDestroy;
import Interfaces.Collateral;
import Interfaces.Equivalent;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;
import Logic.VisualEntityDummy;

public abstract class Entity extends VisualEntityDummy implements Equivalent, Swappable, SpecialDestroy,Collateral {
    protected Colour colour;
    protected String explosionGif;

    Entity(int rowPosition, int columnPosition, Colour colour) {
        this.setPicSize(this.getPicSize() - 35);
        row = rowPosition;
        column = columnPosition;
        this.colour = colour;

        imagePath = colour.toString() + "/" + colour.toString() + ".png";
        explosionGif = colour.toString() + "/explosion/" + colour.toString() + ".gif";
    }

    public Colour getColour() { return colour; }

    @Override public boolean isEquivalent(Equivalent e) { return false; }
    @Override public boolean isEqual(Candy c)           { return false; }
    @Override public boolean isEqual(Stripped s)        { return false; }
    @Override public boolean isEqual(Wrapped w)         { return false; }
    @Override public boolean isEqual(Glazed g)          { return false; }
    @Override public boolean isEqual(Empty e)           { return false; }
    @Override public boolean isEqual(Jelly j)           { return false; }
    @Override public boolean isEqual(MegaStripped m)    { return false; }
    @Override public boolean isEqual(Bomb b)            { return false; }
    @Override public int getScore() { return 0; }

    @Override public boolean isSwappable(Swappable e)   { return false; }
    @Override public boolean canReceive(Candy c)        { return false; }
    @Override public boolean canReceive(Stripped s)     { return false; }
    @Override public boolean canReceive(Wrapped w)      { return false; }
    @Override public boolean canReceive(Glazed g)       { return false; }
    @Override public boolean canReceive(MegaStripped m) { return false; }
    @Override public boolean canReceive(Bomb b)         { return false; }
    
    @Override public boolean hasCollateralDamage()      { return false; }

    @Override public Set<Block> getSpecialDestroy(SpecialDestroy e, Board b)    {return new HashSet<Block>();}
    @Override public Set<Block> getSpecialDestroyables(Candy c, Board b)        {return new HashSet<Block>();}
    @Override public Set<Block> getSpecialDestroyables(Stripped c, Board b)     {return new HashSet<Block>();}
    @Override public Set<Block> getSpecialDestroyables(Wrapped c, Board b)      {return new HashSet<Block>();}
    @Override public Set<Block> getSpecialDestroyables(Glazed g, Board b)       {return new HashSet<Block>();}
    @Override public Set<Block> getSpecialDestroyables(Empty e, Board b)        {return new HashSet<Block>();}
    @Override public Set<Block> getSpecialDestroyables(MegaStripped m, Board b) {return new HashSet<Block>();}
    @Override public Set<Block> getSpecialDestroyables(Bomb bomb, Board b)      {return new HashSet<Block>();}

    public abstract List<Block> getDestroyables(Board b);

    public void destroy() {
        playGif(explosionGif);
        setImage(null);
    }

    protected String setStringColor(String str) {
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_YELLOW = "\u001B[33m";
        final String ANSI_GREEN = "\u001B[32m";
        final String ANSI_PURPLE = "\u001B[35m";
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_BLACK = "\u001B[30m";
        final String ANSI_CYAN = "\u001B[36m";
        final String ANSI_MAGENTA = "\u001B[35m";
        String colourStr = null;
        switch (this.colour) {
            case RED:    colourStr = ANSI_RED;     break;
            case YELLOW: colourStr = ANSI_YELLOW;  break;
            case GREEN:  colourStr = ANSI_GREEN;   break;
            case PURPLE: colourStr = ANSI_PURPLE;  break;
            case BLUE:   colourStr = ANSI_BLUE;    break;
            case NONE:   colourStr = ANSI_BLACK;   break;
            case GLAZED: colourStr = ANSI_CYAN;    break;
            case BOMB:   colourStr = ANSI_MAGENTA; break;
        }
        return colourStr + str + ANSI_RESET;
    }
    protected List<Block> getBlockRow(int row, Board b)
    {
    	List<Block> blockRow = new LinkedList<Block>();
    	if(row>=0 && row<Board.getRows())
    		for(int col = 0;col<Board.getColumns();col++)
    		{
    			blockRow.add(b.getBlock(row, col));
    		}
    	return blockRow;
    }
    protected List<Block> getBlockColumn(int col, Board b)
    {
    	List<Block> blockColumn = new LinkedList<Block>();
    	if(col>=0 && col<Board.getColumns())
    		for(int row = 0;row<Board.getRows();row++)
    		{
    			blockColumn.add(b.getBlock(row, col));
    		}
    	return blockColumn;
    }
    protected List<Block> getBlockSquare(int row,int col,int radius,Board b)
    {
    	List<Block> blockSquare = new LinkedList<Block>();
    	for (int j = col - radius; j <= col + radius; j++)
            for (int i = row - radius; i <= row + radius; i++) 
                if (Board.isValidBlockPosition(i, j))
                    blockSquare.add(b.getBlock(i, j));
    	return blockSquare;
    }
    protected List<Block> addSurroundingEntities(Board b) {
    	
    	List<Block> toDestroy = new ArrayList<>();
    	int[] adyacentRows = { -1, 0, 1, 0 };
        int[] adyacentColumns = { 0, -1, 0, 1 };
        for (int i = 0; i < 4; i++) {
            int newRow = row + adyacentRows[i];
            int newColumn = column + adyacentColumns[i];
            if (Board.isValidBlockPosition(newRow, newColumn)) {
                Block block = b.getBlock(newRow, newColumn);
                Collateral c = block.getEntity();
                if (c.hasCollateralDamage())
                    toDestroy.add(block);
            }
        }
        return toDestroy;
    }
}
