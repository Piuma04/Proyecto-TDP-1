package Entities;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

public class Stripped extends Entity {
	protected boolean isHorizontal;

	public Stripped(int rowPosition, int columnPosition, Colour colour, boolean isHorizontal) {
		super(rowPosition, columnPosition, colour);
		this.isHorizontal = isHorizontal;
		String[] p = imagePath.split("/");
		imagePath = p[0] + "/" + (isHorizontal ? "H" : "V") + p[1];
		// imagePath = (isHorizontal ? "H" : "V") + imagePath;
	}

	public Stripped(int rowPosition, int columnPosition, Colour colour) {
		this(rowPosition, columnPosition, colour, false);
	}

	@Override
	public boolean isEquivalent(Equivalent e) {
		return e.isEqual(this);
	}

	// @Override public boolean equals(Candy c) { return false; }
	
	
	@Override
	public boolean isEqual(Stripped s) {
		return s.getColour() == colour;
	}

	@Override
	public boolean isSwappable(Swappable e) {
		return e.canReceive(this);
	}

	@Override
	public boolean canReceive(Candy c) {
		return true;
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
	public List<Block> getDestroyables(Board b) {
		List<Block> toDestroy = new LinkedList<Block>();
		visited = true;
		toDestroy.add(b.getBlock(row, column));

		if (isHorizontal)
			for (int c = 0; c < Board.getColumns(); c++) {
				if (!(c == column) && !b.getBlock(row, c).getEntity().isVisited())
					toDestroy.addAll(b.getBlock(row, c).getEntity().getDestroyables(b));
			}
		else
			for (int r = 0; r < Board.getRows(); r++) {
				if (!(r == row) && !b.getBlock(r, column).getEntity().isVisited())
					toDestroy.addAll(b.getBlock(r, column).getEntity().getDestroyables(b));
			}
		return toDestroy;
	}

	@Override
	public boolean isBooster() {
		return true;
	}

	public String toString() {
		return super.setStringColor((isHorizontal ? "H" : "V"));
	}
}
