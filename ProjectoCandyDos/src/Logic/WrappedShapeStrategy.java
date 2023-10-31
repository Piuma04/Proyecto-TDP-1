package Logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entities.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;

public class WrappedShapeStrategy extends CombinationStrategy {
	public WrappedShapeStrategy(Board board) {
		super(board);
	}

	@Override
	public Set<Block> findCombination(Block block) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
		// TODO Auto-generated method stub
		return null;
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
		if ((hSize + vSize < 6) && (hSize + vSize > 3) && (hSize >= 2) && (vSize >= 2))
			entity = new PriorityEntity(new Wrapped(row, column, color), 2);
		return entity;
	}

}
