package Logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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
    public List<Entity> checkRemainingCombinations(Set<Integer> columns, Set<Block> combinations) 
    {   
        List<Entity> powerCandys = new LinkedList<Entity>();
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
        Entity entity = null; 
        if (consecutiveH.size() == 2 && consecutiveV.size() == 2) 
            entity = new Wrapped(row, column, color);
        else if (consecutiveH.size() == 3) 
           entity = new Stripped(row, column, color, false);
        else if (consecutiveV.size() == 3) 
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
    private Set<Block> consecutiveH(int row, int column) 
    {
    	Set<Block> toAdd = new HashSet<Block>();
        Entity comparable = board.getBlock(row, column).getEntity();
        boolean cumple = true;
        for (int i = row + 1; i >= 0 && i < Board.getRows() && cumple; i++) 
        {
            cumple = board.getBlock(i, column).getEntity().getColour() == comparable.getColour();
            if (cumple)
                toAdd.add(board.getBlock(i, column));
        }
        cumple = true;
        for (int i = row - 1; i >= 0 && i < Board.getRows() && cumple; i--) 
        {
            cumple = board.getBlock(i, column).getEntity().getColour() == comparable.getColour();
            if (cumple) 
            {
                toAdd.add(board.getBlock(i, column));
            }
        }
        if (toAdd.size() < 2)
            toAdd.clear();
        return toAdd;
    }
    /**
     * Checks the vertical combinations an element specified with row and column makes
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @param combination blocks that make combinations
     * @return amount of vertical combinations
     */
    private Set<Block> consecutiveV(int row, int column) 
    {
        Set<Block> blocks = new HashSet<Block>();
        Entity comparable = board.getBlock(row, column).getEntity();
        boolean cumple = true;
        for (int j = column + 1; j >= 0 && j < Board.getColumns() && cumple; j++) 
        {
            cumple = board.getBlock(row, j).getEntity().getColour() == comparable.getColour();
            if (cumple)
            	blocks.add(board.getBlock(row, j));
        }
        cumple = true;
        for (int j = column - 1; j >= 0 && j < Board.getColumns() && cumple; j--) 
        {
            cumple = board.getBlock(row, j).getEntity().getColour() == comparable.getColour();
            if (cumple) 
            	blocks.add(board.getBlock(row, j));
        }
        if (blocks.size() < 2)
        	blocks.clear();
        return blocks;
    }
}
