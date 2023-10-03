package Entities;

import java.util.List;
import java.util.LinkedList;
import Interfaces.Equivalent;
import Logic.Block;
import Logic.Board;

public class Wrapped extends Entity {

	/* Constructor */
	public Wrapped(int rowPosition, int columnPosition, Colour colour) {
		super(rowPosition, columnPosition, colour);
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
		return false;
	}

	@Override
	public boolean equals(Wrapped w) {
		return w.getColour() == colour;
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
	public String getImage() {
		return "W" + super.getImage();
	}

	@Override
	public boolean isSwappable(Entity e) {
		return e.canReceive(this);
	}

	@Override
	public boolean canReceive(Candy c) {
		return true;
	}

	@Override
	public boolean canReceive(Glazed g) {
		return false;
	}

	@Override
	public boolean canReceive(Stripped s) {
		return true;
	}

	@Override
	public boolean canReceive(Wrapped w) {
		return true;
	}

	@Override
	// TODO
	public List<Block> getDestroyables(Board b) {
		List<Block> toDestroy = new LinkedList<Block>();
		toDestroy.add(b.getBlock(row, column));
		int[] adyacentRows = { -1, -1, -1, 0, 0, 1, 1, 1 };
		int[] adyacentColumns = { -1, 0, 1, -1, 1, -1, 0, 1 };

		for (int i = 0; i < 8; i++) {
			int newRow = row + adyacentRows[i];
			int newColumn = column + adyacentColumns[i];

			if (newRow >= 0 && newRow < b.getRows() && newColumn >= 0 && newColumn < b.getColumns()) {
				toDestroy.add(b.getBlock(newRow, newColumn));
			}
		}
		return toDestroy;
	}

	public String toString() {
		return setStringColor("W");
	}
}
