package Combinations;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;
import Enums.Colour;
import Logic.Block;
import Logic.Board;

public class OnlyTsCombinations extends BaseCombination {

	public OnlyTsCombinations(Board b) {super(b); }
	protected PriorityEntity checkBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
	        final Colour color = board.getBlockColour(block);
	        PriorityEntity entity = null;
	        //Set<Block> tComb = getT(block);
	        Set<Block> lComb = getL(block);
	        Set<Block> xComb = getX(block);
	        //combinationsOut.addAll(tComb);
	        combinationsOut.addAll(lComb);
	        combinationsOut.addAll(xComb);
	        if(!lComb.isEmpty() || !xComb.isEmpty())
	        {
	        	entity = new PriorityEntity(new Wrapped(block.getRow(),block.getColumn(),color),2);
	        }
	        System.out.println(combinationsOut);
	        return entity;
	    }
	@Override
	public Set<Block> checkRemainingCombinations(Map<Integer, List<Block>> emptyColumnBlocks, List<Entity> candysOut) {
		// TODO Auto-generated method stub
		return null;
	}
}
