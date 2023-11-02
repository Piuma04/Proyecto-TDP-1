package Entities;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import Interfaces.SpecialDestroy;
import Interfaces.Equivalent;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;
import Logic.VisualEntityDummy;

public abstract class Entity extends VisualEntityDummy implements Equivalent, Swappable, SpecialDestroy {
    protected Colour colour;
    protected boolean visited = false;
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

    public void visited() { visited = true; }
    public boolean isVisited() { return visited; }

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
}
