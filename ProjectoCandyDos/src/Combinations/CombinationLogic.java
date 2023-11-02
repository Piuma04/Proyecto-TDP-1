package Combinations;

import java.util.List;
import java.util.Map;
import java.util.Set;

import Entities.Entity;
import Logic.Block;

public interface CombinationLogic {

    public Set<Block> checkRemainingCombinations(Map<Integer, List<Block>> emptyColumnBlocks, List<Entity> candysOut);

    public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut);
}
