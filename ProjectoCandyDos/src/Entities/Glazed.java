package Entities;

public class Glazed extends Entity {

	/* Constructor */
	public Glazed(int posRow, int posColumn, Colour colour) {
		this.posRow = posRow;
		this.posColumn = posColumn;
		this.colour = colour;
	}

	/* Methods */
	@Override
	public boolean isEquivalent(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Candy c) {
		return false;
	}

	@Override
	public boolean equals(Glazed g) {
		return true;
	}

	public boolean equals(Wrapped w) {
		return false;
	}

	public boolean equals(Stripped s) {
		return false;
	}

	public boolean equals(Jelly j) {
		return false;
	}
	
	public int getRow() {
		return this.posRow;
	}

	@Override
	public int getColumn() {
		return this.posColumn;
	}

	@Override
	//TODO GUI
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSwappable(Entity e) {
		return false;
	}

	@Override
	public boolean canReceive(Candy c) {
		return false;
	}

	@Override
	public boolean canReceive(Glazed g) {
		return false;
	}

	@Override
	public boolean canReceive(Stripped s) {
		return false;
	}

	@Override
	public boolean canReceive(Wrapped w) {
		return false;
	}

}
