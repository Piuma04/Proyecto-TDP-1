package Entities;

import java.util.List;

import Logic.Board;

public class Candy extends Entity {

	/* Constructor */
	public Candy(int posRow, int posColumn, Colour colour) {
		this.posRow = posRow;
		this.posColumn = posColumn;
		this.colour = colour;
	}

	/* Methods */
	//TODO
	public boolean isEquivalent(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean equals(Candy c) {
		return c.getColour() == this.colour;
	}

	public boolean equals(Glazed g) {
		return false;
	}

	// TODO
	public boolean equals(Wrapped w) {
		return w.getColour() == this.colour;
	}

	// TODO
	public boolean equals(Stripped s) {
		return s.getColour() == this.colour;
	}

	public boolean equals(Jelly j) {
		return false;
	}

	// TODO GUI
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	// TODO SWAPPABLE
	public boolean isSwappable(Entity e) {
		return true;
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
		// TODO Auto-generated method stub
		return null;
	}

}
