package Entities;

import java.util.List;

import Interfaces.Equivalent;

import Interfaces.LogicBlock;
import Interfaces.Swappable;
import Logic.Board;

public abstract class Entity implements Equivalent, Swappable, LogicBlock{
	/* Attributes */
	protected Colour colour;
	protected int posRow;
	protected int posColumn;
	

	

	/* Methods */
	public Colour getColour() {
		return colour;
	}

	public int getRow() {
		return posRow;
	}
	

	public int getColumn() {
		return posColumn;
	}
	
	public void changePosition(int newRow, int newColumn) {
		posRow = newRow;
		posColumn = newColumn;
		
	}
	
	/*private void uploadRepresetnativePictures(String path_img) {
		imagenes_representativas = new String [2];
		imagenes_representativas[0] = path_img + color +".png";
		imagenes_representativas[1] = path_img + color +"-resaltado.png";
	}*/
	//Hay que implementar este metodo, sirve para poner las imagenes a las entidades
	public abstract List<Equivalent> getDestroyables(Board b);
	
	protected String setStringColor(String str) {
	    final String ANSI_RESET = "\u001B[0m";
	    final String ANSI_RED = "\u001B[31m";
	    final String ANSI_YELLOW = "\u001B[33m";
	    final String ANSI_GREEN = "\u001B[32m";
	    final String ANSI_PURPLE = "\u001B[35m";
	    final String ANSI_BLUE = "\u001B[34m";
	    final String ANSI_BLACK = "\u001B[30m";
	    final String ANSI_CYAN = "\u001B[36m";
	    final String ANSI_WHITE = "\u001B[37m";
	    
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
