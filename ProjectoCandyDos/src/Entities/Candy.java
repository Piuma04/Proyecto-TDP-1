package Entities;

import java.util.List;
import java.util.LinkedList;

import Interfaces.Equivalent;
import Logic.Block;
import Logic.Board;

public class Candy extends Entity {

    
	/* Constructor */
	public Candy(int posRow, int posColumn, Colour colour) {
		this.posRow = posRow;
		this.posColumn = posColumn;
		this.colour = colour;
	}

	/* Methods */
	@Override
	public boolean isEquivalent(Entity e) {
		return e.equals(this);
	}

	@Override
	public boolean equals(Candy c) {
		return c.getColour() == this.colour;
	}

	@Override
	public boolean equals(Glazed g) {
		return false;
	}

	@Override
	public boolean equals(Wrapped w) {
		return w.getColour() == this.colour;
	}

	@Override
	public boolean equals(Stripped s) {
		return s.getColour() == this.colour;
	}

	@Override
	public boolean equals(Jelly j) {
		return false;
	}

	@Override
	public boolean equals(Empty e) {
		return false;
	}

	// TODO GUI
	public String getImage() {
		// TODO Auto-generated method stub
	    String imageName = null;
	    switch (colour) {
	    case RED:
	        imageName = "redCaramel.png";
	        break;
	    case YELLOW:
            imageName = "yellowCaramel.png";
            break;
	    case GREEN:
            imageName = "greenCaramel.png";
            break;
	    case PURPLE:
            imageName = "purpleCaramel.png";
            break;
	    case BLUE:
            imageName = "blueCaramel.png";
            break;
        default:
            imageName = null; // TODO ??? exception?
            break;
	    }
		return imageName;
	}

	public boolean isSwappable(Entity e) {
		return e.canReceive(this);
	}

	public boolean canReceive(Candy c) {
		// TODO faltaria ver para las combinaciones?
		return true;
	}

	public boolean canReceive(Glazed g) {
		return false;
	}

	public boolean canReceive(Stripped s) {
		// TODO faltaria ver para las combinaciones?
		return true;
	}

	public boolean canReceive(Wrapped w) {
		// TODO faltaria ver para las combinaciones?
		return true;
	}

	@Override
	//TODO
	public List<Block> getDestroyables(Board b) {
		List<Block> toDestroy = new LinkedList<Block>();
		toDestroy.add(b.getBlock(posRow, posColumn));
		return toDestroy;
	}

    public String toString() { return setStringColor("C"); }
}
