package Logic;

import java.util.Set;

import Entities.Colour;

public interface CombinationStrategy {
	public Set<Block> findCombination(Board board, int hSize, int vSize, int row, int column, Colour colour);
}
