package Entities;

public abstract class Entity implements Equivalent, LogicEntity, Swappable {
	/* Attributes */
	protected Colour colour;
	protected int posRow;
	protected int posColumn;

	/* Methods */
	public Colour getColour() {
		return colour;
	}

	public boolean isEmpty() {
		return false; // solamente lo redefiniria Empty
	}

	/*
	 * public abstract List<Block> getDestroyables(Board b){
	 * 
	 * }
	 */
}
