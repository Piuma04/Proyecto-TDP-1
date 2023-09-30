package Entities;

public abstract class Entity implements Equivalent, LogicEntity, Focusable, Swappable{
	/* Attributes */
	private Colour colour;
	private int posRow;
	private int posColumn;
	
	/* Constructor */
	public Entity(int posRow, int posColumn, Colour colour) {
		this.posRow = posRow;
		this.posColumn = posColumn;
		this.colour = colour;
	}
	
	/*Methods*/
	public Colour getColour() {
		return colour;
	}
	
	public boolean isEmpty() {
		return false; //solamente lo redefiniria Empty
	}
	
	/* public abstract List<Block> getDestroyables(Board b){
		
	}*/
}
