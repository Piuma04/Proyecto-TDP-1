package Logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entities.Colour;
import Entities.Entity;
import Entities.PriorityEntity;

public abstract class CombinationStrategy {
	protected Board board;

	public CombinationStrategy(Board board) { this.board = board; }

	protected Set<Block> consecutiveV(Block block) {
		Set<Block> blocks = new HashSet<Block>();
		int row = block.getRow();
		int column = block.getColumn();
		if (!Board.hasMovableEntity(block))
			return blocks;
		boolean cumple = true;
		for (int r = row + 1; Board.isValidBlockPosition(r, column) && cumple; r++) {
			Block current = board.getBlock(r, column);
			cumple = board.getBlockColour(block) == board.getBlockColour(current);
			if (cumple)
				blocks.add(current);
		}
		cumple = true;
		for (int r = row - 1; r >= 0 && r < Board.getRows() && cumple; r--) {
			Block current = board.getBlock(r, column);
			cumple = board.getBlockColour(block) == board.getBlockColour(current);
			if (cumple)
				blocks.add(current);
		}
		if (blocks.size() < 2)
			blocks.clear();
		return blocks;
	}

	protected Set<Block> consecutiveH(Block block) {
		Set<Block> blocks = new HashSet<Block>();
		int row = block.getRow();
		int column = block.getColumn();
		if (!Board.hasMovableEntity(block))
			return blocks;
		boolean cumple = true;
		for (int c = column + 1; Board.isValidBlockPosition(row, c) && cumple; c++) {
			Block current = board.getBlock(row, c);
			cumple = board.getBlockColour(block) == board.getBlockColour(current);
			if (cumple)
				blocks.add(current);
		}
		cumple = true;
		for (int c = column - 1; Board.isValidBlockPosition(row, c) && cumple; c--) {
			Block current = board.getBlock(row, c);
			cumple = board.getBlockColour(block) == board.getBlockColour(current);
			if (cumple)
				blocks.add(current);
		}
		if (blocks.size() < 2)
			blocks.clear();
		return blocks;
	}

	public abstract Set<Block> findCombination(Block block);

	public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> blocksOut){
		 Set<Block> combinations = new HashSet<Block>();
		    for (Block block : blocks) {
		        if (!combinations.contains(block)) {
		            Set<Block> combination = findCombination(block);
		            if (!combination.isEmpty()) {
		                // No se agrega entidad (candy) a la lista de candysOut
		                blocksOut.addAll(combination);
		                combinations.addAll(combination);
		            }
		        }
		    }
		    return combinations;
	}
	
	protected abstract PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut);
}
