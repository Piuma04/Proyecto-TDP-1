package Combinations;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;
import Enums.Colour;
import Logic.Block;
import Logic.Board;

public class StraightAndT extends BaseCombination {

    public StraightAndT(Board b) { super(b); }

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
        
        Set<Block> tCombinations = new HashSet<Block>();
        for (Block b : combination) {
            candy = checkTForm(b, tCombinations);
            if (candy != null)
                candys.add(candy);
        }

        // if T combinations available, use that. else use simple straight line combination.
        if (tCombinations.size() > 0)
            combination = tCombinations;
        combinationsOut.addAll(combination);

        if (combination.size() < MAX_NUMER_CANDYS_FOR_CREATION) {
            // Get maximum priority.
            candy = candys.size() > 0 ? candys.get(0) : null;
            
            for (PriorityEntity pe : candys) {
                if (pe.getPriority() > candy.getPriority())
                    candy = pe;
            }
        } else candy = null;

        return candy != null ? candy.getEntity() : null;
    }

    protected PriorityEntity checkTForm(Block block, Set<Block> combinationsOut) {
        final Colour color = board.getBlockColour(block);

        Set<Block> hLeft = consecutiveHorizontalLeft(block);
        Set<Block> hRight = consecutiveHorizontalRight(block);
        Set<Block> vDown = consecutiveVerticalDown(block);
        Set<Block> vUp = consecutiveVerticalUp(block);

        int hLeftSize =  hLeft.size();
        int hRightSize = hRight.size();
        int vDownSize =  vDown.size();
        int vUpSize =    vUp.size();

        int hSize = hLeftSize + hRightSize;
        int vSize = vDownSize + vUpSize;

        final boolean plus = vDownSize >= 1 && vUpSize >= 1 && hLeftSize >= 1 && hRightSize >= 1;

        if (plus) {
            // REMOVE SMALLER SIDE.
            if (isSmaller(vUpSize, hLeftSize, hRightSize, vDownSize)) {
                vUp.clear();
                vUpSize = 0;
                
            } else if (isSmaller(hLeftSize, vUpSize, hRightSize, vDownSize)) {
                hLeft.clear();
                hLeftSize = 0;
            } else if (isSmaller(hRightSize, hLeftSize, vUpSize, vDownSize)) {
                hRight.clear();
                hRightSize = 0;
                
            } else if (isSmaller(vDownSize, hLeftSize, hRightSize, vUpSize)) {
                vDown.clear();
                vDownSize = 0;
            } else {
                if (vSize < hSize) {

                    if (hLeftSize < hRightSize) {
                        hLeft.clear();
                        hLeftSize = 0;
                    } else {
                        hRight.clear();
                        hRightSize = 0;
                    }

                } else if (hSize < vSize) {

                    if (vDownSize < vUpSize) {
                        vDown.clear();
                        vDownSize = 0;
                    } else {
                        vUp.clear();
                        vUpSize = 0;
                    }

                } else { // all sides equal
                    vUp.clear();
                    vUpSize = 0;
                }
            }
        }

        hSize = hLeftSize + hRightSize;
        vSize = vDownSize + vUpSize;

        final int combinationSize = hSize + vSize + 1; // + 1 is the checked block.

        final boolean horizontalCombination = hSize + 1 >= MIN_COMBINATION_SIZE;
        final boolean verticalCombination = vSize + 1 >= MIN_COMBINATION_SIZE;
        final boolean tHorizontal = hLeftSize >= 1 && hRightSize >= 1;
        final boolean tVertical = vDownSize >= 1 && vUpSize >= 1;
        final boolean isT = tHorizontal || tVertical;

        //boolean tHorizontal = (hLeftSize >= 1 && hRightSize >= 1 && vSize >= 1);// CAN HAVE T WITHOUT MIN_COMBINATION_SIZE
        //boolean tVertical = (vDownSize >= 1 && vUpSize >= 1 && hSize >= 1);

        PriorityEntity entity = null;
        if (isT && horizontalCombination && verticalCombination) {
            final int row = block.getRow();
            final int column = block.getColumn();

            final boolean createWrapped =
                    (combinationSize <= MAX_WRAPPED_COMBINATION_SIZE) &&
                    (hSize + 1 >= MIN_COMBINATION_SIZE) &&
                    (vSize + 1 >= MIN_COMBINATION_SIZE);
            final boolean createStrippedVertical   = hSize+1 == STRIPPED_COMBINATION_SIZE;
            final boolean createStrippedHorizontal = vSize+1 == STRIPPED_COMBINATION_SIZE;
    
            if (createWrapped)
                entity = new PriorityEntity(new Wrapped(row, column, color), 2);
            else if (createStrippedVertical)
                entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
            else if (createStrippedHorizontal)
                entity = new PriorityEntity(new Stripped(row, column, color, true), 1);

            combinationsOut.add(block);
            combinationsOut.addAll(hLeft);
            combinationsOut.addAll(hRight);
            combinationsOut.addAll(vDown);
            combinationsOut.addAll(vUp);
        }
        return entity;
    }

    private boolean isSmaller(final int smallerThan, final int x1, final int x2, final int x3) {
        return (smallerThan < x1) && (smallerThan < x2) && (smallerThan < x3);
    }
}
