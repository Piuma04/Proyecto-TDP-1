package Entities;

import java.util.List;

import Interfaces.SpecialDestroy;
import Interfaces.Equivalent;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;
import Logic.VisualEntityDummy;

public abstract class Entity extends VisualEntityDummy implements Equivalent, Swappable, SpecialDestroy {
    protected Colour colour;
    protected boolean visited = false;

    Entity(int rowPosition, int columnPosition, Colour colour) {
        this.setPicSize(this.getPicSize() - 35);
        row = rowPosition;
        column = columnPosition;
        this.colour = colour;
        imagePath = colour.toString() + "/" + colour.toString() + ".png";
    }

    public Colour getColour() { return colour; }

    public abstract List<Block> getDestroyables(Board b);

    @Override public boolean isEquivalent(Equivalent e) { return false; }
    @Override public boolean isEqual(Candy c)    { return false; }
    @Override public boolean isEqual(Stripped s) { return false; }
    @Override public boolean isEqual(Wrapped w)  { return false; }
    @Override public boolean isEqual(Glazed g)   { return false; }
    @Override public boolean isEqual(Empty e)    { return false; }
    @Override public boolean isEqual(Jelly j)    { return false; }

    @Override public boolean isSwappable(Swappable e) { return false; }
    @Override public boolean canReceive(Candy c)      { return false; }
    @Override public boolean canReceive(Stripped s)   { return false; }
    @Override public boolean canReceive(Wrapped w)    { return false; }
    @Override public boolean canReceive(Glazed g)     { return false; }

    @Override public boolean isSpecialSwap(Entity e) { return false; }
    @Override public boolean hasSpecialExplosion(Candy c)    { return false; }
    @Override public boolean hasSpecialExplosion(Empty e)    { return false; }
    @Override public boolean hasSpecialExplosion(Wrapped w)  { return false; }
    @Override public boolean hasSpecialExplosion(Stripped s) { return false; }
    @Override public boolean hasSpecialExplosion(Glazed g)  { return false; }

    public void destroy() {
        playGif("explosion.gif");
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
        String colourStr = null;
        switch (this.colour) {
        case RED:
            colourStr = ANSI_RED;
            break;
        case YELLOW:
            colourStr = ANSI_YELLOW;
            break;
        case GREEN:
            colourStr = ANSI_GREEN;
            break;
        case PURPLE:
            colourStr = ANSI_PURPLE;
            break;
        case BLUE:
            colourStr = ANSI_BLUE;
            break;
        case NONE:
            colourStr = ANSI_BLACK;
            break;
        case GLAZED:
            colourStr = ANSI_CYAN;
            break;
        }
        return colourStr + str + ANSI_RESET;
    }
}
