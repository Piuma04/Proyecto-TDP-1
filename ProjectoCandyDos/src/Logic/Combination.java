package Logic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

import Entities.Colour;
import Entities.Entity;
import Entities.PriorityEntity;
import Entities.Stripped;
import Entities.Wrapped;

public class Combination {
    private Board board;

    public Combination(Board b) { board = b; }

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

    private Entity checkFullCombination(Block block, Set<Block> combinationsOut) {
        List<PriorityEntity> candys = new LinkedList<PriorityEntity>();
        PriorityEntity candy = null;

        Set<Block> combination = new HashSet<Block>();
        candy = checkBlockCombination(block, combination);
        if (candy != null) candys.add(candy);

        combinationsOut.addAll(combination);

        for (Block b : combination) {
            Set<Block> currentCombinations = new HashSet<Block>();
            candy = checkBlockCombination(b, currentCombinations);
            if (candy != null)
                candys.add(candy);
            combinationsOut.addAll(currentCombinations);
        }
        // Get maximum priority.
        candy = candys.size() > 0 ? candys.get(0) : null;
        
        for (PriorityEntity pe : candys) {
            if (pe.getPriority() > candy.getPriority())
                candy = pe;
        }

        return candy != null ? candy.getEntity() : null;
    }

    private PriorityEntity checkBlockCombination(Block block, Set<Block> combinationsOut) {
        Set<Block> combination = new HashSet<Block>();
        Set<Block> consecutiveH = new HashSet<Block>();
        Set<Block> consecutiveV = new HashSet<Block>();
        Colour color = block.getEntity().getColour();
        consecutiveH = consecutiveH(block);
        consecutiveV = consecutiveV(block);
        combination.add(block);
        combination.addAll(consecutiveV);
        combination.addAll(consecutiveH);
        int hSize = consecutiveH.size();
        int vSize = consecutiveV.size();
        int row = block.getRow();
        int column = block.getColumn();
        PriorityEntity entity = null;
        if ((hSize + vSize < 6) && (hSize + vSize > 3) && (hSize >= 2) && (vSize >= 2))
            entity = new PriorityEntity(new Wrapped(row, column, color), 2);
        else if (hSize == 3)
            entity = new PriorityEntity(new Stripped(row, column, color, true), 1);
        else if (vSize == 3)
            entity = new PriorityEntity(new Stripped(row, column, color, false), 1);
        if (combination.size() >= 3)
            combinationsOut.addAll(combination);
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
    private Set<Block> consecutiveV(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        if (!Board.hasMovableEntity(block))
            return blocks;
        boolean cumple = true;
        for (int r = row + 1; Board.isValidBlock(r, column) && cumple; r++) {
            Block current = board.getBlock(r, column);
            cumple = board.compareColors(block, current);
            if (cumple)
                blocks.add(current);
        }
        cumple = true;
        for (int r = row - 1; r >= 0 && r < Board.getRows() && cumple; r--) {
            Block current = board.getBlock(r, column);
            cumple = board.compareColors(block, current);
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
    private Set<Block> consecutiveH(Block block) {
        Set<Block> blocks = new HashSet<Block>();
        int row = block.getRow();
        int column = block.getColumn();
        if (!Board.hasMovableEntity(block))
            return blocks;
        boolean cumple = true;
        for (int c = column + 1; Board.isValidBlock(row, c) && cumple; c++) {
            Block current = board.getBlock(row, c);
            cumple = board.compareColors(block, current);
            if (cumple)
                blocks.add(current);
        }
        cumple = true;
        for (int c = column - 1; Board.isValidBlock(row, c) && cumple; c--) {
            Block current = board.getBlock(row, c);
            cumple = board.compareColors(block, current);
            if (cumple)
                blocks.add(current);
        }
        if (blocks.size() < 2)
            blocks.clear();
        return blocks;
    }
}
