package Combinations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entities.Entity;
import Logic.Block;
import Logic.Board;

public class Classic extends BaseCombination {

    public Classic(Board b) { super(b); }

    @Override
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
}
