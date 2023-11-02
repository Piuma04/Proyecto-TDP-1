package Combinations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entities.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;

import Logic.Block;
import Logic.Board;

public class OnlyLinealCombinationsPattern extends BaseCombination {

    public OnlyLinealCombinationsPattern(Board b) { super(b); }

    @Override
    public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
        Set<Block> combinations = new HashSet<Block>();
        PriorityEntity candy = null;
        for (Block block : blocks) {
            if (!combinations.contains(block)) {
                candy = checkBlockHorizontalVerticalCombination(block, combinations);
                if (candy != null)
                    candysOut.add(candy.getEntity());
            }
        }
        return combinations;
    }

    @Override
    protected PriorityEntity checkBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
        final Colour color = board.getBlockColour(block);

        final Set<Block> consecutiveH = consecutiveHorizontal(block);
        final Set<Block> consecutiveV = consecutiveVertical(block);

        final int hSize = consecutiveH.size() + 1; // block to check added.
        final int vSize = consecutiveV.size() + 1; // block to check added.

        PriorityEntity entity = null;
        final int row = block.getRow();
        final int column = block.getColumn();

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