package Entities;

import java.util.List;

import GUI.GraphicalEntity;
import Interfaces.Equivalent;

import Interfaces.LogicEntity;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

public abstract class Entity implements Equivalent, Swappable, LogicEntity {

	protected static final int picSize = 70;
    protected Colour colour;
    protected int row;
    protected int column;
    protected boolean destroyed;

    private GraphicalEntity gEntity;

    public Colour getColour() { return colour; }
    public int getRow() { return row; }
    public int getColumn() { return column; }
    
    Entity(int rowPosition, int columnPosition, Colour colour) {
        destroyed = false;
        row = rowPosition;
        column = columnPosition;
        this.colour = colour;
    }
    
    public void changePosition(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
        gEntity.notifyChangePosition();
    }

    public void setGraphicEntity(GraphicalEntity gEntity) { this.gEntity = gEntity; }

    /*
     * private void uploadRepresetnativePictures(String path_img) {
     * imagenes_representativas = new String [2]; imagenes_representativas[0] =
     * path_img + color +".png"; imagenes_representativas[1] = path_img + color
     * +"-resaltado.png"; }
     */
    // Hay que implementar este metodo, sirve para poner las imagenes a las
    // entidades
    public abstract List<Block> getDestroyables(Board b);
    
    public void destroy() {
        destroyed = true;
        if (gEntity != null) {
            //gEntity.unattach();
            gEntity.notifyChangeStatus();
        }
    }
    
    public String getImage() {
        return destroyed ? null : (this.colour.toString() + ".png");
    }
    public int getPicSize() {
    	return picSize;
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
