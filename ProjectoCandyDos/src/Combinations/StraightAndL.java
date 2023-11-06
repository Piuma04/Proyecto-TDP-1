package Combinations;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Entities.Entity;
import Entities.PriorityEntity;
import Logic.Block;
import Logic.Board;

public class StraightAndL extends BaseCombination {

    public StraightAndL(Board b) { super(b); }

    @Override
    public Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut) {
        Set<Block> combinations = new HashSet<Block>();
        Entity candy = null;
        Set<Block> intersection = null;
        Set<Block> current = new HashSet<Block>();
        for (Block block : blocks) {
            if (!combinations.contains(block)) {
                candy = checkFullCombination(block, current);

                // 3 Entities fall, you check middle which destroys top and right.
                // then you check left entity, problem because middle already formed combination.
                // this intersection checks that. If any entity in my current
                // combination was already destroyed, skip.
                intersection = new HashSet<Block>(current);
                intersection.retainAll(combinations);
                if (intersection.isEmpty()) {
                    combinations.addAll(current);
                    if (candy != null)
                        candysOut.add(candy);
                }
                current.clear();
            }
        }
        return combinations;
    }

    protected Entity checkFullCombination(Block block, Set<Block> combinationsOut) {
        final int MAX_NUMER_CANDYS_FOR_CREATION = 7;
        List<PriorityEntity> candys = new LinkedList<PriorityEntity>();
        PriorityEntity candy = null;

        Set<Block> combination = new HashSet<Block>();
        candy = checkStraightBlockHorizontalVerticalCombination(block, combination);
        if (candy != null)
            candys.add(candy);
        
        Set<Block> LCombinations = new HashSet<Block>();
        for (Block b : combination) {
            candy = checkLForm(b, LCombinations);
            if (candy != null)
                candys.add(candy);
        }

        // if T combinations available, use that. else use simple straight line combination.
        if (LCombinations.size() > 0)
            combination = LCombinations;

        combinationsOut.addAll(combination);

        if (combination.size() < MAX_NUMER_CANDYS_FOR_CREATION) {
            candy = getMaximumPriority(candys);
        } else candy = null;
        return candy != null ? candy.getEntity() : null;
    }

    protected PriorityEntity checkLForm(Block block, Set<Block> combinationsOut) {
        Set<Block> hLeft = consecutiveHorizontalLeft(block);
        Set<Block> hRight = consecutiveHorizontalRight(block);
        Set<Block> vDown = consecutiveVerticalDown(block);
        Set<Block> vUp = consecutiveVerticalUp(block);

        int hLeftSize =  hLeft.size();
        int hRightSize = hRight.size();
        int vDownSize =  vDown.size();
        int vUpSize =    vUp.size();

        if (hLeftSize > hRightSize) {
            hRight.clear();
            hRightSize = 0;
        } else if (hLeftSize > hRightSize) {
            hLeft.clear();
            hLeftSize = 0;
        } else { // hLeftSize == hRightSize.
            // BIASED.
            hLeft.clear();
            hLeftSize = 0;
        }

        if (vUpSize > vDownSize) {
            vDown.clear();
            vDownSize = 0;
        } else if (vUpSize < vDownSize) {
            vUp.clear();
            vUpSize = 0;
        } else { // vUpSize == vDownSize.
            // BIASED.
            vUp.clear();
            vUpSize = 0;
        }

        final int hSize = hLeftSize + hRightSize;
        final int vSize = vDownSize + vUpSize;

        final boolean horizontalCombination = (hSize + 1 >= MIN_COMBINATION_SIZE);
        final boolean verticalCombination = (vSize + 1 >= MIN_COMBINATION_SIZE);
        final boolean LHorizontal = hLeftSize == 0 || hRightSize == 0;
        final boolean LVertical =   vDownSize == 0 || vUpSize == 0;
        final boolean isL = LHorizontal || LVertical;

        PriorityEntity entity = null;
        if ( isL && horizontalCombination && verticalCombination) {
            entity = checkSpecialCreation(block, hSize, vSize);

            combinationsOut.add(block);
            combinationsOut.addAll(hLeft);
            combinationsOut.addAll(hRight);
            combinationsOut.addAll(vDown);
            combinationsOut.addAll(vUp);
        }
        return entity;
    }
}
