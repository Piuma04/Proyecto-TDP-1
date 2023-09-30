package Entities;

import java.util.List;

import Logic.Board;

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
		return e.equals(this);
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

	@Override
	//TODO 
	public List<Block> getDestroyables(Board b) {
		// TODO Auto-generated method stub
		return null;
	}

}
