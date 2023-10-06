package Entities;

import java.util.LinkedList;
import java.util.List;

import GUI.GraphicalEntity;
import Logic.Block;
import Logic.Board;

/**
 * Represents an empty entity.
 */
public class Empty extends Entity {

	public Empty(int rowPosition, int columnPosition) {
		super(rowPosition, columnPosition, Colour.NONE);
	}

	public Empty() {
		this(0, 0);
	}

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
	public void destroy() { }
	
	@Override
	// TODO
	public List<Block> getDestroyables(Board b) {
		return new LinkedList<Block>(); // TODO seria una lista vacia
	}

	public String toString() {
		return setStringColor("*");
	}

}
