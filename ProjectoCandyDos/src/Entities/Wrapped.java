package Entities;

import java.util.List;

import Interfaces.Equivalent;
import Interfaces.Swappable;

import java.util.LinkedList;
import Logic.Block;
import Logic.Board;

public class Wrapped extends Entity {
	public Wrapped(int rowPosition, int columnPosition, Colour colour) {
		super(rowPosition, columnPosition, colour);
		String p[] = imagePath.split("/");
		imagePath = p[0] + "/w" + p[1];
	}

	@Override
	public boolean isEquivalent(Equivalent e) {
		return e.isEqual(this);
	}

	@Override
	public boolean isEqual(Wrapped w) {
		return w.getColour() == colour;
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
		for (int j = column - 1; j <= column + 1; j++)
			for (int i = row - 1; i <= row + 1; i++) {
				if (i >= 0 && i < Board.getRows() && j >= 0 && j < Board.getColumns()
						&& !b.getBlock(i, j).getEntity().isVisited()) {
					toDestroy.add(b.getBlock(i, j));
				}
			}
		return toDestroy;
	}

	public String toString() {
		return super.setStringColor("W");
	}

	@Override
	public boolean bothBooster(Entity e) {
		return e.isBooster(this);
	}

	@Override
	public boolean isBooster(Stripped c) {
		return true;
	}

	@Override
	public boolean isBooster(Wrapped c) {
		return true;
	}	
}
