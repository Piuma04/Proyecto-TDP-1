package Logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entities.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;

public class Match3PlusStrategy extends CombinationStrategy {
	public Match3PlusStrategy(Board board) {
		super(board);
	}

	/*@Override
	public Set<Block> findCombination(Block block) {
		Set<Block> combination = new HashSet<>();
		combination.add(block);

		Set<Block> horizontalCombination = consecutiveH(block);
		Set<Block> verticalCombination = consecutiveV(block);

		if (horizontalCombination.size() >= 2) {
			combination.addAll(horizontalCombination);
		}

		if (verticalCombination.size() >= 2) {
			combination.addAll(verticalCombination);
		}

		return combination.size() >= 3 ? combination : new HashSet<>();
	}*/

	@Override
	public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
		Set<Block> combinations = new HashSet<>();
		for (Block block : blocks) {
			if (!combinations.contains(block)) {
				Set<Block> combination = findCombination(block);
				if (!combination.isEmpty()) {
					PriorityEntity candy = checkBlockCombination(block, combination);
					if (candy != null) {
						candysOut.add(candy);
					}
					combinations.addAll(combination);
				}
			}
		}
		return combinations;
	}
	
	protected PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut) {
		Set<Block> combination = new HashSet<Block>();
		Set<Block> consecutiveH = new HashSet<Block>();
		Set<Block> consecutiveV = new HashSet<Block>();
		Colour color = block.getEntity().getColour();
		consecutiveH = consecutiveH(block);
		consecutiveV = consecutiveV(block);
		combination.add(block);
		combination.addAll(consecutiveV);
		combination.addAll(consecutiveH);
		int hSize = consecutiveH.size();
		int vSize = consecutiveV.size();
		int row = block.getRow();
		int column = block.getColumn();
		PriorityEntity entity = null;
		if (hSize == 3 && vSize == 0)
			entity = new PriorityEntity(new Stripped(row, column, color, true), 1);
		else if (vSize == 3 && hSize == 0)
			entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
		if (combination.size() >= 3)
			combinationsOut.addAll(combination);
		return entity;
	}

}
