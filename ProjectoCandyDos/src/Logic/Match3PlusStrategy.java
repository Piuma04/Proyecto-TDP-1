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
	
	public PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut) {
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
