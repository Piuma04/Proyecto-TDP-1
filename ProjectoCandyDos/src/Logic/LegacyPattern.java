package Logic;

import java.util.HashSet;
import java.util.Set;
import Entities.PriorityEntity;

public class LegacyPattern extends Combination{

	public LegacyPattern(Board b) {
		super(b);
		// TODO Auto-generated constructor stub
	}
	
	protected PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut) {
        Set<Block> combination = new HashSet<Block>();
        Set<Block> consecutiveH = new HashSet<Block>();
        Set<Block> consecutiveV = new HashSet<Block>();
        consecutiveH = consecutiveH(block);
        consecutiveV = consecutiveV(block);
        combination.add(block);
        combination.addAll(consecutiveV);
        combination.addAll(consecutiveH);
        PriorityEntity entity = null;
        if (combination.size() >= 3)
            combinationsOut.addAll(combination);
        return entity;
    }
	

	
}