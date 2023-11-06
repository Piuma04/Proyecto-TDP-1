package Combinations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

import Enums.Colour;

import CandyFactory.CandyFactory;
import CandyFactory.NormalCandy;

import Entities.Entity;
import Entities.PriorityEntity;

import Logic.Block;
import Logic.Board;

public abstract class BaseCombination implements CombinationLogic {

    protected static final int MAX_WRAPPED_COMBINATION_SIZE = 6;
    protected static final int STRIPPED_COMBINATION_SIZE = 4;
    protected static final int MIN_COMBINATION_SIZE = 3;

    protected Board board;
    protected CandyFactory candyFactory = new NormalCandy();

    public BaseCombination(Board b) { board = b; }

    public Set<Block> checkRemainingCombinations(Map<Integer, List<Block>> emptyColumnBlocks, List<Entity> candysOut) {
        Set<Block> unchecked = new HashSet<Block>();
        for (int col = 0; col < Board.getColumns(); col++) {
            List<Block> oldEmptyBlocks = emptyColumnBlocks.get(col);
            if (oldEmptyBlocks.size() > 0)
            {
                Block lower = oldEmptyBlocks.get(0);
                for (int row = lower.getRow(); row >= 0; row--) {
                    Block block = board.getBlock(row, col);
                    if (!block.isEmpty())
                        unchecked.add(block);
                }
            }
        }
        return checkCombinations(unchecked, candysOut);
    }
 
    public abstract Set<Block> checkCombinations(Set<Block> blocks, List<Entity> candysOut);

    protected Entity checkFullCombination(Block block, Set<Block> combinationsOut) {
        final int MAX_NUMER_CANDYS_FOR_CREATION = 7;
        List<PriorityEntity> candys = new LinkedList<PriorityEntity>();
        PriorityEntity candy = null;

        Set<Block> combination = new HashSet<Block>();
        candy = checkBlockHorizontalVerticalCombination(block, combination);
        if (candy != null)
            candys.add(candy);

        Set<Block> currentCombinations = new HashSet<Block>();
        for (Block b : combination) {
            candy = checkBlockHorizontalVerticalCombination(b, currentCombinations);
            if (candy != null)
                candys.add(candy);
        }

        combination.addAll(currentCombinations);
        combinationsOut.addAll(combination);

        if (combination.size() < MAX_NUMER_CANDYS_FOR_CREATION) {
            candy = getMaximumPriority(candys);
        } else candy = null;
        return candy != null ? candy.getEntity() : null;
    }

    protected PriorityEntity checkBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
        final Set<Block> consecutiveH = consecutiveHorizontal(block);
        final Set<Block> consecutiveV = consecutiveVertical(block);

        final int hSize = consecutiveH.size();
        final int vSize = consecutiveV.size(); 
        final int combinationSize = hSize + vSize + 1;  // + 1 is the checked block.

        PriorityEntity entity = null;

        if (combinationSize >= MIN_COMBINATION_SIZE) {
            entity = checkSpecialCreation(block, hSize, vSize);
            combinationsOut.add(block);
            combinationsOut.addAll(consecutiveH);
            combinationsOut.addAll(consecutiveV);
        }

        return entity;
    }

    /**
     * Biased to Horizontal Combinations.
     */
    protected PriorityEntity checkStraightBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
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
                entity = new PriorityEntity(candyFactory.createHorizontalStripped(row, column, color), 1);
        }
        else if (vSize >= MIN_COMBINATION_SIZE) {
            combinationsOut.add(block);
            combinationsOut.addAll(consecutiveV);
            if (vSize == STRIPPED_COMBINATION_SIZE)
                entity = new PriorityEntity(candyFactory.createHorizontalStripped(row, column, color), 1);
        }
        return entity;
    }

    protected Set<Block> consecutiveVertical(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        if (!Board.hasMovableEntity(block))
            return blocks;
        Set<Block> verticalDown = consecutiveVerticalDown(block);
        Set<Block> verticalUp = consecutiveVerticalUp(block);
        final int verticalSize = verticalDown.size() + verticalUp.size() + 1;  // + 1 is the checked block.
        if (verticalSize >= MIN_COMBINATION_SIZE) {
            blocks.addAll(verticalDown);
            blocks.addAll(verticalUp);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveVerticalUp(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int r = row - 1; r >= 0 && r < Board.getRows() && cumple; r--) {
            Block current = board.getBlock(r, column);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveVerticalDown(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int r = row + 1; Board.isValidBlockPosition(r, column) && cumple; r++) {
            Block current = board.getBlock(r, column);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }


    protected Set<Block> consecutiveHorizontal(Block block) {
        
        Set<Block> blocks = new HashSet<Block>();
        if (!Board.hasMovableEntity(block))
            return blocks;
        Set<Block> horizontalLeft = consecutiveHorizontalLeft(block);
        Set<Block> horizontalRight = consecutiveHorizontalRight(block);
        final int horizontalSize = horizontalLeft.size() + horizontalRight.size() + 1; // + 1 is the checked block.
        if (horizontalSize >= MIN_COMBINATION_SIZE) {
            blocks.addAll(horizontalLeft);
            blocks.addAll(horizontalRight);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveHorizontalLeft(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int c = column - 1; Board.isValidBlockPosition(row, c) && cumple; c--) {
            Block current = board.getBlock(row, c);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }
    
    protected Set<Block> consecutiveHorizontalRight(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        boolean cumple = true;
        for (int c = column + 1; Board.isValidBlockPosition(row, c) && cumple; c++) {
            Block current = board.getBlock(row, c);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        return blocks;
    }


    protected PriorityEntity checkSpecialCreation(Block block, int hSize, int vSize) {
        final Colour color = board.getBlockColour(block);
        final int row = block.getRow();
        final int column = block.getColumn();

        final int combinationSize = hSize + vSize + 1;  // + 1 is the checked block.

        final boolean createWrapped = (combinationSize <= MAX_WRAPPED_COMBINATION_SIZE) &&
                (hSize + 1 >= MIN_COMBINATION_SIZE) &&
                (vSize + 1 >= MIN_COMBINATION_SIZE);
        final boolean createStrippedVertical   = hSize+1 == STRIPPED_COMBINATION_SIZE;
        final boolean createStrippedHorizontal = vSize+1 == STRIPPED_COMBINATION_SIZE;

        PriorityEntity entity = null;
        if (createWrapped)
            entity = new PriorityEntity(candyFactory.createWrapped(row, column, color), 2);
        else if (createStrippedVertical)
            entity = new PriorityEntity(candyFactory.createVerticalStripped(row, column, color), 1);
        else if (createStrippedHorizontal)
            entity = new PriorityEntity(candyFactory.createHorizontalStripped(row, column, color), 1);
        return entity;
    }

    protected PriorityEntity getMaximumPriority(List<PriorityEntity> candys) {
        PriorityEntity candy = candys.size() > 0 ? candys.get(0) : null;
        for (PriorityEntity pe : candys)
            if (pe.getPriority() > candy.getPriority())
                candy = pe;
        return candy;
    }
}