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
	public abstract List<Block> getDestroyables(Board b);
}
