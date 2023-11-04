package Combinations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

import Enums.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;
import Logic.Block;
import Logic.Board;

public abstract class BaseCombination implements CombinationLogic {

    protected static final int MAX_WRAPPED_COMBINATION_SIZE = 6;
    protected static final int STRIPPED_COMBINATION_SIZE = 4;
    protected static final int MIN_COMBINATION_SIZE = 3;

    protected Board board;

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
        if (candy != null) candys.add(candy);

        combinationsOut.addAll(combination);

        for (Block b : combination) {
            Set<Block> currentCombinations = new HashSet<Block>();
            candy = checkBlockHorizontalVerticalCombination(b, currentCombinations);
            if (candy != null)
                candys.add(candy);
            combinationsOut.addAll(currentCombinations);
        }

        if (combinationsOut.size() < MAX_NUMER_CANDYS_FOR_CREATION) {
            // Get maximum priority.
            candy = candys.size() > 0 ? candys.get(0) : null;
            
            for (PriorityEntity pe : candys) {
                if (pe.getPriority() > candy.getPriority())
                    candy = pe;
            }
        } else candy = null;

        return candy != null ? candy.getEntity() : null;
    }

    protected PriorityEntity checkBlockHorizontalVerticalCombination(Block block, Set<Block> combinationsOut) {
        final Colour color = board.getBlockColour(block);

        final Set<Block> consecutiveH = consecutiveHorizontal(block);
        final Set<Block> consecutiveV = consecutiveVertical(block);

        final int hSize = consecutiveH.size();
        final int vSize = consecutiveV.size();
        final int combinationSize = hSize + vSize + 1; // + 1 is the checked block. 

        PriorityEntity entity = null;
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

        if (combinationSize >= MIN_COMBINATION_SIZE) {
            combinationsOut.add(block);
            combinationsOut.addAll(consecutiveH);
            combinationsOut.addAll(consecutiveV);
        }

        return entity;
    }

    /**
     * Checks the horizontal combinations an element specified with row and column
     * makes
     * 
     * @param row valid {@code row} values are ({@code row >= 0}) &&
     * ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) &&
     * ({@code column < }{@link Board#COLUMNS}}
     * @param combination blocks that make combinations
     * @return amount of horizontal combinations
     */
    protected Set<Block> consecutiveVertical(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        if (!Board.hasMovableEntity(block))
            return blocks;
        boolean cumple = true;
        for (int r = row + 1; Board.isValidBlockPosition(r, column) && cumple; r++) {
            Block current = board.getBlock(r, column);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        cumple = true;
        for (int r = row - 1; r >= 0 && r < Board.getRows() && cumple; r--) {
            Block current = board.getBlock(r, column);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        if (blocks.size() < 2)
            blocks.clear();
        return blocks;
    }

    /**
     * Checks the vertical combinations an element specified with row and column
     * makes
     * 
     * @param row valid {@code row} values are ({@code row >= 0}) &&
     * ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) &&
     * ({@code column < }{@link Board#COLUMNS}}
     * @param combination blocks that make combinations
     * @return amount of vertical combinations
     */
    protected Set<Block> consecutiveHorizontal(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        if (!Board.hasMovableEntity(block))
            return blocks;
        boolean cumple = true;
        for (int c = column + 1; Board.isValidBlockPosition(row, c) && cumple; c++) {
            Block current = board.getBlock(row, c);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        cumple = true;
        for (int c = column - 1; Board.isValidBlockPosition(row, c) && cumple; c--) {
            Block current = board.getBlock(row, c);
            cumple = board.getBlockColour(block) == board.getBlockColour(current);
            if (cumple)
                blocks.add(current);
        }
        if (blocks.size() < 2)
            blocks.clear();
        return blocks;
    }
}