package Combinations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Enums.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;

import Logic.Block;
import Logic.Board;

public class Straight extends BaseCombination {

    public Straight(Board b) { super(b); }

    @Override
    public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
        Set<Block> combinations = new HashSet<Block>();
        PriorityEntity candy = null;
        for (Block block : blocks) {
            if (!combinations.contains(block)) {
                candy = checkStraightBlockHorizontalVerticalCombination(block, combinations);
                if (candy != null)
                    candysOut.add(candy.getEntity());
            }
        }
        return combinations;
    }
}