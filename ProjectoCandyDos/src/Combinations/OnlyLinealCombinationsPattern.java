package Combinations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entities.Entity;
import Entities.PriorityEntity;
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
}