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
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

}
