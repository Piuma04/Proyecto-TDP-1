package Logic;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.LinkedList;
import java.util.HashSet;

import Entities.Colour;
import Entities.Entity;
import Entities.Stripped;
import Entities.Wrapped;

public class Combination {

	private Board board;
	public Combination(Board b)
	{
		board = b;
	}
	
	 /**
     * checks the combinations of the {@code columns} specified
     * @param columns {@code columns} to be checked
     * @return blocks that make combinations on the {@code columns} specified
     */
    public List<Entity> checkRemainingCombinations(Map<Integer, List<Block>> emptyColumnBlocks, Set<Block> combinations) 
    {   
/*        List<Entity> powerCandys = new LinkedList<Entity>();
        Entity powerCandy = null;
        for (Integer j : columns) {
            for (int i = 0; i < Board.getRows(); i++) {
                if (!combinations.contains(board.getBlock(i, j)))
                {
                    powerCandy = checkCombinations(i, j, combinations);
                    if (powerCandy != null)
                        powerCandys.add(powerCandy);
                }
            }
        }*/
        List<Entity> powerCandys = new LinkedList<Entity>();
        Entity powerCandy = null;
        for (int col = 0; col < Board.getColumns(); col++) {
            List<Block> oldEmptyBlocks = emptyColumnBlocks.get(col);
            if (oldEmptyBlocks.size() > 0) {
                Block lower = oldEmptyBlocks.get(0);
                int row = lower.getRow()+2 > Board.getRows()-1 ? Board.getRows()-1 : lower.getRow() + 2;
                for (; row >= 0; row--) {
                    powerCandy = checkCombinations(row, col, combinations);
                    if (powerCandy != null)
                        powerCandys.add(powerCandy);
                }
            }
        }
        return powerCandys;
    }

    /**
     * Checks the combinations an element specified with {@code row} and {@code column} makes with the surrounding elements
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @return blocks that contain the elements that combined
     */
    public Entity checkCombinations(int row, int column, Set<Block> remaining) 
    {
    	Set<Block> combination = new HashSet<Block>();
        Set<Block> consecutiveH = new HashSet<Block>();
        Set<Block> consecutiveV = new HashSet<Block>();
        Colour color = board.getBlock(row, column).getEntity().getColour();
        consecutiveH = consecutiveH(row, column);
        consecutiveV = consecutiveV(row, column);
        combination.add(board.getBlock(row, column));
        combination.addAll(consecutiveV);
        combination.addAll(consecutiveH);
        int hSize = consecutiveH.size();
        int vSize = consecutiveV.size();
        
        Entity entity = null; 
        if ((hSize + vSize < 6) && (hSize + vSize > 3) && (hSize >= 2) && (vSize >= 2))
            entity = new Wrapped(row, column, color);
        else if (hSize == 3) 
           entity = new Stripped(row, column, color, false);
        else if (vSize == 3) 
            entity = new Stripped(row, column, color, true);
        if(combination.size()<3)
            combination.clear();
        remaining.addAll(combination);
        return entity;
    }
    /**
     * Checks the horizontal combinations an element specified with row and column makes
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @param combination blocks that make combinations
     * @return amount of horizontal combinations
     */
    private Set<Block> consecutiveV(int row, int column) 
    {
    	Set<Block> blocks = new HashSet<Block>();
    	Block comparable = board.getBlock(row, column);
        
        if (!Board.hasMovableEntity(comparable))
            return blocks;

        boolean cumple = true;
        for (int r = row + 1; Board.isValidBlock(r, column) && cumple; r++) 
        {
            Block current = board.getBlock(r, column);
            cumple = board.compareColors(comparable, current);
            if (cumple)
                blocks.add(current);
        }
        cumple = true;
        for (int r = row - 1; r >= 0 && r < Board.getRows() && cumple; r--) 
        {
            Block current = board.getBlock(r, column);
            cumple = board.compareColors(comparable, current);
            if (cumple) 
                blocks.add(current);
        }
        if (blocks.size() < 2)
            blocks.clear();
        return blocks;
    }
    /**
     * Checks the vertical combinations an element specified with row and column makes
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @param combination blocks that make combinations
     * @return amount of vertical combinations
     */
    private Set<Block> consecutiveH(int row, int column) 
    {
        Set<Block> blocks = new HashSet<Block>();
        Block comparable = board.getBlock(row, column);

        if (!Board.hasMovableEntity(comparable))
            return blocks;
        
        boolean cumple = true;
        for (int c = column + 1; Board.isValidBlock(row, c) && cumple; c++) 
        {
            Block current = board.getBlock(row, c);
            cumple = board.compareColors(comparable, current);
            if (cumple)
            	blocks.add(current);
        }
        cumple = true;
        for (int c = column - 1; Board.isValidBlock(row, c) && cumple; c--) 
        {
            Block current = board.getBlock(row, c);
            cumple = board.compareColors(comparable, current);
            if (cumple) 
            	blocks.add(current);
        }
        if (blocks.size() < 2)
        	blocks.clear();
        return blocks;
    }
}
