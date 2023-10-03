package Entities;

import java.util.List;
import java.util.LinkedList;
import Interfaces.Equivalent;
import Logic.Block;
import Logic.Board;

public class Glazed extends Entity {

	public Glazed(int posRow, int posColumn) {
		this.posRow = posRow;
		this.posColumn = posColumn;
		this.colour = Colour.GLAZED;
	}

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
		return false;
	}

	@Override
	// TODO GUI
	public String getImage() {
		// TODO Auto-generated method stub
		return "glazed.png";
	}

	@Override
	// TODO No seria siempre false??
	public boolean isSwappable(Entity e) {
		return e.canReceive(this);
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
	// TODO
	public List<Block> getDestroyables(Board b) {
		List<Block> toDestroy = new LinkedList<Block>();
		toDestroy.add(b.getBlock(posRow, posColumn));
		return toDestroy;
	}

    public String toString() { return setStringColor("G"); }
}
