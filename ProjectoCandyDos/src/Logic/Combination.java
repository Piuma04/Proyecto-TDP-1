package Logic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

import Entities.Entity;
import Entities.PriorityEntity;

public class Combination {
	private Board board;
	private List<CombinationStrategy> combinationStrategies = new LinkedList<CombinationStrategy>();

	public Combination(Board b) {
		board = b;
		addCombination(new Match3PlusStrategy(b));
		addCombination(new WrappedShapeStrategy(b));
		//TODO falta parsear el archivo para solucionar el addCombination
		// TODO solo funciona si el match3 esta primero
	}	

	public void addCombination(CombinationStrategy cs) {
		combinationStrategies.add(cs);
	}

	public void deleteCombination(CombinationStrategy cs) {
		combinationStrategies.remove(cs);
	}

	/*
	 * public List<Set<Block>> findCombinations(Board board, Block block){
	 * List<Set<Block>> allCombinations = new LinkedList(); int hSize =
	 * consecutiveH(block).size(); int vSize = consecutiveV(block).size(); int row =
	 * block.getRow(); int column = block.getColumn(); Colour colour =
	 * block.getEntity().getColour();
	 * 
	 * for(CombinationStrategy cs: combinationStrategies)
	 * allCombinations.add(cs.findCombination(block)); return allCombinations; }
	 */

	public Set<Block> checkRemainingCombinations(Map<Integer, List<Block>> emptyColumnBlocks, List<Entity> candysOut) {
		Set<Block> unchecked = new HashSet<Block>();
		for (int col = 0; col < Board.getColumns(); col++) {
			List<Block> oldEmptyBlocks = emptyColumnBlocks.get(col);
			if (oldEmptyBlocks.size() > 0) {
				Block lower = oldEmptyBlocks.get(0);
				for (int row = lower.getRow(); row >= 0; row--) {
					Block block = board.getBlock(row, col);
					unchecked.add(block);
				}
			}
		}
		return checkCombinations(unchecked, candysOut);
	}

	public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
		Set<Block> combinations = new HashSet<Block>();
		Entity candy = null;
		for (Block block : blocks) {
			if (!combinations.contains(block)) {
				candy = checkFullCombination(block, combinations);
				if (candy != null)
					candysOut.add(candy);
			}
		}
		return combinations;
	}

	private Entity checkFullCombination(Block block, Set<Block> combinationsOut) {
		List<PriorityEntity> candys = new LinkedList<PriorityEntity>();
		PriorityEntity candy = null;

		Set<Block> combination = new HashSet<Block>();
		candy = checkBlockCombination(block, combination);
		if (candy != null)
			candys.add(candy);

		combinationsOut.addAll(combination);

		for (Block b : combination) {
			Set<Block> currentCombinations = new HashSet<Block>();
			candy = checkBlockCombination(b, currentCombinations);
			if (candy != null)
				candys.add(candy);
			combinationsOut.addAll(currentCombinations);
		}
		// Get maximum priority.
		candy = candys.size() > 0 ? candys.get(0) : null;

		for (PriorityEntity pe : candys) {
			if (pe.getPriority() > candy.getPriority())
				candy = pe;
		}

		return candy != null ? candy.getEntity() : null;
	}

	private PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut) {
        PriorityEntity toReturn = null;
        for(CombinationStrategy cs: combinationStrategies) 
        	toReturn = (toReturn == null) ? cs.checkBlockCombination(block, combinationsOut) : toReturn;
        return toReturn;
    }
}