package Entities;

import java.util.List;

import Entities.Interfaces.Equivalent;
import Entities.Interfaces.LogicEntity;
import Entities.Interfaces.Swappable;
import Logic.Board;

public abstract class Entity implements Equivalent, LogicEntity, Swappable {
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

	public boolean isEmpty() {
		return false; //=> solamente lo redefiniria Empty
	}
	
	public abstract List<Block> getDestroyables(Board b);
}
