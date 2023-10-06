package Entities;

import java.util.List;

import Interfaces.Equivalent;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;
import Logic.VisualEntityDummy;

public abstract class Entity extends VisualEntityDummy implements Equivalent, Swappable {

    protected static final int picSize = 70;
    protected Colour colour;
    
    Entity(int rowPosition, int columnPosition, Colour colour) {
        row = rowPosition;
        column = columnPosition;
        this.colour = colour;
        imagePath = colour.toString() + ".png";
    }
    
    public Colour getColour() { return colour; }

    public abstract List<Block> getDestroyables(Board b);
    
    public void destroy() {
        imagePath = "explosion.gif";
        gEntity.notifyChangeState();
        imagePath = null;
        gEntity.notifyChangeState();
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
        //final String ANSI_WHITE = "\u001B[37m";

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
