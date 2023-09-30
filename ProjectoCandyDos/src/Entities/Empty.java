package Entities;

import java.util.LinkedList;
import java.util.List;

import Logic.Board;

public class Empty extends Entity {
	/* Constructor */
	public Empty(int posRow, int posColumn) {
		this.posRow = posRow;
		this.posColumn = posColumn;
		colour = Colour.TRANSPARENT;
	}

	public Empty() {
		this(0, 0); //TODO verificar
	}

	/* Methods */
	@Override
	public boolean isEquivalent(Entity e) {
		return e.equals(this);
		// TODO seria siempre falso?
	}

	@Override
	public boolean equals(Candy c) {
		return false;
	}

	@Override
	public boolean equals(Glazed g) {
		return false;
	}

	@Override
	public boolean equals(Wrapped w) {
		return false;
	}

	@Override
	public boolean equals(Stripped s) {
		return false;
	}

	@Override
	public boolean equals(Jelly j) {
		return false;
	}

	@Override
	public boolean equals(Empty e) {
		return true;
	}

	@Override
	// TODO GUI
	public String getImage() {
		return null;
	}

	@Override
	// TODO seria siempre falso?
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

	public boolean isEmpty() {
		return true;
	}

	@Override
	// TODO
	public List<Block> getDestroyables(Board b) {
		return new LinkedList<Block>(); //TODO seria una lista vacia
	}

}
