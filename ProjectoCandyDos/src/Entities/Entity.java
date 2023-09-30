package Entities;

import java.util.List;

import Interfaces.Equivalent;
import Interfaces.LogicEntity;
import Interfaces.Swappable;
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
	
	public abstract List<Block> getDestroyables(Board b);
}
