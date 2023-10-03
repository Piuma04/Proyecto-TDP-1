package Entities;

import java.util.List;
import java.util.LinkedList;

import Interfaces.Equivalent;
import Logic.Block;
import Logic.Board;

public class Candy extends Entity {

    public Candy(int rowPosition, int columnPosition, Colour colour) {
        super(rowPosition, columnPosition, colour);
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
		toDestroy.add(b.getBlock(row, column));
		return toDestroy;
	}

    public String toString() { return setStringColor("C"); }
}
