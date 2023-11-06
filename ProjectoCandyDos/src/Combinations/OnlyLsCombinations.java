package Combinations;

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

public class OnlyLsCombinations extends BaseCombination{

	public OnlyLsCombinations(Board b) {super(b);}

	//TODO Implement the class correctly
	
	@Override
	protected PriorityEntity checkBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
        final Colour color = board.getBlockColour(block);
        Set<Block> consecutiveH = consecutiveHorizontal(block);
        Set<Block> consecutiveV = consecutiveVertical(block);
        final int hSize = consecutiveH.size() + 1; // block to check added.
        final int vSize = consecutiveV.size() + 1; // block to check added.
        final int row = block.getRow();
        final int column = block.getColumn();
        PriorityEntity entity = null;
        Set<Block> lComb = getL(block);
        combinationsOut.addAll(lComb);
        if(!lComb.isEmpty())
        {
        	entity = new PriorityEntity(new Wrapped(block.getRow(),block.getColumn(),color),2);
        }
        if (hSize >= MIN_COMBINATION_SIZE) {
            combinationsOut.add(block);
            combinationsOut.addAll(consecutiveH);
            if (hSize == STRIPPED_COMBINATION_SIZE)
                entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
        }
        else if (vSize >= MIN_COMBINATION_SIZE) {
            combinationsOut.add(block);
            combinationsOut.addAll(consecutiveV);
            if (vSize == STRIPPED_COMBINATION_SIZE)
                entity = new PriorityEntity(new Stripped(row, column, color, true), 1);
        }
        return entity;
    }
}
