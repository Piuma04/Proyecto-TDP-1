package Entities;

public class Wrapped extends Entity{
	
	/* Constructor */
	public Wrapped(int posRow, int posColumn, Colour colour) {
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Glazed g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Wrapped w) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Stripped s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean equals(Jelly j) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRow() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumn() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSwappable(Entity e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive(Candy c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive(Glazed g) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive(Stripped s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canReceive(Wrapped w) {
		// TODO Auto-generated method stub
		return false;
	}

}
