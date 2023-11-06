package Combinations;

import java.util.List;
import java.util.Map;
import java.util.Set;

import Entities.Entity;
import Logic.Block;
import Logic.Board;

public class OnlyLsCombinations extends BaseCombination{

	public OnlyLsCombinations(Board b) {super(b);}

	//TODO Implement the class correctly
	
	@Override
	public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
		
		return null;
	}

	@Override
	public Set<Block> checkRemainingCombinations(Map<Integer, List<Block>> emptyColumnBlocks, List<Entity> candysOut) {
		// TODO Auto-generated method stub
		return null;
	}

}
